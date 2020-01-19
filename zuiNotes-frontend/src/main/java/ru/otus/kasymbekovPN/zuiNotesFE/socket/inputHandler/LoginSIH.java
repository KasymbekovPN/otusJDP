package ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.FrontendMessageTransmitter;
import ru.otus.kasymbekovPN.zuiNotesFE.messageController.Registrar;

///**
// * Обработчик входящего сообщения типа {@link MessageType#AUTH_USER_RESPONSE} <br><br>
// *
// * {@link #handle(JsonObject)} - преобразует ответ в инстанс {@link OnlineUserPackage}, передает полученный инстанс в
// * соответствующий обработчик {@link #frontendMessageTransmitter}
// */
public class LoginSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginSIH.class);

    private final FrontendMessageTransmitter frontendMessageTransmitter;
    private final Registrar registrar;

    public LoginSIH(FrontendMessageTransmitter frontendMessageTransmitter, Registrar registrar) {
        this.frontendMessageTransmitter = frontendMessageTransmitter;
        this.registrar = registrar;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("LoginSIH : {}", jsonObject);

        String type = jsonObject.get("type").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        JsonObject data = jsonObject.get("data").getAsJsonObject();

        String uiId = registrar.getUIIdByRequestUUID(uuid);
        String login = data.get("login").getAsString();
        registrar.setLoginByUIId(uiId, login);

        frontendMessageTransmitter.handle(data.toString(), uuid, type, true);
    }
}
