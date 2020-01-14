package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

import java.util.*;

public class JsonErrorObjectGeneratorImpl implements JsonErrorObjectGenerator {

    private final Map<Integer, JsonErrorDataGenerator> dataGenerators = new HashMap<>();

    private final String entity;
    private final boolean common;

    public JsonErrorObjectGeneratorImpl(String entity, boolean common) {
        this.entity = entity;
        this.common = common;
    }

    @Override
    public JsonObject generate(JsonErrorDataGenerator dataGenerator) throws Exception {
        if (dataGenerator != null){
            JsonObject jsonError = new JsonObject();
            jsonError.addProperty("code", dataGenerator.getCode());
            jsonError.addProperty("common", common);
            jsonError.addProperty("entity", entity);
            jsonError.add("data", dataGenerator.getData());

            return jsonError;
        } else {
            throw new Exception("DataGenerator-argument is null");
        }
    }
}
