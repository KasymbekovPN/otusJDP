package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.Optional;
import java.util.UUID;

//    /**
//     * Обработчик сообщений, регистрирующих программы-клиенты. <br><br>
//     *
//     * {@link #handle(JsonObject)} - в обработчике создаются клиенты {@link MsClient} системы обмена сообщениями. <br><br>
//     */
public class RegistrationSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSIH.class);

    private final SocketHandler socketHandler;
    private final MessageSystem messageSystem;
    private final MsClientService msClientService;

    private final JsonErrorObjectGenerator jeoGenerator;

    public RegistrationSIH(SocketHandler socketHandler, MessageSystem messageSystem, MsClientService msClientService, JsonErrorObjectGenerator jeoGenerator) {
        this.socketHandler = socketHandler;
        this.messageSystem = messageSystem;
        this.msClientService = msClientService;
        this.jeoGenerator = jeoGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("RegistrationSIH : {}", jsonObject);

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        boolean request = jsonObject.get("request").getAsBoolean();
        boolean registration = jsonObject.get("data").getAsJsonObject().get("registration").getAsBoolean();
        JsonObject from = jsonObject.get("from").getAsJsonObject();
        MsClientUrl url = new MsClientUrl(from.get("host").getAsString(), from.get("port").getAsInt(), from.get("entity").getAsString());

        JsonObject error = new JsonObject();
        if (request){
            Optional<MSClient> optMsClient = msClientService.get(url);
            if (registration){
                error = optMsClient.isPresent()
                        ? jeoGenerator.generate(7, url.getUrl())
                        : msClientService.createClient(url, messageSystem);
            } else {
                error = optMsClient.isPresent()
                        ? msClientService.deleteClient(url)
                        : jeoGenerator.generate(9, url.getUrl());
            }
        } else {
            error = jeoGenerator.generate(8);
        }

        JsonObject respJsonObject = new JsonObject();
        if (error.size() == 0){
            JsonObject data = new JsonObject();
            data.addProperty("url", url.getUrl());
            data.addProperty("registration", registration);
            respJsonObject.addProperty("type", type);
            respJsonObject.addProperty("request", false);
            respJsonObject.addProperty("uuid", uuid);
            respJsonObject.add("data", data);
            respJsonObject.add("to", from);
        } else {
            JsonArray errors = new JsonArray();
            errors.add(error);

            respJsonObject.addProperty("type", "WRONG");
            respJsonObject.addProperty("request", false);
            respJsonObject.addProperty("uuid", UUID.randomUUID().toString());
            respJsonObject.add("original", jsonObject);
            respJsonObject.add("errors", errors);
            respJsonObject.add("to", from);
        }

        socketHandler.send(respJsonObject);
    }
}
