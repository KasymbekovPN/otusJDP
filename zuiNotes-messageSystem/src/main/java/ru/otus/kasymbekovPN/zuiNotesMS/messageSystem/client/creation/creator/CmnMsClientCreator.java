package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.MSMessageHandler;

import java.util.Set;

public class CmnMsClientCreator implements MsClientCreator {

    private static final Logger logger = LoggerFactory.getLogger(CmnMsClientCreator.class);

    private final MSMessageHandler commonHandler;
    private final MSMessageHandler wrongHandler;

    private Set<String> validMessages;

    public CmnMsClientCreator(MSMessageHandler commonHandler, MSMessageHandler wrongHandler) {
        this.commonHandler = commonHandler;
        this.wrongHandler = wrongHandler;
    }

    @Override
    public MSClient create(MsClientUrl url, SocketHandler socketHandler, MessageSystem messageSystem) {
        logger.info("Client creation : {}", url.getUrl());

        MsClientImpl msClient = new MsClientImpl(url, messageSystem);
        msClient.addHandler("WRONG", wrongHandler.deepCopy());
        for (String validMessage : validMessages) {
            msClient.addHandler(validMessage, commonHandler.deepCopy());
        }

        return msClient;
    }

    @Override
    public void setValidMessages(Set<String> validMessages) {
        this.validMessages = validMessages;
    }
}
