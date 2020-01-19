package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSJEDGFromMsClientDoesntExist;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSJEDGToMsClientDoesntExist;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.Optional;
import java.util.UUID;

public class CommonSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonSIH.class);

    private final MsClientService msClientService;
    private final SocketHandler socketHandler;

    private final JsonErrorObjectGenerator jeoGenerator;

    public CommonSIH(MsClientService msClientService, SocketHandler socketHandler, JsonErrorObjectGenerator jeoGenerator) {
        this.msClientService = msClientService;
        this.socketHandler = socketHandler;
        this.jeoGenerator = jeoGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        boolean request = jsonObject.get("request").getAsBoolean();
        logger.info("CommonSIH type : {}, request : {}, uuid : {}, data : {}", type, request, uuid, jsonObject);

        JsonObject from = jsonObject.get("from").getAsJsonObject();
        JsonObject to = jsonObject.get("to").getAsJsonObject();
        MsClientUrl fromUrl = new MsClientUrl(from.get("host").getAsString(), from.get("port").getAsInt(), from.get("entity").getAsString());
        MsClientUrl toUrl = new MsClientUrl(to.get("host").getAsString(), to.get("port").getAsInt(), to.get("entity").getAsString());

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
                errors.add(
                        jeoGenerator.generate(new MSJEDGFromMsClientDoesntExist(fromUrl.getUrl()))
                );
            }
            if (!optToMsClient.isPresent()){
                errors.add(
                        jeoGenerator.generate(new MSJEDGToMsClientDoesntExist(toUrl.getUrl()))
                );
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
