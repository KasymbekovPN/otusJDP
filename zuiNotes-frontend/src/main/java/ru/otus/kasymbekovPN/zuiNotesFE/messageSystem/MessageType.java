package ru.otus.kasymbekovPN.zuiNotesFE.messageSystem;

public enum MessageType {
    WRONG("WRONG"),
    I_AM("I_AM"),
    AUTH_USER("AUTH_USER"),
    ADD_USER("ADD_USER"),
    DEL_USER("DEL_USER");

    private String value;

    public String getValue() {
        return value;
    }

    MessageType(String value) {
        this.value = value;
    }
}
