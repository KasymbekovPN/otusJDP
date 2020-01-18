package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBJEDGEmptyLoginPassword;
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBJEDGWrongLoginPassword;

import java.util.List;

///**
// * Обработчик входящего сообщения типа {@link MessageType#AUTH_USER_REQUEST} <br><br>
// *
// * {@link #handle(JsonObject)} - проверяет, переданные логин и пароль, в случае успешной проверки отправляет сообщение
// * содержащее данные пользователей; при неуспещеой проверке сообщение содержит описание ошибки.
// */
public class LoginSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginSIH.class);

    private final DBServiceOnlineUser dbService;
    private final SocketHandler socketHandler;
    private final JsonErrorObjectGenerator jeoGenerator;

    public LoginSIH(DBServiceOnlineUser dbService, SocketHandler socketHandler, JsonErrorObjectGenerator jeoGenerator) {
        this.dbService = dbService;
        this.socketHandler = socketHandler;
        this.jeoGenerator = jeoGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("LoginSIH : {}", jsonObject);

        String uuid = jsonObject.get("uuid").getAsString();
        String type = jsonObject.get("type").getAsString();

        JsonArray jsonUsers = new JsonArray();
        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String login = data.get("login").getAsString().trim();
        String password = data.get("password") .getAsString().trim();

        String group = "";
        JsonArray errors = new JsonArray();
        if (!login.isEmpty() && !password.isEmpty()){
            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
            if (onlineUsers.size() > 0){
                OnlineUser onlineUser = onlineUsers.get(0);
                if (onlineUser.getPassword().equals(password)){
                    if (onlineUser.isAdmin()){
                        //< load from DB
                        group = "admin";
                        //<
                    } else {
                        //< load from DB
                        group = "user";
                    }
                } else {
                    errors.add(jeoGenerator.generate(new DBJEDGWrongLoginPassword()));
                }
            } else {
                errors.add(jeoGenerator.generate(new DBJEDGWrongLoginPassword()));
            }
        } else {
            errors.add(jeoGenerator.generate(new DBJEDGEmptyLoginPassword()));
        }

        JsonObject responseData = new JsonObject();
        responseData.addProperty("login", login);
        responseData.addProperty("group", group);
        responseData.add("errors", errors);

        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("type", type);
        responseJsonObject.addProperty("request", false);
        responseJsonObject.addProperty("uuid", uuid);
        responseJsonObject.add("data", responseData);

        socketHandler.send(responseJsonObject);

        //<
//        String status = "";
//
//        if (!login.isEmpty() && !password.isEmpty()){
//            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
//            if (onlineUsers.size() > 0){
//                OnlineUser onlineUser = onlineUsers.get(0);
//                if (onlineUser.getPassword().equals(password)){
//                    if (onlineUser.isAdmin()){
//                        jsonUsers.addAll((JsonArray) new JsonParser().parse(new Gson().toJson(dbService.loadAll())));
//                        status = "admin";
//                    } else {
//                        JsonObject jsonUser = new JsonObject();
//                        jsonUser.addProperty("login", onlineUser.getLogin());
//                        jsonUsers.add(jsonUser);
//                        status = "user";
//                    }
//                } else {
//                    status = "Wrong login or/and password.";
//                }
//            } else {
//                status = "Wrong login or/and password.";
//            }
//        } else {
//            status = "Login or/and password is empty.";
//        }
//        logger.info("AuthUserSIH : {}", status);
//
//        JsonObject responseJsonObject = new JsonObject();
//        responseJsonObject.addProperty("type", type);
//        responseJsonObject.addProperty("request", false);
//        responseJsonObject.addProperty("uuid", uuid);
//        responseJsonObject.add("data", JsonHelper.makeData(login, password, status, jsonUsers));
//
//        socketHandler.send(responseJsonObject);
    }
}
