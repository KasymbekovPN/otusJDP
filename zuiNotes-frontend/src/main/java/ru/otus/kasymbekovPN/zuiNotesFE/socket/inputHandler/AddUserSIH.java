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
// * Обработчик входящего сообщения типа {@link MessageType#ADD_USER_RESPONSE} <br><br>
// *
// * {@link #handle(JsonObject)} - преобразует ответ в инстанс {@link OnlineUserPackage}, передает полученный инстанс в
// * соответствующий обработчик {@link #frontendMessageTransmitter}
// */
public class AddUserSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(AddUserSIH.class);

    private final FrontendMessageTransmitter frontendMessageTransmitter;

    public AddUserSIH(FrontendMessageTransmitter frontendMessageTransmitter) {
        this.frontendMessageTransmitter = frontendMessageTransmitter;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("AddUserSIH : {}", jsonObject);

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        JsonObject data = jsonObject.get("data").getAsJsonObject();

        frontendMessageTransmitter.handle(data.toString(), uuid, type, true);
    }
}
