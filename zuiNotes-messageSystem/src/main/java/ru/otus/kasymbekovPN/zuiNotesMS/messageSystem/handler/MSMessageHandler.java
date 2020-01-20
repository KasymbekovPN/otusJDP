package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler;

import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;

public interface MSMessageHandler {
    void handle(Message message);
    MSMessageHandler deepCopy();
}
