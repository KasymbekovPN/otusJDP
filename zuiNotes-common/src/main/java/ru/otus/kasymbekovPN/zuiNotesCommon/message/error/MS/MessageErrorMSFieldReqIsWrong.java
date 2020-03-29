package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

@JsonTypeName("MessageErrorMSFieldReqIsWrong")
public class MessageErrorMSFieldReqIsWrong implements MessageError {

    @JsonCreator
    public MessageErrorMSFieldReqIsWrong() {
    }

    @Override
    public String toString() {
        return "MessageErrorMSFieldReqIsWrong{}";
    }
}
