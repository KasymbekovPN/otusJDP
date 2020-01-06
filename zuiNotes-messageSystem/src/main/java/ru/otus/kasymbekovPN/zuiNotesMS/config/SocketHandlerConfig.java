package ru.otus.kasymbekovPN.zuiNotesMS.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.CLArgsParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.CommonUserResponseSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.RegistrationMessageSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.WrongTypeSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.sendingHandler.MSSocketSendingHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

@Configuration
@RequiredArgsConstructor
public class SocketHandlerConfig {

    private static final String MESSAGE_GROUPS = "messageGroups.json";
    private static final String MS_PORT_ARG_NAME = "ms.port";

    private final MessageSystem messageSystem;
    private final MsClientService msClientService;

    @Bean
    public SocketHandler socketHandler(ApplicationArguments args) throws Exception {

        CLArgsParser clArgsParser = new CLArgsParser(args);
        int msPort = clArgsParser.extractArgAsInt(MS_PORT_ARG_NAME);

        if (!clArgsParser.argsIsValid()){
            throw new Exception(clArgsParser.getStatus());
        }

        //<
        String status = "";
        String content = "";
        URL resource = getClass().getClassLoader().getResource(MESSAGE_GROUPS);
        if (resource != null){
            File file = new File(resource.getFile());
            try{
                content = new String(Files.readAllBytes(file.toPath()));
            } catch (IOException ex){
                status = "JsonCheckerImpl : Failed convert file ("+ MESSAGE_GROUPS +") to string";
                throw new IOException(status);
            }
        } else {
            status = "JsonCheckerImpl : File "+ MESSAGE_GROUPS + " doesn't exist";
            throw new Exception(status);
        }

        JsonObject loadedJsonObject = (JsonObject) new JsonParser().parse(content);
        JsonArray registrationMessages = loadedJsonObject.get("registrationMessages").getAsJsonArray();
        JsonArray commonMessages = loadedJsonObject.get("commonMessages").getAsJsonArray();
        //<


        SocketHandlerImpl socketHandler = new SocketHandlerImpl(new JsonCheckerImpl(), new MSSocketSendingHandler(msPort), msPort);

        for (JsonElement registrationMessage : registrationMessages) {
            socketHandler.addHandler(
                    registrationMessage.getAsString(),
                    new RegistrationMessageSIH(socketHandler, messageSystem, msClientService)
            );
        }

        for (JsonElement commonMessage : commonMessages) {
            socketHandler.addHandler(
                    commonMessage.getAsString(),
                    new CommonUserResponseSIH(msClientService)
            );
        }

        socketHandler.addHandler("WRONG", new WrongTypeSIH());

        //<
//        socketHandler.addHandler(MessageType.I_AM_REQUEST.getValue(), new IAmRequestSIH(socketHandler, messageSystem, msClientService));
//
//        socketHandler.addHandler(MessageType.AUTH_USER_REQUEST.getValue(), new CommonUserRequestSIH(msClientService, socketHandler));
//        socketHandler.addHandler(MessageType.ADD_USER_REQUEST.getValue(), new CommonUserRequestSIH(msClientService, socketHandler));
//        socketHandler.addHandler(MessageType.DEL_USER_REQUEST.getValue(), new CommonUserRequestSIH(msClientService, socketHandler));
//
//        socketHandler.addHandler(MessageType.AUTH_USER_RESPONSE.getValue(), new CommonUserResponseSIH(msClientService));
//        socketHandler.addHandler(MessageType.DEL_USER_RESPONSE.getValue(), new CommonUserResponseSIH(msClientService));
//        socketHandler.addHandler(MessageType.ADD_USER_RESPONSE.getValue(), new CommonUserResponseSIH(msClientService));
//
//        socketHandler.addHandler(MessageType.WRONG_TYPE.getValue(), new WrongTypeSIH());

        msClientService.setSocketHandler(socketHandler);

        return socketHandler;
    }
}
