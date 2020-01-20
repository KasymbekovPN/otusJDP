package ru.otus.kasymbekovPN.zuiNotesCommon.introduce;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class NotifierImpl implements Notifier {

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

