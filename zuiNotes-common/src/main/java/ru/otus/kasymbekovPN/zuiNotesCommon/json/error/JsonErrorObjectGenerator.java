package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public interface JsonErrorObjectGenerator {
    JsonObject generate(JsonErrorDataGenerator dataGenerator) throws Exception;
}
