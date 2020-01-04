package ru.otus.kasymbekovPN.zuiNotesCommon.sockets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonChecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс-обработчик сокетов.<br><br>
 *
 * {@link SocketHandlerImpl#handlers} - обработчики входящих сообщений. <br>
 *
 * {@link SocketHandlerImpl#jsonChecker} - инстанс, проверяющий json-сообщения. <br>
 *
 * {@link SocketHandlerImpl#socketSendingHandler} - инстанс, обрабатывающий отправку сообщений. <br>
 *
 * {@link SocketHandlerImpl#inProcessor} - тред, в котором крутится метод приема входящих сообщений <br>
 *
 * {@link SocketHandlerImpl#selfPort} - номер входящего порта. <br>
 *
 * {@link SocketHandlerImpl#handleClientSocket(Socket)} - обработка входящего сообщения <br>
 *
 * {@link SocketHandlerImpl#send(JsonObject)} - отправка сообщения <br>
 *
 * {@link SocketHandlerImpl#addHandler(String, SocketInputHandler)} - добавление обработчиков принятых сообщений<br>
 */
public class SocketHandlerImpl implements SocketHandler {

    private final static Logger logger = LoggerFactory.getLogger(SocketHandlerImpl.class);

    private final Map<String, SocketInputHandler> handlers = new ConcurrentHashMap<>();
    private final JsonChecker jsonChecker;
    private final SocketSendingHandler socketSendingHandler;
    private final ExecutorService inProcessor = Executors.newSingleThreadExecutor(
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("in-processor-thread");
                return thread;
            }
    );

    private int selfPort;

    public SocketHandlerImpl(JsonChecker jsonChecker, SocketSendingHandler socketSendingHandler, int selfPort) {
        this.jsonChecker = jsonChecker;
        this.socketSendingHandler = socketSendingHandler;
        this.selfPort = selfPort;

        this.inProcessor.submit(this::handleInProcessor);
    }

    private void handleInProcessor(){
        try(ServerSocket serverSocket = new ServerSocket(selfPort)){
            while(!Thread.currentThread().isInterrupted()){
                logger.info("SocketHandlerImpl : Waiting for client connection");
                try(Socket clientSocket = serverSocket.accept()){
                    handleClientSocket(clientSocket);
                }
            }
        } catch (Exception ex){
            logger.error("SocketHandlerImpl::handleInProcessor : Error", ex);
        }
    }

    private void handleClientSocket(Socket clientSocket){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            jsonChecker.setJsonObject(
                    (JsonObject) new JsonParser().parse(in.readLine()),
                    handlers.keySet()
            );
            handlers.get(jsonChecker.getType()).handle(jsonChecker.getJsonObject());

        } catch (Exception ex){
            logger.error("SocketHandlerImpl::handleClientSocket : Error", ex);
        }
    }

    @Override
    public void send(JsonObject jsonObject) {
        socketSendingHandler.send(jsonObject);
    }

    @Override
    public void addHandler(String name, SocketInputHandler handler) {
        handlers.put(name, handler);
    }
}
