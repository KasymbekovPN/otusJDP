package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBJEDGEmptyLoginPassword;
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBJEDGUserAlreadyExist;

import java.util.List;

///**
// * Обработчик входящего сообщения типа {@link MessageType#ADD_USER_REQUEST} <br><br>
// *
// * {@link #handle(JsonObject)} - проверяет, переданные логин и пароль, в случае успешной проверки добавляет нового
// * пользователя; отправляет сообщение содержащее данные пользователей.
// */
public class AddUserSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(AddUserSIH.class);

    private final DBServiceOnlineUser dbService;
    private final SocketHandler socketHandler;
    private final JsonErrorObjectGenerator jeoGenerator;

    public AddUserSIH(DBServiceOnlineUser dbService, SocketHandler socketHandler, JsonErrorObjectGenerator jeoGenerator) {
        this.dbService = dbService;
        this.socketHandler = socketHandler;
        this.jeoGenerator = jeoGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("AddUserSIH : {}", jsonObject);

        String uuid = jsonObject.get("uuid").getAsString();
        String type = jsonObject.get("type").getAsString();

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String login = data.get("login").getAsString().trim();
        String password = data.get("password").getAsString().trim();

        JsonArray errors = new JsonArray();
        if (!login.isEmpty() && !password.isEmpty()){
            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
            if (onlineUsers.isEmpty()){
                dbService.createRecord(
                        new OnlineUser(0, login, password, false, "")
                );
            } else {
                errors.add(jeoGenerator.generate(new DBJEDGUserAlreadyExist()));
            }
        } else {
            errors.add(jeoGenerator.generate(new DBJEDGEmptyLoginPassword()));
        }

        JsonArray users = (JsonArray) new JsonParser().parse(new Gson().toJson(dbService.loadAll()));

        JsonObject responseData = new JsonObject();
        responseData.addProperty("login", login);
        responseData.add("users", users);
        responseData.add("errors", errors);

        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("type", type);
        responseJsonObject.addProperty("request", false);
        responseJsonObject.addProperty("uuid", uuid);
        responseJsonObject.add("data", responseData);

        socketHandler.send(responseJsonObject);
    }
}
