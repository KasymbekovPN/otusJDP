package ru.otus.kasymbekovPN.zuiNotesMS.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.terminator.Terminator;
import ru.otus.kasymbekovPN.zuiNotesCommon.terminator.TerminatorImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;
import ru.otus.kasymbekovPN.zuiNotesMS.terminator.MSTerminatorHandler;

@Configuration
@RequiredArgsConstructor
public class TerminatorConfig {

    private final MsClientService msClientService;
    private final SocketHandler socketHandler;

    @Bean
    public Terminator terminator(){
        return new TerminatorImpl(new MSTerminatorHandler(msClientService, socketHandler));
    }
}
