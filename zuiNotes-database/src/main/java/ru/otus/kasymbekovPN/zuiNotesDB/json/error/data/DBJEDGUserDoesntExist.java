package ru.otus.kasymbekovPN.zuiNotesDB.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class DBJEDGUserDoesntExist extends BaseJEDG implements JsonErrorDataGenerator {
    public DBJEDGUserDoesntExist() {
        super(DBErrorCode.USER_DOESNT_EXIST.getCode());
    }
}
