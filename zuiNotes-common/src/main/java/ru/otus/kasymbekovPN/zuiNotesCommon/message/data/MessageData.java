package ru.otus.kasymbekovPN.zuiNotesCommon.message.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common.MessageDataCommonRegistration;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageDataCommonRegistration.class, name = "MessageDataCommonRegistration")
})
public interface MessageData {
}
