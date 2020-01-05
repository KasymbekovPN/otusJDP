package ru.otus.kasymbekovPN.zuiNotesDB.auxiliary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.service.DBServiceOnlineUser;

import javax.annotation.PostConstruct;
import java.util.List;

///**
// * Служебный класс-инициализатор БД. <br><br>
// *
// * {@link #init} - метод, записывающий данные администратора в БД.
// */
//<
@Component
@RequiredArgsConstructor
public class DBInitialization {

    private static final String LOGIN = "admin";
    private static final String PASSWORD = "qwerty";
    private static final boolean IS_ADMIN = true;

    private final DBServiceOnlineUser dbService;

    @PostConstruct
    public void init(){
        List<OnlineUser> onlineUsers = dbService.loadRecord(LOGIN);
        if (onlineUsers.size() == 0) {
            dbService.createRecord(new OnlineUser(0, LOGIN, PASSWORD, IS_ADMIN));
        }
    }
}
