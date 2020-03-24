package ru.otus.kasymbekovPN.zuiNotesDB.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.CLArgsParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.NioSocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.messageSystem.MessageType;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler.*;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.sendingHandler.NioSocketSendingHandler;

@Configuration
@RequiredArgsConstructor
public class SocketHandlerConfig {

    private static final String SELF_PORT = "self.port";
    private static final String MS_HOST = "ms.host";
    private static final String MS_PORT = "ms.port";
    private static final String TARGET_HOST = "target.host";
    private static final String TARGET_PORT = "target.port";

    private final DBServiceOnlineUser dbService;
    private final Client client;

    private final JsonErrorGenerator jeGenerator;

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

        SocketHandler socketHandler = new NioSocketHandler(
                new JsonCheckerImpl(jeGenerator),
                new NioSocketSendingHandler(msHost, targetHost, msPort, selfPort, targetPort, client),
                selfPort
        );

        //<
        System.out.println("NIOSH : " + socketHandler);

        socketHandler.addHandler(MessageType.WRONG.getValue(), new WrongSIH());
        socketHandler.addHandler(MessageType.LOGIN.getValue(), new LoginSIH(dbService, socketHandler, jeGenerator));
        socketHandler.addHandler(MessageType.USER_DATA.getValue(), new UserDataSIH(dbService, socketHandler, jeGenerator));
        socketHandler.addHandler(MessageType.ADD_USER.getValue(), new AddUserSIH(dbService, socketHandler, jeGenerator));
        socketHandler.addHandler(MessageType.DEL_USER.getValue(), new DelUserSIH(dbService, socketHandler, jeGenerator));
        socketHandler.addHandler(MessageType.TREE_DATA.getValue(), new TreeDataSIH(socketHandler));

        return socketHandler;
    }
}
