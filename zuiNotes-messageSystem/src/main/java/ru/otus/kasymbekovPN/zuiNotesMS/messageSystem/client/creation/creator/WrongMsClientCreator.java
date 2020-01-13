package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;

import java.util.Set;

public class WrongMsClientCreator implements MsClientCreator {

    private static final Logger logger = LoggerFactory.getLogger(WrongMsClientCreator.class);

    @Override
    public MSClient create(MsClientUrl url, SocketHandler socketHandler, MessageSystem messageSystem) {
        logger.warn("The attempt client creation with wrong entity : {}", url.getUrl());
        return null;
    }

    @Override
    public void setValidMessages(Set<String> validMessages) {
    }
}
