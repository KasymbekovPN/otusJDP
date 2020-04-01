package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.database;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;

import java.util.Objects;

@JsonTypeName("MessageDataDBLoginResp")
public class MessageDataDBLoginResp implements MessageData {

    private String login;
    private String group;

    @JsonGetter("login")
    public String getLogin() {
        return login;
    }

    @JsonGetter("group")
    public String getGroup() {
        return group;
    }

    @JsonCreator
    public MessageDataDBLoginResp(
            @JsonProperty("login") String login,
            @JsonProperty("group") String group) {
        this.login = login;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDataDBLoginResp that = (MessageDataDBLoginResp) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, group);
    }

    @Override
    public String toString() {
        return "MessageDataDBLoginResp{" +
                "login='" + login + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
