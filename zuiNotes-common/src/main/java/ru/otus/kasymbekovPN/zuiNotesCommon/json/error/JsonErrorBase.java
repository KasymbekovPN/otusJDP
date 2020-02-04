package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

public class JsonErrorBase {
    private final int code;
    private final boolean common;
    private final String entity;

    public int getCode() {
        return code;
    }

    public boolean isCommon() {
        return common;
    }

    public String getEntity() {
        return entity;
    }

    public JsonErrorBase(int code, boolean common, String entity) {
        this.code = code;
        this.common = common;
        this.entity = entity;
    }
}
