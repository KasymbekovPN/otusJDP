package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

public class CommonJEDGInvalidMessageType extends BaseJEDG implements JsonErrorDataGenerator {

    public CommonJEDGInvalidMessageType(String type) {
        super(ErrorCode.INVALID_FIELD_TYPE.getCode());
        data.addProperty("type", type);
    }
}
