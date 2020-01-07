package ru.otus.kasymbekovPN.zuiNotesMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

//<
//    /**
//     * Для запуска командная строка должна содержать 1 именованый агрумет<br><br>
//     *
//     * <ol>
//     *     <li>ms.port - собственный порт</li>
//     * </ol>
//     *
//     */
//<
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}
