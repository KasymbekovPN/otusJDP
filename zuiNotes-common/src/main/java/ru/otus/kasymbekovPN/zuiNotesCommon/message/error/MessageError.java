package ru.otus.kasymbekovPN.zuiNotesCommon.message.error;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS.*;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.common.MessageErrorCommonFieldNotExist;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.common.MessageErrorCommonInvalidFieldType;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.common.MessageErrorCommonInvalidMessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.common.MessageErrorCommonUnknownFieldType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageErrorCommonFieldNotExist.class, name = "MessageErrorCommonFieldNotExist"),
        @JsonSubTypes.Type(value = MessageErrorCommonInvalidMessageType.class, name = "MessageErrorCommonInvalidMessageType"),
        @JsonSubTypes.Type(value = MessageErrorCommonInvalidFieldType.class, name = "MessageErrorCommonInvalidFieldType"),
        @JsonSubTypes.Type(value = MessageErrorCommonUnknownFieldType.class, name = "MessageErrorCommonUnknownFieldType"),
        @JsonSubTypes.Type(value = MessageErrorMSClientAlreadyDel.class, name = "MessageErrorMSClientAlreadyDel"),
        @JsonSubTypes.Type(value = MessageErrorMSClientAlreadyExist.class, name = "MessageErrorMSClientAlreadyExist"),
        @JsonSubTypes.Type(value = MessageErrorMsClientHasWrongEntity.class, name = "MessageErrorMsClientHasWrongEntity"),
        @JsonSubTypes.Type(value = MessageErrorMSFieldReqIsWrong.class, name = "MessageErrorMSFieldReqIsWrong"),
        @JsonSubTypes.Type(value = MessageErrorMsSolusReg.class, name = "MessageErrorMsSolusReg")
})
public interface MessageError {
}
