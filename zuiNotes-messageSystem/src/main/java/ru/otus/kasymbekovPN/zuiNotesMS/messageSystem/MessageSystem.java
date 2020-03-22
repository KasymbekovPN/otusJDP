package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem;

public interface MessageSystem {
    boolean newMessage(MSMessage MSMessage);
    void dispose() throws  InterruptedException;
}
