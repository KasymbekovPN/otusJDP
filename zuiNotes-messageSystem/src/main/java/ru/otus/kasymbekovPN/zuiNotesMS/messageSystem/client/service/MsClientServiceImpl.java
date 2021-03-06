package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSErrorCode;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MsClientServiceImpl implements MsClientService {

    private static final Logger logger = LoggerFactory.getLogger(MsClientServiceImpl.class);

    private final JsonErrorGenerator jeGenerator;

    private final Map<MsClientUrl, MSClient> clients = new HashMap<>();

    @Override
    public JsonObject addClient(MsClientUrl url, MSClient msClient) throws Exception {
        if (clients.containsKey(url)){
            return jeGenerator.handle(false, MSErrorCode.MS_CLIENT_ALREADY_EXIST.getCode())
                    .set("url", url.getUrl())
                    .get();
        } else {
            clients.put(url, msClient);
            return new JsonObject();
        }
    }

    @Override
    public synchronized JsonObject deleteClient(MsClientUrl url) throws Exception {
        clients.remove(url);
        return new JsonObject();
    }

    @Override
    public synchronized Optional<MSClient> get(MsClientUrl url) {
        return Optional.ofNullable(clients.getOrDefault(url, null));
    }

    @Override
    public Map<String, Set<MsClientUrl>> search(Set<String> entities) {

        Map<String, Set<MsClientUrl>> result = new HashMap<>();
        for (String entity : entities) {
            result.put(entity, new HashSet<>());
        }

        for (MsClientUrl msClientUrl : clients.keySet()) {
            String entity = msClientUrl.getEntity();
            if (entities.contains(entity)){
                result.get(entity).add(msClientUrl);
            }
        }

        return result;
    }

    @Override
    public Set<MsClientUrl> getAll() {
        return clients.keySet();
    }
}
