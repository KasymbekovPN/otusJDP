package ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data;

public enum CommonErrorCode {
    FIELD_DOESNT_EXIST("FIELD_DOESNT_EXIST", 1),
    INVALID_MESSAGE_TYPE("INVALID_MESSAGE_TYPE", 2),
    INVALID_FIELD_TYPE("INVALID_FIELD_TYPE", 3),
    UNKNOWN_FIELD_TYPE("UNKNOWN_FIELD_TYPE", 4);

    private final String name;
    private final int code;

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    CommonErrorCode(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
