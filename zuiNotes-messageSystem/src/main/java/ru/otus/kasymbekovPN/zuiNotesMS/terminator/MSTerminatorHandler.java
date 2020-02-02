package ru.otus.kasymbekovPN.zuiNotesMS.terminator;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.terminator.TerminatorHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.Set;
import java.util.UUID;

public class MSTerminatorHandler implements TerminatorHandler {

    private static final Logger logger = LoggerFactory.getLogger(MSTerminatorHandler.class);

    private final MsClientService msClientService;
    private final SocketHandler socketHandler;

    public MSTerminatorHandler(MsClientService msClientService, SocketHandler socketHandler) {
        this.msClientService = msClientService;
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle() {

        Set<MsClientUrl> clientUrls = msClientService.getAll();
        for (MsClientUrl clientUrl : clientUrls) {

            JsonObject jsonObject = new JsonBuilderImpl()
                    .add("type", clientUrl.getRegistrationMessageType())
                    .add("request", true)
                    .add("uuid", UUID.randomUUID().toString())
                    .add(
                            "to",
                            new JsonBuilderImpl()
                            .add("host", clientUrl.getHost())
                            .add("port", clientUrl.getPort())
                            .add("entity", clientUrl.getEntity())
                            .get()
                    )
                    .add(
                            "data",
                            new JsonBuilderImpl()
                            .add("registration", false)
                            .add("url", clientUrl.getUrl())
                            .get()
                    )
                    .get();

            socketHandler.send(jsonObject);
        }

        logger.info("Clients were notify about shutdowning");
    }
}
