package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SolusImpl implements Solus {

    private static final Logger logger = LoggerFactory.getLogger(SolusImpl.class);

    private Map<String, Boolean> entityStates = new HashMap<>();

    public SolusImpl(JsonObject jsonConfig, String solusField) {
        for (String entity : jsonConfig.keySet()) {
            boolean solus = jsonConfig.get(entity).getAsJsonObject().get(solusField).getAsBoolean();
            if (solus){
                entityStates.put(entity, false);
            }
        }
    }

    @Override
    public boolean register(String entity) {
        if (entityStates.containsKey(entity)){
            if (entityStates.get(entity)){
                return false;
            } else {
                entityStates.put(entity, true);
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void unregister(String entity) {
        if (entityStates.containsKey(entity)){
            entityStates.put(entity, false);
        }
    }
}
