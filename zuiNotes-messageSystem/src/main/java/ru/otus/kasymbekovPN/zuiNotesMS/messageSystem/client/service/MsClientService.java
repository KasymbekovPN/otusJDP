package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service;

import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface MsClientService {
//    JsonObject addClient(MsClientUrl url, MSClient msClient) throws Exception;
    //<
    Optional<MessageError> addClient(MsClientUrl url, MSClient msClient);

//    JsonObject deleteClient(MsClientUrl url) throws Exception;
    //<
    Optional<MessageError> deleteClient(MsClientUrl url);

    Optional<MSClient> get(MsClientUrl url);
    Map<String, Set<MsClientUrl>> search(Set<String> entities);
    Set<MsClientUrl> getAll();
}
