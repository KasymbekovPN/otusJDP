package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;

@Slf4j
public class WrongSIH implements SocketInputHandler {

    //<
//    private static final Logger logger = LoggerFactory.getLogger(WrongSIH.class);

    public WrongSIH() {
    }

    @Override
    public void handle(JsonObject jsonObject) {
//        logger.warn("WrongSIH : {}", jsonObject);
    }

    @Override
    public void handle(Message message) {
        log.info("WrongSIH.handle message : {}", message);
    }
}
