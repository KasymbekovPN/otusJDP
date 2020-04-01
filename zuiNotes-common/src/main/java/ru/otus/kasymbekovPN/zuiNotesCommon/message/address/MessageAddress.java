package ru.otus.kasymbekovPN.zuiNotesCommon.message.address;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageAddressImpl.class, name = "MessageAddressImpl")
})
public interface MessageAddress extends Serializable {
    String getEntity();
    String getHost();
    Integer getPort();
}
