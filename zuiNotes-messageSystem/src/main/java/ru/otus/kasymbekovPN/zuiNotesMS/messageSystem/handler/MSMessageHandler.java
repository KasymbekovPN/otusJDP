package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler;

import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MSMessage;

public interface MSMessageHandler {
    void handle(MSMessage MSMessage);
    MSMessageHandler deepCopy();
}
