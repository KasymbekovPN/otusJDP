package ru.otus.kasymbekovPN.zuiNotesDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//    /**
//     * Для запуска командная строка должна содержать 5 агруметов <br><br>
//     *
//     * <ol>
//     *     <li>--self.port - собственный порт</li>
//     *     <li>--ms.host - хост системы сообщений</li>
//     *     <li>--ms.port - порт системы сообщений</li>
//     *     <li>--target.host - хост database-клиента</li>
//     *     <li>--target.port - порт database-клиента</li>
//     * </ol>
//     *
//     */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}
