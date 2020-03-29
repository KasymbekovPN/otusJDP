package ru.otus.kasymbekovPN.zuiNotesCommon.message.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common.MessageDataCommonRegistrationReq;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common.MessageDataCommonRegistrationResp;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageDataCommonRegistrationReq.class, name = "MessageDataCommonRegistrationReq"),
        @JsonSubTypes.Type(value = MessageDataCommonRegistrationResp.class, name = "MessageDataCommonRegistrationResp")
})
public interface MessageData {
}
