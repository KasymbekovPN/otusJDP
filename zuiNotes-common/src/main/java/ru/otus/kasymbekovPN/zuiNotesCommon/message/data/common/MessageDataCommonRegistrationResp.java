package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeName("MessageDataCommonRegistrationResp")
public class MessageDataCommonRegistrationResp implements MessageData {

    private String url;
    private Boolean registration;

    @JsonGetter("url")
    public String getUrl() {
        return url;
    }

    @JsonGetter("registration")
    public Boolean getRegistration() {
        return registration;
    }

    @JsonCreator
    public MessageDataCommonRegistrationResp(
            @JsonProperty("url") String url,
            @JsonProperty("registration") Boolean registration) {
        this.url = url;
        this.registration = registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDataCommonRegistrationResp that = (MessageDataCommonRegistrationResp) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(registration, that.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, registration);
    }

    @Override
    public String toString() {
        return "MessageDataCommonRegistrationResp{" +
                "url='" + url + '\'' +
                ", registration=" + registration +
                '}';
    }
}
