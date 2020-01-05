package ru.otus.kasymbekovPN.zuiNotesDB.db.hibernate.sessionManager;

public class SessionManagerException extends RuntimeException {
    public SessionManagerException(String msg){
        super(msg);
    }

    public SessionManagerException(Exception ex){
        super(ex);
    }
}
