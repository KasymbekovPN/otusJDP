package ru.otus.kasymbekovPN.zuiNotesMS.socket.inputHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.JsonBuilderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.Message;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.MessageImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddress;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS.MessageErrorMSFromClientNotExist;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS.MessageErrorMSToClientNotExist;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeader;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeaderImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.SocketHandler;
import ru.otus.kasymbekovPN.zuiNotesCommon.sockets.input.SocketInputHandler;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSErrorCode;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.MSMessage;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MSClient;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;
import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.service.MsClientService;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class CommonSIH implements SocketInputHandler {

    //<
//    private static final Logger logger = LoggerFactory.getLogger(CommonSIH.class);

    private final MsClientService msClientService;
    private final SocketHandler socketHandler;

    private final JsonErrorGenerator jeGenerator;

    public CommonSIH(MsClientService msClientService, SocketHandler socketHandler, JsonErrorGenerator jeGenerator) {
        this.msClientService = msClientService;
        this.socketHandler = socketHandler;
        this.jeGenerator = jeGenerator;
    }

    @Override
    public void handle(JsonObject jsonObject) throws Exception {

        //<
//        JsonObject header = jsonObject.get("header").getAsJsonObject();
//        String type = header.get("type").getAsString();
//        logger.info("CommonSIH header : {}, data : {}", header, jsonObject);
//
//        JsonObject from = jsonObject.get("from").getAsJsonObject();
//        JsonObject to = jsonObject.get("to").getAsJsonObject();
//        MsClientUrl fromUrl = new MsClientUrl(
//                from.get("host").getAsString(),
//                from.get("port").getAsInt(),
//                from.get("entity").getAsString(),
//                type
//        );
//        MsClientUrl toUrl = new MsClientUrl(
//                to.get("host").getAsString(),
//                to.get("port").getAsInt(),
//                to.get("entity").getAsString(),
//                type
//        );
//
//        Optional<MSClient> optFromMsClient = msClientService.get(fromUrl);
//        Optional<MSClient> optToMsClient = msClientService.get(toUrl);
//
//        if (optFromMsClient.isPresent() && optToMsClient.isPresent()){
//            String str = jsonObject.toString();
//            MSClient fromMsClient = optFromMsClient.get();
//            MSMessage MSMessage = fromMsClient.produceMessage(toUrl, str, type);
//            fromMsClient.sendMessage(MSMessage);
//        } else {
//
//            JsonArray errors = new JsonArray();
//            if (!optFromMsClient.isPresent()){
//                errors.add(
//                        jeGenerator
//                            .handle(false, MSErrorCode.FROM_MS_CLIENT_DOESNT_EXIST.getCode())
//                            .set("client", fromUrl.getUrl())
//                            .get()
//                );
//            }
//            if (!optToMsClient.isPresent()){
//                errors.add(
//                        jeGenerator
//                            .handle(false, MSErrorCode.TO_MS_CLIENT_DOESNT_EXIST.getCode())
//                            .set("client", toUrl.getUrl())
//                            .get()
//                );
//            }
//
//            JsonObject responseJsonObject = new JsonBuilderImpl()
//                    .add(
//                            "header",
//                            new JsonBuilderImpl()
//                            .add("type", "WRONG")
//                            .add("request", false)
//                            .add("uuid", UUID.randomUUID().toString())
//                            .get()
//                    )
//                    .add("errors", errors)
//                    .add(
//                            "original",
//                            new JsonBuilderImpl().add("header", header).get()
//                    ).get();
//
//            socketHandler.send(responseJsonObject);
//        }
    }

    @Override
    public void handle(Message message) {
        log.info("CommonSIH.handle message : {}", message);

        MessageHeader header = message.getHeader();
        String type = header.getType();
        UUID uuid = header.getUUID();

        MessageAddress from = message.getFrom();
        MsClientUrl fromUrl = new MsClientUrl(from, type);

        MessageAddress to = message.getTo();
        MsClientUrl toUrl = new MsClientUrl(to, type);

        Optional<MSClient> optFromMsClient = msClientService.get(fromUrl);
        Optional<MSClient> optToMsClient = msClientService.get(toUrl);

        if (optFromMsClient.isPresent() && optToMsClient.isPresent()){
            MSClient fromMsClient = optFromMsClient.get();
            MSMessage MSMessage = fromMsClient.produceMessage(toUrl, message, type);
            fromMsClient.sendMessage(MSMessage);
        } else {
            HashSet<MessageError> errors = new HashSet<>();
            if (!optFromMsClient.isPresent()){
                errors.add(new MessageErrorMSFromClientNotExist(fromUrl.getUrl()));
            }

            if (!optToMsClient.isPresent()){
                errors.add(new MessageErrorMSToClientNotExist(toUrl.getUrl()));
            }

            Message responseMessage = new MessageImpl(
                    new MessageHeaderImpl("WRONG", false, uuid),
                    null,
                    from,
                    null,
                    errors.size() == 0 ? null : errors
            );
            socketHandler.send(responseMessage);
        }
    }
}
