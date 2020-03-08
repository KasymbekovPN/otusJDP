package ru.otus.kasymbekovPN.zuiNotesMS.socket.sendingHandler;

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

    private final String selfHost;
    private final int selfPort;
    private final Client client;

    public NioSocketSendingHandler(int selfPort, Client client) throws UnknownHostException {
        this.selfHost = InetAddress.getLocalHost().getHostAddress();
        this.selfPort = selfPort;
        this.client = client;
    }

    @Override
    public void send(JsonObject jsonObject) {
        JsonObject to = jsonObject.get("to").getAsJsonObject();
        String toHost = to.get("host").getAsString();
        int toPort = to.get("port").getAsInt();

        if (!jsonObject.has("from")){
            jsonObject = new JsonBuilderImpl(jsonObject)
                    .add(
                            "from",
                            new JsonBuilderImpl()
                                    .add("host", selfHost)
                                    .add("port", selfPort)
                                    .add("entity", client.getEntity())
                                    .get()
                    )
                    .get();
        }

        try(SocketChannel channel = SocketChannel.open(new InetSocketAddress(toHost, toPort))){
            ByteBuffer buffer = ByteBuffer.wrap(jsonObject.toString().getBytes());
            channel.write(buffer);

            log.info("NioSocketSendingHandler send : {}", jsonObject);
        } catch (IOException ex){
            log.error("MSSocketSendingHandler Error : '{}:{}' is unreachable", toHost, toPort);
        }
    }
}
