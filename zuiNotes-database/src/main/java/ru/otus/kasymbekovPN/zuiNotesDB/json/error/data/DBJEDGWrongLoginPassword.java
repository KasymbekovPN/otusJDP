package ru.otus.kasymbekovPN.zuiNotesDB.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class DBJEDGWrongLoginPassword extends BaseJEDG implements JsonErrorDataGenerator {
    public DBJEDGWrongLoginPassword() {
        super(DBErrorCode.WRONG_LOGIN_PASSWORD.getCode());
    }
}
