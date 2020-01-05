package ru.otus.kasymbekovPN.zuiNotesDB.db.api.sessionManager;

import ru.otus.kasymbekovPN.zuiNotesDB.db.hibernate.sessionManager.DataBaseSessionHibernate;

/**
 * Интерфейс для реализации менеджера сессий
 */
public interface SessionManager extends AutoCloseable{

    /**
     * Начать сессию
     */
    void beginSession();

    /**
     * Фиксировать изменения
     */
    void commitSession();

    /**
     * Откатить изменения
     */
    void rollbackSession();

    /**
     * Закрыить сессию
     */
    void close();

    /**
     * Геттер текущий сессии
     * @return Текущая сессия
     */
    DataBaseSessionHibernate getCurrentSession();
}
