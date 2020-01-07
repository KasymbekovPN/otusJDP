package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

///**
// * Класс, реализующий провеку валидности json-объекта. <br><br>
// *
// * {@link JsonCheckerImpl#standardJsonObjects} - мапа эталонных сообщений, солученных из файла
// * {@ling JsonCheckerImpl#FILE_NAME}. <br>
// *
// * {@link JsonCheckerImpl#jsonObject} - проверяемое сообщение. <br><br>
// *
// * {@link JsonCheckerImpl#JsonCheckerImpl()} - в конструкторе производится выгрузка содержимого файла
// * {@link JsonCheckerImpl#FILE_NAME}; в случае удачной выгрузки производится инициализация содержимым файла через
// * {@link JsonCheckerImpl#init(String)}; в случае неудачной выгрузки - через {@link JsonCheckerImpl#defaultInit()}  <br><br>
// *
// * {@link JsonCheckerImpl#init(String)} - в {@link JsonCheckerImpl#standardJsonObjects} добавляются пары, ключи которых
// * соответствуют всем значением перечистения {@link MessageType}, если json-объект, полученный из агрумента метода,
// * содержит соответствующее ключу эталонное сообщение, то значение, соответствующее ключу берется из данного объекта,
// * иначе значение - пусной json-объект. <br>
// *
// * {@link JsonCheckerImpl#defaultInit()} - в {@link JsonCheckerImpl#standardJsonObjects} добавляются пары, ключи которых
// * соответствуют всем значениям перечистления {@link MessageType}, значения по ключам - пустые json-объекты.<br>
// *
// * {@link JsonCheckerImpl#getType()} - возвращает тип проверенного json-сообщения<br>
// *
// * {@link JsonCheckerImpl#setJsonObject(JsonObject, Set)} - сеттер json-сообщения для проверки; передаются :
// * json-сообщение и набор валинды типов. Так же здась производится проверки переданного сообения.<br>
// *
// * {@link JsonCheckerImpl#getJsonObject()} - геттер, проверенного json-сообщения<br>
// *
// * {@link JsonCheckerImpl#parse(Set)} - лпроизводит разбор проверяемого сообщения, в случае обнаружения невалидности
// * сообщения - тип сообщения меняется на {@link MessageType#WRONG_TYPE} и добавляется поле errorDescription<br>
// *
// * {@link JsonCheckerImpl#changeByError(String)} - модифицирует проверяемое json-сообщение меняя его тип на
// * {@link MessageType#WRONG_TYPE} и добавляя поле errorDescription<br>
// *
// * {@link JsonCheckerImpl#traverse(JsonObject, JsonObject, StringBuilder, String)} - осуществляет поуровневый обход,
// * проверяемого сообщения. <br>
// */
//<
public class JsonCheckerImpl implements JsonChecker {

    private static final Logger logger = LoggerFactory.getLogger(JsonCheckerImpl.class);
    private static final String WRONG_TYPE = "WRONG";
    private static final String FILE_NAME = "standardMessages.json";
    private static final Set<String> MANDATORY_FIELDS = new HashSet<String>(){{
        add("type");
        add("request");
        add("uuid");
    }};

//    private Map<String, JsonObject> standardJsonObjects = new HashMap<>();
    //<
    private Map<String, Map<Boolean, JsonObject>> standardJsonObjects = new HashMap<>();

    private JsonObject jsonObject;

    public JsonCheckerImpl() throws Exception {

        this.jsonObject = new JsonObject();

        String status = "";
        String content = "";
        URL resource = getClass().getClassLoader().getResource(FILE_NAME);
        if (resource != null){
            File file = new File(resource.getFile());
            try{
                content = new String(Files.readAllBytes(file.toPath()));
            } catch (IOException ex){
                status = "JsonCheckerImpl : Failed convert file ("+ FILE_NAME +") to string";
                logger.error(status);
                throw new IOException(status);
            }
        } else {
            status = "JsonCheckerImpl : File "+ FILE_NAME + " doesn't exist";
            logger.error(status);
            throw new Exception(status);
        }

        init(content);

        //<
//        String content = "";
//        String fileName = getClass().getClassLoader().getResource(FILE_NAME).getFile();
//
//        if (fileName != null){
//            File file = new File(fileName);
//            if (file.exists()){
//                try {
//                    content = new String(Files.readAllBytes(file.toPath()));
//                } catch (IOException ex) {
//                    logger.warn("JsonCheckerImpl : Failed convert file to string.");
//                }
//            }
//        } else {
//            logger.warn("JsonCheckerImpl : File doesn't exist.");
//        }

        //<
//        if (content.isEmpty()){
//            defaultInit();
//        } else {
//            init(content);
//        }
    }

    //< need checking
    private void init(String content) throws Exception {
        JsonObject loadedJsonObject = (JsonObject) new JsonParser().parse(content);

//        JsonObject common = loadedJsonObject.get("common").getAsJsonObject();
//        JsonObject specific = loadedJsonObject.get("specific").getAsJsonObject();

        for (String messageType : loadedJsonObject.keySet()) {
            JsonObject specificItem = loadedJsonObject.get(messageType).getAsJsonObject();
            //<
//            JsonObject requestMessagesContent = specificItem.get("requestMessageContent").getAsJsonObject();
//            JsonObject responseMessageContent = specificItem.get("responseMessageContent").getAsJsonObject();

            Map<Boolean, JsonObject> mapItem = new HashMap<>();
            mapItem.put(false, specificItem.get("responseMessageContent").getAsJsonObject());
            mapItem.put(true, specificItem.get("requestMessageContent").getAsJsonObject());
            //<
//            mapItem.put(true, common.deepCopy());
//            for (String item : requestMessagesContent.keySet()) {
//                mapItem.get(true).add(item, requestMessagesContent.get(item));
//            }
            //<
//            mapItem.put(false, common.deepCopy());
//            for (String item : responseMessageContent.keySet()) {
//                mapItem.get(false).add(item, responseMessageContent.get(item));
//            }

            standardJsonObjects.put(messageType, mapItem);
        }

        //<
        System.out.println(standardJsonObjects);
    }

    //<
//    private void init(String content){
//        JsonObject parsedContent = (JsonObject) new JsonParser().parse(content);
//        for (MessageType item : MessageType.values()) {
//            String sItem = item.getValue();
//            if (parsedContent.has(sItem)){
//                standardJsonObjects.put(sItem, parsedContent.get(sItem).getAsJsonObject());
//            } else {
//                standardJsonObjects.put(sItem, new JsonObject());
//            }
//        }
//    }
//
//    private void defaultInit(){
//        for (MessageType item : MessageType.values()) {
//            standardJsonObjects.put(item.getValue(), new JsonObject());
//        }
//    }

    @Override
    public String getType() {
        return jsonObject.has("type")
                ? jsonObject.get("type").getAsString()
                : WRONG_TYPE;
        //<
//        return jsonObject.has("type")
//                ? jsonObject.get("type").getAsString()
//                : MessageType.WRONG_TYPE.getValue();
    }

    @Override
    public void setJsonObject(JsonObject jsonObject, Set<String> validTypes) {

        //<
        System.out.println(jsonObject);
        //<

        this.jsonObject = jsonObject;
        parse(validTypes);

        //<
        System.out.println(this.jsonObject);
        //<
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    private void parse(Set<String> validTypes){

        StringBuilder nonexistent = new StringBuilder();
        String delimiter = "";
        for (String mandatoryField : MANDATORY_FIELDS) {
            if (!jsonObject.has(mandatoryField)){
                nonexistent.append(delimiter).append(mandatoryField);
                delimiter = ", ";
            }
        }

        if (nonexistent.toString().isEmpty()){
            String type = jsonObject.get("type").getAsString();
            boolean request = jsonObject.get("request").getAsBoolean();
            if (validTypes.contains(type)){
                StringBuilder errorDescription = new StringBuilder();
                String path = "";
                traverse(jsonObject, standardJsonObjects.get(type).get(request), errorDescription, path);

                if (!errorDescription.toString().isEmpty()){
                    errorDescription.append(" Original Type : ").append(type).append(";");
                    changeByError(errorDescription.toString());
                }
            } else {
                changeByError("Invalid field 'type' : " + type);
            }
        } else {
            changeByError("Nonexistent fields : " + nonexistent);
        }

        //<
//        if (jsonObject.has("type")){
//            if (jsonObject.has("request")){
//                String type = jsonObject.get("type").getAsString();
//                boolean request = jsonObject.get("request").getAsBoolean();
//                if (validTypes.contains(type)){
//                    StringBuilder errorDescription = new StringBuilder();
//                    String path = "";
//                    traverse(jsonObject, standardJsonObjects.get(type).get(request), errorDescription, path);
//
//                    if (!errorDescription.toString().isEmpty()){
//                        errorDescription.append(" Original Type : ").append(type).append(";");
//                        changeByError(errorDescription.toString());
//                    }
//                } else {
//                    changeByError("Invalid field 'type' : " + type);
//                }
//            } else {
//                changeByError("Field 'request' doesn't exist");
//            }
//        } else {
//            changeByError("Field 'type' doesn't exist");
//        }
    }

    private void changeByError(String errorDescription){
        jsonObject.addProperty("type", WRONG_TYPE);
        jsonObject.addProperty("errorDescription", errorDescription);
    }

    private static void traverse(JsonObject jsonObject, JsonObject std, StringBuilder errorDescription, String path){
        Set<String> keys = std.keySet();
        for (String key : keys) {
            String currentPath = path + ":" + key;
            JsonElement stdElement = std.get(key);
            JsonElement element = null;
            if (jsonObject.has(key)){
                element = jsonObject.get(key);
            } else {
                errorDescription.append(" Field '").append(currentPath).append("' doesn't exist;");
            }

            if (stdElement.isJsonObject()){
                if (element != null){
                    if (element.isJsonObject()){
                        traverse(element.getAsJsonObject(), stdElement.getAsJsonObject(), errorDescription, currentPath);
                    } else {
                        errorDescription.append(" Field '").append(currentPath).append("' isn't object;");
                    }
                }
            } else if (stdElement.isJsonArray()){
                if (element != null){
                    if (!element.isJsonArray()){
                        errorDescription.append(" Field '").append(currentPath).append("' isn't array;");
                    }
                }
            } else if (stdElement.isJsonPrimitive()){
                if (element != null){
                    switch (stdElement.getAsString()){
                        case "String":
                            try{
                                element.getAsString();
                            } catch (NumberFormatException ex){
                                errorDescription.append(" Field '").append(currentPath).append("' isn't String;");
                            }
                            break;
                        case "Integer":
                            try {
                                element.getAsInt();
                            } catch (NumberFormatException ex){
                                errorDescription.append(" Field '").append(currentPath).append("' isn't Integer;");
                            }
                            break;
                        default:
                            errorDescription.append(" Field '").append(currentPath).append("' has unknown type;");
                            break;
                    }
                }
            }
        }
    }


    //<
//    private void parse(Set<String> validTypes){
//        if (jsonObject.has("type")){
//            String type = jsonObject.get("type").getAsString();
//            if (validTypes.contains(type)){
//                StringBuilder errorDescription = new StringBuilder();
//                String path = "";
//                traverse(jsonObject, standardJsonObjects.get(type), errorDescription, path);
//
//                if (!errorDescription.toString().isEmpty()){
//                    errorDescription.append(" Original Type : ").append(type).append(";");
//                    changeByError(errorDescription.toString());
//                }
//
//            } else {
//                changeByError("Invalid field 'type' : " + type);
//            }
//        } else {
//            changeByError("Field 'type' doesn't exist");
//        }
//    }
//
//    private void changeByError(String errorDescription){
//        jsonObject.addProperty("type", MessageType.WRONG_TYPE.getValue());
//        jsonObject.addProperty("errorDescription", errorDescription);
//    }
//
//    private static void traverse(JsonObject jsonObject, JsonObject std, StringBuilder errorDescription, String path){
//        Set<String> keys = std.keySet();
//        for (String key : keys) {
//            String currentPath = path + ":" + key;
//            JsonElement stdElement = std.get(key);
//            JsonElement element = null;
//            if (jsonObject.has(key)){
//                element = jsonObject.get(key);
//            } else {
//                errorDescription.append(" Field '").append(currentPath).append("' doesn't exist;");
//            }
//
//            if (stdElement.isJsonObject()){
//                if (element != null){
//                    if (element.isJsonObject()){
//                        traverse(element.getAsJsonObject(), stdElement.getAsJsonObject(), errorDescription, currentPath);
//                    } else {
//                        errorDescription.append(" Field '").append(currentPath).append("' isn't object;");
//                    }
//                }
//            } else if (stdElement.isJsonArray()){
//                if (element != null){
//                    if (!element.isJsonArray()){
//                        errorDescription.append(" Field '").append(currentPath).append("' isn't array;");
//                    }
//                }
//            } else if (stdElement.isJsonPrimitive()){
//                if (element != null){
//                    switch (stdElement.getAsString()){
//                        case "String":
//                            try{
//                                element.getAsString();
//                            } catch (NumberFormatException ex){
//                                errorDescription.append(" Field '").append(currentPath).append("' isn't String;");
//                            }
//                            break;
//                        case "Integer":
//                            try {
//                                element.getAsInt();
//                            } catch (NumberFormatException ex){
//                                errorDescription.append(" Field '").append(currentPath).append("' isn't Integer;");
//                            }
//                            break;
//                        default:
//                            errorDescription.append(" Field '").append(currentPath).append("' has unknown type;");
//                            break;
//                    }
//                }
//            }
//        }
//    }
}
