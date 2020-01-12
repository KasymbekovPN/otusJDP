package ru.otus.kasymbekovPN.zuiNotesCommon.client;

public class ClientImpl implements Client {

    private final String entity;

    public ClientImpl(String entity) {
        this.entity = entity;
    }

    @Override
    public String getEntity() {
        return entity;
    }
}
