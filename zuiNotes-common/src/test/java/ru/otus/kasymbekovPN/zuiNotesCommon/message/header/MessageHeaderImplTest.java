package ru.otus.kasymbekovPN.zuiNotesCommon.message.header;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("MessageHeaderImpl testing which is instance of MessageHeader")
public class MessageHeaderImplTest {

    @Test
    @DisplayName("The testing of instance to json-line conversion")
    void objectToJson() throws JsonProcessingException {
        String type = "MessageType";
        Boolean request = true;
        UUID uuid = UUID.randomUUID();

        MessageHeader messageHeader = new MessageHeaderImpl(type, request, uuid);
        String result = new ObjectMapper().writeValueAsString(messageHeader);

        log.info("instance : {}", messageHeader);
        log.info("json line : {}", result);

        assertThat(result).contains(
                "@type", "MessageHeaderImpl",
                "type", type,
                "request", request.toString(),
                "uuid", uuid.toString()
        );
    }

    @Test
    @DisplayName("The testing of instance to json-line conversion (type is null)")
    void objectToJson1() throws JsonProcessingException {
        String type = null;
        Boolean request = true;
        UUID uuid = UUID.randomUUID();

        MessageHeader messageHeader = new MessageHeaderImpl(type, request, uuid);
        String result = new ObjectMapper().writeValueAsString(messageHeader);

        log.info("instance : {}", messageHeader);
        log.info("json line : {}", result);

        assertThat(result).contains(
                "@type", "MessageHeaderImpl",
                "type", "null",
                "request", request.toString(),
                "uuid", uuid.toString()
        );
    }

    @Test
    @DisplayName("The testing of json-line to instance conversion")
    void jsonToObject() throws IOException {
        String type = "MessageType";
        Boolean request = true;
        UUID uuid = UUID.randomUUID();

        String json = "{\"@type\":\"MessageHeaderImpl\",\"type\":\""+type+"\",\"request\":"+request+",\"uuid\":\""+uuid+"\"}";

        MessageHeader messageHeader = (MessageHeader) new ObjectMapper().readerFor(MessageHeaderImpl.class).readValue(json);
        log.info("instance : {}", messageHeader);

        MessageHeader mh = new MessageHeaderImpl(type, request, uuid);
        assertThat(mh).isEqualTo(messageHeader);
    }

    @Test
    @DisplayName("The testing of json-line to instance conversion (type is null)")
    void jsonToObject1() throws IOException {
        String type = null;
        Boolean request = true;
        UUID uuid = UUID.randomUUID();

        String json = "{\"@type\":\"MessageHeaderImpl\",\"type\":"+type+",\"request\":"+request+",\"uuid\":\""+uuid+"\"}";
        log.info("json: {}", json);

        MessageHeader messageHeader = (MessageHeader) new ObjectMapper().readerFor(MessageHeaderImpl.class).readValue(json);
        log.info("instance : {}", messageHeader);

        MessageHeader mh = new MessageHeaderImpl(type, request, uuid);
        assertThat(mh).isEqualTo(messageHeader);
    }
}
