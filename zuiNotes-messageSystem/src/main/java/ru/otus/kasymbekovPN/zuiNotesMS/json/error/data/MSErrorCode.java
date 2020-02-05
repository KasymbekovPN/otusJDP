package ru.otus.kasymbekovPN.zuiNotesMS.json.error.data;

public enum MSErrorCode {
    FROM_MS_CLIENT_DOESNT_EXIST("FROM_MS_CLIENT_DOESNT_EXIST", 1),
    TO_MS_CLIENT_DOESNT_EXIST("TO_MS_CLIENT_DOESNT_EXIST", 2),
    MS_CLIENT_ALREADY_EXIST("MS_CLIENT_ALREADY_EXIST", 3),
    MS_CLIENT_HAS_WRONG_ENTITY("MS_CLIENT_HAS_WRONG_ENTITY", 4),
    SOLUS_REG("SOLUS_REG", 5),
    FIELD_REQUEST_IS_WRONG("FIELD_REQUEST_IS_WRONG", 6),
    MS_CLIENT_ALREADY_DEL("MS_CLIENT_ALREADY_DEL", 7);

    private final String name;
    private final int code;

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    MSErrorCode(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
