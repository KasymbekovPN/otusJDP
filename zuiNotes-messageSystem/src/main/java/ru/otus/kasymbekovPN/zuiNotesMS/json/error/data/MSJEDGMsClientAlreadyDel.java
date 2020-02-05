package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class MSJEDGMsClientAlreadyDel extends BaseJEDG implements JsonErrorDataGenerator {

    public MSJEDGMsClientAlreadyDel(String url) {
        super(MSErrorCode.MS_CLIENT_ALREADY_DEL.getCode());
        data.addProperty("url", url);
    }
}
