package ru.otus.kasymbekovPN.zuiNotesFE.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGeneratorImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonJEDGenerator1;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonJEDGenerator2;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonJEDGenerator3;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonJEDGenerator4;

@Configuration
@RequiredArgsConstructor
public class JEOGeneratorConfig {

    private final Client client;

    @Bean
    JsonErrorObjectGenerator common(){
        JsonErrorObjectGeneratorImpl jeoGenerator = new JsonErrorObjectGeneratorImpl(client.getEntity(), true);
        jeoGenerator.addDataGenerator(1, new CommonJEDGenerator1());
        jeoGenerator.addDataGenerator(2, new CommonJEDGenerator2());
        jeoGenerator.addDataGenerator(3, new CommonJEDGenerator3());
        jeoGenerator.addDataGenerator(4, new CommonJEDGenerator4());

        return jeoGenerator;
    }
}
