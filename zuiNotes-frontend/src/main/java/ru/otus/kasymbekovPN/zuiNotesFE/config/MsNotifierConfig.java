package ru.otus.kasymbekovPN.zuiNotesFE.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.RegistrationMessageNR;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.Notifier;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.NotifierImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.introduce.NotifierRunner;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesFE.messageSystem.MessageType;
import ru.otus.kasymbekovPN.zuiNotesFE.socket.inputHandler.RegistrationMessageSIH;

@Configuration
@RequiredArgsConstructor
public class MsNotifierConfig {

    private final SocketHandler socketHandler;

    @Bean
    public Notifier msNotifier(){
        final String registrationMessageType = MessageType.I_AM.getValue();
        NotifierRunner notifierRunner = new RegistrationMessageNR(socketHandler, registrationMessageType);
        NotifierImpl registrar = new NotifierImpl(notifierRunner);
        socketHandler.addHandler(registrationMessageType, new RegistrationMessageSIH(registrar));

        return registrar;
    }
}
