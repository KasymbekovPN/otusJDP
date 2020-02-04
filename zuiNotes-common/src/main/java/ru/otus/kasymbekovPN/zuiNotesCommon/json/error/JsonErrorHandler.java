package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

import com.google.gson.JsonObject;

public interface JsonErrorHandler {
    JsonErrorHandler set(String property, String value);
    JsonErrorHandler set(String property, Number value);
    JsonErrorHandler set(String property, Character value);
    JsonErrorHandler set(String property, Boolean value);
    JsonObject get();
}
