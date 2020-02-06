package ru.otus.kasymbekovPN.zuiNotesCommon.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.*;

import java.io.*;
import java.util.*;

/**
 * Класс, реализующий провеку валидности json-объекта. <br><br>
 *
 * {@link JsonCheckerImpl#standardJsonObjects} - мапа эталонных сообщений, солученных из файла
 * {@ling JsonCheckerImpl#FILE_NAME}. <br>
 *
 * {@link JsonCheckerImpl#jsonObject} - проверяемое сообщение. <br><br>
 *
 * {@link JsonCheckerImpl#init(String)} - в {@link JsonCheckerImpl#standardJsonObjects} добавляются пары, ключи которых
 * соответствуют всем значением перечистения , если json-объект, полученный из агрумента метода,
 * содержит соответствующее ключу эталонное сообщение, то значение, соответствующее ключу берется из данного объекта,
 * иначе значение - пусной json-объект. <br>
 *
 * {@link JsonCheckerImpl#getType()} - возвращает тип проверенного json-сообщения<br>
 *
 * {@link JsonCheckerImpl#setJsonObject(JsonObject, Set)} - сеттер json-сообщения для проверки; передаются :
 * json-сообщение и набор валинды типов. Так же здась производится проверки переданного сообения.<br>
 *
 * {@link JsonCheckerImpl#getJsonObject()} - геттер, проверенного json-сообщения<br>
 *
 * {@link JsonCheckerImpl#parse(Set)} - лпроизводит разбор проверяемого сообщения, в случае обнаружения невалидности
 * сообщения - тип сообщения меняется на WRONG_TYPE и добавляется поле errorDescription<br>
 */
public class JsonCheckerImpl implements JsonChecker {
    private static final String WRONG_TYPE = "WRONG";
    private static final String FILE_NAME = "/standardMessages.json";

    private static final Set<String> MANDATORY_HEADER_FIELDS = new HashSet<>(){{
        add("type");
        add("request");
        add("uuid");
    }};
    private static final String MESSAGE_HEADER_FIELD = "header";

    private final JsonErrorGenerator jeGenerator;

    private Map<String, Map<Boolean, JsonObject>> standardJsonObjects = new HashMap<>();
    private JsonObject jsonObject;

    public JsonCheckerImpl(JsonErrorGenerator jeGenerator) throws Exception {
        this.jeGenerator = jeGenerator;
        this.jsonObject = new JsonObject();

        StringBuilder content = new StringBuilder();
        InputStream in = getClass().getResourceAsStream(FILE_NAME);
        if (in != null){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line);
            }
        } else {
            throw new Exception("JsonCheckerImpl : File "+ FILE_NAME + " doesn't exist");
        }

        init(String.valueOf(content));
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
        return jsonObject.get(MESSAGE_HEADER_FIELD).getAsJsonObject().get("type").getAsString();
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
        JsonObject header = new JsonObject();
        if (jsonObject.has(MESSAGE_HEADER_FIELD)) {
            header = jsonObject.get(MESSAGE_HEADER_FIELD).getAsJsonObject();
            original.add(MESSAGE_HEADER_FIELD, new JsonObject());

            for (String mandatoryHeaderField : MANDATORY_HEADER_FIELDS) {
                if (!header.has(mandatoryHeaderField)){
                    errors.add(
                            jeGenerator
                                    .handle(true, CommonErrorCode.FIELD_DOESNT_EXIST.getCode())
                                    .set("field", mandatoryHeaderField)
                                    .get()
                    );
                } else {
                    original.get(MESSAGE_HEADER_FIELD).getAsJsonObject().add(mandatoryHeaderField, header.get(mandatoryHeaderField));
                }
            }
        } else {
            errors.add(
                    jeGenerator
                        .handle(true, CommonErrorCode.FIELD_DOESNT_EXIST.getCode())
                        .set("field", MESSAGE_HEADER_FIELD)
                        .get()
            );
        }

        if (errors.size() == 0){
            String type = header.get("type").getAsString();
            boolean request = header.get("request").getAsBoolean();
            if (validTypes.contains(type)){
                String path = "";
                traverse(jsonObject, standardJsonObjects.get(type).get(request), errors, path);
            } else {
                errors.add(
                        jeGenerator
                            .handle(true, CommonErrorCode.INVALID_MESSAGE_TYPE.getCode())
                            .set("type", type)
                            .get()
                );
            }
        }

        if (errors.size() != 0) {
            this.jsonObject = new JsonBuilderImpl(this.jsonObject.deepCopy())
                    .add(
                            MESSAGE_HEADER_FIELD,
                            new JsonBuilderImpl()
                            .add("type", WRONG_TYPE)
                            .add("request", false)
                            .add("uuid", UUID.randomUUID().toString())
                            .get()
                    )
                    .add("original", original)
                    .add("errors", errors)
                    .get();
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
                        jeGenerator
                            .handle(true, CommonErrorCode.FIELD_DOESNT_EXIST.getCode())
                            .set("field", currentPath)
                            .get()
                );
            }

            if (stdElement.isJsonObject()){
                if (element != null){
                    if (element.isJsonObject()){
                        traverse(element.getAsJsonObject(), stdElement.getAsJsonObject(), errors, currentPath);
                    } else {
                        errors.add(
                                jeGenerator
                                    .handle(true, CommonErrorCode.INVALID_FIELD_TYPE.getCode())
                                    .set("type", "Object")
                                    .set("field", currentPath)
                                    .get()
                        );
                    }
                }
            } else if (stdElement.isJsonArray()){
                if (element != null){
                    if (!element.isJsonArray()){
                        errors.add(
                                jeGenerator
                                        .handle(true, CommonErrorCode.INVALID_FIELD_TYPE.getCode())
                                        .set("type", "Array")
                                        .set("field", currentPath)
                                        .get()
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
                                        jeGenerator
                                                .handle(true, CommonErrorCode.INVALID_FIELD_TYPE.getCode())
                                                .set("type", "String")
                                                .set("field", currentPath)
                                                .get()
                                );
                            }
                            break;
                        case "Integer":
                            try {
                                element.getAsInt();
                            } catch (NumberFormatException ex){
                                errors.add(
                                        jeGenerator
                                                .handle(true, CommonErrorCode.INVALID_FIELD_TYPE.getCode())
                                                .set("type", "Integer")
                                                .set("field", currentPath)
                                                .get()
                                );
                            }
                            break;
                        case "Boolean":
                            try{
                                element.getAsBoolean();
                            } catch (NumberFormatException ex){
                                errors.add(
                                        jeGenerator
                                                .handle(true, CommonErrorCode.INVALID_FIELD_TYPE.getCode())
                                                .set("type", "Boolean")
                                                .set("field", currentPath)
                                                .get()
                                );
                            }
                            break;
                        default:
                            errors.add(
                                    jeGenerator
                                        .handle(true, CommonErrorCode.UNKNOWN_FIELD_TYPE.getCode())
                                        .set("field", currentPath)
                                        .get()
                            );
                            break;
                    }
                }
            }
        }
    }
}
