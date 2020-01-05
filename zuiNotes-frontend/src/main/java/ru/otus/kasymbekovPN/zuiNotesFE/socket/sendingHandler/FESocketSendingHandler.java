package ru.otus.kasymbekovPN.zuiNotesFE.socket.sendingHandler;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.entity.Entity;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonHelper;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketSendingHandler;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

///**
// * Обработчик отправки сообщения. <br><br>
// *
// * {@link FESocketSendingHandler#send(JsonObject)} - отправка сообщения. Сообщение отправляется в систему сообщений
// * {@link #msHost}:{@link #msPort}; в сообщение добавляются поля "from", содержащие данные об источнике сообщении и
// * "to", содержащее данные о приемнике. <br>
// */
//<
public class FESocketSendingHandler implements SocketSendingHandler {

    private static final Logger logger = LoggerFactory.getLogger(FESocketSendingHandler.class);

    private static final Entity selfEntity = Entity.FRONTEND;
    private static final Entity targetEntity = Entity.DATABASE;

    private final String msHost;
    private final String selfHost;
    private final String targetHost;

    private final int msPort;
    private final int selfPort;
    private final int targetPort;

    public FESocketSendingHandler(String msHost, String targetHost, int msPort, int selfPort, int targetPort) throws UnknownHostException {
        this.msHost = msHost;
        this.selfHost = InetAddress.getLocalHost().getHostAddress();
        this.targetHost = targetHost;
        this.msPort = msPort;
        this.selfPort = selfPort;
        this.targetPort = targetPort;
    }

    @Override
    public void send(JsonObject jsonObject) {
        try(Socket clientSocket = new Socket(msHost, msPort)){
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            jsonObject.add("from", JsonHelper.makeUrl(selfHost, selfPort, selfEntity));
            jsonObject.add("to", JsonHelper.makeUrl(targetHost, targetPort, targetEntity));

            logger.info("FESocketSendingHandler send : {}", jsonObject);
            out.println(jsonObject);
        } catch (Exception ex){
            logger.error("FESocketSendingHandler Error : '{}:{}' is unreachable", msHost, msPort);
        }
    }
}
