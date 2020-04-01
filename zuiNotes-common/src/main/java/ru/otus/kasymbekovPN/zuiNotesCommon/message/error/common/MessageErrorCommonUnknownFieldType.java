package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeName("MessageErrorCommonUnknownFieldType")
public class MessageErrorCommonUnknownFieldType implements MessageError {

    private String field;

    @JsonGetter("field")
    public String getField() {
        return field;
    }

    @JsonCreator
    public MessageErrorCommonUnknownFieldType(
            @JsonProperty("field") String field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageErrorCommonUnknownFieldType that = (MessageErrorCommonUnknownFieldType) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    @Override
    public String toString() {
        return "MessageErrorCommonUnknownFieldType{" +
                "field='" + field + '\'' +
                '}';
    }
}
