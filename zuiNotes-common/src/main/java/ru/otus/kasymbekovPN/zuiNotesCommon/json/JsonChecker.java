package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonObject;

import java.util.Set;

/**
 * Интерфейс, служащий для реализации класса, проверяющего валидность содержимого сообщения отправляемого от клиента к
 * серверу или наоборот. <br><br>
 *
 * {@link JsonChecker#getType()} - возвращает тип проверяемого сообщение. <br>
 * {@link JsonChecker#setJsonObject(JsonObject, Set)} - сеттер json-объекта для проверки. <br>
 * {@link JsonChecker#getJsonObject()} - геттер провереного json-объекта. <br>
 */
public interface JsonChecker {
    String getType();
    void setJsonObject(JsonObject jsonObject, Set<String> validTypes) throws Exception;
    JsonObject getJsonObject();
}
