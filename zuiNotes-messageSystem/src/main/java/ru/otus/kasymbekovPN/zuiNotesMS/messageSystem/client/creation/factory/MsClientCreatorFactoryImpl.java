package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.CmnMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.MsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.WrongMsClientCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MsClientCreatorFactoryImpl implements MsClientCreatorFactory {

    private final Map<String, Set<String>> config = new HashMap<>();

    public MsClientCreatorFactoryImpl(JsonObject jsonConfig, String messagesField) {
        for (String entity : jsonConfig.keySet()) {
            JsonArray entityMessages = jsonConfig.get(entity).getAsJsonObject().get(messagesField).getAsJsonArray();
            Set<String> set = new HashSet<>();
            for (JsonElement entityMessage : entityMessages) {
                set.add(entityMessage.getAsString());
            }
            this.config.put(entity, set);
        }
    }

    @Override
    public MsClientCreator get(String entity) {
        return config.containsKey(entity)
                ? new CmnMsClientCreator(config.get(entity))
                : new WrongMsClientCreator();
    }
}
