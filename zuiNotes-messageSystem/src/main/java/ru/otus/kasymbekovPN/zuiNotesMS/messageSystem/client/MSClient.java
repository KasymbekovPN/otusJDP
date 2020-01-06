package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client;

import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.MSMessageHandler;

//<
//    /**
//     * Интерфейс, служащий для создания клиента системы сообщений {@link ru.otus.kasymbekovPN.HW16M.messageSystem.MessageSystem} <br><br>
//     *
//     * {@link #addHandler(MessageType, MSMessageHandler)} - добавление обработчика сообщений {@link MSMessageHandler} <br><br>
//     *
//     * {@link #sendMessage(Message)} - отправка сообщения <br><br>
//     *
//     * {@link #handle(Message)} - обработка принятого сообщения <br><br>
//     *
//     * {@link #getUrl()} - геттер уникального url <br><br>
//     *
//     * {@link #produceMessage(String, Object, MessageType)} - создание сообщения <br><br>
//     */
//<
public interface MSClient {
    void addHandler(String type, MSMessageHandler handler);
    boolean sendMessage(Message message);
    void handle(Message message);
    String getUrl();
    <T> Message produceMessage(String toUrl, T data, MessageType type);
}
