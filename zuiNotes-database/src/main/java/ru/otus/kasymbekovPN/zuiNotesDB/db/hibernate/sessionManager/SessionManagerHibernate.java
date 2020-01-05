package ru.otus.kasymbekovPN.zuiNotesDB.db.hibernate.sessionManager;

import lombok.experimental.NonFinal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.otus.kasymbekovPN.zuiNotesDB.db.api.sessionManager.SessionManager;

/**
 * Реализации менеджера сессий
 */
public class SessionManagerHibernate implements SessionManager {

    /**
     * Сессия для работы с БД
     */
    @NonFinal
    private DataBaseSessionHibernate dataBaseSession;


    /**
     * Фактория сессий.
     */
    private final SessionFactory sessionFactory;

    public SessionManagerHibernate(SessionFactory sessionFactory) {
        if (sessionFactory == null)
            throw new SessionManagerException("SessionFactory is null");
        this.sessionFactory = sessionFactory;
    }

    /**
     * Начать сессию
     */
    @Override
    public void beginSession() {
        try{
            dataBaseSession = new DataBaseSessionHibernate(sessionFactory.openSession());
        } catch (Exception ex){
            throw new SessionManagerException(ex);
        }
    }

    /**
     * Фиксировать изменения
     */
    @Override
    public void commitSession() {
        checkSessionAndTransaction();
        try{
            dataBaseSession.getTransaction().commit();
            dataBaseSession.getSession().close();
        } catch (Exception ex){
            throw new SessionManagerException(ex);
        }
    }

    /**
     * Откатить изменения
     */
    @Override
    public void rollbackSession() {
        checkSessionAndTransaction();
        try {
            dataBaseSession.getTransaction().rollback();
            dataBaseSession.getSession().close();
        } catch (Exception ex) {
            throw new SessionManagerException(ex);
        }
    }

    /**
     * Закрыить сессию
     */
    @Override
    public void close() {
        if (dataBaseSession == null) {
            return;
        }
        Session session = dataBaseSession.getSession();
        if (session == null || !session.isConnected()) {
            return;
        }

        Transaction transaction = dataBaseSession.getTransaction();
        if (transaction == null || !transaction.isActive()) {
            return;
        }

        try {
            dataBaseSession.close();
            dataBaseSession = null;
        } catch (Exception ex) {
            throw new SessionManagerException(ex);
        }
    }

    /**
     * Геттер текущий сессии
     * @return Текущая сессия
     */
    @Override
    public DataBaseSessionHibernate getCurrentSession() {
        checkSessionAndTransaction();
        return dataBaseSession;
    }

    /**
     * Проверить сессию и транзакцию.
     */
    private void checkSessionAndTransaction(){
        if (dataBaseSession == null)
            throw new SessionManagerException("DataSession not opened");

        Session session = dataBaseSession.getSession();
        if (session == null || !session.isConnected())
            throw new SessionManagerException("Session not opened");

        Transaction transaction = dataBaseSession.getTransaction();
        if (transaction == null || !transaction.isActive())
            throw new SessionManagerException("Session not opened");
    }
}
