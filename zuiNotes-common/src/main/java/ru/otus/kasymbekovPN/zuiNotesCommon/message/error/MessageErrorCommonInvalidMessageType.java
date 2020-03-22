package ru.otus.kasymbekovPN.zuiNotesCommon.message.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

@JsonTypeName("MessageErrorCommonInvalidMessageType")
public class MessageErrorCommonInvalidMessageType implements MessageError {

    private String type;

    @JsonGetter("type")
    public String getType() {
        return type;
    }

    @JsonCreator
    public MessageErrorCommonInvalidMessageType(
            @JsonProperty("type") String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageErrorCommonInvalidMessageType that = (MessageErrorCommonInvalidMessageType) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "MessageErrorCommonInvalidMessageType{" +
                "type='" + type + '\'' +
                '}';
    }
}
