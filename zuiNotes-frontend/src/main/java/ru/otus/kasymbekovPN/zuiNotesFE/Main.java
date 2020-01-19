package ru.otus.kasymbekovPN.zuiNotesFE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

///**
// * Для запуска командная строка должна содержать следующие агрументы <br><br>
// *
// * <ol>
// *     <li>--self.port - собственный порт</li>
// *     <li>--ms.host - хост системы сообщений</li>
// *     <li>--ms.port - порт системы сообщений</li>
// *     <li>--db.host - хост frontend-клиента</li>
// *     <li>--db.port - порт frontend-клиента</li>
// *     <li>--server.port - порт веб-сервера</li>
// * </ol>
// *
// */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
