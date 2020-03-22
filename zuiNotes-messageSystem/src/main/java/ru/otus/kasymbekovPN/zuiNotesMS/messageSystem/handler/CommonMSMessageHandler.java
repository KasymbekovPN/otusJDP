package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.Serializers;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MSMessage;

public class CommonMSMessageHandler implements MSMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonMSMessageHandler.class);

    private final SocketHandler socketHandler;

    public CommonMSMessageHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(MSMessage MSMessage) {
        logger.info("CommonMSMessageHandler type : {} message : {}", MSMessage.getType(), MSMessage);

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(
                Serializers.deserialize(MSMessage.getPayload(), String.class)
        );
        socketHandler.send(jsonObject);
    }

    @Override
    public MSMessageHandler deepCopy() {
        return new CommonMSMessageHandler(socketHandler);
    }
}
