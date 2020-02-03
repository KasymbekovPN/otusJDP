package ru.otus.kasymbekovPN.zuiNotesFE.socket.sendingHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.sending.SocketSendingHandler;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class FESocketSendingHandler implements SocketSendingHandler {

    private static final Logger logger = LoggerFactory.getLogger(FESocketSendingHandler.class);

    private final String msHost;
    private final String selfHost;
    private final String targetHost;

    private final int msPort;
    private final int selfPort;
    private final int targetPort;

    private final Client client;

    public FESocketSendingHandler(String msHost, String targetHost, int msPort, int selfPort, int targetPort, Client client) throws UnknownHostException {
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
        try(Socket clientSocket = new Socket(msHost, msPort)){
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

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
                            .add("entity", "DATABASE")
                            .get()
                    )
                    .get();

            logger.info("FESocketSendingHandler send : {}", jsonObject);
            out.println(jsonObject);
        } catch (Exception ex){
            logger.error("FESocketSendingHandler Error : '{}:{}' is unreachable", msHost, msPort);
        }
    }
}
