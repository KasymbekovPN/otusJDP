package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

import com.google.gson.JsonObject;

import java.util.List;

public class CommonJEDGenerator3 implements JsonErrorDataGenerator{
    @Override
    public JsonObject generate(List<Object> objects) {
        JsonObject data = new JsonObject();
        data.addProperty("type", objects.get(0).toString());
        data.addProperty("field", objects.get(1).toString());
        return data;
    }
}
