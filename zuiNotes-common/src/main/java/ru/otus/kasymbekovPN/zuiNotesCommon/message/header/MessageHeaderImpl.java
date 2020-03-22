package ru.otus.kasymbekovPN.zuiNotesCommon.message.header;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

@JsonTypeName("MessageHeaderImpl")
public class MessageHeaderImpl implements MessageHeader {

    private String entity;
    private String host;
    private int port;

    @JsonGetter("entity")
    @Override
    public String getEntity() {
        return entity;
    }

    @JsonGetter("host")
    @Override
    public String getHost() {
        return host;
    }

    @JsonGetter("port")
    @Override
    public int getPort() {
        return port;
    }

    @JsonCreator
    public MessageHeaderImpl(
            @JsonProperty("entity") String entity,
            @JsonProperty("host") String host,
            @JsonProperty("port") int port) {
        this.entity = entity;
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageHeaderImpl that = (MessageHeaderImpl) o;
        return port == that.port &&
                Objects.equals(entity, that.entity) &&
                Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, host, port);
    }

    @Override
    public String toString() {
        return "MessageHeaderImpl{" +
                "entity='" + entity + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
