package ru.otus.kasymbekovPN.zuiNotesDB.socket.sendingHandler;

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

/**
 * Обработчик отправки сообщения. <br><br>
 *
 * {@link DBSocketSendingHandler#send(JsonObject)} - отправка сообщения. Сообщение отправляется в систему сообщений
 * {@link #msHost}:{@link #msPort}; в сообщение добавляются поля "from", содержащие данные об источнике сообщении и
 * "to", содержащее данные о приемнике. <br>
 */
public class DBSocketSendingHandler implements SocketSendingHandler {

    private static final Logger logger = LoggerFactory.getLogger(DBSocketSendingHandler.class);

    private final String msHost;
    private final String selfHost;
    private final String targetHost;

    private final int msPort;
    private final int selfPort;
    private final int targetPort;

    private final Client client;

    public DBSocketSendingHandler(String msHost, String targetHost, int msPort, int selfPort, int targetPort, Client client) throws UnknownHostException {
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
                            .add("entity", "FRONTEND")
                            .get()
                    )
                    .get();

            logger.info("DBSocketSendingHandler send : {}", jsonObject);
            out.println(jsonObject);
        } catch (Exception ex){
            logger.error("DBSocketSendingHandler Error : '{}:{}' is unreachable", msHost, msPort);
        }
    }
}

