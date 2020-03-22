package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MSMessage;

public class WrongMSMessageHandler implements MSMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(WrongMSMessageHandler.class);

    public WrongMSMessageHandler() {
    }

    @Override
    public void handle(MSMessage MSMessage) {
        logger.warn("WrongMSMessageHandler : {}", MSMessage);
    }

    @Override
    public MSMessageHandler deepCopy() {
        return new WrongMSMessageHandler();
    }
}
