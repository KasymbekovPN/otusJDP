package ru.otus.kasymbekovPN.zuiNotesCommon.introduce;

/**
 * Интерфейс, служащий для реализации класса, осуществяющего отправку уведомтений<br><br>
 *
 * {@link @MsNotifierRunner#run} - метод, выполняющий отправку уведомнения.
 */
public interface NotifierRunner {
    void run();
}

