package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.Optional;

//<
//    /**
//     * Обработчик входящего request-сообщения <br><br>
//     *
//     * {@link #handle(JsonObject)} - проверяет наличие клиентов : приемника и источника, в случае успеха отправляет сообщение
//     * в систему<br><br>.
//     */
///<
public class CommonUserResponseSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonUserResponseSIH.class);

    private final MsClientService msClientService;

    public CommonUserResponseSIH(MsClientService msClientService) {
        this.msClientService = msClientService;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        String type = jsonObject.get("type").getAsString();
        logger.info("CommonUserResponseSIH type : {}, data : {}", type, jsonObject);

        String fromUrl = JsonHelper.extractUrl(jsonObject.get("from").getAsJsonObject());
        String toUrl = JsonHelper.extractUrl(jsonObject.get("to").getAsJsonObject());

        Optional<MSClient> optFromMSClient = msClientService.get(fromUrl);
        Optional<MSClient> optToMsClient = msClientService.get(toUrl);

        if (optFromMSClient.isPresent() && optToMsClient.isPresent()){
            String str = jsonObject.toString();
            MSClient fromMsClient = optFromMSClient.get();
            Message message = fromMsClient.produceMessage(toUrl, str, MessageType.valueOf(type));
            fromMsClient.sendMessage(message);
        } else {
            logger.error("DelUserResponseSIH : client not found.");
        }
    }
}
