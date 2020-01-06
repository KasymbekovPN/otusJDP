package ru.otus.kasymbekovPN.zuiNotesDB.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.CLArgsParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler.AddUserRequestSIH;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler.AuthUserRequestSIH;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler.DelUserRequestSIH;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler.WrongSIH;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.sendingHandler.DBSocketSendingHandler;

@Configuration
@RequiredArgsConstructor
public class SocketHandlerConfig {

    private static final String SELF_PORT = "self.port";
    private static final String MS_HOST = "ms.host";
    private static final String MS_PORT = "ms.port";
    private static final String TARGET_HOST = "target.host";
    private static final String TARGET_PORT = "target.port";

    private final DBServiceOnlineUser dbService;

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
                new DBSocketSendingHandler(msHost, targetHost, msPort, selfPort, targetPort),
                selfPort
        );

        socketHandler.addHandler("WRONG", new WrongSIH());
        socketHandler.addHandler("AUTH_USER", new AuthUserRequestSIH(dbService, socketHandler));
        socketHandler.addHandler("ADD_USER", new AddUserRequestSIH(dbService, socketHandler));
        socketHandler.addHandler("DEL_USER", new DelUserRequestSIH(dbService, socketHandler));
        //<
//        socketHandler.addHandler(MessageType.WRONG_TYPE.getValue(), new WrongTypeSIH());
//        socketHandler.addHandler(MessageType.AUTH_USER_REQUEST.getValue(), new AuthUserRequestSIH(dbService, socketHandler));
//        socketHandler.addHandler(MessageType.ADD_USER_REQUEST.getValue(), new AddUserRequestSIH(dbService, socketHandler));
//        socketHandler.addHandler(MessageType.DEL_USER_REQUEST.getValue(), new DelUserRequestSIH(dbService, socketHandler));

        return socketHandler;
    }
}
