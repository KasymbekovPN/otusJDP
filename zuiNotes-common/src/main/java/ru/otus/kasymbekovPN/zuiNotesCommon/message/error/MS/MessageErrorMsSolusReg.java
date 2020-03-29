package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.util.Objects;

@JsonTypeName("MessageErrorMsSolusReg")
public class MessageErrorMsSolusReg implements MessageError {

    private String entity;

    @JsonGetter("entity")
    public String getEntity() {
        return entity;
    }

    @JsonCreator
    public MessageErrorMsSolusReg(
            @JsonProperty("entity") String entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageErrorMsSolusReg that = (MessageErrorMsSolusReg) o;
        return Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public String toString() {
        return "MessageErrorMsSolusReg{" +
                "entity='" + entity + '\'' +
                '}';
    }
}
