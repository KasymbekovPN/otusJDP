package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

import java.util.List;

public class MSJEDGenerator9 implements JsonErrorDataGenerator {
    @Override
    public JsonObject generate(List<Object> objects) {
        JsonObject data = new JsonObject();
        data.addProperty("url", objects.get(0).toString());
        return data;
    }
}
