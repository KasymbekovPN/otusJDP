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
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.Solus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private final Map<String, MSClient> clients = new HashMap<>();

    private SocketHandler socketHandler;

    public MsClientServiceImpl(MsClientCreatorFactory msClientCreatorFactory, Solus solus) {
        this.msClientCreatorFactory = msClientCreatorFactory;
        this.solus = solus;
    }

    @Override
    public synchronized JsonObject createClient(String host, int port, String entity, MessageSystem messageSystem) throws Exception {

        String url = JsonHelper.extractUrl(JsonHelper.makeUrl(host, port, entity));
        if (clients.containsKey(url)){
            return jeoGenerator.generate(3, url);
        } else {
            final boolean notReg = solus.register(entity);
            if (notReg){
                MSClient msClient = msClientCreatorFactory.get(entity).create(url, socketHandler, messageSystem);
                if (msClient != null){
                    clients.put(url, msClient);
                    return new JsonObject();
                } else {
                    return jeoGenerator.generate(4, entity);
                }
            } else {
                return jeoGenerator.generate(5, entity);
            }
        }
    }

    @Override
    public synchronized JsonObject deleteClient(String url) throws Exception {
        MSClient removedClient = clients.remove(url);
        return removedClient == null
                ? jeoGenerator.generate(6, url)
                : new JsonObject();
    }

    @Override
    public synchronized void setSocketHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public synchronized Optional<MSClient> get(String url) {
        return Optional.ofNullable(clients.getOrDefault(url, null));
    }
}
