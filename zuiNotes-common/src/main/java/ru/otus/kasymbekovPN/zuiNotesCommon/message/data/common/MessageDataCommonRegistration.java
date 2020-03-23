package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common;

import com.fasterxml.jackson.annotation.*;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;

import java.util.Objects;

@JsonTypeName("MessageDataCommonRegistration")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDataCommonRegistration implements MessageData {
    private Boolean registration;

    @JsonGetter("registration")
    public Boolean getRegistration() {
        return registration;
    }

    @JsonCreator
    public MessageDataCommonRegistration(
            @JsonProperty("registration") Boolean registration) {
        this.registration = registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDataCommonRegistration that = (MessageDataCommonRegistration) o;
        return Objects.equals(registration, that.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registration);
    }

    @Override
    public String toString() {
        return "MessageDataCommonRegistration{" +
                "registration=" + registration +
                '}';
    }
}
