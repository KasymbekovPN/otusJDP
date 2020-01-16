package ru.otus.kasymbekovPN.zuiNotesCommon.introduce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

///**
// * Класс, реализующий периодического сервис-уведомитель.<br><br>
// *
// * {@link NotifierImpl#runFlag} - основное состояние уведомителя; true - включен, false - отключен. <br>
// *
// * {@link NotifierImpl#processor} - поток, выполняющий основной функционал уведомителя <br>
// *
// * {@link NotifierImpl#notifierRunner} - инстанс, выполняющий уведомление.<br><br>
// *
// * {@link NotifierImpl#NotifierImpl(NotifierRunner)} - конструктор принимает инстанс {@link NotifierRunner} в
// * качестве агрумента, а также задает в {@link NotifierImpl#processor} выполняетмый метод
// * {@link NotifierImpl#handleProcessor()} <br><br>
// *
// * {@link NotifierImpl#handleProcessor()} - пока уведомитель включен ({@link NotifierImpl#runFlag} == true) через
// * каждые 100 мс производится отправка уведомления через {@link NotifierImpl#notifierRunner}. Если уведомитель
// * отключается ({@link NotifierImpl#runFlag} == false), то отправка уведомлений прекращается.<br>
// *
// * {@link NotifierImpl#shutdownProcessor()} - останавливает выполнение {@link NotifierImpl#processor} <br>
// *
// * {@link NotifierImpl#sleep(int)} - метод, организуюший ожидание <br>
// *
// * {@link NotifierImpl#stop()} - метод, останавливающий работу уведомителя.
// */
@Service
public class NotifierImpl implements Notifier {

    private static Logger logger = LoggerFactory.getLogger(NotifierImpl.class);

    private final AtomicBoolean runFlag = new AtomicBoolean(true);
    private final ExecutorService processor = Executors.newSingleThreadExecutor(
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("registrarImpl-processor-thread");
                return thread;
            }
    );

    private final NotifierRunner notifierRunner;

    public NotifierImpl(NotifierRunner notifierRunner) {
        this.notifierRunner = notifierRunner;
        processor.submit(this::handleProcessor);
    }

    private void handleProcessor(){
        while(runFlag.get()){
            notifierRunner.run();
            sleep(100);
        }

        processor.submit(this::waitStart);
    }

    private void waitStart(){
        while (!runFlag.get()){
            sleep(1_000);
        }

        processor.submit(this::handleProcessor);
    }

    private static void sleep(int delay){
        try{
            Thread.sleep(delay);
        } catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void start() {
        runFlag.set(true);
    }

    @Override
    public void stop() {
        runFlag.set(false);
    }
}

