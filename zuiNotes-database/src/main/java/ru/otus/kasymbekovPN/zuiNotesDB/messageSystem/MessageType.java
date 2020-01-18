package ru.otus.kasymbekovPN.zuiNotesDB.messageSystem;

public enum MessageType {
    WRONG("WRONG"),
    I_AM("I_AM"),
    LOGIN("LOGIN"),
    USER_DATA("USER_DATA"),
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
