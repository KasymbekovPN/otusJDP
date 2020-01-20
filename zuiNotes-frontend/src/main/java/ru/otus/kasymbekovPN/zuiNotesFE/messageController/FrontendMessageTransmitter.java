package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FrontendMessageTransmitter {

    private static final Logger logger = LoggerFactory.getLogger(FrontendMessageTransmitter.class);

    private final Registrar registrar;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void handle(String data, String uuid, String messageType, boolean uuidIsRequestUUID){
        logger.info("FrontendMessageTransmitter uuid : {}, messageTYpe : {}", uuid, messageType);

        String UIId = uuidIsRequestUUID
                ? registrar.getUIIdByRequestUUID(uuid)
                : uuid;

        String destination = "/topic/" + messageType + "/" + UIId;
        simpMessagingTemplate.convertAndSend(destination, data);
    }
}
