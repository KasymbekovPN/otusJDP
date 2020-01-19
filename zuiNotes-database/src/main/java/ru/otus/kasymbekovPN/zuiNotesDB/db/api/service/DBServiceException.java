package ru.otus.kasymbekovPN.zuiNotesDB.db.api.service;

public class DBServiceException  extends  RuntimeException{
    DBServiceException(Exception ex){
        super(ex);
    }
}
