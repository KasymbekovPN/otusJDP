package ru.otus.kasymbekovPN.zuiNotesDB.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class DBJEDGUserAlreadyExist extends BaseJEDG implements JsonErrorDataGenerator {
    public DBJEDGUserAlreadyExist() {
        super(DBErrorCode.USER_ALREADY_EXIST.getCode());
    }
}
