package ru.otus.kasymbekovPN.zuiNotesCommon.sockets;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.ClientUrl;

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
    void subscribeEcho(String message, boolean request, ClientUrl echoTarget);
    void unsubscribeEcho(String message, boolean request, ClientUrl echoTarget);
}
