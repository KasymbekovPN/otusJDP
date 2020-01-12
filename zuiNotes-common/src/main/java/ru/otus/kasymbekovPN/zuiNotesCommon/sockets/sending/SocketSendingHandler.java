package ru.otus.kasymbekovPN.zuiNotesCommon.sockets.sending;

import com.google.gson.JsonObject;

/**
 * Интерфейс, служащий для реализации класса, производящего отпраку сообщений.<br><br>
 *
 * {@link #send(JsonObject)} - отправка сообщения <br>
 */
public interface SocketSendingHandler {
    void send(JsonObject jsonObject);
}
