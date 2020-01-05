package ru.otus.kasymbekovPN.zuiNotesDB.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.IAmNotifierRunner;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.Notifier;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.NotifierImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.NotifierRunner;
import ru.otus.kasymbekovPN.zuiNotesCommon.messages.MessageType;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesDB.socket.inputHandler.IAmResponseSIH;

@Configuration
@RequiredArgsConstructor
public class MsNotifierConfig {

    private final SocketHandler socketHandler;

    @Bean
    public Notifier msNotifier(){
        NotifierRunner notifierRunner = new IAmNotifierRunner(socketHandler);
        Notifier notifier = new NotifierImpl(notifierRunner);
        socketHandler.addHandler(MessageType.I_AM_RESPONSE.getValue(), new IAmResponseSIH(notifier));

        return notifier;
    }
}
