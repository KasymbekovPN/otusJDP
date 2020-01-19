package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory;

import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.MsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.Solus;

public interface MsClientCreatorFactory {
    MsClientCreator get(String entity);
}
