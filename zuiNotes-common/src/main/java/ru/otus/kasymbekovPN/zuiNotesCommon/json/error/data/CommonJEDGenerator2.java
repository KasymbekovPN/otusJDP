package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

import com.google.gson.JsonObject;

import java.util.List;

public class CommonJEDGenerator2 implements JsonErrorDataGenerator {
    @Override
    public JsonObject generate(List<Object> objects) {
        JsonObject data = new JsonObject();
        data.addProperty("type", objects.get(0).toString());
        return data;
    }
}
