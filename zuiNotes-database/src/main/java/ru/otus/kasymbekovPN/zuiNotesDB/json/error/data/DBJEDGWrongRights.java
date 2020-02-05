package ru.otus.kasymbekovPN.zuiNotesDB.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class DBJEDGWrongRights extends BaseJEDG implements JsonErrorDataGenerator {
    public DBJEDGWrongRights() {
        super(DBErrorCode.WRONG_RIGHTS.getCode());
    }
}
