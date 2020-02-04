package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

import java.util.HashMap;
import java.util.Map;

public class JsonErrorGeneratorImpl implements JsonErrorGenerator {

    private final Map<Integer, JsonErrorHandler> jeHandlers = new HashMap<>();

    @Override
    public JsonErrorHandler handle(int code) throws Exception {
        if (!jeHandlers.containsKey(code)){
            throw new Exception("Invalid error code : " + code);
        }
        return jeHandlers.get(code);
    }

    @Override
    public void addHandler(int code, JsonErrorHandler jeh) {
        jeHandlers.put(code, jeh);
    }
}
