package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;

import java.util.Optional;

//    /**
//     * Интерфейс, служащйи для создания сервиса клиентов {@link MsClient} системы сообщений {@link MessageSystem} <br><br>
//     *
//     * {@link #createClient(String, int, Entity, MessageSystem)} - создание нового клиента <br>
//     *
//     * {@link #deleteClient(String)} - удаление клиента <br>
//     *
//     * {@link #setSocketHandler(SocketHandler)} - сеттер обработчика сокета <br>
//     *
//     * {@link #get(String)} - геттер клиента <br>
//     */
public interface MsClientService {
    JsonObject createClient(String host, int port, String entity, MessageSystem messageSystem) throws Exception;
    JsonObject deleteClient(String url) throws Exception;
    void setSocketHandler(SocketHandler socketHandler);
    Optional<MSClient> get(String url);
}
