package ru.otus.kasymbekovPN.zuiNotesCommon.sockets.sending;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;

/**
 * Интерфейс, служащий для реализации класса, производящего отпраку сообщений.<br><br>
 *
 * {@link #send(JsonObject)} - отправка сообщения <br>
 */
public interface SocketSendingHandler {
    void send(JsonObject jsonObject);
    void send(Message message);
}
