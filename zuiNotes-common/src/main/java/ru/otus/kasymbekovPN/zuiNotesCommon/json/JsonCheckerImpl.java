package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;

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
public class JsonCheckerImpl implements JsonChecker {

    private static final Logger logger = LoggerFactory.getLogger(JsonCheckerImpl.class);
    private static final String WRONG_TYPE = "WRONG";
    private static final String FILE_NAME = "standardMessages.json";
    private static final Set<String> MANDATORY_FIELDS = new HashSet<String>(){{
        add("type");
        add("request");
        add("uuid");
    }};

    private final JsonErrorObjectGenerator jeoGenerator;

    private Map<String, Map<Boolean, JsonObject>> standardJsonObjects = new HashMap<>();
    private JsonObject jsonObject;

    public JsonCheckerImpl(JsonErrorObjectGenerator jeoGenerator) throws Exception {
        this.jeoGenerator = jeoGenerator;
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
    public void setJsonObject(JsonObject jsonObject, Set<String> validTypes) throws Exception {
        this.jsonObject = jsonObject;
        parse(validTypes);
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    private void parse(Set<String> validTypes) throws Exception {

        JsonObject original = new JsonObject();
        JsonArray errors = new JsonArray();
        for (String mandatoryField : MANDATORY_FIELDS) {
            if (!jsonObject.has(mandatoryField)){
                errors.add(
                        jeoGenerator.generate(1, mandatoryField)
                );
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
                errors.add(
                        jeoGenerator.generate(2, type)
                );
            }
        }

        if (errors.size() != 0) {
            jsonObject.addProperty("type", "WRONG");
            jsonObject.addProperty("request", false);
            jsonObject.addProperty("uuid", UUID.randomUUID().toString());
            jsonObject.add("original", original);
            jsonObject.add("errors", errors);
        }
    }

    private void traverse(JsonObject jsonObject, JsonObject std, JsonArray errors, String path) throws Exception {
        Set<String> keys = std.keySet();
        for (String key : keys) {
            String currentPath = path + ":" + key;
            JsonElement stdElement = std.get(key);
            JsonElement element = null;
            if (jsonObject.has(key)){
                element = jsonObject.get(key);
            } else {
                errors.add(
                        jeoGenerator.generate(1, currentPath)
                );
            }

            if (stdElement.isJsonObject()){
                if (element != null){
                    if (element.isJsonObject()){
                        traverse(element.getAsJsonObject(), stdElement.getAsJsonObject(), errors, currentPath);
                    } else {
                        errors.add(
                                jeoGenerator.generate(3, "Object", currentPath)
                        );
                    }
                }
            } else if (stdElement.isJsonArray()){
                if (element != null){
                    if (!element.isJsonArray()){
                        errors.add(
                                jeoGenerator.generate(3, "Array", currentPath)
                        );
                    }
                }
            } else if (stdElement.isJsonPrimitive()){
                if (element != null){
                    switch (stdElement.getAsString()){
                        case "String":
                            try{
                                element.getAsString();
                            } catch (NumberFormatException ex){
                                errors.add(
                                        jeoGenerator.generate(3, "String", currentPath)
                                );
                            }
                            break;
                        case "Integer":
                            try {
                                element.getAsInt();
                            } catch (NumberFormatException ex){
                                errors.add(
                                        jeoGenerator.generate(3, "Integer", currentPath)
                                );
                            }
                            break;
                        case "Boolean":
                            try{
                                element.getAsBoolean();
                            } catch (NumberFormatException ex){
                                errors.add(
                                        jeoGenerator.generate(3, "Boolean", currentPath)
                                );
                            }
                            break;
                        default:
                            errors.add(
                                    jeoGenerator.generate(4, currentPath)
                            );
                            break;
                    }
                }
            }
        }
    }
}
