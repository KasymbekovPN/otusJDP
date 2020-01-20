package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client;

import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.MSMessageHandler;

public interface MSClient {
    void addHandler(String type, MSMessageHandler handler);
    boolean sendMessage(Message message);
    void handle(Message message);
    MsClientUrl getUrl();
    <T> Message produceMessage(MsClientUrl toUrl, T data, String type);
}
