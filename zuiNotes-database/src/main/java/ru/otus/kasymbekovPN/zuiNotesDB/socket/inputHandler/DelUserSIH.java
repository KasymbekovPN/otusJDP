package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
 * Обработчик входящего сообщения типа {@link ru.otus.kasymbekovPN.zuiNotesDB.messageSystem.MessageType#DEL_USER} <br><br>
 *
 * {@link #handle(JsonObject)} - проверяет, переданные логин, в случае успешной проверки удаляет пользователя; отправляет
 * сообщение содержащее данные пользователей.
 */
public class DelUserSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(DelUserSIH.class);

    private final DBServiceOnlineUser dbService;
    private final SocketHandler socketHandler;
    private final JsonErrorGenerator jeGenerator;

    public DelUserSIH(DBServiceOnlineUser dbService, SocketHandler socketHandler, JsonErrorGenerator jeGenerator) {
        this.dbService = dbService;
        this.socketHandler = socketHandler;
        this.jeGenerator = jeGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("DelUserSIH : {}", jsonObject);

        JsonObject header = jsonObject.get("header").getAsJsonObject();
        String uuid = header.get("uuid").getAsString();
        String type = header.get("type").getAsString();

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String login = data.get("login").getAsString().trim();

        JsonArray errors = new JsonArray();
        if (!login.isEmpty()){
            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
            if (!onlineUsers.isEmpty()){
                dbService.deleteRecord(login);
            } else {
                errors.add(
                        jeGenerator.handle(false, DBErrorCode.USER_DOESNT_EXIST.getCode()).get()
                );
            }
        } else {
            errors.add(
                    jeGenerator.handle(false, DBErrorCode.EMPTY_LOGIN_PASSWORD.getCode()).get()
            );
        }

        JsonArray users = (JsonArray) new JsonParser().parse(new Gson().toJson(dbService.loadAll()));

        JsonObject message = new JsonBuilderImpl()
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
                        .add("users", users)
                        .add("errors", errors)
                        .get()
                ).get();

        socketHandler.send(message);
    }
}
