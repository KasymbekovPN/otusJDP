package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBErrorCode;

import java.util.List;

/**
 * Обработчик входящего сообщения типа {@link ru.otus.kasymbekovPN.zuiNotesDB.messageSystem.MessageType#LOGIN} <br><br>
 *
 * {@link #handle(JsonObject)} - проверяет, переданные логин и пароль, в случае успешной проверки отправляет сообщение
 * содержащее данные пользователей; при неуспещеой проверке сообщение содержит описание ошибки.
 */
public class LoginSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginSIH.class);

    private final DBServiceOnlineUser dbService;
    private final SocketHandler socketHandler;
    private final JsonErrorGenerator jeGenerator;

    public LoginSIH(DBServiceOnlineUser dbService, SocketHandler socketHandler, JsonErrorGenerator jeGenerator) {
        this.dbService = dbService;
        this.socketHandler = socketHandler;
        this.jeGenerator = jeGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("LoginSIH : {}", jsonObject);

        JsonObject header = jsonObject.get("header").getAsJsonObject();
        String uuid = header.get("uuid").getAsString();
        String type = header.get("type").getAsString();

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String login = data.get("login").getAsString().trim();
        String password = data.get("password") .getAsString().trim();

        String group = "";
        JsonArray errors = new JsonArray();
        if (!login.isEmpty() && !password.isEmpty()){
            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
            if (!onlineUsers.isEmpty()){
                OnlineUser onlineUser = onlineUsers.get(0);
                if (onlineUser.getPassword().equals(password)){
                    if (onlineUser.isAdmin()){
                        //< load from DB
                        group = "admin";
                    } else {
                        //< load from DB
                        group = "user";
                    }
                } else {
                    errors.add(
                            jeGenerator.handle(false, DBErrorCode.WRONG_LOGIN_PASSWORD.getCode()).get()
                    );
                }
            } else {
                errors.add(
                        jeGenerator.handle(false, DBErrorCode.WRONG_LOGIN_PASSWORD.getCode()).get()
                );
            }
        } else {
            errors.add(
                    jeGenerator.handle(false, DBErrorCode.EMPTY_LOGIN_PASSWORD.getCode()).get()
            );
        }

        JsonObject responseJsonObject = new JsonBuilderImpl()
                .add(
                        "header",
                        new JsonBuilderImpl()
                        .add("type", type)
                        .add("request", false)
                        .add("uuid", uuid)
                        .get()
                )
                .add(
                        "data",
                        new JsonBuilderImpl()
                        .add("login", login)
                        .add("group", group)
                        .add("errors", errors)
                        .get()
                ).get();

        socketHandler.send(responseJsonObject);
    }
}
