package ru.otus.kasymbekovPN.zuiNotesMS.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.CLArgsParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.*;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.sendingHandler.MSSocketSendingHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SocketHandlerConfig {

    private static final String MESSAGE_GROUPS = "messageGroups.json";
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

    @Autowired
    @Qualifier("common")
    private JsonErrorObjectGenerator commonJeoGenerator;

    @Autowired
    @Qualifier("ms")
    private JsonErrorObjectGenerator msJeoGenerator;

    @Bean
    public SocketHandler socketHandler(ApplicationArguments args) throws Exception {

        CLArgsParser clArgsParser = new CLArgsParser(args);
        int msPort = clArgsParser.extractArgAsInt(MS_PORT_ARG_NAME);

        if (!clArgsParser.argsIsValid()){
            throw new Exception(clArgsParser.getStatus());
        }

        JsonObject config = loadAndCheckConfig();
        JsonArray commonMessages = config.get(COMMON_FIELD).getAsJsonArray();
        JsonArray registrationMessages = config.get(REGISTRATION_FIELD).getAsJsonArray();
        JsonArray echoMessages = config.get(ECHO_FIELD).getAsJsonArray();
        JsonArray clientsMessages = config.get(CLIENTS_FIELD).getAsJsonArray();

        SocketHandlerImpl socketHandler = new SocketHandlerImpl(
                new JsonCheckerImpl(commonJeoGenerator),
                new MSSocketSendingHandler(msPort, client),
                msPort
        );

        for (JsonElement commonMessage : commonMessages) {
            socketHandler.addHandler(
                    commonMessage.getAsString(),
                    new CommonSIH(msClientService, socketHandler, msJeoGenerator)
            );
        }

        for (JsonElement registrationMessage : registrationMessages) {
            socketHandler.addHandler(
                    registrationMessage.getAsString(),
                    new RegistrationSIH(socketHandler, messageSystem, msClientService, msJeoGenerator)
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
                    new ClientsSIH(socketHandler, msClientService, msJeoGenerator)
            );
        }

        socketHandler.addHandler("WRONG", new WrongSIH(socketHandler));

        msClientService.setSocketHandler(socketHandler);

        return socketHandler;
    }

    private JsonObject loadAndCheckConfig() throws Exception {
        JsonObject jsonObject = new JsonObject();
        URL resource = getClass().getClassLoader().getResource(MESSAGE_GROUPS);
        if (resource != null){
            File file = new File(resource.getFile());
            try{
                jsonObject = (JsonObject) new JsonParser().parse(
                        new String(Files.readAllBytes(file.toPath()))
                );
            } catch (IOException ex){
                throw new IOException("JsonCheckerImpl : Failed convert file ("+ MESSAGE_GROUPS +") to string");
            }
        } else {
            throw new Exception("JsonCheckerImpl : File "+ MESSAGE_GROUPS + " doesn't exist");
        }

        StringBuilder status = new StringBuilder();
        for (String configArrayName : CONFIG_ARRAY_NAMES) {
            if (jsonObject.has(configArrayName)){
                JsonElement element = jsonObject.get(configArrayName);
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

        return jsonObject;
    }
}
