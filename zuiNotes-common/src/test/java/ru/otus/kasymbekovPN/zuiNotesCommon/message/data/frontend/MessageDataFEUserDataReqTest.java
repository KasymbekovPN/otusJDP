package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.frontend;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.MessageService;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("MessageDataFEUserDataReq Testing")
public class MessageDataFEUserDataReqTest {

    private String login;

    @BeforeEach
    void init(){
        login = "admin";
    }

    @Test
    @DisplayName("Testing of instance to string MessageDataFEUserDataReq conversion")
    void testInstance2StringConversion(){

        MessageData data = new MessageDataFEUserDataReq(login);
        log.info("instance : {}", data);

        Optional<String> maybeJson = MessageService.getAsString(data);
        log.info("maybeJson : {}", maybeJson);

        assertThat(maybeJson).isPresent();
        assertThat(maybeJson.get()).contains(
                "@type", "MessageDataFEUserDataReq",
                "login", login
        );
    }

    @Test
    @DisplayName("testing of string to instance MessageDataFEUserDataReq conversion")
    void testString2InstanceConversion(){
        String json = "{\"@type\":\"MessageDataFEUserDataReq\",\"login\":\""+login+"\"}";
        Optional<Object> maybeInstance = MessageService.getAsInstance(MessageDataFEUserDataReq.class, json);
        log.info("maybeInstance : {}", maybeInstance);

        assertThat(maybeInstance).isPresent();
        assertThat(maybeInstance.get()).isEqualTo(new MessageDataFEUserDataReq(login));
    }
}
