package ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;

public class WrongSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(WrongSIH.class);

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("WrongSIH : {}", jsonObject);
    }
}
