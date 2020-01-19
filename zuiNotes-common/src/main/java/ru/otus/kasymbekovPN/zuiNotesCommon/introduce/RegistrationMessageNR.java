package ru.otus.kasymbekovPN.zuiNotesCommon.introduce;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;

import java.util.UUID;

/**
 * Класс, реализующий уведомление системы сообщений о сущности клиента, который отправляет данного уведомление, а
 * также о его хосте и порте.
 */
public class RegistrationMessageNR implements NotifierRunner {

    private final SocketHandler socketHandler;
    private final JsonObject message;

    public RegistrationMessageNR(SocketHandler socketHandler, String type) {
        this.socketHandler = socketHandler;
        this.message = new JsonObject();
        this.message.addProperty("type", type);
        this.message.addProperty("request", true);
        this.message.addProperty("uuid", UUID.randomUUID().toString());

        JsonObject data = new JsonObject();
        data.addProperty("registration", true);
        this.message.add("data", data);
    }

    @Override
    public void run() {
        socketHandler.send(message);
    }
}

