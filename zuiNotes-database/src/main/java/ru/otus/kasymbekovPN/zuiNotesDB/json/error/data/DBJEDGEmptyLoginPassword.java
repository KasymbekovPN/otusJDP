package ru.otus.kasymbekovPN.zuiNotesDB.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class DBJEDGEmptyLoginPassword extends BaseJEDG implements JsonErrorDataGenerator {
    public DBJEDGEmptyLoginPassword() {
        super(DBErrorCode.EMPTY_LOGIN_PASSWORD.getCode());
    }
}
