package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;

import java.util.List;

///**
// * Обработчик входящего сообщения типа {@link MessageType#AUTH_USER_REQUEST} <br><br>
// *
// * {@link #handle(JsonObject)} - проверяет, переданные логин и пароль, в случае успешной проверки отправляет сообщение
// * содержащее данные пользователей; при неуспещеой проверке сообщение содержит описание ошибки.
// */
//<
public class AuthUserRequestSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthUserRequestSIH.class);

    private final DBServiceOnlineUser dbService;
    private final SocketHandler socketHandler;

    public AuthUserRequestSIH(DBServiceOnlineUser dbService, SocketHandler socketHandler) {
        this.dbService = dbService;
        this.socketHandler = socketHandler;
    }

    @Override
    public void handle(JsonObject jsonObject) {
        logger.info("AuthUserRequestSIH : {}", jsonObject);

        JsonArray jsonUsers = new JsonArray();
        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String login = data.get("login").getAsString().trim();
        String password = data.get("password") .getAsString().trim();
        String status = "";

        if (!login.isEmpty() && !password.isEmpty()){
            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
            if (onlineUsers.size() > 0){
                OnlineUser onlineUser = onlineUsers.get(0);
                if (onlineUser.getPassword().equals(password)){
                    if (onlineUser.isAdmin()){
                        jsonUsers.addAll((JsonArray) new JsonParser().parse(new Gson().toJson(dbService.loadAll())));
                        status = "admin";
                    } else {
                        JsonObject jsonUser = new JsonObject();
                        jsonUser.addProperty("login", onlineUser.getLogin());
                        jsonUsers.add(jsonUser);
                        status = "user";
                    }
                } else {
                    status = "Wrong login or/and password.";
                }
            } else {
                status = "Wrong login or/and password.";
            }
        } else {
            status = "Login or/and password is empty.";
        }
        logger.info("AuthUserRequestSIH : {}", status);

        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("type", MessageType.AUTH_USER_RESPONSE.getValue());
        responseJsonObject.add("data", JsonHelper.makeData(login, password, status, jsonUsers));

        socketHandler.send(responseJsonObject);
    }
}
