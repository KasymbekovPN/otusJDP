package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSJEDGMsClientAlreadyExist;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;

import java.util.*;

//    /**
//     * Сервис клиентов {@link MsClient} системы сообщений {@link MessageSystem} <br><br>
//     *
//     * {@link #createClient(String, int, Entity, MessageSystem)} - создание нового клиента.
//     *
//     * {@link #deleteClient(String)} - удаление клиента <br>
//     *
//     * {@link #setSocketHandler(SocketHandler)} - сеттер обработчика сокета <br>
//     *
//     * {@link #get(String)} - геттер клиента <br>
//     */
@Service
public class MsClientServiceImpl implements MsClientService {

    private static final Logger logger = LoggerFactory.getLogger(MsClientServiceImpl.class);

    @Qualifier("ms")
    @Autowired
    private JsonErrorObjectGenerator jeoGenerator;

    private final Map<MsClientUrl, MSClient> clients = new HashMap<>();

    @Override
    public JsonObject addClient(MsClientUrl url, MSClient msClient) throws Exception {
        if (clients.containsKey(url)){
            return jeoGenerator.generate(new MSJEDGMsClientAlreadyExist(url.getUrl()));
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
