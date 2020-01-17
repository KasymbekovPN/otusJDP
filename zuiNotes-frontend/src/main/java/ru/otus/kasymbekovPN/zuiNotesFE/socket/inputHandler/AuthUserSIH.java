package ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.FrontendMessageTransmitter;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.OnlineUserPackage;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.Registrar;

import java.util.ArrayList;
import java.util.List;

///**
// * Обработчик входящего сообщения типа {@link MessageType#AUTH_USER_RESPONSE} <br><br>
// *
// * {@link #handle(JsonObject)} - преобразует ответ в инстанс {@link OnlineUserPackage}, передает полученный инстанс в
// * соответствующий обработчик {@link #frontendMessageTransmitter}
// */
public class AuthUserSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthUserSIH.class);

    private final FrontendMessageTransmitter frontendMessageTransmitter;
    private final Registrar registrar;

    public AuthUserSIH(FrontendMessageTransmitter frontendMessageTransmitter, Registrar registrar) {
        this.frontendMessageTransmitter = frontendMessageTransmitter;
        this.registrar = registrar;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("AuthUserSIH : {}", jsonObject);

        //<
//        AuthUserSIH :
//        {"type":"AUTH_USER",
//        "request":false,
//        "uuid":"8c0ce888-2918-4e67-855f-3a456cb913ae",
//        "data":
//                  {"login":"admin",
//                  "group":"admin",
//                  "errors":[]
//                 },"from":{"host":"192.168.0.100","port":8101,"entity":"DATABASE"},"to":{"host":"192.168.0.100","port":8081,"entity":"FRO
//            NTEND"}}

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        JsonObject data = jsonObject.get("data").getAsJsonObject();

        frontendMessageTransmitter.handle(data.toString(), uuid, type);
        //<
//        JsonObject data = jsonObject.get("data").getAsJsonObject();
//        String status = data.get("status").getAsString();
//        JsonArray jsonUsers = data.get("users").getAsJsonArray();
//
//        List<OnlineUser> users = new ArrayList<>();
//        Gson gson = new Gson();
//        for (JsonElement element : jsonUsers) {
//            users.add(
//                    gson.fromJson((JsonObject)element, OnlineUser.class)
//            );
//        }
//
//        String type = jsonObject.get("type").getAsString();
//        String uuid = jsonObject.get("uuid").getAsString();
//
//        OnlineUserPackage onlineUserPackage = new OnlineUserPackage();
//        onlineUserPackage.setStatus(status);
//        onlineUserPackage.setUsers(users);
//
//        frontendMessageTransmitter.handle(onlineUserPackage, uuid, type);
    }
}
