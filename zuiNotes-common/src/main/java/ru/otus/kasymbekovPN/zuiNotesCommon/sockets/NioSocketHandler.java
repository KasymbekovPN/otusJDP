package ru.otus.kasymbekovPN.zuiNotesCommon.sockets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonChecker;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.echo.EchoClient;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.sending.SocketSendingHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class NioSocketHandler implements SocketHandler {

    //< !!! как заравать значение
    private static final int BUFFER_SIZE = 4096;

    private final Map<String, Map<Boolean, Set<EchoClient>>> echoTargets = new ConcurrentHashMap<>();
    private final Map<String, SocketInputHandler> handlers = new ConcurrentHashMap<>();
    private final JsonChecker jsonChecker;
    private final SocketSendingHandler socketSendingHandler;
    private final int selfPort;
    private final ExecutorService inputProcess = Executors.newSingleThreadExecutor(
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("input-process-thread");
                return thread;
            }
    );

    public NioSocketHandler(JsonChecker jsonChecker, SocketSendingHandler socketSendingHandler, int selfPort) {
        this.jsonChecker = jsonChecker;
        this.socketSendingHandler = socketSendingHandler;
        this.selfPort = selfPort;

        this.inputProcess.submit(this::handleInputProcess);
    }

    private void handleInputProcess() {

        try(Selector selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open()){

            channel.bind(new InetSocketAddress(selfPort));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (true){
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()){
                        register(selector, channel);
                    } else if (key.isReadable()){
                        handle(buffer, key);
                    }

                    iterator.remove();
                }
            }
        } catch (IOException ex){
            log.error("handleInProcessor : " + ex);
        }
    }

    private void handle(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        client.close();

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(new String(buffer.array()).trim());
        buffer.clear();

        echoSend(jsonObject);
        try{
            jsonChecker.setJsonObject(jsonObject, handlers.keySet());
            handlers.get(jsonChecker.getType()).handle(jsonChecker.getJsonObject());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void register(Selector selector, ServerSocketChannel channel) throws IOException {
        SocketChannel client = channel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private void echoSend(JsonObject jsonObject){
        if (jsonObject.has("header")){
            JsonObject header = jsonObject.get("header").getAsJsonObject();
            if (header.has("type") && header.has("request")){
                String type = header.get("type").getAsString();
                String request = header.get("request").getAsString();

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
