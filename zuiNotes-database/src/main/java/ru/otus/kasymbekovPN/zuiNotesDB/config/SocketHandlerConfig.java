package ru.otus.kasymbekovPN.zuiNotesDB.config;

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
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.messageSystem.MessageType;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler.*;
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
        int selfPort = clArgsParser.extractArgAsInt(SELF_PORT);
        String msHost = clArgsParser.extractArgAsString(MS_HOST);
        int msPort = clArgsParser.extractArgAsInt(MS_PORT);
        String targetHost = clArgsParser.extractArgAsString(TARGET_HOST);
        int targetPort = clArgsParser.extractArgAsInt(TARGET_PORT);

        if (!clArgsParser.argsIsValid()){
            throw new Exception(clArgsParser.getStatus());
        }

        SocketHandlerImpl socketHandler = new SocketHandlerImpl(
                new JsonCheckerImpl(commonJeoGenerator),
                new DBSocketSendingHandler(msHost, targetHost, msPort, selfPort, targetPort, client),
                selfPort
        );

        socketHandler.addHandler(MessageType.WRONG.getValue(), new WrongSIH());
        socketHandler.addHandler(MessageType.LOGIN.getValue(), new LoginSIH(dbService, socketHandler, msJeoGenerator));
        socketHandler.addHandler(MessageType.USER_DATA.getValue(), new UserDataSIH(dbService, socketHandler, msJeoGenerator));
        socketHandler.addHandler(MessageType.ADD_USER.getValue(), new AddUserSIH(dbService, socketHandler));
        socketHandler.addHandler(MessageType.DEL_USER.getValue(), new DelUserSIH(dbService, socketHandler));

        return socketHandler;
    }
}
