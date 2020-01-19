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
// * Обработчик входящего сообщения типа {@link MessageType#AUTH_USER_RESPONSE} <br><br>
// *
// * {@link #handle(JsonObject)} - преобразует ответ в инстанс {@link OnlineUserPackage}, передает полученный инстанс в
// * соответствующий обработчик {@link #frontendMessageTransmitter}
// */
public class AuthUserSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthUserSIH.class);

    private FrontendMessageTransmitter frontendMessageTransmitter;

    public AuthUserSIH(FrontendMessageTransmitter frontendMessageTransmitter) {
        this.frontendMessageTransmitter = frontendMessageTransmitter;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("AuthUserSIH : {}", jsonObject);

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

        OnlineUserPackage onlineUserPackage = new OnlineUserPackage();
        onlineUserPackage.setStatus(status);
        onlineUserPackage.setUsers(users);

        frontendMessageTransmitter.handleAuthUserResponse(onlineUserPackage);
    }
}
