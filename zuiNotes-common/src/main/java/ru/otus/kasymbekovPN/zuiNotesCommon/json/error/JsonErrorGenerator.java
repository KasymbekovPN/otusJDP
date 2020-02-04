package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

public interface JsonErrorGenerator {
    JsonErrorHandler handle(int code) throws Exception;
    void addHandler(int code, JsonErrorHandler jeh);
}
