package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

public class CommonJEDGFieldDoesntExist extends BaseJEDG implements JsonErrorDataGenerator{

    public CommonJEDGFieldDoesntExist(String field) {
        super(ErrorCode.FIELD_DOESNT_EXIST.getCode());
        data.addProperty("field", field);
    }
}
