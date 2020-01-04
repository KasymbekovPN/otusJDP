package ru.otus.kasymbekovPN.zuiNotesCommon.entity;

/**
 * Пересиление сущностей системы<br><br>
 *
 * <ul>
 *     <li>{@link Entity#UNKNOWN} - неизвестная сущность</li>
 *     <li>{@link Entity#FRONTEND} - отправляет в DATABASE запросы через MESSAGE_SYSTEM</li>
 *     <li>{@link Entity#DATABASE} - отправляет в FRONTEND ответы через MESSAGE_SYSTEM</li>
 *     <li>{@link Entity#MESSAGE_SYSTEM} - систеы сообщений</li>
 * </ul>
 */
public enum Entity {
    UNKNOWN("UNKNOWN"),
    FRONTEND("FRONTEND"),
    DATABASE("DATABASE"),
    MESSAGE_SYSTEM("MESSAGE_SYSTEM");

    private String value;

    public String getValue() {
        return value;
    }

    Entity(String value) {
        this.value = value;
    }
}

