package ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.MessageService;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.content.frontend.MessageContentFELogin;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.database.MessageDataDBLoginResp;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeader;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.FrontendMessageTransmitter;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.Registrar;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class LoginSIH implements SocketInputHandler {

    //<
//    private static final Logger logger = LoggerFactory.getLogger(LoginSIH.class);

    private final FrontendMessageTransmitter frontendMessageTransmitter;
    private final Registrar registrar;

    public LoginSIH(FrontendMessageTransmitter frontendMessageTransmitter, Registrar registrar) {
        this.frontendMessageTransmitter = frontendMessageTransmitter;
        this.registrar = registrar;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        //<
//        logger.info("LoginSIH : {}", jsonObject);
//
//        JsonObject header = jsonObject.get("header").getAsJsonObject();
//        String type = header.get("type").getAsString();
//        String uuid = header.get("uuid").getAsString();
//        JsonObject data = jsonObject.get("data").getAsJsonObject();
//
//        String uiId = registrar.getUIIdByRequestUUID(uuid);
//        String login = data.get("login").getAsString();
//        registrar.setLoginByUIId(uiId, login);
//
//        frontendMessageTransmitter.handle(data.toString(), uuid, type, true);
    }

    @Override
    public void handle(Message message) {
        log.info("LoginSIH.handle message : {}", message);

        MessageHeader header = message.getHeader();
        String type = header.getType();
        UUID uuid = header.getUUID();

        MessageDataDBLoginResp data = (MessageDataDBLoginResp) message.getData();

        MessageContentFELogin messageContent = new MessageContentFELogin(data, message.getErrors());
        Optional<String> maybeJsonMC = MessageService.getAsString(messageContent);
        if (maybeJsonMC.isPresent()){
            String uiId = registrar.getUIIdByRequestUUID(uuid.toString());
            registrar.setLoginByUIId(uiId, data.getLogin());
            frontendMessageTransmitter.handle(maybeJsonMC.get(), uuid.toString(), type, true);
        } else {
            log.error("Invalid message");
        }
    }
}
