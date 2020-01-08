package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.Optional;

//<
//    /**
//     * Обработчик сообщений, регистрирующих программы-клиенты. <br><br>
//     *
//     * {@link #handle(JsonObject)} - в обработчике создаются клиенты {@link MsClient} системы обмена сообщениями. <br><br>
//     */
//<

//< rename
public class RegistrationSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSIH.class);

    private final SocketHandler socketHandler;
    private final MessageSystem messageSystem;
    private final MsClientService msClientService;

    public RegistrationSIH(SocketHandler socketHandler, MessageSystem messageSystem, MsClientService msClientService) {
        this.socketHandler = socketHandler;
        this.messageSystem = messageSystem;
        this.msClientService = msClientService;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("RegistrationSIH : {}", jsonObject);

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        boolean request = jsonObject.get("request").getAsBoolean();
        JsonObject from = jsonObject.get("from").getAsJsonObject();
        String url = JsonHelper.extractUrl(from);
        String host = from.get("host").getAsString();
        String entity = from.get("entity").getAsString();
        int port = from.get("port").getAsInt();

        String status = "";
        if (request){
            Optional<MSClient> optMsClient = msClientService.get(url);
            if (optMsClient.isPresent()){
                status = "The client '" + url + "' already exists";
            } else {
                if (msClientService.createClient(host, port, entity, messageSystem)) {
                    status = "Client '" + url + "' was add.";
                } else {
                    status = "Client '" + url + "' wasn't add.";
                }
            }
        } else {
            status = "Field 'request' has invalid value (false)";
        }

        logger.info("RegistrationSIH : {}", status);

        JsonObject data = new JsonObject();
        data.addProperty("url", url);
        JsonObject respJsonObject = new JsonObject();
        respJsonObject.addProperty("type", type);
        respJsonObject.addProperty("request", false);
        respJsonObject.addProperty("uuid", uuid);
        respJsonObject.add("data", data);
        respJsonObject.add("to", from);

        socketHandler.send(respJsonObject);
    }
}
