package ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.FrontendMessageTransmitter;

public class TreeDataSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(TreeDataSIH.class);

    private final FrontendMessageTransmitter frontendMessageTransmitter;

    public TreeDataSIH(FrontendMessageTransmitter frontendMessageTransmitter) {
        this.frontendMessageTransmitter = frontendMessageTransmitter;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("TreeDataSIH : {}", jsonObject);

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        JsonObject data = new JsonObject();

        frontendMessageTransmitter.handle(data.toString(), uuid, type, true);
    }
}
