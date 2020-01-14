package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class MSJEDGToMsClientDoesntExist extends BaseJEDG implements JsonErrorDataGenerator {

    public MSJEDGToMsClientDoesntExist(String client) {
        super(ErrorCode.TO_MS_CLIENT_DOESNT_EXIST.getCode());
        data.addProperty("client", client);
    }
}
