package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
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

        JsonObject header = jsonObject.get("header").getAsJsonObject();
        String type = header.get("type").getAsString();
        String uuid = header.get("uuid").getAsString();
        boolean request = header.get("request").getAsBoolean();
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
                    jsonEntityUrls.add(
                            new JsonBuilderImpl()
                            .add("url", entityMsClientUrl.getUrl())
                            .add("host", entityMsClientUrl.getHost())
                            .add("port", entityMsClientUrl.getPort())
                            .get()
                    );
                }

                data.add(entity, jsonEntityUrls);
            }
        } else {
            error = jeoGenerator.generate(new MSJEDGFieldRequestIsWrong());
        }

        JsonObject responseJsonObject;
        if (error.size() == 0){
            responseJsonObject = new JsonBuilderImpl()
                    .add(
                            "header",
                            new JsonBuilderImpl()
                            .add("type", type)
                            .add("request", false)
                            .add("uuid", uuid)
                            .get()
                    )
                    .add("data", data)
                    .add("to", to)
                    .get();

        } else {
            JsonArray errors = new JsonArray();
            errors.add(error);

            responseJsonObject = new JsonBuilderImpl()
                    .add(
                            "header",
                            new JsonBuilderImpl()
                            .add("type", type)
                            .add("request", false)
                            .add("uuid", UUID.randomUUID().toString())
                            .get()
                    )
                    .add("original", jsonObject)
                    .add("errors", errors)
                    .add("to", to)
                    .get();
        }

        socketHandler.send(responseJsonObject);
    }
}
