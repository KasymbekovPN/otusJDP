package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory;

import ru.otus.kasymbekovPN.zuiNotesCommon.entity.Entity;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.MsClientCreator;

public interface MsClientCreatorFactory {
    MsClientCreator get(Entity entity);
}
