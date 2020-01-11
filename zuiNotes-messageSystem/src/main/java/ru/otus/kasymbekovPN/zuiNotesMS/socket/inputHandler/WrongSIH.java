package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;

//    /**
//     * Обработчик невалидный сообщений.
//     */
public class WrongSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(WrongSIH.class);

    private final SocketHandler socketHandler;

    public WrongSIH(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("WrongSIH : {}", jsonObject);

        jsonObject.add("to", jsonObject.get("from").deepCopy());
        jsonObject.remove("from");

        socketHandler.send(jsonObject);

    }
}
