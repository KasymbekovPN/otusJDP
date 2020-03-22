package ru.otus.kasymbekovPN.zuiNotesCommon.message.header;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes(
        @JsonSubTypes.Type(value = MessageHeaderImpl.class, name = "MessageHeaderImpl")
)
public interface MessageHeader {
    String getEntity();
    String getHost();
    int getPort();
}
