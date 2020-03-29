package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.util.Objects;

@JsonTypeName("MessageErrorMsClientHasWrongEntity")
public class MessageErrorMsClientHasWrongEntity implements MessageError {

    private String entity;

    @JsonGetter("entity")
    public String getEntity() {
        return entity;
    }

    @JsonCreator
    public MessageErrorMsClientHasWrongEntity(
            @JsonProperty("entity") String entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageErrorMsClientHasWrongEntity that = (MessageErrorMsClientHasWrongEntity) o;
        return Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public String toString() {
        return "MessageErrorMsClientHasWrongEntity{" +
                "entity='" + entity + '\'' +
                '}';
    }
}
