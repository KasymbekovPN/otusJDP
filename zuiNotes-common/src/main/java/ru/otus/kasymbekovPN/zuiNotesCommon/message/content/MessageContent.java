package ru.otus.kasymbekovPN.zuiNotesCommon.message.content;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.content.frontend.MessageContentFELogin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageContentFELogin.class, name = "MessageContentFELogin")
})
public interface MessageContent {
}
