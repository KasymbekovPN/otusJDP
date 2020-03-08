package ru.otus.kasymbekovPN.zuiNotesDB.socket.sendingHandler;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.sending.SocketSendingHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class NioSocketSendingHandler implements SocketSendingHandler {

    private final String msHost;
    private final String selfHost;
    private final String targetHost;

    private final int msPort;
    private final int selfPort;
    private final int targetPort;

    private final Client client;

    public NioSocketSendingHandler(String msHost, String targetHost, int msPort, int selfPort, int targetPort, Client client) throws UnknownHostException {
        this.msHost = msHost;
        this.selfHost = InetAddress.getLocalHost().getHostAddress();
        this.targetHost = targetHost;
        this.msPort = msPort;
        this.selfPort = selfPort;
        this.targetPort = targetPort;
        this.client = client;
    }

    @Override
    public void send(JsonObject jsonObject) {

        jsonObject = new JsonBuilderImpl(jsonObject)
                .add(
                        "from",
                        new JsonBuilderImpl()
                                .add("host", selfHost)
                                .add("port", selfPort)
                                .add("entity", client.getEntity())
                                .get()
                )
                .add(
                        "to",
                        new JsonBuilderImpl()
                                .add("host", targetHost)
                                .add("port", targetPort)
                                .add("entity", "FRONTEND")
                                .get()
                )
                .get();

        try(SocketChannel channel = SocketChannel.open(new InetSocketAddress(msHost, msPort))){
            ByteBuffer buffer = ByteBuffer.wrap(jsonObject.toString().getBytes());
            channel.write(buffer);

            log.info("NioSocketSendingHandler send : {}", jsonObject);
        } catch (IOException ex){
            log.error("MSSocketSendingHandler Error : '{}:{}' is unreachable", msHost, msPort);
        }


        //<
//        try(Socket clientSocket = new Socket(msHost, msPort)){
//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//
//            jsonObject = new JsonBuilderImpl(jsonObject)
//                    .add(
//                            "from",
//                            new JsonBuilderImpl()
//                                    .add("host", selfHost)
//                                    .add("port", selfPort)
//                                    .add("entity", client.getEntity())
//                                    .get()
//                    )
//                    .add(
//                            "to",
//                            new JsonBuilderImpl()
//                                    .add("host", targetHost)
//                                    .add("port", targetPort)
//                                    .add("entity", "FRONTEND")
//                                    .get()
//                    )
//                    .get();
//
//            logger.info("DBSocketSendingHandler send : {}", jsonObject);
//            out.println(jsonObject);
//        } catch (Exception ex){
//            logger.error("DBSocketSendingHandler Error : '{}:{}' is unreachable", msHost, msPort);
//        }
    }
}
