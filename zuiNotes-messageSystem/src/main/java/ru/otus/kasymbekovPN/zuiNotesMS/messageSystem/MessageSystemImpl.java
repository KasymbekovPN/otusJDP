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
    private final BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_SIZE);
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
                Message message = messageQueue.take();
                if (message == Message.getVoidMessage()){
                    logger.info("Received the stop message");
                } else {
                    Optional<MSClient> optClientTo = msClientService.get(message.getToUrl());
                    if (optClientTo.isPresent()){
                        messageHandler.submit(
                                () -> handlerMessage(optClientTo.get(), message)
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

    private void handlerMessage(MSClient msClient, Message message){
        try{
            msClient.handle(message);
        } catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            logger.error("message : {}", message);
        }
    }

    private void insertStopMessage() throws InterruptedException {
        boolean result = messageQueue.offer(Message.getVoidMessage());
        while (!result){
            Thread.sleep(100);
            result = messageQueue.offer(Message.getVoidMessage());
        }
    }

    @Override
    public synchronized boolean newMessage(Message message) {
        if (runFlag.get()){
            return messageQueue.offer(message);
        } else {
            logger.warn("MS is being shutting down... rejected : {}", message);
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

