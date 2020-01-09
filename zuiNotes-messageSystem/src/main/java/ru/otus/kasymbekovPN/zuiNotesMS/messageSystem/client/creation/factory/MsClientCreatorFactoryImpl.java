package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.CmnMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.MsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.WrongMsClientCreator;

import java.util.HashSet;
import java.util.Set;

public class MsClientCreatorFactoryImpl implements MsClientCreatorFactory {

    private final JsonObject config;
    private final String solusField;
    private final String messagesField;

    public MsClientCreatorFactoryImpl(JsonObject config, String solusField, String messagesField) {
        this.config = config;
        this.solusField = solusField;
        this.messagesField = messagesField;
    }

    @Override
    public MsClientCreator get(String entity) {
        if (config.has(entity)){
            Set<String> messages = new HashSet<>();
            for (JsonElement element : config.get(entity).getAsJsonObject().get(messagesField).getAsJsonArray()) {
                messages.add(element.getAsString());
            }
            return new CmnMsClientCreator(messages);
        } else {
            return new WrongMsClientCreator();
        }
    }
}
