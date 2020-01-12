package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service;

import com.google.gson.JsonObject;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    JsonObject addClient(MsClientUrl url, MSClient msClient) throws Exception;
    JsonObject deleteClient(MsClientUrl url) throws Exception;
    Optional<MSClient> get(MsClientUrl url);
    Map<String, Set<MsClientUrl>> search(Set<String> entities);
}
