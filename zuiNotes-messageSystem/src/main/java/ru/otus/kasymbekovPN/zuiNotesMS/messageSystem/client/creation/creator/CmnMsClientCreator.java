package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.CommonMSMessageHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.WrongMSMessageHandler;

public class CmnMsClientCreator implements MsClientCreator {

    private static final Logger logger = LoggerFactory.getLogger(CmnMsClientCreator.class);

    private final MessageType auth;
    private final MessageType add;
    private final MessageType del;

    public CmnMsClientCreator(MessageType auth, MessageType add, MessageType del) {
        this.auth = auth;
        this.add = add;
        this.del = del;
    }

    @Override
    public MSClient create(String url, SocketHandler socketHandler, MessageSystem messageSystem) {
        logger.info("Client creation : {}", url);

        MsClientImpl msClient = new MsClientImpl(url, messageSystem);

        msClient.addHandler(MessageType.WRONG_TYPE, new WrongMSMessageHandler());
        msClient.addHandler(auth, new CommonMSMessageHandler(socketHandler));
        msClient.addHandler(add, new CommonMSMessageHandler(socketHandler));
        msClient.addHandler(del, new CommonMSMessageHandler(socketHandler));

        return msClient;
    }
}
