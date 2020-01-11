package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.Notifier;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;

import java.util.UUID;

///**
// * Обработчик входящего сообщения типа {@link MessageType#I_AM_RESPONSE} <br><br>
// *
// * {@link #handle(JsonObject)} - при получении ответа на запрос {@link MessageType#I_AM_REQUEST} останавливает периодическое
// * уведомление системы сообщений о своем запуске.
// */
public class RegistrationSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSIH.class);

    private final Notifier notifier;
    private final SocketHandler socketHandler;

    public RegistrationSIH(Notifier notifier, SocketHandler socketHandler) {
        this.notifier = notifier;
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("RegistrationSIH : {}", jsonObject);
        notifier.stop();
    }
}