package ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;

/**
 * Интерфейс, служащий для реализации класса, производящего обработки входящего сообщения <br><br>
 *
 * {@link #handle(JsonObject)} - метод, обрабатывающий входящие сообщения <br>
 */
public interface SocketInputHandler {
    void handle(JsonObject jsonObject) throws Exception;
    void handle(Message message);
}
