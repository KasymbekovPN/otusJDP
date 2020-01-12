package ru.otus.kasymbekovPN.zuiNotesMS;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystem;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MessageSystemImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactory;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.creation.factory.MsClientCreatorFactoryImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientServiceImpl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.Solus;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.solus.SolusImpl;

public class MessageSystemImplTest {

    private static final Logger logger = LoggerFactory.getLogger(MessageSystemImplTest.class);

    private static final String SOLUS_FIELD = "solus";
    private static final String MESSAGES_FIELD = "messages";
    private static final String DATABASE = "DATABASE";
    private static final String FRONTEND = "FRONTEND";

    private static JsonObject clientConfig = new JsonObject();
    static {
        JsonObject tmp = new JsonObject();

        JsonObject db = new JsonObject();
        JsonArray dbMsgs = new JsonArray();
        dbMsgs.add("AUTH_USER");
        dbMsgs.add("ADD_USER");
        dbMsgs.add("DEL_USER");
        db.addProperty(SOLUS_FIELD, false);
        db.add(MESSAGES_FIELD, dbMsgs);
        tmp.add(DATABASE, db);

        JsonObject fe = new JsonObject();
        JsonArray feMsgs = new JsonArray();
        feMsgs.add("AUTH_USER");
        feMsgs.add("ADD_USER");
        feMsgs.add("DEL_USER");
        fe.addProperty(SOLUS_FIELD, false);
        fe.add(MESSAGES_FIELD, feMsgs);
        tmp.add(FRONTEND, fe);

        clientConfig = tmp;
    }

//    private static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);
//
//    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
//    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
//    private static final String HANDLER_MAPPING_USER_DATA = "userData";
//
//
//    private MessageSystem messageSystem;
//    private FrontendService frontendService;
//    private MsClient databaseMsClient;
//    private MsClient frontendMsClient;
//
//<

    private MessageSystem messageSystem;

    @BeforeEach
    public void setup(){

//        MsClientCreatorFactory msClientCreatorFactory = new MsClientCreatorFactoryImpl(clientConfig, MESSAGES_FIELD);
//        Solus solus = new SolusImpl(clientConfig, SOLUS_FIELD);
//        MsClientServiceImpl msClientService = new MsClientServiceImpl(msClientCreatorFactory, solus);
//
//        messageSystem = new MessageSystemImpl(msClientService);
//
//        msClientService.createClient()
    }

//    @BeforeEach
//    public void setup() {
//        logger.info("setup");
//        messageSystem = new MessageSystemImpl();
//
//        databaseMsClient = spy(new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem));
//        DBService dbService = mock(DBService.class);
//        when(dbService.getUserData(any(Long.class))).thenAnswer(invocation -> String.valueOf((Long)invocation.getArgument(0)));
//        databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbService));
//        messageSystem.addClient(databaseMsClient);
//
//        frontendMsClient = spy(new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem));
//        frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
//        frontendMsClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
//        messageSystem.addClient(frontendMsClient);
//
//        logger.info("setup done");
//    }

    @Test
    public void test(){
        System.out.println(clientConfig);
    }


//    @DisplayName("Базовый сценарий получения данных")
//    @RepeatedTest(1000)
//    public void getDataById() throws Exception {
//        int counter = 3;
//        CountDownLatch waitLatch = new CountDownLatch(counter);
//
//        IntStream.range(0, counter).forEach(id ->
//                frontendService.getUserData(id, data -> {
//                    assertThat(data).isEqualTo(String.valueOf(id));
//                    waitLatch.countDown();
//                }));
//
//        waitLatch.await();
//        messageSystem.dispose();
//        logger.info("done");
//    }
//
//    @DisplayName("Выполнение запроса после остановки сервиса")
//    @RepeatedTest(1000)
//    public void getDataAfterShutdown() throws Exception {
//        messageSystem.dispose();
//
//        CountDownLatch waitLatchShutdown = new CountDownLatch(1);
//
//        Mockito.reset(frontendMsClient);
//        when(frontendMsClient.sendMessage(any(Message.class))).
//                thenAnswer(invocation -> {
//                    waitLatchShutdown.countDown();
//                    return null;
//                });
//
//        frontendService.getUserData(5, data -> logger.info("data:{}", data));
//        waitLatchShutdown.await();
//        boolean result = verify(frontendMsClient).sendMessage(any(Message.class));
//        assertThat(result).isFalse();
//
//        logger.info("done");
//    }



//    }

}
