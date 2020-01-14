package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

import com.google.gson.JsonObject;

public class BaseJEDG implements JsonErrorDataGenerator {

    private int errorCode;
    protected JsonObject data;

    public BaseJEDG(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public int getCode() {
        return errorCode;
    }

    @Override
    public JsonObject getData() {
        return data;
    }
}
