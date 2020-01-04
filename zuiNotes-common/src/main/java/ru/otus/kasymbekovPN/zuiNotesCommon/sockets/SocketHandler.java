package ru.otus.kasymbekovPN.zuiNotesCommon.sockets;

import com.google.gson.JsonObject;

/**
 * Интерфейс, служащий для реализации класса-обработчика сокетов.<br><br>
 *
 * {@link SocketHandler#send(JsonObject)} - отправка json-сообщений<br>
 *
 * {@link SocketHandler#addHandler(String, SocketInputHandler)} - добавление обработчиков принятых сообщений<br>
 */
public interface SocketHandler {
    void send(JsonObject jsonObject);
    void addHandler(String name, SocketInputHandler handler);
}
