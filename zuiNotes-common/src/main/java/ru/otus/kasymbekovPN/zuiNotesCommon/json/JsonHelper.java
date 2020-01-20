package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonObject;

public class JsonHelper {

    public static JsonObject makeUrl(String host, int port, String entity){
        JsonObject url = new JsonObject();
        url.addProperty("host", host);
        url.addProperty("port", port);
        url.addProperty("entity", entity);

        return url;
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
}

