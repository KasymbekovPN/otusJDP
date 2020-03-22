package ru.otus.kasymbekovPN.zuiNotesCommon.message.header;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("The testing of MessageError implementations")
public class MessageErrorTest {

    @Test
    @DisplayName("The testing of MessageErrorCommonFieldNotExist instance to json-line conversion")
    void testMECFieldNotExistInstanceToJson() throws JsonProcessingException {
        String fieldValue = "any value";
        MessageError instance = new MessageErrorCommonFieldNotExist(fieldValue);
        String json = new ObjectMapper().writeValueAsString(instance);
        log.info("json-line : {}", json);

        assertThat(json).contains(
                "@type", "MessageErrorCommonFieldNotExist",
                "field", fieldValue
        );
    }

    @Test
    @DisplayName("The testing json-line to MessageErrorCommonFieldNotExist instance conversion")
    void testJsonToMECFieldNotExistInstance() throws IOException {
        String fieldValue = "any value";
        String json = "{\"@type\":\"MessageErrorCommonFieldNotExist\",\"field\":\""+fieldValue+"\"}";
        MessageError messageError = (MessageError) new ObjectMapper()
                .readerFor(MessageErrorCommonFieldNotExist.class)
                .readValue(json);
        log.info("instance : {}", messageError);

        assertThat(new MessageErrorCommonFieldNotExist(fieldValue)).isEqualTo(messageError);
    }

    @Test
    @DisplayName("The testing of MessageErrorCommonInvalidMessageType to json-line conversion")
    void testMECInvalidMessageTypeInstanceToJson() throws JsonProcessingException {
        String type = "wrong type";
        MessageError me = new MessageErrorCommonInvalidMessageType(type);
        String json = new ObjectMapper().writeValueAsString(me);
        log.info("json-line {} : ", json);

        assertThat(json).contains(
                "@type", "MessageErrorCommonInvalidMessageType",
                "type", type
        );
    }

    @Test
    @DisplayName("The testing json-line to MessageErrorCommonInvalidMessageType instance conversion")
    void testJsonToMECInvalidMessageTypeInstance() throws IOException {
        String type = "wrong type";
        String json = "{\"@type\":\"MessageErrorCommonInvalidMessageType\",\"type\":\""+type+"\"}";
        MessageError messageError = (MessageError) new ObjectMapper()
                .readerFor(MessageErrorCommonInvalidMessageType.class)
                .readValue(json);
        log.info("instance : {}", messageError);

        assertThat(new MessageErrorCommonInvalidMessageType(type)).isEqualTo(messageError);
    }

    @Test
    @DisplayName("The testing of MessageErrorCommonInvalidFieldType to json-line conversion")
    void testMECInvalidFieldTypeInstanceToJson() throws JsonProcessingException {
        String type = "wrong type";
        String field = "any field";
        MessageErrorCommonInvalidFieldType me = new MessageErrorCommonInvalidFieldType(field, type);
        String json = new ObjectMapper().writeValueAsString(me);
        log.info("json-line {} : ", json);

        assertThat(json).contains(
                "@type", "MessageErrorCommonInvalidFieldType",
                "field", field,
                "type", type
        );
    }

    @Test
    @DisplayName("The testing json-line to MessageErrorCommonInvalidFieldType instance conversion")
    void testJsonToMECInvalidFieldTypeInstance() throws IOException {
        String field = "any field";
        String type = "wrong type";
        String json = "{\"@type\":\"MessageErrorCommonInvalidFieldType\",\"field\":\""+field+"\",\"type\":\""+type+"\"}";
        MessageError messageError = (MessageError) new ObjectMapper()
                .readerFor(MessageErrorCommonInvalidFieldType.class)
                .readValue(json);
        log.info("instance : {}", messageError);

        assertThat(new MessageErrorCommonInvalidFieldType(field, type)).isEqualTo(messageError);
    }

    @Test
    @DisplayName("The testing of MessageErrorCommonUnknownFieldType instance to json-line conversion")
    void testMECUnknownFieldTypeInstanceToJson() throws JsonProcessingException {
        String field = "any";
        MessageError me = new MessageErrorCommonUnknownFieldType(field);
        String json = new ObjectMapper().writeValueAsString(me);
        log.info("json-line : {}", json);

        assertThat(json).contains(
                "@type", "MessageErrorCommonUnknownFieldType",
                "field", field
        );
    }

    @Test
    @DisplayName("The testing json-line to MessageErrorCommonUnknownFieldType instance conversion")
    void testJsonToMECUnknownFieldType() throws IOException {
        String field = "any";
        String json = "{\"@type\":\"MessageErrorCommonUnknownFieldType\",\"field\":\""+field+"\"}";
        MessageError messageError = (MessageError) new ObjectMapper()
                .readerFor(MessageErrorCommonUnknownFieldType.class)
                .readValue(json);
        log.info("instance : {}", messageError);

        assertThat(new MessageErrorCommonUnknownFieldType(field)).isEqualTo(messageError);
    }
}
