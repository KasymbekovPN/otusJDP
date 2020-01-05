package ru.otus.kasymbekovPN.zuiNotesDB.db.api.dao;

import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.sessionManager.SessionManager;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для реализации DAO для DBUser
 */
public interface OnlineUserDao {

    /**
     * Выгрузка записи по ключу
     * @param id ключ
     * @return полученный объект
     */
    Optional<OnlineUser> loadRecord(long id);

    /**
     * Выгрузка записей по полю login
     * @param login значение поля login
     * @return Список записей
     */
    List<OnlineUser> loadRecord(String login);

    /**
     * Выгрузка всех записей
     * @return Все записи таблицы
     */
    List<OnlineUser> loadAll();

    /**
     * Сохранение объекта
     * @param user объекта
     * @return успешность сохранения
     */
    boolean createRecord(OnlineUser user);

    /**
     * Обновление записи
     * @param user записываем объект
     * @return успешность
     */
    boolean updateRecord(OnlineUser user);

    /**
     * Удаление записи по значению поля login
     * @param login значения поля login
     * @return успешность
     */
    boolean deleteRecord(String login);

    /**
     * @return Текущий менеджер сессий
     */
    SessionManager getSessionManager();
}

