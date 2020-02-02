package ru.otus.kasymbekovPN.zuiNotesCommon.introduce;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
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
        this.message = new JsonBuilderImpl()
                .add("type", type)
                .add("request", true)
                .add("uuid", UUID.randomUUID().toString())
                .add(
                        "data",
                        new JsonBuilderImpl().add("registration", true).get()
                )
                .get();
    }

    @Override
    public void run() {
        socketHandler.send(message);
    }
}

