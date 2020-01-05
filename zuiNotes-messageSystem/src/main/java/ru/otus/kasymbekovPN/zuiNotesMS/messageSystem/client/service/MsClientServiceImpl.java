package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.kasymbekovPN.zuiNotesCommon.entity.Entity;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactory;

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
//<
@Service
public class MsClientServiceImpl implements MsClientService {

    private static final Logger logger = LoggerFactory.getLogger(MsClientServiceImpl.class);

    private final MsClientCreatorFactory msClientCreatorFactory;
    private final Map<String, MSClient> clients = new HashMap<>();

    private SocketHandler socketHandler;

    public MsClientServiceImpl(MsClientCreatorFactory msClientCreatorFactory) {
        this.msClientCreatorFactory = msClientCreatorFactory;
    }

    @Override
    public synchronized boolean createClient(String host, int port, Entity entity, MessageSystem messageSystem) {
        String url = JsonHelper.extractUrl(JsonHelper.makeUrl(host, port, entity));
        if (!clients.containsKey(url)){
            final MSClient client = msClientCreatorFactory.get(entity).create(url, socketHandler, messageSystem);
            if (client != null){
                clients.put(url, client);
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public synchronized void deleteClient(String url) {
        MSClient removedClient = clients.remove(url);
        if (removedClient == null){
            logger.warn("MsClientServiceImpl::deleteClient : client '{}' not found", url);
        } else {
            logger.info("MsClientServiceImpl::deleteClient : client '{}' was delete", url);
        }
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
