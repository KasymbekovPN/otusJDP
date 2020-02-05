package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.BaseJEDG;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.JsonErrorDataGenerator;

public class MSJEDGFieldRequestIsWrong extends BaseJEDG implements JsonErrorDataGenerator {

    public MSJEDGFieldRequestIsWrong() {
        super(MSErrorCode.FIELD_REQUEST_IS_WRONG.getCode());
    }
}
