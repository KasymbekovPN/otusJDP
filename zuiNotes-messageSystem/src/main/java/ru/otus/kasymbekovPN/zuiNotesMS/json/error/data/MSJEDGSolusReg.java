package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class MSJEDGSolusReg extends BaseJEDG implements JsonErrorDataGenerator {

    public MSJEDGSolusReg(String entity) {
        super(ErrorCode.SOLUS_REG.getCode());
        data.addProperty("entity", entity);
    }
}
