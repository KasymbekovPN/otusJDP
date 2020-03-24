package ru.otus.kasymbekovPN.zuiNotesCommon.sockets;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.echo.EchoClient;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;

/**
 * Интерфейс, служащий для реализации класса-обработчика сокетов.<br><br>
 *
 * {@link SocketHandler#send(JsonObject)} - отправка json-сообщений<br>
 *
 * {@link SocketHandler#addHandler(String, SocketInputHandler)} - добавление обработчиков принятых сообщений<br>
 */
public interface SocketHandler {
    void send(JsonObject jsonObject);
    void send(Message message);
    void addHandler(String name, SocketInputHandler handler);
    void subscribeEcho(String observedMessageType, boolean request, EchoClient echoClient);
    void unsubscribeEcho(String observedMessageType, boolean request, EchoClient echoClient);
}
