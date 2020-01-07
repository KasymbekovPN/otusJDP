package ru.otus.kasymbekovPN.zuiNotesMS.socket.sendingHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketSendingHandler;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Обработчик отправки сообщения. <br><br>
 *
 * {@link MSSocketSendingHandler#send(JsonObject)} - отправка сообщения. <br>
 */
public class MSSocketSendingHandler implements SocketSendingHandler {

    private static final Logger logger = LoggerFactory.getLogger(MSSocketSendingHandler.class);

    private final String selfHost;
    private final int selfPort;

    public MSSocketSendingHandler(int selfPort) throws UnknownHostException {
        this.selfHost = InetAddress.getLocalHost().getHostAddress();
        this.selfPort = selfPort;
    }

    @Override
    public void send(JsonObject jsonObject) {
        JsonObject to = jsonObject.get("to").getAsJsonObject();
        String toHost = to.get("host").getAsString();
        int toPort = to.get("port").getAsInt();

        if (!jsonObject.has("from")){
            jsonObject.add("from", JsonHelper.makeUrl(selfHost, selfPort, "MESSAGE_SYSTEM"));
        }

        try(Socket clientSocket = new Socket(toHost, toPort)){
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            logger.info("MSSocketSendingHandler send : {}", jsonObject);
            out.println(jsonObject);
        } catch (Exception ex){
            logger.error("MSSocketSendingHandler Error : '{}:{}' is unreachable", toHost, toPort);
        }
    }
}
