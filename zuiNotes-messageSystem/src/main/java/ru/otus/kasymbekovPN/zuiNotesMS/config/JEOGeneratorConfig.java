package ru.otus.kasymbekovPN.zuiNotesMS.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorObjectGeneratorImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonJEDGenerator1;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonJEDGenerator2;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonJEDGenerator3;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonJEDGenerator4;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.*;

@Configuration
@RequiredArgsConstructor
public class JEOGeneratorConfig {

    private final Client client;

    @Qualifier("common")
    @Bean
    JsonErrorObjectGenerator common(){
        JsonErrorObjectGeneratorImpl jeoGenerator = new JsonErrorObjectGeneratorImpl(client.getEntity(), true);
        jeoGenerator.addDataGenerator(1, new CommonJEDGenerator1());
        jeoGenerator.addDataGenerator(2, new CommonJEDGenerator2());
        jeoGenerator.addDataGenerator(3, new CommonJEDGenerator3());
        jeoGenerator.addDataGenerator(4, new CommonJEDGenerator4());

        //<
        System.out.println("common : " + jeoGenerator);

        return jeoGenerator;
    }

    @Qualifier("ms")
    @Bean
    JsonErrorObjectGenerator ms(){
        JsonErrorObjectGeneratorImpl jeoGenerator = new JsonErrorObjectGeneratorImpl(client.getEntity(), false);
        jeoGenerator.addDataGenerator(1, new MSJEDGenerator1());
        jeoGenerator.addDataGenerator(2, new MSJEDGenerator2());
        jeoGenerator.addDataGenerator(3, new MSJEDGenerator3());
        jeoGenerator.addDataGenerator(4, new MSJEDGenerator4());
        jeoGenerator.addDataGenerator(5, new MSJEDGenerator5());
        jeoGenerator.addDataGenerator(6, new MSJEDGenerator6());
        jeoGenerator.addDataGenerator(7, new MSJEDGenerator7());
        jeoGenerator.addDataGenerator(8, new MSJEDGenerator8());
        jeoGenerator.addDataGenerator(9, new MSJEDGenerator9());

        //<
        System.out.println("ms : " + jeoGenerator);

        return jeoGenerator;
    }
}
