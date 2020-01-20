package ru.otus.kasymbekovPN.zuiNotesFE.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class FEJEDGInvalidLogin extends BaseJEDG implements JsonErrorDataGenerator {
    public FEJEDGInvalidLogin(String login) {
        super(ErrorCode.INVALID_LOGIN.getCode());
        data.addProperty("login", login);
    }
}
