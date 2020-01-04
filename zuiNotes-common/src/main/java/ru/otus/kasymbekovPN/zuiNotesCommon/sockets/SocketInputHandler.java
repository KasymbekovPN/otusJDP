package ru.otus.kasymbekovPN.zuiNotesCommon.sockets;

import com.google.gson.JsonObject;

/**
 * Интерфейс, служащий для реализации класса, производящего обработки входящего сообщения <br><br>
 *
 * {@link #handle(JsonObject)} - метод, обрабатывающий входящие сообщения <br>
 */
public interface SocketInputHandler {
    void handle(JsonObject jsonObject);
}
