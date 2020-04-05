package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.database;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

import java.util.Objects;

@JsonTypeName("MessageErrorDBWrongLoginPassword")
public class MessageErrorDBWrongLoginPassword implements MessageError {
    @Override
    public int hashCode() {
        return Objects.hashCode(getClass());
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass());
    }
}
