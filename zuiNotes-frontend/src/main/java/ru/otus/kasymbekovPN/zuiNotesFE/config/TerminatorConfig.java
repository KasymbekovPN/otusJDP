package ru.otus.kasymbekovPN.zuiNotesFE.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.terminator.Terminator;
import ru.otus.kasymbekovPN.zuiNotesCommon.terminator.TerminatorImpl;
import ru.otus.kasymbekovPN.zuiNotesFE.terminator.FETerminatorHandler;

@Configuration
@RequiredArgsConstructor
public class TerminatorConfig {

    private final SocketHandler socketHandler;

    @Bean
    public Terminator terminator(){
        return new TerminatorImpl(new FETerminatorHandler(socketHandler));
    }
}
