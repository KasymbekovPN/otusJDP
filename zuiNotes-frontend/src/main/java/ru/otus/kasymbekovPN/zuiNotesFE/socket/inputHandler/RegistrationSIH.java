package ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.Notifier;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;

public class RegistrationSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSIH.class);

    private final Notifier notifier;

    public RegistrationSIH(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("RegistrationSIH : {}", jsonObject);
        boolean registration = jsonObject.get("data").getAsJsonObject().get("registration").getAsBoolean();
        if (registration){
            notifier.stop();
        } else {
            notifier.start();
        }
    }

    @Override
    public void handle(Message message) {

    }
}
