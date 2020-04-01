package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;

import java.io.Serializable;

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
}
