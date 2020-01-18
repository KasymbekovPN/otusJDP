package ru.otus.kasymbekovPN.zuiNotesFE.config;

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
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.FrontendMessageTransmitter;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.Registrar;
import ru.otus.kasymbekovPN.zuiNotesFE.messageSystem.MessageType;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.*;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.sendingHandler.FESocketSendingHandler;

@Configuration
@RequiredArgsConstructor
public class SocketHandlerConfig {

    private final FrontendMessageTransmitter frontendMessageTransmitter;
    private final Registrar registrar;

    private static final String SELF_PORT = "self.port";
    private static final String MS_HOST = "ms.host";
    private static final String MS_PORT = "ms.port";
    private static final String TARGET_HOST = "target.host";
    private static final String TARGET_PORT = "target.port";

    private final Client client;

    @Autowired
    @Qualifier("common")
    private JsonErrorObjectGenerator jeoGenerator;

    @Bean
    public SocketHandler socketHandler(ApplicationArguments args) throws Exception {

        CLArgsParser clArgsParser = new CLArgsParser(args);
        int selfPort = clArgsParser.extractArgAsInt(SELF_PORT);
        String msHost = clArgsParser.extractArgAsString(MS_HOST);
        int msPort = clArgsParser.extractArgAsInt(MS_PORT);
        String targetHost = clArgsParser.extractArgAsString(TARGET_HOST);
        int targetPort = clArgsParser.extractArgAsInt(TARGET_PORT);

        if (!clArgsParser.argsIsValid()){
            throw new Exception(clArgsParser.getStatus());
        }

        SocketHandlerImpl socketHandler = new SocketHandlerImpl(
                new JsonCheckerImpl(jeoGenerator),
                new FESocketSendingHandler(msHost, targetHost, msPort, selfPort, targetPort, client),
                selfPort
        );

        socketHandler.addHandler(MessageType.WRONG.getValue(), new WrongSIH());
        socketHandler.addHandler(MessageType.LOGIN.getValue(), new LoginSIH(frontendMessageTransmitter, registrar));
        socketHandler.addHandler(MessageType.USER_DATA.getValue(), new UserDataSIH(frontendMessageTransmitter, registrar));
        socketHandler.addHandler(MessageType.ADD_USER.getValue(), new AddUserSIH(frontendMessageTransmitter));
        socketHandler.addHandler(MessageType.DEL_USER.getValue(), new DelUserSIH(frontendMessageTransmitter));

        return socketHandler;
    }
}
