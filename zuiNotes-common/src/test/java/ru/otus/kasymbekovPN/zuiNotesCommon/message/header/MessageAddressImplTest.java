package ru.otus.kasymbekovPN.zuiNotesCommon.message.header;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddress;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddressImpl;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("MessageAddressImpl testing which is instance of MessageHeader")
public class MessageAddressImplTest {

    @Test
    @DisplayName("The testing of instance to json-line conversion")
    void objectToJson() throws JsonProcessingException {
        String entity = "DATABASE";
        String host = "localhost";
        Integer port = 8080;

        MessageAddress address = new MessageAddressImpl(entity, host, port);
        String result = new ObjectMapper().writeValueAsString(address);

        log.info("instance : {}", address);
        log.info("json line : {}", result);

        assertThat(result).contains(
                "@type", "MessageAddressImpl",
                "entity", entity,
                "host", host,
                "port", String.valueOf(port)
        );
    }

    @Test
    @DisplayName("The testing of instance to json-line conversion (entity is null)")
    void objectToJson1() throws JsonProcessingException {
        String entity = null;
        String host = "localhost";
        Integer port = 8080;

        MessageAddress address = new MessageAddressImpl(entity, host, port);
        String result = new ObjectMapper().writeValueAsString(address);

        log.info("instance : {}", address);
        log.info("json line : {}", result);

        assertThat(result).contains(
                "@type", "MessageAddressImpl",
                "host", host,
                "port", String.valueOf(port)
        );
    }

    @Test
    @DisplayName("The testing of json-line to instance conversion")
    void jsonToObject() throws IOException {
        String entity = "DATABASE";
        String host = "localhost";
        Integer port = 8080;

        String json = "{\"@type\":\"MessageAddressImpl\",\"entity\":\""+entity+"\",\"host\":\""+host+"\",\"port\":"+port+"}";

        MessageAddress ma = (MessageAddress) new ObjectMapper().readerFor(MessageAddressImpl.class).readValue(json);
        log.info("instance : {}", ma);

        assertThat(new MessageAddressImpl(entity, host, port)).isEqualTo(ma);
    }

    @Test
    @DisplayName("The testing of json-line to instance conversion (without entity)")
    void jsonToObject1() throws IOException {
        String host = "localhost";
        Integer port = 8080;

        String json = "{\"@type\":\"MessageAddressImpl\",\"host\":\""+host+"\",\"port\":"+port+"}";

        MessageAddress ma = (MessageAddress) new ObjectMapper().readerFor(MessageAddressImpl.class).readValue(json);
        log.info("instance : {}", ma);

        assertThat(new MessageAddressImpl(null, host, port)).isEqualTo(ma);
    }
}
