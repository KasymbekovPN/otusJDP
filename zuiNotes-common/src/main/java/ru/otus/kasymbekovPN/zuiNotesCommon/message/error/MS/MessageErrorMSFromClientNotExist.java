package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeName("MessageErrorMSFromClientNotExist")
public class MessageErrorMSFromClientNotExist implements MessageError {

    private String client;

    @JsonGetter("client")
    public String getClient() {
        return client;
    }

    @JsonCreator
    public MessageErrorMSFromClientNotExist(
            @JsonProperty("client") String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageErrorMSFromClientNotExist that = (MessageErrorMSFromClientNotExist) o;
        return Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }

    @Override
    public String toString() {
        return "MessageErrorMSFromClientNotExist{" +
                "client='" + client + '\'' +
                '}';
    }
}
