package ru.otus.kasymbekovPN.zuiNotesCommon.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;

public class ClientUrl {

    private String host;
    private int port;
    private String entity;
    private String message;

    public ClientUrl(String host, int port, String entity, String message) {
        this.host = host;
        this.port = port;
        this.entity = entity;
        this.message = message;
    }

    public JsonObject getJsonUrl(){
        JsonObject url = new JsonObject();
        url.addProperty("host", host);
        url.addProperty("port", port);
        url.addProperty("entity", entity);

        return url;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientUrl clientUrl = (ClientUrl) o;
        return port == clientUrl.port &&
                Objects.equals(host, clientUrl.host) &&
                Objects.equals(entity, clientUrl.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, entity);
    }
}
