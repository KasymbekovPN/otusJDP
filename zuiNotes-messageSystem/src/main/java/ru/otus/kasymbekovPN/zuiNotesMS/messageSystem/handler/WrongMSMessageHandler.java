package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;

//    /**
//     * Обработчик сообщений неверного типа
//     */
public class WrongMSMessageHandler implements MSMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(WrongMSMessageHandler.class);

    public WrongMSMessageHandler() {
    }

    @Override
    public void handle(Message message) {
        logger.warn("WrongMSMessageHandler : {}", message);
    }

    @Override
    public MSMessageHandler deepCopy() {
        return new WrongMSMessageHandler();
    }
}
