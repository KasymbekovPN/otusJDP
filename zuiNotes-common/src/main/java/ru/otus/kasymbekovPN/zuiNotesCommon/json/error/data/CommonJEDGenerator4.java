package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

import com.google.gson.JsonObject;

import java.util.List;

public class CommonJEDGenerator4 implements JsonErrorDataGenerator{
    @Override
    public JsonObject generate(List<Object> objects) {
        JsonObject data = new JsonObject();
        data.addProperty("field", objects.get(0).toString());
        return data;
    }
}
