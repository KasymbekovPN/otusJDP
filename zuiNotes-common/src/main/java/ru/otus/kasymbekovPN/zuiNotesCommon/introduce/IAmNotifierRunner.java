package ru.otus.kasymbekovPN.zuiNotesCommon.introduce;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;

import java.util.UUID;

/**
 * Класс, реализующий уведомление системы сообщений о сущности клиента, который отправляет данного уведомление, а
 * также о его хосте и порте.
 */
public class IAmNotifierRunner implements NotifierRunner {

    private final SocketHandler socketHandler;
    private final JsonObject message;

    public IAmNotifierRunner(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
        this.message = new JsonObject();

//        this.message.addProperty("type", MessageType.I_AM_REQUEST.getValue());
        //<
        //<
        // I_AM replace in config-file
        //<
        this.message.addProperty("type", "I_AM");
        this.message.addProperty("request", true);
        this.message.addProperty("uuid", UUID.randomUUID().toString());
    }

    @Override
    public void run() {
        socketHandler.send(message);
    }
}

