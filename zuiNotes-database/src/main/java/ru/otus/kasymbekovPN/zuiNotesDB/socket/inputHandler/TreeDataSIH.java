package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;

public class TreeDataSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(TreeDataSIH.class);

    private final SocketHandler socketHandler;

    public TreeDataSIH(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("TreeDataSIH : {}", jsonObject);

        String uuid = jsonObject.get("uuid").getAsString();
        String type = jsonObject.get("type").getAsString();

        JsonObject message = new JsonObject();
        message.addProperty("type", type);
        message.addProperty("request", false);
        message.addProperty("uuid", uuid);
        message.add("data", new JsonObject());

        socketHandler.send(message);
    }
}
