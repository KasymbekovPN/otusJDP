package ru.otus.kasymbekovPN.zuiNotesFE.terminator;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
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

        JsonObject jsonObject = new JsonBuilderImpl()
                .add(
                        "header",
                        new JsonBuilderImpl()
                        .add("type", MessageType.I_AM.getValue())
                        .add("request", true)
                        .add("uuid", UUID.randomUUID().toString())
                        .get()
                )
                .add(
                        "data",
                        new JsonBuilderImpl().add("registration", false).get()
                )
                .get();

        socketHandler.send(jsonObject);
    }
}
