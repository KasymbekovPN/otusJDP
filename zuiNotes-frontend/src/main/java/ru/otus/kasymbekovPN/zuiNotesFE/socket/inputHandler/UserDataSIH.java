package ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.FrontendMessageTransmitter;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.Registrar;

public class UserDataSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserDataSIH.class);

    private final FrontendMessageTransmitter frontendMessageTransmitter;
    private final Registrar registrar;

    public UserDataSIH(FrontendMessageTransmitter frontendMessageTransmitter, Registrar registrar) {
        this.frontendMessageTransmitter = frontendMessageTransmitter;
        this.registrar = registrar;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("UserDataSIH : {}", jsonObject);

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        JsonObject data = jsonObject.get("data").getAsJsonObject();

        frontendMessageTransmitter.handle(data.toString(), uuid, type);

        //<
//        UserDataSIH : {"type":"USER_DATA","uuid":"ec270e5a-b5a1-49b8-aae8-a6068dbffb7c","req
//            uest":false,"data":{"users":[{"id":1,"login":"admin","password":"qwerty","admin":true},{"id":2,"login":"user1","password":"user1pass","admin":false,"uiId":""},{"id":7,"login":"user6","
//            password":"user6pass","admin":false,"uiId":""},{"id":8,"login":"user7","password":"user7pass","admin":false,"uiId":""},{"id":9,"login":"user8","password":"user8pass","admin":false,"uiI
//            d":""},{"id":10,"login":"user9","password":"user9pass","admin":false,"uiId":""}],"errors":[]},"from":{"host":"192.168.0.100","port":8101,"entity":"DATABASE"},"to":{"host":"192.168.0.10
//            0","port":8081,"entity":"FRONTEND"}}
    }
}
