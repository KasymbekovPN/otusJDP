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
        //<
        System.out.println("ctr this.common : " + this.common + " : " + common);
    }

    @Override
    public JsonObject generate(Integer code, Object... objects) throws Exception {

        //<
        System.out.println(this);
        System.out.println("this.common : " + common);

        //<

        List<Object> lObjects = new ArrayList<>(Arrays.asList(objects));
        JsonErrorDataGenerator dataGenerator = dataGenerators.getOrDefault(code, null);
        if (dataGenerator != null){
            JsonObject data = dataGenerator.generate(lObjects);
            return generate(code, data);
        } else {
            throw new Exception("Invalid args : code : " + code.toString());
        }
    }

    @Override
    public void addDataGenerator(Integer code, JsonErrorDataGenerator dataGenerator) {
        dataGenerators.put(code, dataGenerator);

        //<
        System.out.println(this + " : " + common + " : " + code);
        //<
    }

    private JsonObject generate(Integer code, JsonObject data){
        JsonObject error = new JsonObject();
        error.addProperty("code", code);
        error.addProperty("common", common);
        error.addProperty("entity", entity);
        error.add("data", data);

        return error;
    }
}
