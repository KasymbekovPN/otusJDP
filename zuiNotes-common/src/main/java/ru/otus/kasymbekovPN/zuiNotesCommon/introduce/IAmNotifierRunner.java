package ru.otus.kasymbekovPN.zuiNotesCommon.introduce;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;

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
        this.message.addProperty("type", MessageType.I_AM_REQUEST.getValue());
    }

    @Override
    public void run() {
        socketHandler.send(message);
    }
}

