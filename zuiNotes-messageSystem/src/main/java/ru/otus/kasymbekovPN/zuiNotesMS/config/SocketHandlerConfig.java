package ru.otus.kasymbekovPN.zuiNotesMS.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.CLArgsParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.NioSocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.CmnMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.WrongMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactoryImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.Solus;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.SolusImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.CommonMSMessageHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.WrongMSMessageHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.*;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.sendingHandler.MSSocketSendingHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.sendingHandler.NioSocketSendingHandler;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SocketHandlerConfig {

    private static final String CLIENT_CONFIG = "/clientConfig.json";
    private static final String SOLUS_FIELD = "solus";
    private static final String MESSAGES_FIELD = "messages";
    private static final String MESSAGE_GROUPS = "/messageGroups.json";
    private static final String MS_PORT_ARG_NAME = "ms.port";
    private static final String COMMON_FIELD = "common";
    private static final String REGISTRATION_FIELD = "registration";
    private static final String ECHO_FIELD = "echo";
    private static final String CLIENTS_FIELD = "clients";
    private static final Set<String> CONFIG_ARRAY_NAMES = new HashSet<String>(){{
        add(COMMON_FIELD);
        add(REGISTRATION_FIELD);
        add(ECHO_FIELD);
        add(CLIENTS_FIELD);
    }};

    private final MessageSystem messageSystem;
    private final MsClientService msClientService;
    private final Client client;

    private final JsonErrorGenerator jeGenerator;

    @Bean
    public SocketHandler socketHandler(ApplicationArguments args) throws Exception {

        CLArgsParser clArgsParser = new CLArgsParser(args);
        int msPort = clArgsParser.extractArgAsInt(MS_PORT_ARG_NAME);

        if (!clArgsParser.argsIsValid()){
            throw new Exception(clArgsParser.getStatus());
        }

        JsonObject config = loadAndCheckMessageGroups();
        JsonArray commonMessages = config.get(COMMON_FIELD).getAsJsonArray();
        JsonArray registrationMessages = config.get(REGISTRATION_FIELD).getAsJsonArray();
        JsonArray echoMessages = config.get(ECHO_FIELD).getAsJsonArray();
        JsonArray clientsMessages = config.get(CLIENTS_FIELD).getAsJsonArray();

//        SocketHandlerImpl socketHandler = new SocketHandlerImpl(
//                new JsonCheckerImpl(jeGenerator),
//                new MSSocketSendingHandler(msPort, client),
//                msPort
//        );
        //<
        SocketHandler socketHandler = new NioSocketHandler(
                new JsonCheckerImpl(jeGenerator),
                new NioSocketSendingHandler(msPort, client),
                msPort
        );

        for (JsonElement commonMessage : commonMessages) {
            socketHandler.addHandler(
                    commonMessage.getAsString(),
                    new CommonSIH(msClientService, socketHandler, jeGenerator)
            );
        }

        for (JsonElement registrationMessage : registrationMessages) {
            socketHandler.addHandler(
                    registrationMessage.getAsString(),
                    new RegistrationSIH(socketHandler, messageSystem, msClientService,
                            jeGenerator, createMsClientCreatorFactory(socketHandler), createSolus())
            );
        }

        for (JsonElement echoMessage : echoMessages) {
            socketHandler.addHandler(
                    echoMessage.getAsString(),
                    new EchoSIH(socketHandler)
            );
        }

        for (JsonElement clientsMessage : clientsMessages) {
            socketHandler.addHandler(
                    clientsMessage.getAsString(),
                    new ClientsSIH(socketHandler, msClientService, jeGenerator)
            );
        }

        socketHandler.addHandler("WRONG", new WrongSIH(socketHandler));

        return socketHandler;
    }

    private MsClientCreatorFactory createMsClientCreatorFactory(SocketHandler socketHandler) throws Exception{
        JsonObject config = loadAndCheckClientConfig();

        return new MsClientCreatorFactoryImpl(
                new CmnMsClientCreator(
                        new CommonMSMessageHandler(socketHandler),
                        new WrongMSMessageHandler()
                ),
                new WrongMsClientCreator(),
                config,
                MESSAGES_FIELD
        );
    }

    private Solus createSolus() throws Exception{
        JsonObject config = loadAndCheckClientConfig();
        return new SolusImpl(config, SOLUS_FIELD);
    }

    private JsonObject loadAndCheckClientConfig() throws Exception {
        JsonObject jsonConfig = loadConfig(CLIENT_CONFIG);
        StringBuilder status = new StringBuilder();
        if (jsonConfig.size() != 0){
            for (String clientEntity : jsonConfig.keySet()) {
                JsonElement element = jsonConfig.get(clientEntity);
                if (element.isJsonObject()){
                    JsonObject object = element.getAsJsonObject();

                    if (object.has(SOLUS_FIELD)){
                        JsonElement solusElement = object.get(SOLUS_FIELD);
                        if (!solusElement.isJsonPrimitive() ||
                                !solusElement.getAsJsonPrimitive().isBoolean()){
                            status.append("| '").append(clientEntity).append("' '").append(SOLUS_FIELD)
                                    .append("' value type isn't boolean |");
                        }
                    } else {
                        status.append("| object '").append(clientEntity).append("' doesn't contain field (bool) '")
                                .append(SOLUS_FIELD).append("' |");
                    }

                    if (object.has(MESSAGES_FIELD)){
                        JsonElement messagesElement = object.get(MESSAGES_FIELD);
                        if (messagesElement.isJsonArray()){
                            JsonArray array = messagesElement.getAsJsonArray();
                            for (JsonElement arrayElement : array) {
                                if (!arrayElement.isJsonPrimitive() ||
                                        !arrayElement.getAsJsonPrimitive().isString()){

                                    status.append("| field '").append(MESSAGES_FIELD).append("' of '")
                                            .append(clientEntity).append("' contains item ")
                                            .append(arrayElement.toString()).append(" isn't string |");
                                }
                            }
                        } else {
                            status.append("| '").append(clientEntity).append("' '").append(MESSAGES_FIELD)
                                    .append("' value type isn't array |");
                        }
                    } else {
                        status.append("| object '").append(clientEntity).append("' doesn't contain field (bool) '")
                                .append(MESSAGES_FIELD).append("' |");
                    }
                } else {
                    status.append("| element '").append(clientEntity).append("' isn't object | ");
                }
            }
        } else {
            status.append("| '").append(CLIENT_CONFIG).append("' doesn't contain any one object |");
        }

        if (!status.toString().isEmpty()){
            throw new Exception(status.toString());
        }

        return jsonConfig;
    }

    private JsonObject loadAndCheckMessageGroups() throws Exception{

        JsonObject jsonConfig = loadConfig(MESSAGE_GROUPS);

        StringBuilder status = new StringBuilder();
        for (String configArrayName : CONFIG_ARRAY_NAMES) {
            if (jsonConfig.has(configArrayName)){
                JsonElement element = jsonConfig.get(configArrayName);
                if (element.isJsonArray()){
                    JsonArray array = element.getAsJsonArray();
                    for (JsonElement arrayElement : array) {
                        if (!arrayElement.isJsonPrimitive() ||
                                !arrayElement.getAsJsonPrimitive().isString()){

                            status.append(configArrayName).append(" contains element '").append(arrayElement.toString())
                                    .append("' which isn't string | ");
                        }
                    }
                } else {
                    status.append(configArrayName).append(" isn't array | ");
                }
            } else {
                status.append(MESSAGE_GROUPS).append(" doesn't contain string array ").append(configArrayName).append(" | ");
            }
        }

        if (!status.toString().isEmpty()){
            throw new Exception(status.toString());
        }

        return jsonConfig;
    }

    private JsonObject loadConfig(String fileName) throws Exception{
        JsonObject jsonConfig = new JsonObject();

        InputStream in = getClass().getResourceAsStream(fileName);
        if (in != null){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line);
            }

            jsonConfig = (JsonObject) new JsonParser().parse(String.valueOf(content));
        } else {
            throw new Exception("File '"+fileName+"' doesn't exist");
        }

        return jsonConfig;
    }
}
