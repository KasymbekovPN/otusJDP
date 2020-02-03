package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
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

        JsonObject header = jsonObject.get("header").getAsJsonObject();
        String type = header.get("type").getAsString();
        logger.info("CommonSIH header : {}, data : {}", header, jsonObject);

        JsonObject from = jsonObject.get("from").getAsJsonObject();
        JsonObject to = jsonObject.get("to").getAsJsonObject();
        MsClientUrl fromUrl = new MsClientUrl(
                from.get("host").getAsString(),
                from.get("port").getAsInt(),
                from.get("entity").getAsString(),
                type
        );
        MsClientUrl toUrl = new MsClientUrl(
                to.get("host").getAsString(),
                to.get("port").getAsInt(),
                to.get("entity").getAsString(),
                type
        );

        Optional<MSClient> optFromMsClient = msClientService.get(fromUrl);
        Optional<MSClient> optToMsClient = msClientService.get(toUrl);

        if (optFromMsClient.isPresent() && optToMsClient.isPresent()){
            String str = jsonObject.toString();
            MSClient fromMsClient = optFromMsClient.get();
            Message message = fromMsClient.produceMessage(toUrl, str, type);
            fromMsClient.sendMessage(message);
        } else {

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

            JsonObject responseJsonObject = new JsonBuilderImpl()
                    .add(
                            "header",
                            new JsonBuilderImpl()
                            .add("type", "WRONG")
                            .add("request", false)
                            .add("uuid", UUID.randomUUID().toString())
                            .get()
                    )
                    .add("errors", errors)
                    .add(
                            "original",
                            new JsonBuilderImpl().add("header", header).get()
                    ).get();

            socketHandler.send(responseJsonObject);
        }
    }
}
