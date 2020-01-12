package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.Solus;

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

    private final Solus solus;
    private final MsClientCreatorFactory msClientCreatorFactory;
    private final Map<MsClientUrl, MSClient> clients = new HashMap<>();

    private SocketHandler socketHandler;

    public MsClientServiceImpl(MsClientCreatorFactory msClientCreatorFactory, Solus solus) {
        this.msClientCreatorFactory = msClientCreatorFactory;
        this.solus = solus;
    }

    @Override
    public synchronized JsonObject createClient(MsClientUrl url, MessageSystem messageSystem) throws Exception {
        if (clients.containsKey(url)){
            return jeoGenerator.generate(3, url);
        } else {
            final boolean notReg = solus.register(url.getEntity());
            if (notReg){
                MSClient msClient = msClientCreatorFactory.get(url.getEntity()).create(url, socketHandler, messageSystem);
                if (msClient != null){
                    clients.put(url, msClient);
                    return new JsonObject();
                } else {
                    return jeoGenerator.generate(4, url.getEntity());
                }
            } else {
                return jeoGenerator.generate(5, url.getEntity());
            }
        }
    }

    @Override
    public synchronized JsonObject deleteClient(MsClientUrl url) throws Exception {
        MSClient removedClient = clients.remove(url);
        return removedClient == null
                ? jeoGenerator.generate(6, url.getUrl())
                : new JsonObject();
    }

    @Override
    public synchronized void setSocketHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
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
}
