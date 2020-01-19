package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.MsClientCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MsClientCreatorFactoryImpl implements MsClientCreatorFactory {

    private final MsClientCreator commonCreator;
    private final MsClientCreator wrongCreator;
    private final Map<String, Set<String>> config = new HashMap<>();

    public MsClientCreatorFactoryImpl(MsClientCreator commonCreator, MsClientCreator wrongCreator,
                                      JsonObject jsonConfig, String messagesField) {
        this.commonCreator = commonCreator;
        this.wrongCreator = wrongCreator;
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
        MsClientCreator creator = config.containsKey(entity) ? commonCreator : wrongCreator;
        creator.setValidMessages(config.get(entity));
        return creator;
    }
}
