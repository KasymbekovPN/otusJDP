package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client;

import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MSMessage;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.MSMessageHandler;

public interface MSClient {
    void addHandler(String type, MSMessageHandler handler);
    boolean sendMessage(MSMessage MSMessage);
    void handle(MSMessage MSMessage);
    MsClientUrl getUrl();
    <T> MSMessage produceMessage(MsClientUrl toUrl, T data, String type);
}
