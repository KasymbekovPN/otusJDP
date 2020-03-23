package ru.otus.kasymbekovPN.zuiNotesCommon.message.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

@JsonTypeName("MessageErrorCommonFieldNotExist")
public class MessageErrorCommonFieldNotExist implements MessageError {

    private String field;

    @JsonGetter("field")
    public String getField() {
        return field;
    }

    @JsonCreator
    public MessageErrorCommonFieldNotExist(
            @JsonProperty("field") String field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageErrorCommonFieldNotExist that = (MessageErrorCommonFieldNotExist) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    @Override
    public String toString() {
        return "MessageErrorCommonFieldNotExist{" +
                "field='" + field + '\'' +
                '}';
    }
}
