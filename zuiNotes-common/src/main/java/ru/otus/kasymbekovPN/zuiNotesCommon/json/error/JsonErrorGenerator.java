package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

public interface JsonErrorGenerator {

    //< !!! Нужно без исключения
    JsonErrorHandler handle(boolean common, int code) throws Exception;


    void addHandler(boolean common, int code, JsonErrorHandler jeh);
}
