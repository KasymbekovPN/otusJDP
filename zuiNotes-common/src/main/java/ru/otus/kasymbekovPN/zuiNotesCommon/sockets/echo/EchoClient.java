package ru.otus.kasymbekovPN.zuiNotesCommon.sockets.echo;

import com.google.gson.JsonObject;

public interface EchoClient {
    JsonObject getUrl();
    String getEchoMessageType();
}
