package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.Notifier;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common.MessageDataCommonRegistrationResp;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;

/**
 * Обработчик входящего сообщения типа {@link ru.otus.kasymbekovPN.zuiNotesDB.messageSystem.MessageType#I_AM} <br><br>
 *
 * {@link #handle(JsonObject)} - при получении ответа на запрос останавливает периодическое
 * уведомление системы сообщений о своем запуске.
 */
@Slf4j
public class RegistrationSIH implements SocketInputHandler {
    private final Notifier notifier;

    public RegistrationSIH(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void handle(JsonObject jsonObject) {
    }

    @Override
    public void handle(Message message) {
        log.info("RegistrationSIH.handler : {}", message);

        MessageDataCommonRegistrationResp data = (MessageDataCommonRegistrationResp) message.getData();
        if (data.getRegistration()){
            notifier.stop();
        } else {
            notifier.start();
        }
    }
}
