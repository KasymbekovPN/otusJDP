package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.common;

import com.fasterxml.jackson.annotation.*;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeName("MessageErrorCommonInvalidFieldType")
public class MessageErrorCommonInvalidFieldType implements MessageError {

    private String field;
    private String type;

    @JsonGetter("field")
    public String getField() {
        return field;
    }

    @JsonGetter("type")
    public String getType() {
        return type;
    }

    @JsonCreator
    public MessageErrorCommonInvalidFieldType(
            @JsonProperty("field") String field,
            @JsonProperty("type") String type) {
        this.field = field;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageErrorCommonInvalidFieldType that = (MessageErrorCommonInvalidFieldType) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, type);
    }

    @Override
    public String toString() {
        return "MessageErrorCommonInvalidFieldType{" +
                "field='" + field + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
