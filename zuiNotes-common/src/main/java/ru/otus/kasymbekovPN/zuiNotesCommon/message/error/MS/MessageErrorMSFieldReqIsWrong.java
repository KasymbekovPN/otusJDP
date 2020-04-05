package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeName("MessageErrorMSFieldReqIsWrong")
public class MessageErrorMSFieldReqIsWrong implements MessageError {

    @JsonCreator
    public MessageErrorMSFieldReqIsWrong() {
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClass());
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass());
    }
}
