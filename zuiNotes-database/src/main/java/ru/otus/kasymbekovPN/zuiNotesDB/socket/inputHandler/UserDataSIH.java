package ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBJEDGWrongLoginPassword;
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBJEDGWrongRights;

import java.util.List;

public class UserDataSIH implements SocketInputHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserDataSIH.class);

    private final DBServiceOnlineUser dbService;
    private final SocketHandler socketHandler;
    private final JsonErrorObjectGenerator jeoGenerator;

    public UserDataSIH(DBServiceOnlineUser dbService, SocketHandler socketHandler, JsonErrorObjectGenerator jeoGenerator) {
        this.dbService = dbService;
        this.socketHandler = socketHandler;
        this.jeoGenerator = jeoGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {
        logger.info("UserDataSIH : {}", jsonObject);

        JsonObject data = jsonObject.get("data").getAsJsonObject();
        String login = data.get("login").getAsString();

        JsonArray users = new JsonArray();
        JsonArray errors = new JsonArray();
        List<OnlineUser> onlineUsers = dbService.loadRecord(login);
        if (!onlineUsers.isEmpty()){
            OnlineUser onlineUser = onlineUsers.get(0);
            if (onlineUser.isAdmin()){
                users.addAll((JsonArray) new JsonParser().parse(new Gson().toJson(dbService.loadAll())));
            } else {
                errors.add(jeoGenerator.generate(new DBJEDGWrongRights()));
            }
        } else {
            errors.add(jeoGenerator.generate(new DBJEDGWrongLoginPassword()));
        }

        JsonObject header = jsonObject.get("header").getAsJsonObject();
        String type = header.get("type").getAsString();
        String uuid = header.get("uuid").getAsString();

        JsonObject responseMessage = new JsonBuilderImpl()
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
                        .add("users", users)
                        .add("errors", errors)
                        .get()
                ).get();

        socketHandler.send(responseMessage);
    }
}
