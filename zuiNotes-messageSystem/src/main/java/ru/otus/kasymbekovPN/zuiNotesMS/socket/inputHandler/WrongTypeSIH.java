package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;

//<
// !!! Как он должен реазировать ?
// !!! Должен ли он отсылать ответ и есл да, то какой?
//<

//<
//    /**
//     * Обработчик невалидный сообщений.
//     */
//<
public class WrongTypeSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(WrongTypeSIH.class);

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("WrongTypeSIH : {}", jsonObject);
    }
}
