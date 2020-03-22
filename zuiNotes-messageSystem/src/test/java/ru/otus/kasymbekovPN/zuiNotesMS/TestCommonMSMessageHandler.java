package ru.otus.kasymbekovPN.zuiNotesMS;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.common.Serializers;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MSMessage;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.MSMessageHandler;

import java.util.Map;

public class TestCommonMSMessageHandler implements MSMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(TestCommonMSMessageHandler.class);

    private Map<Integer, JsonObject> buffer;

    public TestCommonMSMessageHandler(Map<Integer, JsonObject> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void handle(MSMessage MSMessage) {
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(
                Serializers.deserialize(MSMessage.getPayload(), String.class)
        );

        int value = jsonObject.get("value").getAsInt();
        buffer.put(value, jsonObject);
    }

    @Override
    public MSMessageHandler deepCopy() {
        return new TestCommonMSMessageHandler(buffer);
    }
}
