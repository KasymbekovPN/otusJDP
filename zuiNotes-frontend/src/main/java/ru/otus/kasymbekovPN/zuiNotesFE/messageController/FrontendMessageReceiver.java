package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.json.error.data.FEErrorCode;
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

    private final JsonErrorGenerator jeGenerator;

    @MessageMapping("/LOGIN")
    public void handleLogin(OnlineUser user){
        logger.info("handleLogin : {}", user);

        String uuid = UUID.randomUUID().toString();
        registrar.setUIIdByRequestUUID(uuid, user.getUiId());

        JsonObject jsonObject = new JsonBuilderImpl()
                .add(
                        "header",
                        new JsonBuilderImpl()
                        .add("type", MessageType.LOGIN.getValue())
                        .add("request", true)
                        .add("uuid", uuid)
                        .get()
                )
                .add(
                        "data",
                        new JsonBuilderImpl()
                        .add("login", user.getLogin())
                        .add("password", user.getPassword())
                        .get()
                )
                .get();

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

            JsonObject jsonObject = new JsonBuilderImpl()
                    .add(
                            "header",
                            new JsonBuilderImpl()
                            .add("type", MessageType.USER_DATA.getValue())
                            .add("request", true)
                            .add("uuid", uuid)
                            .get()
                    )
                    .add(
                            "data",
                            new JsonBuilderImpl().add("login", login.get()).get()
                    )
                    .get();

            socketHandler.send(jsonObject);
        } else {
            JsonArray errors = new JsonArray();
            errors.add(
                    jeGenerator
                        .handle(false, FEErrorCode.INVALID_LOGIN.getCode())
                        .set("login", user.getLogin())
                        .get()
            );

            String data = new JsonBuilderImpl()
                    .add("users", new JsonObject())
                    .add("errors", errors)
                    .get()
                    .toString();

            frontendMessageTransmitter.handle(data, user.getUiId(), MessageType.USER_DATA.getValue(), false);
        }
    }

    @MessageMapping("/ADD_USER")
    public void handleAddUser(OnlineUser user){
        logger.info("handleAddUser : {}", user);

        String uuid = UUID.randomUUID().toString();
        registrar.setUIIdByRequestUUID(uuid, user.getUiId());

        JsonObject jsonObject = new JsonBuilderImpl()
                .add(
                        "header",
                        new JsonBuilderImpl()
                        .add("type", MessageType.ADD_USER.getValue())
                        .add("request", true)
                        .add("uuid", uuid)
                        .get()
                )
                .add(
                        "data",
                        new JsonBuilderImpl()
                        .add("login", user.getLogin())
                        .add("password", user.getPassword())
                        .get()
                )
                .get();

        socketHandler.send(jsonObject);
    }

    @MessageMapping("/DEL_USER")
    public void handleDelUser(OnlineUser user){
        logger.info("handleDelUser : {}", user);

        String uuid = UUID.randomUUID().toString();
        registrar.setUIIdByRequestUUID(uuid, user.getUiId());

        JsonObject jsonObject = new JsonBuilderImpl()
                .add(
                        "header",
                        new JsonBuilderImpl()
                        .add("type", MessageType.DEL_USER.getValue())
                        .add("request", true)
                        .add("uuid", uuid)
                        .get()
                )
                .add(
                        "data",
                        new JsonBuilderImpl().add("login", user.getLogin()).get()
                )
                .get();

        socketHandler.send(jsonObject);
    }

    @MessageMapping("/TREE_DATA")
    public void handleTreeData(OnlineUser user) {
        logger.info("handleTreeData : {}", user);

        String uuid = UUID.randomUUID().toString();
        registrar.setUIIdByRequestUUID(uuid, user.getUiId());

        JsonObject jsonObject = new JsonBuilderImpl()
                .add(
                        "header",
                        new JsonBuilderImpl()
                        .add("type", MessageType.TREE_DATA.getValue())
                        .add("request", true)
                        .add("uuid", uuid)
                        .get()
                )
                .get();

        socketHandler.send(jsonObject);
    }
}
