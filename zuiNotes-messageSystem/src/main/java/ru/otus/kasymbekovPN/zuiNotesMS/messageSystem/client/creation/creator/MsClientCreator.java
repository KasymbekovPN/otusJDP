package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator;

import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;

import java.util.Set;

//    /**
//     * Интерфейс, служащий для реализации классов, создающих клиенты {@link MsClient} системы сообщений {@link MessageSystem} <br><br>
//     */
public interface MsClientCreator {
    MSClient create(MsClientUrl url, SocketHandler socketHandler, MessageSystem messageSystem);
    void setValidMessages(Set<String> validMessages);
}
