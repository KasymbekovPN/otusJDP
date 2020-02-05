package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

public class CommonJEDGInvalidFieldType extends BaseJEDG implements JsonErrorDataGenerator{

    public CommonJEDGInvalidFieldType(String type, String field) {
        super(CommonErrorCode.INVALID_MESSAGE_TYPE.getCode());
        data.addProperty("type", type);
        data.addProperty("field", field);
    }
}
