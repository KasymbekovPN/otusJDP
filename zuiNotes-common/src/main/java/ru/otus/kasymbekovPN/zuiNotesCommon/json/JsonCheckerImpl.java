package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonArray;
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
import java.util.*;

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
    }

    //< need checking
    private void init(String content) throws Exception {
        JsonObject loadedJsonObject = (JsonObject) new JsonParser().parse(content);

        for (String messageType : loadedJsonObject.keySet()) {
            JsonObject specificItem = loadedJsonObject.get(messageType).getAsJsonObject();

            Map<Boolean, JsonObject> mapItem = new HashMap<>();
            mapItem.put(false, specificItem.get("responseMessageContent").getAsJsonObject());
            mapItem.put(true, specificItem.get("requestMessageContent").getAsJsonObject());

            standardJsonObjects.put(messageType, mapItem);
        }
    }

    @Override
    public String getType() {
        return jsonObject.get("type").getAsString();
    }

    @Override
    public void setJsonObject(JsonObject jsonObject, Set<String> validTypes) {
        this.jsonObject = jsonObject;
        parse(validTypes);
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    private void parse(Set<String> validTypes){

        JsonObject original = new JsonObject();
        JsonArray errors = new JsonArray();
        for (String mandatoryField : MANDATORY_FIELDS) {
            if (!jsonObject.has(mandatoryField)){
                JsonObject err = new JsonObject();
                err.addProperty("code", 3);
                err.addProperty("field", mandatoryField);
                errors.add(err);
            } else {
                original.add(mandatoryField, jsonObject.get(mandatoryField));
            }
        }

        if (errors.size() == 0){
            String type = jsonObject.get("type").getAsString();
            boolean request = jsonObject.get("request").getAsBoolean();
            if (validTypes.contains(type)){
                String path = "";
                traverse(jsonObject, standardJsonObjects.get(type).get(request), errors, path);
            } else {
                JsonObject err = new JsonObject();
                err.addProperty("code", 4);
                err.addProperty("type", type);
                errors.add(err);
            }
        }

        if (errors.size() != 0) {
//            jsonObject = new JsonObject();
            //<
            jsonObject.addProperty("type", "WRONG");
            jsonObject.addProperty("request", false);
            jsonObject.addProperty("uuid", UUID.randomUUID().toString());
            jsonObject.add("original", original);
            jsonObject.add("errors", errors);
        }

        //<
//        StringBuilder nonexistent = new StringBuilder();
//        String delimiter = "";
//        for (String mandatoryField : MANDATORY_FIELDS) {
//            if (!jsonObject.has(mandatoryField)){
//                nonexistent.append(delimiter).append(mandatoryField);
//                delimiter = ", ";
//            }
//        }
//
//        if (nonexistent.toString().isEmpty()){
//            String type = jsonObject.get("type").getAsString();
//            boolean request = jsonObject.get("request").getAsBoolean();
//            if (validTypes.contains(type)){
//                StringBuilder errorDescription = new StringBuilder();
//                String path = "";
//                traverse(jsonObject, standardJsonObjects.get(type).get(request), errorDescription, path);
//
//                if (!errorDescription.toString().isEmpty()){
//                    errorDescription.append(" Original Type : ").append(type).append(";");
//                    changeByError(errorDescription.toString());
//                }
//            } else {
//                changeByError("Invalid field 'type' : " + type);
//            }
//        } else {
//            changeByError("Nonexistent fields : " + nonexistent);
//        }
    }

    //<
//    private void changeByError(String errorDescription){
//        jsonObject.addProperty("type", WRONG_TYPE);
//        jsonObject.addProperty("errorDescription", errorDescription);
//    }

    private static void traverse(JsonObject jsonObject, JsonObject std, JsonArray errors, String path){
        Set<String> keys = std.keySet();
        for (String key : keys) {
            String currentPath = path + ":" + key;
            JsonElement stdElement = std.get(key);
            JsonElement element = null;
            if (jsonObject.has(key)){
                element = jsonObject.get(key);
            } else {
                JsonObject err = new JsonObject();
                err.addProperty("code", 3);
                err.addProperty("field", currentPath);
                errors.add(err);
            }

            if (stdElement.isJsonObject()){
                if (element != null){
                    if (element.isJsonObject()){
                        traverse(element.getAsJsonObject(), stdElement.getAsJsonObject(), errors, currentPath);
                    } else {
                        JsonObject err = new JsonObject();
                        err.addProperty("code", 5);
                        err.addProperty("type", "Object");
                        errors.add(err);
                    }
                }
            } else if (stdElement.isJsonArray()){
                if (element != null){
                    if (!element.isJsonArray()){
                        JsonObject err = new JsonObject();
                        err.addProperty("code", 5);
                        err.addProperty("type", "Array");
                        errors.add(err);
                    }
                }
            } else if (stdElement.isJsonPrimitive()){
                if (element != null){
                    switch (stdElement.getAsString()){
                        case "String":
                            try{
                                element.getAsString();
                            } catch (NumberFormatException ex){
                                JsonObject err = new JsonObject();
                                err.addProperty("code", 5);
                                err.addProperty("type", "String");
                                errors.add(err);
                            }
                            break;
                        case "Integer":
                            try {
                                element.getAsInt();
                            } catch (NumberFormatException ex){
                                JsonObject err = new JsonObject();
                                err.addProperty("code", 5);
                                err.addProperty("type", "Integer");
                                errors.add(err);
                            }
                            break;
                        default:
                            JsonObject err = new JsonObject();
                            err.addProperty("code", 6);
                            errors.add(err);
                            break;
                    }
                }
            }
        }
    }
    //<
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
