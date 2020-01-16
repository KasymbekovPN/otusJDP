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

import java.util.ArrayList;
import java.util.List;

///**
// * Обработчик входящего сообщения типа {@link MessageType#DEL_USER_RESPONSE} <br><br>
// *
// * {@link #handle(JsonObject)} - преобразует ответ в инстанс {@link OnlineUserPackage}, передает полученный инстанс в
// * соответствующий обработчик {@link #frontendMessageTransmitter}
// */
public class DelUserSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(DelUserSIH.class);

    private FrontendMessageTransmitter frontendMessageTransmitter;

    public DelUserSIH(FrontendMessageTransmitter frontendMessageTransmitter) {
        this.frontendMessageTransmitter = frontendMessageTransmitter;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("DelUserSIH : {}", jsonObject);

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String status = data.get("status").getAsString();
        JsonArray jsonUsers = data.get("users").getAsJsonArray();

        List<OnlineUser> users = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement element : jsonUsers) {
            users.add(
                    gson.fromJson((JsonObject)element, OnlineUser.class)
            );
        }

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();

        OnlineUserPackage onlineUserPackage = new OnlineUserPackage();
        onlineUserPackage.setStatus(status);
        onlineUserPackage.setUsers(users);

        frontendMessageTransmitter.handle(onlineUserPackage, uuid, type);
    }
}
