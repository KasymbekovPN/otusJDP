package ru.otus.kasymbekovPN.zuiNotesCommon.message.header;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;
import java.util.UUID;

@JsonTypeName("MessageHeaderImpl")
public class MessageHeaderImpl implements MessageHeader {

    private String type;
    private Boolean request;
    private UUID uuid;

    @JsonGetter("type")
    @Override
    public String getType() {
        return type;
    }

    @JsonGetter("request")
    @Override
    public Boolean isRequest() {
        return request;
    }

    @JsonGetter("uuid")
    @Override
    public UUID getUUID() {
        return uuid;
    }

    @JsonCreator
    public MessageHeaderImpl(
            @JsonProperty("type") String type,
            @JsonProperty("request") Boolean request,
            @JsonProperty("uuid") UUID uuid) {
        this.type = type;
        this.request = request;
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageHeaderImpl that = (MessageHeaderImpl) o;
        return request == that.request &&
                Objects.equals(type, that.type) &&
                Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, request, uuid);
    }

    @Override
    public String toString() {
        return "MessageHeaderImpl{" +
                "type='" + type + '\'' +
                ", request=" + request +
                ", uuid=" + uuid +
                '}';
    }
}
