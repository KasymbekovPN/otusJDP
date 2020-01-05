package ru.otus.kasymbekovPN.zuiNotesFE.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.CLArgsParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.FrontendMessageTransmitter;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.AddUserResponseSIH;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.AuthUserResponseSIH;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.DelUserResponseSIH;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.WrongResponseSIH;
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
        socketHandler.addHandler(MessageType.AUTH_USER_RESPONSE.getValue(), new AuthUserResponseSIH(frontendMessageTransmitter));
        socketHandler.addHandler(MessageType.ADD_USER_RESPONSE.getValue(), new AddUserResponseSIH(frontendMessageTransmitter));
        socketHandler.addHandler(MessageType.DEL_USER_RESPONSE.getValue(), new DelUserResponseSIH(frontendMessageTransmitter));
        socketHandler.addHandler(MessageType.WRONG_TYPE.getValue(), new WrongResponseSIH());

        return socketHandler;
    }
}
