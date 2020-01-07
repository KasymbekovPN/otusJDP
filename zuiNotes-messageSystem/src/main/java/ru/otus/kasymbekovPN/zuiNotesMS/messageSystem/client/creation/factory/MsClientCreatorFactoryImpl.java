package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.CmnMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.MsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.WrongMsClientCreator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@Service
public class MsClientCreatorFactoryImpl implements MsClientCreatorFactory {

    private static final String CLIENT_CONFIG = "clientConfig.json";
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
    }

    @Override
    public MsClientCreator get(String entity) {
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
}
