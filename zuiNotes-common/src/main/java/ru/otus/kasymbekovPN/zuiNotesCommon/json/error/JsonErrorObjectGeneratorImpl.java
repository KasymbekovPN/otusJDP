package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
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
            return new JsonBuilderImpl()
                    .add("code", dataGenerator.getCode())
                    .add("common", common)
                    .add("entity", entity)
                    .add("data", dataGenerator.getData())
                    .get();
        } else {
            throw new Exception("DataGenerator-argument is null");
        }
    }
}
