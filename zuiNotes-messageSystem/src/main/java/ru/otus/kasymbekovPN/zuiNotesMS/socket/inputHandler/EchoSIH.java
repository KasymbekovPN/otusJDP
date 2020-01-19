package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.echo.EchoClient;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.echo.EchoClientImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;

public class EchoSIH implements SocketInputHandler {

    private final static Logger logger = LoggerFactory.getLogger(EchoSIH.class);

    private final SocketHandler socketHandler;

    public EchoSIH(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("EchoSIH : {}", jsonObject);

        String echoMessageType = jsonObject.get("type").getAsString();
        JsonObject from = jsonObject.get("from").getAsJsonObject();
        String host = from.get("host").getAsString();
        int port = from.get("port").getAsInt();
        String entity = from.get("entity").getAsString();
        EchoClient echoClient = new EchoClientImpl(host, port, entity, echoMessageType);

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        boolean subscribe = data.get("subscribe").getAsBoolean();
        String observedMessageType = data.get("message").getAsString();
        boolean request = data.get("request").getAsBoolean();

        if (subscribe){
            socketHandler.subscribeEcho(observedMessageType, request, echoClient);
        } else {
            socketHandler.unsubscribeEcho(observedMessageType, request, echoClient);
        }
    }
}
