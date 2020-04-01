package ru.otus.kasymbekovPN.zuiNotesCommon.message.header;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes(
        @JsonSubTypes.Type(value = MessageHeaderImpl.class, name = "MessageHeaderImpl")
)
public interface MessageHeader extends Serializable {
    String getType();
    Boolean isRequest();
    UUID getUUID();
}
