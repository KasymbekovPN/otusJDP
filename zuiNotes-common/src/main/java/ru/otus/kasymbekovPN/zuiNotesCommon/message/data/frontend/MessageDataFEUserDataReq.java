package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.frontend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;

import java.util.Objects;

@JsonTypeName("MessageDataFEUserDataReq")
public class MessageDataFEUserDataReq implements MessageData {

    @JsonProperty("login")
    private String login;

    @JsonCreator
    public MessageDataFEUserDataReq(
            @JsonProperty("login") String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDataFEUserDataReq that = (MessageDataFEUserDataReq) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "MessageDataFEUserDataReq{" +
                "login='" + login + '\'' +
                '}';
    }
}
