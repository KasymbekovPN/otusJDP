package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.Optional;
import java.util.UUID;

public class CommonSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonSIH.class);

    private final MsClientService msClientService;
    private final SocketHandler socketHandler;

    public CommonSIH(MsClientService msClientService, SocketHandler socketHandler) {
        this.msClientService = msClientService;
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        boolean request = jsonObject.get("request").getAsBoolean();
        logger.info("CommonSIH type : {}, request : {}, uuid : {}, data : {}", type, request, uuid, jsonObject);

        String fromUrl = JsonHelper.extractUrl(jsonObject.get("from").getAsJsonObject());
        String toUrl = JsonHelper.extractUrl(jsonObject.get("to").getAsJsonObject());

        Optional<MSClient> optFromMsClient = msClientService.get(fromUrl);
        Optional<MSClient> optToMsClient = msClientService.get(toUrl);

        if (optFromMsClient.isPresent() && optToMsClient.isPresent()){
            String str = jsonObject.toString();
            MSClient fromMsClient = optFromMsClient.get();
            Message message = fromMsClient.produceMessage(toUrl, str, type);
            fromMsClient.sendMessage(message);
        } else {

            JsonObject original = new JsonObject();
            original.addProperty("type", type);
            original.addProperty("request", request);
            original.addProperty("uuid", uuid);

            JsonArray errors = new JsonArray();
            if (!optFromMsClient.isPresent()){
                JsonObject err = new JsonObject();
                err.addProperty("code", 1);
                err.addProperty("client", fromUrl);
                errors.add(err);
            }
            if (!optToMsClient.isPresent()){
                JsonObject err = new JsonObject();
                err.addProperty("code", 2);
                err.addProperty("client", toUrl);
                errors.add(err);
            }

            JsonObject responseJsonObject = new JsonObject();
            responseJsonObject.addProperty("type", "WRONG");
            responseJsonObject.addProperty("request", false);
            responseJsonObject.addProperty("uuid", UUID.randomUUID().toString());
            responseJsonObject.add("original", original);
            responseJsonObject.add("errors", errors);

            socketHandler.send(responseJsonObject);
        }
    }
}