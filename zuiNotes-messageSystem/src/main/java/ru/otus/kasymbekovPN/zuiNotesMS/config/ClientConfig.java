package ru.otus.kasymbekovPN.zuiNotesMS.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactoryImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.Solus;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.SolusImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

@Configuration
public class ClientConfig {

    private static final String CLIENT_CONFIG = "clientConfig.json";
    private static final String SOLUS_FIELD = "solus";
    private static final String MESSAGES_FIELD = "messages";

    @Bean
    public MsClientCreatorFactory msClientCreatorFactory() throws Exception{
        JsonObject config = loadAndCheckConfig();
        return new MsClientCreatorFactoryImpl(
                config,
                MESSAGES_FIELD
        );
    }

    @Bean
    public Solus solus() throws Exception{
        JsonObject config = loadAndCheckConfig();
        return new SolusImpl(config, SOLUS_FIELD);
    }

    private JsonObject loadAndCheckConfig() throws Exception{

        JsonObject config = new JsonObject();
        URL resource = getClass().getClassLoader().getResource(CLIENT_CONFIG);
        if (resource != null){
            File file = new File(resource.getFile());
            try{
                config = (JsonObject) new JsonParser().parse(
                        new String(Files.readAllBytes(file.toPath()))
                );
            } catch (IOException ex){
                throw new IOException("| JsonCheckerImpl : Failed convert file ("+ CLIENT_CONFIG +") to string |");
            }
        } else {
            throw new Exception("| JsonCheckerImpl : File "+ CLIENT_CONFIG + " doesn't exist |");
        }

        StringBuilder status = new StringBuilder();
        if (config.size() != 0){
            for (String clientEntity : config.keySet()) {
                JsonElement element = config.get(clientEntity);
                if (element.isJsonObject()){
                    JsonObject object = element.getAsJsonObject();

                    if (object.has(SOLUS_FIELD)){
                        JsonElement solusElement = object.get(SOLUS_FIELD);
                        if (!solusElement.isJsonPrimitive() ||
                            !solusElement.getAsJsonPrimitive().isBoolean()){
                            status.append("| '").append(clientEntity).append("' '").append(SOLUS_FIELD)
                                    .append("' value type isn't boolean |");
                        }
                    } else {
                        status.append("| object '").append(clientEntity).append("' doesn't contain field (bool) '")
                                .append(SOLUS_FIELD).append("' |");
                    }

                    if (object.has(MESSAGES_FIELD)){
                        JsonElement messagesElement = object.get(MESSAGES_FIELD);
                        if (messagesElement.isJsonArray()){
                            JsonArray array = messagesElement.getAsJsonArray();
                            for (JsonElement arrayElement : array) {
                                if (!arrayElement.isJsonPrimitive() ||
                                    !arrayElement.getAsJsonPrimitive().isString()){

                                    status.append("| field '").append(MESSAGES_FIELD).append("' of '")
                                            .append(clientEntity).append("' contains item ")
                                            .append(arrayElement.toString()).append(" isn't string |");
                                }
                            }
                        } else {
                            status.append("| '").append(clientEntity).append("' '").append(MESSAGES_FIELD)
                                    .append("' value type isn't array |");
                        }
                    } else {
                        status.append("| object '").append(clientEntity).append("' doesn't contain field (bool) '")
                                .append(MESSAGES_FIELD).append("' |");
                    }
                } else {
                    status.append("| element '").append(clientEntity).append("' isn't object | ");
                }
            }
        } else {
            status.append("| '").append(CLIENT_CONFIG).append("' doesn't contain any one object |");
        }

        if (!status.toString().isEmpty()){
            throw new Exception(status.toString());
        }

        return config;
    }
}
