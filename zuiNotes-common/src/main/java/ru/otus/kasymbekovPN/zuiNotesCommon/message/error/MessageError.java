package ru.otus.kasymbekovPN.zuiNotesCommon.message.error;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageErrorCommonFieldNotExist.class, name = "MessageErrorCommonFieldNotExist"),
        @JsonSubTypes.Type(value = MessageErrorCommonInvalidMessageType.class, name = "MessageErrorCommonInvalidMessageType"),
        @JsonSubTypes.Type(value = MessageErrorCommonInvalidFieldType.class, name = "MessageErrorCommonInvalidFieldType"),
        @JsonSubTypes.Type(value = MessageErrorCommonUnknownFieldType.class, name = "MessageErrorCommonUnknownFieldType")
})
public interface MessageError {
}
