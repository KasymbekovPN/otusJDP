package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import ru.otus.kasymbekovPN.zuiNotesCommon.entity.Entity;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.CmnMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.MsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.WrongMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.WrongMSMessageHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class MsClientCreatorFactoryImpl implements MsClientCreatorFactory {
//    private final Map<Entity, MsClientCreator> creators = new HashMap<Entity, MsClientCreator>(){{
//        put(Entity.DATABASE, new CmnMsClientCreator(MessageType.AUTH_USER_REQUEST, MessageType.ADD_USER_REQUEST, MessageType.DEL_USER_REQUEST));
//        put(Entity.FRONTEND, new CmnMsClientCreator(MessageType.AUTH_USER_RESPONSE, MessageType.ADD_USER_RESPONSE, MessageType.DEL_USER_RESPONSE));
//        put(Entity.MESSAGE_SYSTEM, new WrongMsClientCreator());
//        put(Entity.UNKNOWN, new WrongMsClientCreator());
//    }};
//
//    public MsClientCreatorFactoryImpl() throws Exception {
//        StringBuilder status = new StringBuilder();
//        for (Entity entity : Entity.values()) {
//            if (!creators.containsKey(entity)){
//                status.append(" ").append(entity.getValue()).append(";");
//            }
//        }
//
//        if (!status.toString().isEmpty()){
//            throw new Exception("Creators is not complete : " + status);
//        }
//    }
    //<

    private static final String CLIENT_CONFIG = "clientConfig.json";

//    private final Map<String, MsClientCreator> creators = new HashMap<>();
    //<
    private final JsonObject config;

    public MsClientCreatorFactoryImpl() throws Exception {

        String status = "";
        String content = "";
        URL resource = getClass().getClassLoader().getResource(CLIENT_CONFIG);
        if (resource != null){
            File file = new File(resource.getFile());
            try{
                content = new String(Files.readAllBytes(file.toPath()));
            } catch (IOException ex){
                status = "JsonCheckerImpl : Failed convert file ("+ CLIENT_CONFIG +") to string";
                throw new IOException(status);
            }
        } else {
            status = "JsonCheckerImpl : File "+ CLIENT_CONFIG + " doesn't exist";
            throw new Exception(status);
        }

        //< add checking
        this.config = (JsonObject) new JsonParser().parse(content);
        //<
//        JsonArray registrationMessages = loadedJsonObject.get("registrationMessages").getAsJsonArray();
//        JsonArray commonMessages = loadedJsonObject.get("commonMessages").getAsJsonArray();

//        this.creators = creators;
    }

    @Override
    public MsClientCreator get(String entity) {

//        Set<String> messages = new HashSet<>();
//        for (String entity : config.keySet()) {
//            messages.add(element.getAsString());
//        }
//        return config.has(entity)
//                ? new CmnMsClientCreator(messages)
//                : new WrongMsClientCreator();
        //<

        //<
        System.out.println(config);

        if (config.has(entity)){
            Set<String> messages = new HashSet<>();
            for (JsonElement element : config.get(entity).getAsJsonObject().get("messages").getAsJsonArray()) {
                messages.add(element.getAsString());
            }
            return new CmnMsClientCreator(messages);
        } else {
            return new WrongMsClientCreator();
        }
    }

    //<
//    @Override
//    public MsClientCreator get(Entity entity) {
//        return creators.get(entity);
//    }
}
