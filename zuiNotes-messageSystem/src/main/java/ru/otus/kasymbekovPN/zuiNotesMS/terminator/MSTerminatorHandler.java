package ru.otus.kasymbekovPN.zuiNotesMS.terminator;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            JsonObject to = new JsonObject();
            to.addProperty("host", clientUrl.getHost());
            to.addProperty("port", clientUrl.getPort());
            to.addProperty("entity", clientUrl.getEntity());

            JsonObject data = new JsonObject();
            data.addProperty("registration", false);
            data.addProperty("url", clientUrl.getUrl());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", clientUrl.getRegistrationMessageType());
            jsonObject.addProperty("request", true);
            jsonObject.addProperty("uuid", UUID.randomUUID().toString());
            jsonObject.add("to", to);
            jsonObject.add("data", data);

            socketHandler.send(jsonObject);
        }

        logger.info("Clients were notify about shutdowning");
    }
}
