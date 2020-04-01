package ru.otus.kasymbekovPN.zuiNotesCommon.message.error.database;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;

@JsonTypeName("MessageErrorDBEmptyLoginPassword")
public class MessageErrorDBEmptyLoginPassword implements MessageError {
}
