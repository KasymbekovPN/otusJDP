package ru.otus.kasymbekovPN.zuiNotesCommon.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddress;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeader;

import java.util.Optional;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageImpl.class, name = "MessageImpl")
})
public interface Message {
    MessageHeader getHeader();
    MessageAddress getFrom();
    MessageAddress getTo();
    MessageData getData();
    Set<MessageError> getErrors();
    void setFrom(MessageAddress from);
    void setTo(MessageAddress to);
    //<
 //   Optional<String> getAsJson();
}
