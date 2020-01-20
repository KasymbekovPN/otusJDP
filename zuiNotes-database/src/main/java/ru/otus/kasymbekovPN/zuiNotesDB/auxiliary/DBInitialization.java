package ru.otus.kasymbekovPN.zuiNotesDB.auxiliary;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Служебный класс-инициализатор БД. <br><br>
 *
 * {@link #init} - метод, записывающий данные администратора в БД.
 */
@Component
@RequiredArgsConstructor
public class DBInitialization {

    private static final String DB_CONFIG = "/dbConfig.json";

    private final DBServiceOnlineUser dbService;

    @PostConstruct
    public void init() throws Exception {
        JsonObject config = loadAndCheckDBConfig();
        for (JsonElement element : config.get("admins").getAsJsonArray()) {
            JsonObject object = element.getAsJsonObject();
            String login = object.get("login").getAsString();
            String password = object.get("password").getAsString();

            List<OnlineUser> onlineUsers = dbService.loadRecord(login);
            if (onlineUsers.isEmpty()) {
                dbService.createRecord(new OnlineUser(0, login, password, true, ""));
            }
        }
    }

    private JsonObject loadAndCheckDBConfig() throws Exception {
        JsonObject jsonConfig = loadConfig(DB_CONFIG);

        StringBuilder status = new StringBuilder();
        if (jsonConfig.has("admins")){
            JsonElement elementAdmins = jsonConfig.get("admins");
            if (elementAdmins.isJsonArray()){
                for (JsonElement elementItem : elementAdmins.getAsJsonArray()) {
                    if (elementItem.isJsonObject()){
                        JsonObject objectItem = elementItem.getAsJsonObject();
                        if (!objectItem.has("login") || !objectItem.has("password")){
                            status.append("|Wrong array element|");
                        }
                    } else {
                        status.append("|Array element isn't json object|");
                    }
                }
            }
        } else {
            status.append("| Config doesn't contain array 'admins'|");
        }

        if (!status.toString().isEmpty()){
            throw new Exception(status.toString());
        }

        return jsonConfig;
    }

    private JsonObject loadConfig(String fileName) throws Exception{
        JsonObject jsonConfig = new JsonObject();

        InputStream in = getClass().getResourceAsStream(fileName);
        if (in != null){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line);
            }

            jsonConfig = (JsonObject) new JsonParser().parse(String.valueOf(content));
        } else {
            throw new Exception("File '"+fileName+"' doesn't exist");
        }

        return jsonConfig;
    }
}
