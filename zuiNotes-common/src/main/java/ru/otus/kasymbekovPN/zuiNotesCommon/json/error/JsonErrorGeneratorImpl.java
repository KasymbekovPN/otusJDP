package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

import java.util.HashMap;
import java.util.Map;

public class JsonErrorGeneratorImpl implements JsonErrorGenerator {

    private final Map<Boolean, Map<Integer, JsonErrorHandler>> jeHandlers = new HashMap<Boolean, Map<Integer, JsonErrorHandler>>(){{
        put(false, new HashMap<Integer, JsonErrorHandler>());
        put(true, new HashMap<Integer, JsonErrorHandler>());
    }};

    @Override
    public JsonErrorHandler handle(boolean common, int code) throws Exception {
        if (!jeHandlers.get(common).containsKey(code)){
            throw new Exception("Invalid error code : " + code);
        }
        return jeHandlers.get(common).get(code);
    }

    @Override
    public void addHandler(boolean common, int code, JsonErrorHandler jeh) {
        jeHandlers.get(common).put(code, jeh);
    }
}
