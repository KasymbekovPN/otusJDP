package ru.otus.kasymbekovPN.zuiNotesMS.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.CLArgsParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.CommonUserRequestSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.CommonUserResponseSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.IAmRequestSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.WrongTypeSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.sendingHandler.MSSocketSendingHandler;

@Configuration
@RequiredArgsConstructor
public class SocketHandlerConfig {

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

        SocketHandlerImpl socketHandler = new SocketHandlerImpl(new JsonCheckerImpl(), new MSSocketSendingHandler(msPort), msPort);

        socketHandler.addHandler(MessageType.I_AM_REQUEST.getValue(), new IAmRequestSIH(socketHandler, messageSystem, msClientService));

        socketHandler.addHandler(MessageType.AUTH_USER_REQUEST.getValue(), new CommonUserRequestSIH(msClientService, socketHandler));
        socketHandler.addHandler(MessageType.ADD_USER_REQUEST.getValue(), new CommonUserRequestSIH(msClientService, socketHandler));
        socketHandler.addHandler(MessageType.DEL_USER_REQUEST.getValue(), new CommonUserRequestSIH(msClientService, socketHandler));

        socketHandler.addHandler(MessageType.AUTH_USER_RESPONSE.getValue(), new CommonUserResponseSIH(msClientService));
        socketHandler.addHandler(MessageType.DEL_USER_RESPONSE.getValue(), new CommonUserResponseSIH(msClientService));
        socketHandler.addHandler(MessageType.ADD_USER_RESPONSE.getValue(), new CommonUserResponseSIH(msClientService));

        socketHandler.addHandler(MessageType.WRONG_TYPE.getValue(), new WrongTypeSIH());

        msClientService.setSocketHandler(socketHandler);

        return socketHandler;
    }
}
