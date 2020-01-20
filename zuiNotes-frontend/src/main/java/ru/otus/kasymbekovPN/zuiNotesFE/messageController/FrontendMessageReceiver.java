package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.json.error.data.FEJEDGInvalidLogin;
import ru.otus.kasymbekovPN.zuiNotesFE.messageSystem.MessageType;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FrontendMessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(FrontendMessageReceiver.class);

    private final Registrar registrar;
    private final SocketHandler socketHandler;
    private final FrontendMessageTransmitter frontendMessageTransmitter;

    @Autowired
    @Qualifier("ms")
    private JsonErrorObjectGenerator jeoGenerator;

    @MessageMapping("/LOGIN")
    public void handleLogin(OnlineUser user){
        logger.info("handleLogin : {}", user);

        String uuid = UUID.randomUUID().toString();
        registrar.setUIIdByRequestUUID(uuid, user.getUiId());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", MessageType.LOGIN.getValue());
        jsonObject.addProperty("request", true);
        jsonObject.addProperty("uuid", uuid);
        jsonObject.add("data", JsonHelper.makeData(user.getLogin(), user.getPassword()));

        socketHandler.send(jsonObject);
    }

    @MessageMapping("/LOGOUT")
    public void handleLogout(OnlineUser user){
        logger.info("handleLogout : {}", user);

        registrar.delLoginBuUIId(user.getUiId());
    }

    @MessageMapping("/USER_DATA")
    public void handleUserData(OnlineUser user) throws Exception {
        logger.info("handleUserdata : {}", user);

        Optional<String> login = registrar.getLoginBuUIId(user.getUiId());
        if (login.isPresent()){
            String uuid = UUID.randomUUID().toString();
            registrar.setUIIdByRequestUUID(uuid, user.getUiId());

            JsonObject data = new JsonObject();
            data.addProperty("login", login.get());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", MessageType.USER_DATA.getValue());
            jsonObject.addProperty("request", true);
            jsonObject.addProperty("uuid", uuid);
            jsonObject.add("data", data);

            socketHandler.send(jsonObject);
        } else {
            JsonArray errors = new JsonArray();
            errors.add(jeoGenerator.generate(new FEJEDGInvalidLogin(user.getLogin())));

            JsonObject data = new JsonObject();
            data.add("users", new JsonObject());
            data.add("errors", errors);

            frontendMessageTransmitter.handle(data.toString(), user.getUiId(), MessageType.USER_DATA.getValue(), false);
        }
    }

    @MessageMapping("/ADD_USER")
    public void handleAddUser(OnlineUser user){
        logger.info("handleAddUser : {}", user);

        String uuid = UUID.randomUUID().toString();
        registrar.setUIIdByRequestUUID(uuid, user.getUiId());

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
        registrar.setUIIdByRequestUUID(uuid, user.getUiId());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", MessageType.DEL_USER.getValue());
        jsonObject.addProperty("request", true);
        jsonObject.addProperty("uuid", uuid);
        jsonObject.add("data", JsonHelper.makeData(user.getLogin()));

        socketHandler.send(jsonObject);
    }

    @MessageMapping("/TREE_DATA")
    public void handleTreeData(OnlineUser user) {
        logger.info("handleTreeData : {}", user);

        String uuid = UUID.randomUUID().toString();
        registrar.setUIIdByRequestUUID(uuid, user.getUiId());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", MessageType.TREE_DATA.getValue());
        jsonObject.addProperty("request", true);
        jsonObject.addProperty("uuid", uuid);

        socketHandler.send(jsonObject);
    }
}
