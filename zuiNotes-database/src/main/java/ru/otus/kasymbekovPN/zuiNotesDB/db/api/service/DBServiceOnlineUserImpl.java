package ru.otus.kasymbekovPN.zuiNotesDB.db.api.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.dao.OnlineUserDao;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализации сервиса работы класса
 * OnlineUser с БД.
 */
@Service
@RequiredArgsConstructor
public class DBServiceOnlineUserImpl implements DBServiceOnlineUser {

    private final static Logger logger = LoggerFactory.getLogger(DBServiceOnlineUserImpl.class);

    /**
     * DAO
     */
    private final OnlineUserDao dao;

    /**
     * Создание записи в БД
     * @param user записываемый объект
     * @return успешность
     */
    @Override
    public boolean createRecord(OnlineUser user) {
        try(SessionManager sessionManager = dao.getSessionManager()){
            sessionManager.beginSession();
            try{
                boolean success = dao.createRecord(user);
                if (success){
                    sessionManager.commitSession();
                    logger.info("The record was create");
                } else {
                    sessionManager.rollbackSession();
                    logger.error("The record wasn't create");
                }

                return success;
            } catch(Exception ex){
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceException(ex);
            }
        }
    }

    /**
     * Обновление записи в БД
     * @param user Объект для записи
     * @return Успешность
     */
    @Override
    public boolean updateRecord(OnlineUser user) {
        try(SessionManager sessionManager = dao.getSessionManager()){
            sessionManager.beginSession();
            try{
                boolean success = dao.updateRecord(user);
                if (success){
                    sessionManager.commitSession();
                    logger.info("The record was update");
                } else {
                    sessionManager.rollbackSession();
                    logger.info("The record wasn't update");
                }
                return success;
            } catch(Exception ex){
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceException(ex);
            }
        }
    }

    /**
     * Выгрузка данных по ключу
     * @param id значение ключа
     * @return Инстанс, с выгруженными данными.
     */
    @Override
    public Optional<OnlineUser> loadRecord(long id) {
        try(SessionManager sessionManager = dao.getSessionManager()){
            sessionManager.beginSession();
            try{
                Optional<OnlineUser> record = dao.loadRecord(id);
                logger.info("loaded record : {}", record.orElse(null));
                return record;
            } catch (Exception ex){
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
            }

            return Optional.empty();
        }
    }

    /**
     * Выгрузка записей по значению поля login
     * @param login значение поля login
     * @return Список записей
     */
    @Override
    public List<OnlineUser> loadRecord(String login) {
        try(SessionManager sessionManager = dao.getSessionManager()){
            sessionManager.beginSession();
            try{
                List<OnlineUser> records = dao.loadRecord(login);
                logger.info("loaded records : " + records);
                return records;
            } catch (Exception ex){
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
            }

            return new ArrayList<>();
        }
    }

    /**
     * Выгрузка всех записей
     * @return Все записи таблицы
     */
    @Override
    public List<OnlineUser> loadAll() {
        try(SessionManager sessionManager = dao.getSessionManager()){
            sessionManager.beginSession();
            try{
                List<OnlineUser> records = dao.loadAll();
                logger.info("loaded records : " + records);
                return records;
            } catch (Exception ex){
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
            }

            return new ArrayList<>();
        }
    }

    /**
     * Удаление поля по значению поля login
     * @param login значение поля login
     * @return Успешность
     */
    @Override
    public boolean deleteRecord(String login) {
        try(SessionManager sessionManager = dao.getSessionManager()){
            sessionManager.beginSession();
            try{
                boolean success = dao.deleteRecord(login);
                if (success){
                    sessionManager.commitSession();
                    logger.info("The record was create");
                } else {
                    sessionManager.rollbackSession();
                    logger.info("The record wasn't create");
                }

                return success;
            } catch(Exception ex){
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceException(ex);
            }
        }
    }
}
