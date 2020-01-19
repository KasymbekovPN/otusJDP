package ru.otus.kasymbekovPN.zuiNotesMS.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGeneratorImpl;

@Configuration
@RequiredArgsConstructor
public class JEOGeneratorConfig {

    private final Client client;

    @Qualifier("common")
    @Bean
    JsonErrorObjectGenerator common(){
        return new JsonErrorObjectGeneratorImpl(client.getEntity(), true);
    }

    @Qualifier("ms")
    @Bean
    JsonErrorObjectGenerator ms(){
        return new JsonErrorObjectGeneratorImpl(client.getEntity(), false);
    }
}
