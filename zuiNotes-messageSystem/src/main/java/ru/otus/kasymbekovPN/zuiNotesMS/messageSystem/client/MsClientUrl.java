package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client;

import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddress;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeader;

import java.util.Objects;

public class MsClientUrl {

    private final String host;
    private final int port;
    private final String entity;
    private final String registrationMessageType;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getEntity() {
        return entity;
    }

    public String getRegistrationMessageType() {
        return registrationMessageType;
    }

    public String getUrl(){
        return  host + ":" + String.valueOf(port) + "/" + entity;
    }

    public MsClientUrl(String host, int port, String entity, String registrationMessageType) {
        this.host = host;
        this.port = port;
        this.entity = entity;
        this.registrationMessageType = registrationMessageType;
    }

    public MsClientUrl(MessageAddress messageAddress, String registrationMessageType){
        this.registrationMessageType = registrationMessageType;
        this.entity = messageAddress.getEntity();
        this.host = messageAddress.getHost();
        this.port = messageAddress.getPort();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsClientUrl that = (MsClientUrl) o;
        return port == that.port &&
                Objects.equals(host, that.host) &&
                Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, entity);
    }
}
