package ru.otus.kasymbekovPN.zuiNotesCommon.message.data.frontend;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.MessageService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("Testing of MessageDataFELoginReq")
public class MessageDataFELoginReqTest {

    private String login;
    private String password;

    @BeforeEach
    void init(){
        login = "admin";
        password = "qwerty";
    }

    @Test
    @DisplayName("Testing of instance to string conversion")
    void testInstance2String(){
        MessageDataFELoginReq instance = new MessageDataFELoginReq(login, password);
        Optional<String> maybeJson = MessageService.getAsString(instance);
        log.info("maybeJson : {}", maybeJson);

        assertThat(maybeJson).isPresent();
        assertThat(maybeJson.get()).contains(
                "@type", "MessageDataFELoginReq",
                "login", login,
                "password", password
        );
    }

    @Test
    @DisplayName("Testing of string to instance conversion")
    void testString2Instance(){
        String json = "{\"@type\":\"MessageDataFELoginReq\",\"login\":\""+login+"\",\"password\":\""+password+"\"}";
        Optional<Object> maybeObject = MessageService.getAsInstance(MessageDataFELoginReq.class, json);
        log.info("maybeObject : {}", maybeObject);

        assertThat(maybeObject).isPresent();
        assertThat(maybeObject.get()).isEqualTo(new MessageDataFELoginReq(login, password));
    }
}
