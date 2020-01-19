package ru.otus.kasymbekovPN.zuiNotesDB.db.hibernate.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.kasymbekovPN.zuiNotesCommon.model.OnlineUser;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.dao.OnlineUserDao;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.dao.OnlineUserDaoException;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.sessionManager.SessionManager;
import ru.otus.kasymbekovPN.zuiNotesDB.db.hibernate.sessionManager.DataBaseSessionHibernate;
import ru.otus.kasymbekovPN.zuiNotesDB.db.hibernate.sessionManager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализации DAO для OnlineUser
 */
@Repository
@RequiredArgsConstructor
public class OnlineUserDaoHibernate implements OnlineUserDao {

    private static Logger logger = LoggerFactory.getLogger(OnlineUserDaoHibernate.class);

    /**
     * Менеджер сессий
     */
    private final SessionManagerHibernate sessionManager;

    /**
     * Выгрузка записи по ключю
     * @param id ключ
     * @return полученный объект
     */
    @Override
    public Optional<OnlineUser> loadRecord(long id) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try{
            return Optional.ofNullable(
                    currentSession.getSession().find(OnlineUser.class, id)
            );
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }

        return Optional.empty();
    }

    /**
     * Выгрузка записей по полю login
     * @param login значение поля login
     * @return Список записей
     */
    @Override
    public List<OnlineUser> loadRecord(String login) {

        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try{
            Criteria criteria = currentSession.getSession().createCriteria(OnlineUser.class);
            List records = criteria.add(Restrictions.eq("login", login)).list();

            List<OnlineUser> retList = new ArrayList<>();
            for (Object record : records) {
                retList.add((OnlineUser) record);
            }

            return retList;
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }

        return new ArrayList<>();
    }

    /**
     * Выгрузка всех записей
     * @return Все записи таблицы
     */
    @Override
    public List<OnlineUser> loadAll() {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try{
            List<OnlineUser> retList = new ArrayList<>();

            Criteria criteria = currentSession.getSession().createCriteria(OnlineUser.class);
            List onlineUsers = criteria.list();

            for (Object onlineUser : onlineUsers) {
                retList.add((OnlineUser) onlineUser);
            }
            return retList;
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }

        return new ArrayList<>();
    }

    /**
     * Сохрание объекта
     * @param user объекта
     * @return Успешность сохранения
     */
    @Override
    public boolean createRecord(OnlineUser user) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try{
            if (user.getId() == 0){
                currentSession.getSession().persist(user);
                return true;
            }

            logger.error("This record already exist");
            return false;
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            throw new OnlineUserDaoException(ex);
        }
    }

    /**
     * Обновление записи объекта
     * @param user записываем объект
     * @return успешность
     */
    @Override
    public boolean updateRecord(OnlineUser user) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try{
            if (user.getId() > 0){
                currentSession.getSession().merge(user);
                return true;
            }

            logger.error("The record doesn't exist");
            return false;
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            throw new OnlineUserDaoException(ex);
        }
    }

    /**
     * Удаление записи по значению поля login
     * @param login значения поля login
     * @return успешность
     */
    @Override
    public boolean deleteRecord(String login) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try{
            List<OnlineUser> records = loadRecord(login);
            if (records.size() > 0){
                for (OnlineUser record : records) {
                    currentSession.getSession().delete(record);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            throw new OnlineUserDaoException(ex);
        }
    }

    /**
     * @return Текущий менеджер сессий
     */
    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }


}