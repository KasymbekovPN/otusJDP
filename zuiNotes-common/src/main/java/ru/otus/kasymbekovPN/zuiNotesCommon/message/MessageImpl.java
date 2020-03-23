package ru.otus.kasymbekovPN.zuiNotesCommon.message;

import com.fasterxml.jackson.annotation.*;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddress;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeader;

import java.util.Objects;
import java.util.Set;

@JsonTypeName("MessageImpl")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageImpl implements Message {

    private MessageHeader header;
    private MessageAddress from;
    private MessageAddress to;
    private MessageData data;
    private Set<MessageError> errors;

    @JsonGetter("header")
    @Override
    public MessageHeader getHeader() {
        return header;
    }

    @JsonGetter("from")
    @Override
    public MessageAddress getFrom() {
        return from;
    }

    @JsonGetter("to")
    @Override
    public MessageAddress getTo() {
        return to;
    }

    @JsonGetter("data")
    @Override
    public MessageData getData() {
        return data;
    }

    @JsonGetter("errors")
    @Override
    public Set<MessageError> getErrors() {
        return errors;
    }

    @JsonCreator
    public MessageImpl(
            @JsonProperty("header") MessageHeader header,
            @JsonProperty("from") MessageAddress from,
            @JsonProperty("to") MessageAddress to,
            @JsonProperty("data") MessageData data,
            @JsonProperty("errors") Set<MessageError> errors) {
        this.header = header;
        this.from = from;
        this.to = to;
        this.data = data;
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageImpl message = (MessageImpl) o;
        return Objects.equals(header, message.header) &&
                Objects.equals(from, message.from) &&
                Objects.equals(to, message.to) &&
                Objects.equals(data, message.data) &&
                Objects.equals(errors, message.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, from, to, data, errors);
    }

    @Override
    public String toString() {
        return "MessageImpl{" +
                "header=" + header +
                ", from=" + from +
                ", to=" + to +
                ", data=" + data +
                ", errors=" + errors +
                '}';
    }
}
