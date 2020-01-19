package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

import com.google.gson.JsonObject;

import java.util.List;

public interface JsonErrorDataGenerator {
    int getCode();
    JsonObject getData();
}
