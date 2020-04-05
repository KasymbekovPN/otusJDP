package ru.otus.kasymbekovPN.zuiNotesCommon.introduce;

import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.MessageImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common.MessageDataCommonRegistrationReq;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeaderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;

import java.util.UUID;

/**
 * Класс, реализующий уведомление системы сообщений о сущности клиента, который отправляет данного уведомление, а
 * также о его хосте и порте.
 */
public class RegistrationMessageNR implements NotifierRunner {

    private final SocketHandler socketHandler;
    private final Message message;

    public RegistrationMessageNR(SocketHandler socketHandler, String type) {
        this.socketHandler = socketHandler;
        this.message = new MessageImpl(
                new MessageHeaderImpl(type, true, UUID.randomUUID()),
                null,
                null,
                new MessageDataCommonRegistrationReq(true),
                null
        );
    }

    @Override
    public void run() {
        socketHandler.send(message);
    }
}

