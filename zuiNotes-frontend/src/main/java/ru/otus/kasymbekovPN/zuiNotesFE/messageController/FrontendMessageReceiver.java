package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.messageSystem.MessageType;

import java.util.UUID;

///**
// * Контроллер, осуществляющий обработку сообщений из GUI.<br><br>
// *
// * {@link FrontendMessageReceiver#handleAuthUserRequest(OnlineUser)} - обработчик авторизационного
// * запроса от GUI. В систему сообщений отправляется сообщение типа - {@link MessageType#AUTH_USER_REQUEST}
// * <br>
// * <br>
// * {@link FrontendMessageReceiver#handleAddUserRequest(OnlineUser)} - обработчик запроса на
// * добавление пользователя от GUI. В систему сообщений отправляется сообщение типа - {@link MessageType#ADD_USER_REQUEST}<br><br>
// *
// * {@link FrontendMessageReceiver#handleDelUserRequest(OnlineUser)} - обработчик запроса на удаления
// * пользователя от GUI. В систему сообщений отправляется сообщение типа - {@link MessageType#DEL_USER_REQUEST}<br><br>
// */
@Controller
@RequiredArgsConstructor
public class FrontendMessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(FrontendMessageReceiver.class);

    private final RequestRegistrar requestRegistrar;
    private final SocketHandler socketHandler;

    @MessageMapping("/AUTH_USER")
    public void handleAuthUser(OnlineUser user){
        logger.info("handleAuthUser : {}", user);

        String uuid = UUID.randomUUID().toString();
        requestRegistrar.set(uuid, user.getUiId());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", MessageType.AUTH_USER.getValue());
        jsonObject.addProperty("request", true);
        jsonObject.addProperty("uuid", uuid);
        jsonObject.add("data", JsonHelper.makeData(user.getLogin(), user.getPassword()));

        socketHandler.send(jsonObject);
    }

    @MessageMapping("/ADD_USER")
    public void handleAddUser(OnlineUser user){
        logger.info("handleAddUser : {}", user);

        String uuid = UUID.randomUUID().toString();
        requestRegistrar.set(uuid, user.getUiId());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", MessageType.ADD_USER.getValue());
        jsonObject.addProperty("request", true);
        jsonObject.addProperty("uuid", uuid);
        jsonObject.add("data", JsonHelper.makeData(user.getLogin(), user.getPassword()));

        socketHandler.send(jsonObject);
    }

    @MessageMapping("/DEL_USER")
    public void handleDelUser(OnlineUser user){
        logger.info("handleDelUser : {}", user);

        String uuid = UUID.randomUUID().toString();
        requestRegistrar.set(uuid, user.getUiId());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", MessageType.DEL_USER.getValue());
        jsonObject.addProperty("request", true);
        jsonObject.addProperty("uuid", uuid);
        jsonObject.add("data", JsonHelper.makeData(user.getLogin()));

        socketHandler.send(jsonObject);
    }
}
