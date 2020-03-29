package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.util.Objects;

@JsonTypeName("MessageErrorMSClientAlreadyExist")
public class MessageErrorMSClientAlreadyExist implements MessageError {

    private String url;

    @JsonGetter("url")
    public String getUrl() {
        return url;
    }

    @JsonCreator
    public MessageErrorMSClientAlreadyExist(
            @JsonProperty("url") String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageErrorMSClientAlreadyExist that = (MessageErrorMSClientAlreadyExist) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "MessageErrorMSClientAlreadyExist{" +
                "url='" + url + '\'' +
                '}';
    }
}
