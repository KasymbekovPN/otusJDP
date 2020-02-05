package ru.otus.kasymbekovPN.zuiNotesFE.json.error.data;

public enum FEErrorCode {
    INVALID_LOGIN("INVALID_LOGIN", 1);

    private final String name;
    private final int code;

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    FEErrorCode(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
