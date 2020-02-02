package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonBuilderImpl implements JsonBuilder {

    private JsonObject data;

    public JsonBuilderImpl() {
        this.data = new JsonObject();
    }

    public JsonBuilderImpl(JsonObject data)
    {
        this.data = data;
    }

    @Override
    public JsonBuilder add(String property, JsonElement value) {
        data.add(property, value);
        return this;
    }

    @Override
    public JsonBuilder add(String property, String value) {
        data.addProperty(property, value);
        return this;
    }

    @Override
    public JsonBuilder add(String property, Boolean value) {
        data.addProperty(property, value);
        return this;
    }

    @Override
    public JsonBuilder add(String property, Number value) {
        data.addProperty(property, value);
        return this;
    }

    @Override
    public JsonBuilder add(String property, Character value) {
        data.addProperty(property, value);
        return this;
    }

    @Override
    public JsonObject get() {
        return data;
    }
}
