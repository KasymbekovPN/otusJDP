package ru.otus.kasymbekovPN.zuiNotesCommon.message.content.frontend;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.MessageService;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.content.MessageContent;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.database.MessageDataDBLoginResp;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MS.MessageErrorMSToClientNotExist;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.database.MessageErrorDBEmptyLoginPassword;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("Testing of MessageContentFELogin")
public class MessageContentFELoginTest {

    private String login;
    private String group;
    private String client;

    @BeforeEach
    void init(){
        login = "admin";
        group = "aGroup";
        client = "client";
    }

    @Test
    @DisplayName("Instance to strong conversion testing (MessageDataDBLoginResp + errors)")
    void testInstance2String(){

        MessageDataDBLoginResp data = new MessageDataDBLoginResp(login, group);
        Set<MessageError> errors = new HashSet<MessageError>(){{
            add(new MessageErrorDBEmptyLoginPassword());
            add(new MessageErrorMSToClientNotExist(client));
        }};

        MessageContent messageContent = new MessageContentFELogin(data, errors);
        log.info("instance : {}", messageContent);

        Optional<String> maybeJson = MessageService.getAsString(messageContent);
        log.info("maybeJson : {}", maybeJson);

        assertThat(maybeJson).isPresent();
        assertThat(maybeJson.get()).contains(
            "@type", "MessageContentFELogin", "MessageDataDBLoginResp", "MessageErrorDBEmptyLoginPassword", "MessageErrorMSToClientNotExist",
            "login", login,
            "group", group,
            "client", client
        );
    }

    @Test
    @DisplayName("String to instance conversion testing")
    void testString2Instance(){
        String json = "{\"@type\":\"MessageContentFELogin\",\"data\":{\"@type\":\"MessageDataDBLoginResp\",\"login\":\""+login+"\",\"group\":\""+group+"\"},\"errors\":[{\"@type\":\"MessageErrorDBEmptyLoginPassword\"},{\"@type\":\"MessageErrorMSToClientNotExist\",\"client\":\""+client+"\"}]}";
        Optional<Object> maybeInstance = MessageService.getAsInstance(MessageContentFELogin.class, json);

        assertThat(maybeInstance).isPresent();
        log.info("maybeInstance : {}", maybeInstance);

        MessageDataDBLoginResp data = new MessageDataDBLoginResp(login, group);
        Set<MessageError> errors = new HashSet<MessageError>(){{
            add(new MessageErrorDBEmptyLoginPassword());
            add(new MessageErrorMSToClientNotExist(client));
        }};
        assertThat(maybeInstance.get()).isEqualTo(new MessageContentFELogin(data, errors));
    }
}
