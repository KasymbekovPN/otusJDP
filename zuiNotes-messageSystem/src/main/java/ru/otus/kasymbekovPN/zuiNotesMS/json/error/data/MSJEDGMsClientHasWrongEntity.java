package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class MSJEDGMsClientHasWrongEntity extends BaseJEDG implements JsonErrorDataGenerator {

    public MSJEDGMsClientHasWrongEntity(String entity) {
        super(ErrorCode.MS_CLIENT_HAS_WRONG_ENTITY.getCode());
        data.addProperty("entity", entity);
    }
}
