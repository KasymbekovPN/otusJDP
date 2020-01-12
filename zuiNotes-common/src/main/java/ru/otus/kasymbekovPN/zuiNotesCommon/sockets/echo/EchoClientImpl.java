package ru.otus.kasymbekovPN.zuiNotesCommon.sockets.echo;

import com.google.gson.JsonObject;

import java.util.Objects;

public class EchoClientImpl implements EchoClient {

    private final String host;
    private final String entity;
    private final String echoMessageType;
    private final int port;

    public EchoClientImpl(String host, int port, String entity, String echoMessageType) {
        this.host = host;
        this.entity = entity;
        this.echoMessageType = echoMessageType;
        this.port = port;
    }

    @Override
    public JsonObject getUrl() {
        JsonObject url = new JsonObject();
        url.addProperty("host", host);
        url.addProperty("port", port);
        url.addProperty("entity", entity);

        return url;
    }

    @Override
    public String getEchoMessageType() {
        return echoMessageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EchoClientImpl that = (EchoClientImpl) o;
        return port == that.port &&
                Objects.equals(host, that.host) &&
                Objects.equals(entity, that.entity) &&
                Objects.equals(echoMessageType, that.echoMessageType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, entity, echoMessageType, port);
    }
}
