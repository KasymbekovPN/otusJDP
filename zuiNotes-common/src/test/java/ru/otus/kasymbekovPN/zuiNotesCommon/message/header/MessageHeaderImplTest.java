package ru.otus.kasymbekovPN.zuiNotesCommon.message.header;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("MessageHeaderImpl testing which is instance of MessageHeader")
public class MessageHeaderImplTest {

    @Test
    @DisplayName("The testing of instance to json-line conversion")
    void objectToJson() throws JsonProcessingException {

        String entity = "db";
        String host = "localhost";
        int port = 8080;

        MessageHeader messageHeader = new MessageHeaderImpl(entity, host, port);
        String result = new ObjectMapper().writeValueAsString(messageHeader);

        log.info("instance : {}", messageHeader);
        log.info("json line : {}", result);

        assertThat(result).contains(
                "@type", "MessageHeaderImpl",
                "entity", entity,
                "host", host,
                "port", String.valueOf(port)
        );
    }

    @Test
    @DisplayName("The testing of json-line to instance conversion")
    void jsonToObject() throws IOException {
        String entity = "db";
        String host = "localhost";
        int port = 8080;
        String json = "{\"@type\":\"MessageHeaderImpl\",\"entity\":\""+entity+"\",\"host\":\""+host+"\",\"port\":"+8080+"}";

        MessageHeader messageHeader = (MessageHeader) new ObjectMapper().readerFor(MessageHeaderImpl.class).readValue(json);
        log.info("instance : {}", messageHeader);

        MessageHeader mh = new MessageHeaderImpl(entity, host, port);
        assertThat(mh).isEqualTo(messageHeader);
    }
}
