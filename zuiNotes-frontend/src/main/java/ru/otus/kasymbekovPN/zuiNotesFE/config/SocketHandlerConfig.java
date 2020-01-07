package ru.otus.kasymbekovPN.zuiNotesFE.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.CLArgsParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.FrontendMessageTransmitter;
import ru.otus.kasymbekovPN.zuiNotesFE.messageSystem.MessageType;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.AddUserSIH;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.AuthUserSIH;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.DelUserSIH;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.WrongSIH;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.sendingHandler.FESocketSendingHandler;

@Configuration
@RequiredArgsConstructor
public class SocketHandlerConfig {

    private final FrontendMessageTransmitter frontendMessageTransmitter;

    private static final String SELF_PORT = "self.port";
    private static final String MS_HOST = "ms.host";
    private static final String MS_PORT = "ms.port";
    private static final String TARGET_HOST = "target.host";
    private static final String TARGET_PORT = "target.port";

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
                new JsonCheckerImpl(),
                new FESocketSendingHandler(msHost, targetHost, msPort, selfPort, targetPort),
                selfPort
        );

        socketHandler.addHandler(MessageType.WRONG.getValue(), new WrongSIH());
        socketHandler.addHandler(MessageType.AUTH_USER.getValue(), new AuthUserSIH(frontendMessageTransmitter));
        socketHandler.addHandler(MessageType.ADD_USER.getValue(), new AddUserSIH(frontendMessageTransmitter));
        socketHandler.addHandler(MessageType.DEL_USER.getValue(), new DelUserSIH(frontendMessageTransmitter));
        //<
//        socketHandler.addHandler("WRONG", new WrongSIH());
//        socketHandler.addHandler("AUTH_USER", new AuthUserResponseSIH(frontendMessageTransmitter));
//        socketHandler.addHandler("ADD_USER", new AddUserResponseSIH(frontendMessageTransmitter));
//        socketHandler.addHandler("DEL_USER", new DelUserResponseSIH(frontendMessageTransmitter));

        //<
//        socketHandler.addHandler(MessageType.AUTH_USER_RESPONSE.getValue(), new AuthUserResponseSIH(frontendMessageTransmitter));
//        socketHandler.addHandler(MessageType.ADD_USER_RESPONSE.getValue(), new AddUserResponseSIH(frontendMessageTransmitter));
//        socketHandler.addHandler(MessageType.DEL_USER_RESPONSE.getValue(), new DelUserResponseSIH(frontendMessageTransmitter));
//        socketHandler.addHandler(MessageType.WRONG_TYPE.getValue(), new WrongResponseSIH());

        return socketHandler;
    }
}
