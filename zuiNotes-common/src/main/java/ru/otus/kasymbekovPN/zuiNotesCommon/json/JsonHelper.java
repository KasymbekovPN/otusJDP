package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.entity.Entity;

public class JsonHelper {

    public static JsonObject makeUrl(String host, int port, Entity entity){
        JsonObject url = new JsonObject();
        url.addProperty("host", host);
        url.addProperty("port", port);
        url.addProperty("entity", entity.getValue());

        return url;
    }

    public static String extractUrl(JsonObject jsonObject){
        String host = jsonObject.get("host").getAsString();
        String entity = jsonObject.get("entity").getAsString();
        int port = jsonObject.get("port").getAsInt();
        return  host + ":" + String.valueOf(port) + "/" + entity;
    }

    public static JsonObject makeData(String login){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);

        return jsonObject;
    }

    public static JsonObject makeData(String login, String password){
        JsonObject data = new JsonObject();
        data.addProperty("login", login);
        data.addProperty("password", password);

        return  data;
    }

    public static JsonObject makeData(String login, String status, JsonArray users){
        JsonObject data = new JsonObject();
        data.addProperty("login", login);
        data.addProperty("status", status);
        data.add("users", users);

        return data;
    }

    public static JsonObject makeData(String login, String password, String status, JsonArray users){
        JsonObject data = new JsonObject();
        data.addProperty("login", login);
        data.addProperty("password", password);
        data.addProperty("status", status);
        data.add("users", users);

        return data;
    }
}

