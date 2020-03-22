package ru.otus.kasymbekovPN.zuiNotesCommon.message.address;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

@JsonTypeName("MessageAddressImpl")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageAddressImpl implements MessageAddress {
    private String entity;
    private String host;
    private Integer port;

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
    public Integer getPort() {
        return port;
    }

    @JsonCreator
    public MessageAddressImpl(
            @JsonProperty("entity") String entity,
            @JsonProperty("host") String host,
            @JsonProperty("port") Integer port) {
        this.entity = entity;
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageAddressImpl address = (MessageAddressImpl) o;
        return Objects.equals(entity, address.entity) &&
                Objects.equals(host, address.host) &&
                Objects.equals(port, address.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, host, port);
    }

    @Override
    public String toString() {
        return "MessageAddressImpl{" +
                "entity='" + entity + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
