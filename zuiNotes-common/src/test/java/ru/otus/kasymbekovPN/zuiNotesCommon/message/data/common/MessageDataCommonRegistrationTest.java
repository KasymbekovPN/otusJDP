package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("Testing of MessageDataCommonRegistration instance")
public class MessageDataCommonRegistrationTest {

    @Test
    @DisplayName("The testing of instance to json-like conversion")
    void objectToJson() throws JsonProcessingException {
        Boolean registration = true;
        MessageData md = new MessageDataCommonRegistration(registration);
        String json = new ObjectMapper().writeValueAsString(md);

        log.info("instance : {}", md);
        log.info("json : {}", json);

        assertThat(json).contains(
                "@type", "MessageDataCommonRegistration",
                "registration", String.valueOf(registration)
        );
    }

    @Test
    @DisplayName("The testing of instance to json-like conversion (registration == null)")
    void objectToJson1() throws JsonProcessingException {
        Boolean registration = null;
        MessageData md = new MessageDataCommonRegistration(registration);
        String json = new ObjectMapper().writeValueAsString(md);

        log.info("instance : {}", md);
        log.info("json : {}", json);

        assertThat(json).contains(
                "@type", "MessageDataCommonRegistration"
        );
    }

    @Test
    @DisplayName("The testing of json-line to instance")
    void jsonToObject() throws IOException {
        Boolean registration = true;
        String json = "{\"@type\":\"MessageDataCommonRegistration\",\"registration\":"+registration+"}";

        MessageData md = (MessageData) new ObjectMapper().readerFor(MessageDataCommonRegistration.class).readValue(json);
        log.info("instance : {}", md);

        assertThat(new MessageDataCommonRegistration(registration)).isEqualTo(md);
    }

    @Test
    @DisplayName("The testing of json-line to instance (it doesn't contain registration)")
    void jsonToObject1() throws IOException {
        String json = "{\"@type\":\"MessageDataCommonRegistration\"}";

        MessageData md = (MessageData) new ObjectMapper().readerFor(MessageDataCommonRegistration.class).readValue(json);
        log.info("instance : {}", md);

        assertThat(new MessageDataCommonRegistration(null)).isEqualTo(md);
    }

    @Test
    @DisplayName("The testing of json-line to instance (registration is null0")
    void jsonToObject2() throws IOException {
        Boolean registration = null;
        String json = "{\"@type\":\"MessageDataCommonRegistration\",\"registration\":"+registration+"}";

        MessageData md = (MessageData) new ObjectMapper().readerFor(MessageDataCommonRegistration.class).readValue(json);
        log.info("instance : {}", md);

        assertThat(new MessageDataCommonRegistration(registration)).isEqualTo(md);
    }
}
