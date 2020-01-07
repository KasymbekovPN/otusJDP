package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;

import java.util.List;

///**
// * Обработчик входящего сообщения типа {@link MessageType#DEL_USER_REQUEST} <br><br>
// *
// * {@link #handle(JsonObject)} - проверяет, переданные логин, в случае успешной проверки удаляет пользователя; отправляет
// * сообщение содержащее данные пользователей.
// */
//<
public class DelUserSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(DelUserSIH.class);

    private final DBServiceOnlineUser dbService;
    private final SocketHandler socketHandler;

    public DelUserSIH(DBServiceOnlineUser dbService, SocketHandler socketHandler) {
        this.dbService = dbService;
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("DelUserSIH : {}", jsonObject);

        String uuid = jsonObject.get("uuid").getAsString();
        String type = jsonObject.get("type").getAsString();

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String login = data.get("login").getAsString().trim();
        String status = "";

        if (!login.isEmpty()){
            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
            if (onlineUsers.size() != 0){
                dbService.deleteRecord(login);
                status = "User '" + login + "' was delete.";
            } else {
                status = "User '" + login + "' doesn't exist.";
            }
        } else {
            status = "Login is empty.";
        }
        logger.info("DelUserSIH : {}", status);

        JsonArray jsonUsers = (JsonArray) new JsonParser().parse(new Gson().toJson(dbService.loadAll()));
        JsonObject responseJsonData = new JsonObject();
        responseJsonData.addProperty("type", type);
        responseJsonData.addProperty("request", false);
        responseJsonData.addProperty("uuid", uuid);
        responseJsonData.add("data", JsonHelper.makeData(login, status, jsonUsers));

        socketHandler.send(responseJsonData);
    }
}
