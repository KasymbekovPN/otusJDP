package ru.otus.kasymbekovPN.zuiNotesFE.terminator;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.terminator.TerminatorHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.messageSystem.MessageType;

import java.util.UUID;

public class FETerminatorHandler implements TerminatorHandler {

    private static final Logger logger = LoggerFactory.getLogger(FETerminatorHandler.class);

    private final SocketHandler socketHandler;

    public FETerminatorHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle() {
        logger.info("MessageSystem-client was was notify about shutdowning");

        JsonObject data = new JsonObject();
        data.addProperty("registration", false);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", MessageType.I_AM.getValue());
        jsonObject.addProperty("request", true);
        jsonObject.addProperty("uuid", UUID.randomUUID().toString());
        jsonObject.add("data", data);

        socketHandler.send(jsonObject);
    }
}
