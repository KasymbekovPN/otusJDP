package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

public interface RequestRegistrar {
    void set(String requestUUID, String UIId);
    String get(String requestUUID);
}
