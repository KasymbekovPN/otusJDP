package ru.otus.kasymbekovPN.zuiNotesFE.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.ClientImpl;

@Configuration
public class ClientConfig {

    @Bean
    Client client(){
        return new ClientImpl("FRONTEND");
    }
}
