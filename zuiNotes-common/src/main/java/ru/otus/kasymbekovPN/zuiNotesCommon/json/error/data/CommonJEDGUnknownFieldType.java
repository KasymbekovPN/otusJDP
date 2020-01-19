package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

public class CommonJEDGUnknownFieldType extends BaseJEDG implements JsonErrorDataGenerator{

    public CommonJEDGUnknownFieldType(String field) {
        super(ErrorCode.UNKNOWN_FIELD_TYPE.getCode());
        data.addProperty("field", field);
    }
}
