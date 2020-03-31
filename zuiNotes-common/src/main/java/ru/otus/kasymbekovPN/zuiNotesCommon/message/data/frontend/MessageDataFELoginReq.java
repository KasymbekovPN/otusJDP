package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.frontend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;

import java.util.Objects;

@JsonTypeName("MessageDataFELoginReq")
public class MessageDataFELoginReq implements MessageData {

    private String login;
    private String password;

    @JsonGetter("login")
    public String getLogin() {
        return login;
    }

    @JsonGetter("password")
    public String getPassword() {
        return password;
    }

    @JsonCreator
    public MessageDataFELoginReq(
            @JsonProperty("login") String login,
            @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDataFELoginReq that = (MessageDataFELoginReq) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "MessageDataFELoginReq{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
