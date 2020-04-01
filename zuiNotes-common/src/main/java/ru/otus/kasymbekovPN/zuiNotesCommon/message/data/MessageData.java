package ru.otus.kasymbekovPN.zuiNotesCommon.message.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common.MessageDataCommonRegistrationReq;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common.MessageDataCommonRegistrationResp;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.database.MessageDataDBLoginResp;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.frontend.MessageDataFELoginReq;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageDataCommonRegistrationReq.class, name = "MessageDataCommonRegistrationReq"),
        @JsonSubTypes.Type(value = MessageDataCommonRegistrationResp.class, name = "MessageDataCommonRegistrationResp"),
        @JsonSubTypes.Type(value = MessageDataFELoginReq.class, name = "MessageDataFELoginReq"),
        @JsonSubTypes.Type(value = MessageDataDBLoginResp.class, name = "MessageDataDBLoginResp")
})
public interface MessageData extends Serializable {
}
