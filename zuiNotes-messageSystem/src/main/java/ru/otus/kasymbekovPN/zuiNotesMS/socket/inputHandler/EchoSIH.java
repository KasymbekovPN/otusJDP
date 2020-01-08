package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.ClientUrl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;

public class EchoSIH implements SocketInputHandler {

    private final static Logger logger = LoggerFactory.getLogger(EchoSIH.class);

    private final SocketHandler socketHandler;

    public EchoSIH(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("EchoSIH : {}", jsonObject);

        String type = jsonObject.get("type").getAsString();
        JsonObject from = jsonObject.get("from").getAsJsonObject();
        String host = from.get("host").getAsString();
        int port = from.get("port").getAsInt();
        String entity = from.get("entity").getAsString();
        ClientUrl clientUrl = new ClientUrl(host, port, entity, type);

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        boolean subscribe = data.get("subscribe").getAsBoolean();
        String message = data.get("message").getAsString();
        boolean request = data.get("request").getAsBoolean();

        if (subscribe){
            socketHandler.subscribeEcho(message, request, clientUrl);
        } else {
            socketHandler.unsubscribeEcho(message, request, clientUrl);
        }
    }
}
