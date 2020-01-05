package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;

///**
// * Обработчик входящего сообщения типа {@link ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType#WRONG_TYPE} <br><br>
// */
public class WrongTypeSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(WrongTypeSIH.class);

    public WrongTypeSIH() {
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.warn("WrongTypeSIH : {}", jsonObject);
    }
}