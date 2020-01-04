package ru.otus.kasymbekovPN.zuiNotesCommon.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

///**
// * Перечисление типов сообщений <br><br>
// *
// * {@link MessageType#I_AM_REQUEST} - запрос от клиента ({@link entity.Entity#DATABASE} или {@link entity.Entity#FRONTEND})
// * в сервер ({@link entity.Entity#MESSAGE_SYSTEM}), уведомляющий о том, что соответвстующий клиент был запущен. <br>
// *
// * {@link MessageType#I_AM_RESPONSE} - ответ от сервера ({@link entity.Entity#MESSAGE_SYSTEM}) в клиент
// * ({@link entity.Entity#DATABASE} или {{@link entity.Entity#FRONTEND}} на запрос {@link MessageType#I_AM_REQUEST}<br>
// *
// * {@link MessageType#WRONG_TYPE} - неверный тип сообщения <br>
// *
// * {@link MessageType#AUTH_USER_REQUEST} - авторизационный запрос от {@link entity.Entity#FRONTEND} - клиента в
// * {@link entity.Entity#DATABASE} - клиент. <br>
// *
// * {@link MessageType#ADD_USER_REQUEST} - запрос на добавление нового пользователя от {@link entity.Entity#FRONTEND} - клиента
// * в {@link entity.Entity#DATABASE} - клиент. <br>
// *
// * {@link MessageType#DEL_USER_REQUEST} - запрос на удаление пользователя от {@link entity.Entity#FRONTEND} - клиента
// * в {@link entity.Entity#DATABASE} - клиент. <br>
// *
// * {@link MessageType#AUTH_USER_RESPONSE} - ответ на запрос {@link MessageType#AUTH_USER_REQUEST}<br>
// *
// * {@link MessageType#ADD_USER_RESPONSE} - ответ на запрос {@link MessageType#ADD_USER_REQUEST}<br>
// *
// * {@link MessageType#DEL_USER_RESPONSE} - ответ на запрос {@link MessageType#DEL_USER_REQUEST}<br>
// */

public enum MessageType {
    I_AM_REQUEST("I_AM_REQUEST"),
    I_AM_RESPONSE("I_AM_RESPONSE"),
    WRONG_TYPE("WRONG_TYPE"),
    AUTH_USER_REQUEST("AUTH_USER_REQUEST"),
    ADD_USER_REQUEST("ADD_USER_REQUEST"),
    DEL_USER_REQUEST("DEL_USER_REQUEST"),
    AUTH_USER_RESPONSE("AUTH_USER_RESPONSE"),
    ADD_USER_RESPONSE("ADD_USER_RESPONSE"),
    DEL_USER_RESPONSE("DEL_USER_RESPONSE");

    private static Map<MessageType, MessageType> opposites = new HashMap<>();
    static {
        Map<MessageType, MessageType> buffer = new HashMap<>();
        buffer.put(MessageType.I_AM_REQUEST, MessageType.I_AM_RESPONSE);
        buffer.put(MessageType.I_AM_RESPONSE, MessageType.I_AM_REQUEST);
        buffer.put(MessageType.WRONG_TYPE, MessageType.WRONG_TYPE);
        buffer.put(MessageType.AUTH_USER_REQUEST, MessageType.AUTH_USER_RESPONSE);
        buffer.put(MessageType.AUTH_USER_RESPONSE, MessageType.AUTH_USER_REQUEST);
        buffer.put(MessageType.ADD_USER_REQUEST, MessageType.ADD_USER_RESPONSE);
        buffer.put(MessageType.ADD_USER_RESPONSE, MessageType.ADD_USER_REQUEST);
        buffer.put(MessageType.DEL_USER_REQUEST, MessageType.DEL_USER_RESPONSE);
        buffer.put(MessageType.DEL_USER_RESPONSE, MessageType.DEL_USER_REQUEST);
        opposites = Collections.unmodifiableMap(buffer);
    }

    private String value;

    public static MessageType getOpposite(MessageType mType){
        return opposites.get(mType);
    }

    public String getValue() {
        return value;
    }

    MessageType(String value) {
        this.value = value;
    }
}

