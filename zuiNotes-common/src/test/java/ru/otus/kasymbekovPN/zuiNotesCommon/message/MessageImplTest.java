package ru.otus.kasymbekovPN.zuiNotesCommon.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddress;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddressImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common.MessageDataCommonRegistrationReq;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.common.MessageErrorCommonFieldNotExist;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeader;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeaderImpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("The messageImpl testing")
@Slf4j
public class MessageImplTest {

    @Test
    @DisplayName("The testing of instance to json-line conversion")
    void instanceToJson() throws JsonProcessingException {

        String type = "messageType";
        Boolean request = true;
        UUID uuid = UUID.randomUUID();
        MessageHeader header = new MessageHeaderImpl(type, request, uuid);

        String fromEntity = "DATABASE";
        String fromHost = "localhost";
        Integer fromPort = 8080;
        MessageAddress from = new MessageAddressImpl(fromEntity, fromHost, fromPort);

        String toEntity = "FRONTEND";
        String toHost = "localhost";
        Integer toPort = 8081;
        MessageAddress to = new MessageAddressImpl(toEntity, toHost, toPort);

        Boolean registration = false;
        MessageData data = new MessageDataCommonRegistrationReq(registration);

        String field = "any field";
        MessageError error0 = new MessageErrorCommonFieldNotExist(field);

        Set<MessageError> errors = new HashSet<MessageError>(){{
            add(error0);
        }};

        Message message = new MessageImpl(header, from, to, data, errors);
        log.info("instance : {}", message);

        String json = new ObjectMapper().writeValueAsString(message);
        log.info("json : {}", json);
    }

    @Test
    @DisplayName("The testing of json-line to instance conversion")
    void jsonToInstance() throws IOException {

        String type = "messageType";
        Boolean request = true;
        UUID uuid = UUID.randomUUID();
        MessageHeader header = new MessageHeaderImpl(type, request, uuid);

        String fromEntity = "DATABASE";
        String fromHost = "localhost";
        Integer fromPort = 8080;
        MessageAddress from = new MessageAddressImpl(fromEntity, fromHost, fromPort);

        String toEntity = "FRONTEND";
        String toHost = "localhost";
        Integer toPort = 8081;
        MessageAddress to = new MessageAddressImpl(toEntity, toHost, toPort);

        Boolean registration = false;
        MessageData data = new MessageDataCommonRegistrationReq(registration);

        String field = "any field";
        MessageError error0 = new MessageErrorCommonFieldNotExist(field);

        Set<MessageError> errors = new HashSet<MessageError>(){{
            add(error0);
        }};

        String json = "{\"@type\":\"MessageImpl\"," +
                "\"header\":{\"@type\":\"MessageHeaderImpl\",\"type\":\""+type+"\",\"request\":"+request+",\"uuid\":\""+uuid+"\"}," +
                "\"from\":{\"@type\":\"MessageAddressImpl\",\"entity\":\""+fromEntity+"\",\"host\":\""+fromHost+"\",\"port\":"+fromPort+"}," +
                "\"to\":{\"@type\":\"MessageAddressImpl\",\"entity\":\""+toEntity+"\",\"host\":\""+toHost+"\",\"port\":"+toPort+"}," +
                "\"data\":{\"@type\":\"MessageDataCommonRegistration\",\"registration\":"+registration+"}," +
                "\"errors\":[{\"@type\":\"MessageErrorCommonFieldNotExist\",\"field\":\""+field+"\"}]}";

        Message message = (Message) new ObjectMapper().readerFor(MessageImpl.class).readValue(json);
        log.info("instance : {}", message.toString());

        assertThat(new MessageImpl(header, from, to, data, errors)).isEqualTo(message);
    }
}
