package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem;

public interface MessageSystem {
    boolean newMessage(Message message);
    void dispose() throws  InterruptedException;
}
