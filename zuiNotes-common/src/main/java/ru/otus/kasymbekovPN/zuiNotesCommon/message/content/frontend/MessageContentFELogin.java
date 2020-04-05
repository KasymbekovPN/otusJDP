package ru.otus.kasymbekovPN.zuiNotesCommon.message.content.frontend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.content.MessageContent;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.util.Objects;
import java.util.Set;

@JsonTypeName("MessageContentFELogin")
public class MessageContentFELogin implements MessageContent {

    @JsonProperty("data")
    private MessageData data;

    @JsonProperty("errors")
    private Set<MessageError> errors;

    public MessageContentFELogin(
            @JsonProperty("data") MessageData data,
            @JsonProperty("errors") Set<MessageError> errors) {
        this.data = data;
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageContentFELogin that = (MessageContentFELogin) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, errors);
    }

    @Override
    public String toString() {
        return "MessageContentLogin{" +
                "data=" + data +
                ", errors=" + errors +
                '}';
    }
}
