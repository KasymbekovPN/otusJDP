package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class MSJEDGFromMsClientDoesntExist extends BaseJEDG implements JsonErrorDataGenerator {

    public MSJEDGFromMsClientDoesntExist(String client) {
        super(MSErrorCode.FROM_MS_CLIENT_DOESNT_EXIST.getCode());
        data.addProperty("client", client);
    }
}
