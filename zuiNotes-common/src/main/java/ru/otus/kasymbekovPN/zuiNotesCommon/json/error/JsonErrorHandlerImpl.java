package ru.otus.kasymbekovPN.zuiNotesCommon.json.error;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;

import java.util.Set;

public class JsonErrorHandlerImpl implements JsonErrorHandler {

    private final Set<String> stringProperties;
    private final Set<String> numberProperties;
    private final Set<String> characterProperties;
    private final Set<String> booleanProperties;
    private final JsonErrorBase jeBase;

    private JsonObject data = new JsonObject();

    public JsonErrorHandlerImpl(JsonErrorBase jeBase,
                                Set<String> stringProperties,
                                Set<String> numberProperties,
                                Set<String> characterProperties,
                                Set<String> booleanProperties) {
        this.jeBase = jeBase;
        this.stringProperties = stringProperties;
        this.numberProperties = numberProperties;
        this.characterProperties = characterProperties;
        this.booleanProperties = booleanProperties;
    }

    @Override
    public JsonErrorHandler set(String property, String value) {
        if (stringProperties.contains(property)){
            data.addProperty(property, value);
        }
        return this;
    }

    @Override
    public JsonErrorHandler set(String property, Number value) {
        if (numberProperties.contains(property)){
            data.addProperty(property, value);
        }
        return this;
    }

    @Override
    public JsonErrorHandler set(String property, Character value) {
        if (characterProperties.contains(property)){
            data.addProperty(property, value);
        }
        return this;
    }

    @Override
    public JsonErrorHandler set(String property, Boolean value) {
        if (booleanProperties.contains(property)){
            data.addProperty(property, value);
        }
        return this;
    }

    @Override
    public JsonObject get() {
        JsonObject d = data;
        data = new JsonObject();

        return new JsonBuilderImpl()
                .add("code", jeBase.getCode())
                .add("entity",jeBase.getEntity())
                .add("common", jeBase.isCommon())
                .add("data", d)
                .get();
    }
}
