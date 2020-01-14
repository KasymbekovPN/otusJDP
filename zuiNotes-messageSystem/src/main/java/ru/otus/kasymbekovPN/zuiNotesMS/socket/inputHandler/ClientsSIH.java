package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSJEDGFieldRequestIsWrong;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ClientsSIH implements SocketInputHandler {

    private final static Logger logger = LoggerFactory.getLogger(ClientsSIH.class);

    private final SocketHandler socketHandler;
    private final MsClientService msClientService;
    private final JsonErrorObjectGenerator jeoGenerator;

    public ClientsSIH(SocketHandler socketHandler, MsClientService msClientService, JsonErrorObjectGenerator jeoGenerator) {
        this.socketHandler = socketHandler;
        this.msClientService = msClientService;
        this.jeoGenerator = jeoGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("ClientsSIH : {}", jsonObject);

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        boolean request = jsonObject.get("request").getAsBoolean();
        JsonArray jsonEntities = jsonObject.get("data").getAsJsonObject().get("entities").getAsJsonArray();
        JsonObject to = jsonObject.get("from").getAsJsonObject().deepCopy();

        JsonObject error = new JsonObject();
        JsonObject data = new JsonObject();
        if (request){
            Set<String> entities = new HashSet<>();
            for (JsonElement jsonEntity : jsonEntities) {
                entities.add(jsonEntity.getAsString());
            }

            Map<String, Set<MsClientUrl>> urls = msClientService.search(entities);
            for (String entity : urls.keySet()) {
                JsonArray jsonEntityUrls = new JsonArray();
                Set<MsClientUrl> entityMsClientUrls = urls.get(entity);
                for (MsClientUrl entityMsClientUrl : entityMsClientUrls) {
                    JsonObject object = new JsonObject();
                    object.addProperty("url", entityMsClientUrl.getUrl());
                    object.addProperty("host", entityMsClientUrl.getHost());
                    object.addProperty("port", entityMsClientUrl.getPort());

                    jsonEntityUrls.add(object);
                }

                data.add(entity, jsonEntityUrls);
            }
        } else {
            error = jeoGenerator.generate(new MSJEDGFieldRequestIsWrong());
        }

        JsonObject responseJsonObject = new JsonObject();
        if (error.size() == 0){
            responseJsonObject.addProperty("type", type);
            responseJsonObject.addProperty("request", false);
            responseJsonObject.addProperty("uuid", uuid);
            responseJsonObject.add("data", data);
            responseJsonObject.add("to", to);
        } else {
            JsonArray errors = new JsonArray();
            errors.add(error);

            responseJsonObject.addProperty("type", "WRONG");
            responseJsonObject.addProperty("request", false);
            responseJsonObject.addProperty("uuid", UUID.randomUUID().toString());
            responseJsonObject.add("original", jsonObject);
            responseJsonObject.add("errors", errors);
            responseJsonObject.add("to", to);
        }

        socketHandler.send(responseJsonObject);
    }
}
