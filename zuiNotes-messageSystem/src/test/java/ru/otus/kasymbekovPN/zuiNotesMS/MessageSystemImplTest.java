package ru.otus.kasymbekovPN.zuiNotesMS;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.ClientImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonCheckerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.Message;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystemImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.CmnMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.creator.WrongMsClientCreator;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactoryImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientServiceImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.handler.WrongMSMessageHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.CommonSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler.WrongSIH;
import ru.otus.kasymbekovPN.zuiNotesMS.socket.sendingHandler.MSSocketSendingHandler;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.*;

public class MessageSystemImplTest {

//    private static final Logger logger = LoggerFactory.getLogger(MessageSystemImplTest.class);
//
//    private static final String SOLUS_FIELD = "solus";
//    private static final String MESSAGES_FIELD = "messages";
//    private static final String DATABASE = "DATABASE";
//    private static final String FRONTEND = "FRONTEND";
//
//    private static JsonObject clientConfig = new JsonObject();
//    private static JsonObject testMessage = new JsonObject();
//    static {
//        JsonObject tmp = new JsonObject();
//
//        JsonObject db = new JsonObject();
//        JsonArray dbMsgs = new JsonArray();
//        dbMsgs.add("TEST_MESSAGE");
//        db.addProperty(SOLUS_FIELD, false);
//        db.add(MESSAGES_FIELD, dbMsgs);
//        tmp.add(DATABASE, db);
//
//        JsonObject fe = new JsonObject();
//        JsonArray feMsgs = new JsonArray();
//        feMsgs.add("TEST_MESSAGE");
//        fe.addProperty(SOLUS_FIELD, false);
//        fe.add(MESSAGES_FIELD, feMsgs);
//        tmp.add(FRONTEND, fe);
//
//        clientConfig = tmp;
//
//        tmp = new JsonObject();
//        tmp.addProperty("type", "TEST_MESSAGE");
//        tmp.addProperty("request", true);
//        tmp.addProperty("uuid", UUID.randomUUID().toString());
//
//        testMessage = tmp;
//    }
//
//    private MessageSystem messageSystem = null;
//    private MsClientServiceImpl msClientService;
//    private SocketHandler socketHandler;
//    private MsClientCreatorFactoryImpl msClientCreatorFactory;
//    private MsClientUrl dbMsClientUrl;
//    private MsClientUrl feMsClientUrl;
//
//    private Map<Integer, JsonObject> buffer;
//
//    @BeforeEach
//    public void setup() throws Exception {
//
//        buffer = new ConcurrentHashMap<>();
//
//        msClientCreatorFactory = new MsClientCreatorFactoryImpl(
//                new CmnMsClientCreator(new TestCommonMSMessageHandler(buffer), new WrongMSMessageHandler()),
//                new WrongMsClientCreator(),
//                clientConfig,
//                MESSAGES_FIELD
//        );
//
//        msClientService = new MsClientServiceImpl();
//        socketHandler = createSocketHandler(msClientService);
//        messageSystem = new MessageSystemImpl(msClientService);
//
//        dbMsClientUrl = new MsClientUrl("localhost", 1000, DATABASE, "I_AM");
//        feMsClientUrl = new MsClientUrl("localhost", 1001, FRONTEND, "I_AM");
//
//
//        Optional<MSClient> maybeDBMsClient = msClientCreatorFactory.get(dbMsClientUrl.getEntity()).create(
//                dbMsClientUrl,
//                socketHandler,
//                messageSystem
//        );
//        if (maybeDBMsClient.isPresent()){
//            msClientService.addClient(
//                    dbMsClientUrl,
//                    maybeDBMsClient.get()
//            );
//        }
//        Optional<MSClient> maybeFEMsClient = msClientCreatorFactory.get(feMsClientUrl.getEntity()).create(
//                feMsClientUrl,
//                socketHandler,
//                messageSystem
//        );
//        if (maybeFEMsClient.isPresent()){
//            msClientService.addClient(
//                    feMsClientUrl,
//                    maybeFEMsClient.get()
//            );
//        }
//    }
//
//    private SocketHandler createSocketHandler(MsClientService msClientService) throws Exception {
//        int msPort = 1003;
//        Client client = new ClientImpl("MESSAGE_SYSTEM");
//        SocketHandler socketHandler = new SocketHandlerImpl(
//                new JsonCheckerImpl(createCommonJeoGenerator()),
//                new MSSocketSendingHandler(msPort, client),
//                msPort
//        );
//
//        final JsonArray commonMessages = new JsonArray();
//
//        for (JsonElement commonMessage : commonMessages) {
//            socketHandler.addHandler(
//                    commonMessage.getAsString(),
//                    new CommonSIH(msClientService, socketHandler, createMsJeoGenerator())
//            );
//        }
//
//        socketHandler.addHandler("WRONG", new WrongSIH(socketHandler));
//
//        return socketHandler;
//    }
//
//    private JsonErrorObjectGenerator createCommonJeoGenerator(){
//        return new JsonErrorObjectGeneratorImpl("MESSAGE_SYSTEM", true);
//    }
//
//    private JsonErrorObjectGenerator createMsJeoGenerator(){
//        return new JsonErrorObjectGeneratorImpl("MESSAGE_SYSTEM", false);
//    }
//
//    @Test
//    void testMS() throws InterruptedException {
//
//        int number = 1_000;
//
//        for(int i = 0 ;i < number; i++){
//            Optional<MSClient> maybeFEClient = msClientService.get(feMsClientUrl);
//            if (maybeFEClient.isPresent()){
//                MSClient msClient = maybeFEClient.get();
//                JsonObject jsonObject = testMessage.deepCopy();
//                jsonObject.addProperty("value", i);
//                Message message = msClient.produceMessage(dbMsClientUrl, jsonObject.toString(), "TEST_MESSAGE");
//                msClient.sendMessage(message);
//            }
//        }
//
//        Thread.sleep(2 * number);
//
//        int counter = 0;
//        for (int i = 0; i < number; i++) {
//            JsonObject jsonObject = buffer.get(i);
//            if (jsonObject.get("type").getAsString().equals(testMessage.get("type").getAsString()) &&
//                jsonObject.get("request").getAsString().equals(testMessage.get("request").getAsString()) &&
//                jsonObject.get("uuid").getAsString().equals(testMessage.get("uuid").getAsString()) &&
//                jsonObject.get("value").getAsInt() == i){
//                counter++;
//            }
//        }
//
//        assertThat(number).isEqualTo(counter);
//
//        messageSystem.dispose();
//    }
}
