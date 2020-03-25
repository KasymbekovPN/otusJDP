package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;
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

        JsonObject header = jsonObject.get("header").getAsJsonObject();
        String uuid = header.get("uuid").getAsString();
        String type = header.get("type").getAsString();

        JsonObject message = new JsonBuilderImpl()
                .add(
                        "header",
                        new JsonBuilderImpl()
                        .add("type", type)
                        .add("request", false)
                        .add("uuid", uuid)
                        .get()
                )
                .add("data", new JsonObject())
                .get();

        socketHandler.send(message);
    }

    @Override
    public void handle(Message message) {

    }
}
