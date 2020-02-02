package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface JsonBuilder {
    JsonBuilder add(String property, JsonElement value);
    JsonBuilder add(String property, String value);
    JsonBuilder add(String property, Boolean value);
    JsonBuilder add(String property, Number value);
    JsonBuilder add(String property, Character value);
    JsonObject get();
}
