package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class MSJEDGMsClientAlreadyExist extends BaseJEDG implements JsonErrorDataGenerator {

    public MSJEDGMsClientAlreadyExist(String url) {
        super(MSErrorCode.MS_CLIENT_ALREADY_EXIST.getCode());
        data.addProperty("url", url);
    }
}
