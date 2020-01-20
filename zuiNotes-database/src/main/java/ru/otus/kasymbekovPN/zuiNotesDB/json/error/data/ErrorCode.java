package ru.otus.kasymbekovPN.zuiNotesDB.json.error.data;

public enum ErrorCode {
    WRONG_LOGIN_PASSWORD("WRONG_LOGIN_PASSWORD", 1),
    EMPTY_LOGIN_PASSWORD("EMPTY_LOGIN_PASSWORD", 2),
    WRONG_RIGHTS("WRONG_RIGHTS", 3),
    USER_ALREADY_EXIST("USER_ALREADY_EXIST", 4),
    USER_DOESNT_EXIST("USER_DOESNT_EXIST", 5);

    private final String name;
    private final int code;

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    ErrorCode(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
