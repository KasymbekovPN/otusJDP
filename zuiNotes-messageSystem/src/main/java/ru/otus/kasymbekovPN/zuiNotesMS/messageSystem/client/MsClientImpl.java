package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.Serializers;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.MSMessageHandler;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MsClientImpl implements MSClient {

    private static final Logger logger = LoggerFactory.getLogger(MsClientImpl.class);

    private final MsClientUrl url;
    private final MessageSystem messageSystem;
    private final Map<String, MSMessageHandler> handlers = new ConcurrentHashMap<>();

    public MsClientImpl(MsClientUrl url, MessageSystem messageSystem) {
        this.url = url;
        this.messageSystem = messageSystem;
    }

    @Override
    public void addHandler(String type, MSMessageHandler handler) {
        handlers.put(type, handler);
    }

    @Override
    public boolean sendMessage(Message message) {
        boolean result = messageSystem.newMessage(message);
        if (!result){
            logger.error("Last message was reject : {}", message);
        }
        return result;
    }

    @Override
    public void handle(Message message) {
        logger.info("New message : {}", message);
        try{
            MSMessageHandler handler = handlers.get(message.getType());
            if (handler != null){
                handler.handle(message);
            } else {
                logger.error("Handler not found for the message type : {}; url : {}", message.getType(), url);
            }
        } catch(Exception ex){
            logger.error("Message : {}, {}", message, ex);
        }
    }

    @Override
    public MsClientUrl getUrl() {
        return url;
    }

    @Override
    public <T> Message produceMessage(MsClientUrl toUrl, T data, String type) {
        return new Message(url, toUrl, type, Serializers.serialize(data));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsClientImpl msClient = (MsClientImpl) o;
        return Objects.equals(url, msClient.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
