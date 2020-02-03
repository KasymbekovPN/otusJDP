package ru.otus.kasymbekovPN.zuiNotesCommon.sockets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.echo.EchoClient;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonChecker;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.sending.SocketSendingHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
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

    private final Map<String, Map<Boolean, Set<EchoClient>>> echoTargets = new ConcurrentHashMap<>();
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

            JsonObject jsonObject = (JsonObject) new JsonParser().parse(in.readLine());
            echoSend(jsonObject);
            jsonChecker.setJsonObject(jsonObject, handlers.keySet());
            handlers.get(jsonChecker.getType()).handle(jsonChecker.getJsonObject());

        } catch (Exception ex){
            logger.error("SocketHandlerImpl::handleClientSocket : Error", ex);
        }
    }

    private void echoSend(JsonObject jsonObject){
        if (jsonObject.has("header")){
            JsonObject header = jsonObject.get("header").getAsJsonObject();
            if (header.has("type") && header.has("request")){
                String type = header.get("type").getAsString();
                boolean request = header.get("request").getAsBoolean();

                if (echoTargets.containsKey(type) && echoTargets.get(type).containsKey(request)){

                    JsonObject echoJsonObject = new JsonBuilderImpl()
                            .add(
                                    "header",
                                    new JsonBuilderImpl()
                                    .add("request", false)
                                    .add("uuid", UUID.randomUUID().toString())
                                    .get()
                            )
                            .add(
                                    "data",
                                    new JsonBuilderImpl()
                                    .add("message", type)
                                    .add("request", request)
                                    .add("data", jsonObject.deepCopy())
                                    .get()
                            )
                            .get();

                    Set<EchoClient> echoClients = echoTargets.get(type).get(request);
                    for (EchoClient echoClient : echoClients) {
                        echoJsonObject.get("header").getAsJsonObject().addProperty("type", echoClient.getEchoMessageType());
                        echoJsonObject.add("to", echoClient.getUrl());

                        send(echoJsonObject.deepCopy());
                    }
                }
            }
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

    @Override
    public void subscribeEcho(String observedMessageType, boolean request, EchoClient echoClient) {
        if (!echoTargets.containsKey(observedMessageType)){
            echoTargets.put(observedMessageType, new HashMap<>());
        }
        if (!echoTargets.get(observedMessageType).containsKey(request)){
            echoTargets.get(observedMessageType).put(request, new HashSet<>());
        }
        echoTargets.get(observedMessageType).get(request).add(echoClient);
    }

    @Override
    public void unsubscribeEcho(String observedMessageType, boolean request, EchoClient echoClient) {
        if (echoTargets.containsKey(observedMessageType)){
            Map<Boolean, Set<EchoClient>> booleanSetMap = echoTargets.get(observedMessageType);
            if (booleanSetMap.containsKey(request)){
                Set<EchoClient> echoClients = booleanSetMap.get(request);
                echoClients.remove(echoClient);

                if (echoClients.isEmpty()){
                    booleanSetMap.remove(request);
                    if (booleanSetMap.isEmpty()){
                        echoTargets.remove(observedMessageType);
                    }
                }
            }
        }
    }
}
