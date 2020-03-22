package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MessageSystemImpl implements MessageSystem {

    private static final Logger logger = LoggerFactory.getLogger(MessageSystemImpl.class);

    private static final int MESSAGE_QUEUE_SIZE = 1_000;
    private static final int MESSAGE_HANDLER_THREAD_LIMIT = 2;

    private final AtomicBoolean runFlag = new AtomicBoolean(true);
    private final MsClientService msClientService;
    private final BlockingQueue<MSMessage> MSMessageQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_SIZE);
    private final ExecutorService messageProcessor = Executors.newSingleThreadExecutor(
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("message-processor-thread");
                return thread;
            }
    );
    private final ExecutorService messageHandler = Executors.newFixedThreadPool(
            MESSAGE_HANDLER_THREAD_LIMIT,
            new ThreadFactory() {
                private final AtomicInteger threadNameCounter = new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable);
                    thread.setName("message-handler-thread-" + threadNameCounter.incrementAndGet());
                    return  thread;
                }
            }
    );

    public MessageSystemImpl(MsClientService msClientService) {
        this.msClientService = msClientService;
        messageProcessor.submit(this::messageProcessor);
    }

    private void messageProcessor(){
        logger.info("messageProcessor started");
        while (runFlag.get())
        {
            try{
                MSMessage MSMessage = MSMessageQueue.take();
                if (MSMessage == MSMessage.getVoidMsMessage()){
                    logger.info("Received the stop message");
                } else {
                    Optional<MSClient> optClientTo = msClientService.get(MSMessage.getToUrl());
                    if (optClientTo.isPresent()){
                        messageHandler.submit(
                                () -> handlerMessage(optClientTo.get(), MSMessage)
                        );
                    } else {
                        logger.warn("Client not found");
                    }
                }
            } catch (InterruptedException ex){
                logger.error(ex.getMessage(), ex);
                Thread.currentThread().interrupt();
            } catch (Exception ex){
                logger.error(ex.getMessage(), ex);
            }
        }

        messageHandler.submit(this::messageHandlerShutdown);
        logger.info("messageProcessor finished");
    }

    private void messageHandlerShutdown(){
        messageHandler.shutdown();
        logger.info("messageHandler has been shut down");
    }

    private void handlerMessage(MSClient msClient, MSMessage MSMessage){
        try{
            msClient.handle(MSMessage);
        } catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            logger.error("message : {}", MSMessage);
        }
    }

    private void insertStopMessage() throws InterruptedException {
        boolean result = MSMessageQueue.offer(MSMessage.getVoidMsMessage());
        while (!result){
            Thread.sleep(100);
            result = MSMessageQueue.offer(MSMessage.getVoidMsMessage());
        }
    }

    @Override
    public synchronized boolean newMessage(MSMessage MSMessage) {
        if (runFlag.get()){
            return MSMessageQueue.offer(MSMessage);
        } else {
            logger.warn("MS is being shutting down... rejected : {}", MSMessage);
            return false;
        }
    }

    @Override
    public synchronized void dispose() throws InterruptedException {
        runFlag.set(false);
        insertStopMessage();
        messageProcessor.shutdown();
        messageHandler.awaitTermination(60, TimeUnit.SECONDS);
    }
}

