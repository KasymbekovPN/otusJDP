package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSJEDGMsClientHasWrongEntity;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSJEDGSolusReg;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSJEDGFieldRequestIsWrong;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSJEDGMsClientAlreadyDel;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.Solus;

import java.util.Optional;
import java.util.UUID;

public class RegistrationSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSIH.class);

    private final SocketHandler socketHandler;
    private final MessageSystem messageSystem;
    private final MsClientService msClientService;
    private final JsonErrorObjectGenerator jeoGenerator;
    private final MsClientCreatorFactory msClientCreatorFactory;
    private final Solus solus;

    public RegistrationSIH(SocketHandler socketHandler, MessageSystem messageSystem, MsClientService msClientService,
                           JsonErrorObjectGenerator jeoGenerator, MsClientCreatorFactory msClientCreatorFactory,
                           Solus solus) {
        this.socketHandler = socketHandler;
        this.messageSystem = messageSystem;
        this.msClientService = msClientService;
        this.jeoGenerator = jeoGenerator;
        this.msClientCreatorFactory = msClientCreatorFactory;
        this.solus = solus;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("RegistrationSIH : {}", jsonObject);

        JsonObject header = jsonObject.get("header").getAsJsonObject();
        String type = header.get("type").getAsString();
        String uuid = header.get("uuid").getAsString();
        boolean request = header.get("request").getAsBoolean();

        boolean registration = jsonObject.get("data").getAsJsonObject().get("registration").getAsBoolean();
        JsonObject from = jsonObject.get("from").getAsJsonObject();
        MsClientUrl url = new MsClientUrl(
                from.get("host").getAsString(),
                from.get("port").getAsInt(),
                from.get("entity").getAsString(),
                type
        );

        JsonObject error = new JsonObject();
        if (request){
            Optional<MSClient> optMsClient = msClientService.get(url);
            if (registration){
                boolean notReg = solus.register(url.getEntity());
                if (notReg){
                    final Optional<MSClient> maybeMsClient = msClientCreatorFactory.get(url.getEntity()).create(url, socketHandler, messageSystem);
                    if (maybeMsClient.isPresent()){
                        error = msClientService.addClient(url, maybeMsClient.get());
                    } else {
                        error = jeoGenerator.generate(new MSJEDGMsClientHasWrongEntity(url.getEntity()));
                    }
                } else {
                    error = jeoGenerator.generate(new MSJEDGSolusReg(url.getEntity()));
                }
            } else {
                solus.unregister(url.getEntity());
                error = optMsClient.isPresent()
                        ? msClientService.deleteClient(url)
                        : jeoGenerator.generate(new MSJEDGMsClientAlreadyDel(url.getUrl()));
            }
        } else {
            error = jeoGenerator.generate(new MSJEDGFieldRequestIsWrong());
        }

        if (registration){
            JsonObject respJsonObject;
            if (error.size() == 0){
                respJsonObject = new JsonBuilderImpl()
                        .add(
                                "header",
                                new JsonBuilderImpl()
                                .add("type", type)
                                .add("request", false)
                                .add("uuid", uuid)
                                .get()
                        )
                        .add("to", from)
                        .add(
                                "data",
                                new JsonBuilderImpl()
                                .add("url", url.getUrl())
                                .add("registration", true)
                                .get()
                        )
                        .get();
            } else {
                JsonArray errors = new JsonArray();
                errors.add(error);

                respJsonObject = new JsonBuilderImpl()
                        .add(
                                "header",
                                new JsonBuilderImpl()
                                .add("type", "WRONG")
                                .add("request", false)
                                .add("uuid", UUID.randomUUID().toString())
                                .get()
                        )
                        .add("original", jsonObject)
                        .add("errors", errors)
                        .add("to", from)
                        .get();
            }

            socketHandler.send(respJsonObject);
        }
    }
}
