package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBJEDGUserDoesntExist;

import java.util.List;

///**
// * Обработчик входящего сообщения типа {@link MessageType#DEL_USER_REQUEST} <br><br>
// *
// * {@link #handle(JsonObject)} - проверяет, переданные логин, в случае успешной проверки удаляет пользователя; отправляет
// * сообщение содержащее данные пользователей.
// */
public class DelUserSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(DelUserSIH.class);

    private final DBServiceOnlineUser dbService;
    private final SocketHandler socketHandler;
    private final JsonErrorObjectGenerator jeoGenerator;

    public DelUserSIH(DBServiceOnlineUser dbService, SocketHandler socketHandler, JsonErrorObjectGenerator jeoGenerator) {
        this.dbService = dbService;
        this.socketHandler = socketHandler;
        this.jeoGenerator = jeoGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("DelUserSIH : {}", jsonObject);

        String uuid = jsonObject.get("uuid").getAsString();
        String type = jsonObject.get("type").getAsString();

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String login = data.get("login").getAsString().trim();

        JsonArray errors = new JsonArray();
        if (!login.isEmpty()){
            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
            if (!onlineUsers.isEmpty()){
                dbService.deleteRecord(login);
            } else {
                errors.add(jeoGenerator.generate(new DBJEDGUserDoesntExist()));
            }
        } else {
            errors.add(jeoGenerator.generate(new DBJEDGEmptyLoginPassword()));
        }

        JsonArray users = (JsonArray) new JsonParser().parse(new Gson().toJson(dbService.loadAll()));
        JsonObject responseData = new JsonObject();
        responseData.add("users", users);
        responseData.add("errors", errors);
        responseData.addProperty("login", login);

        JsonObject message = new JsonObject();
        message.addProperty("type", type);
        message.addProperty("request", false);
        message.addProperty("uuid", uuid);
        message.add("data", responseData);

        socketHandler.send(message);
    }
}
