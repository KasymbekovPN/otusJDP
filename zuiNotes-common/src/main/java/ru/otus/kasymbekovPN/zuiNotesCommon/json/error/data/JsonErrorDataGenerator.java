package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

import com.google.gson.JsonObject;

import java.util.List;

public interface JsonErrorDataGenerator {
    JsonObject generate(List<Object> objects);
}
