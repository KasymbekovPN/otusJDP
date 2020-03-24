package ru.otus.kasymbekovPN.zuiNotesDB.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorBase;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGeneratorImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonErrorCode;
import ru.otus.kasymbekovPN.zuiNotesDB.json.error.data.DBErrorCode;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class JEGeneratorConfig {

    private final Client client;

    @Bean
    public JsonErrorGenerator jsonErrorGenerator(){
        JsonErrorGeneratorImpl jeGenerator = new JsonErrorGeneratorImpl();

        jeGenerator.addHandler(
                true,
                CommonErrorCode.FIELD_NOT_EXIST.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(CommonErrorCode.FIELD_NOT_EXIST.getCode(), true, client.getEntity()),
                        new HashSet<String>(){{add("field");}}
                )
        );

        jeGenerator.addHandler(
                true,
                CommonErrorCode.INVALID_MESSAGE_TYPE.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(CommonErrorCode.INVALID_MESSAGE_TYPE.getCode(), true, client.getEntity()),
                        new HashSet<String>(){{add("type");}}
                )
        );

        jeGenerator.addHandler(
                true,
                CommonErrorCode.INVALID_FIELD_TYPE.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(CommonErrorCode.INVALID_FIELD_TYPE.getCode(), true, client.getEntity()),
                        new HashSet<String>(){{
                            add("type");
                            add("field");
                        }}
                )
        );

        jeGenerator.addHandler(
                true,
                CommonErrorCode.UNKNOWN_FIELD_TYPE.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(CommonErrorCode.UNKNOWN_FIELD_TYPE.getCode(), true, client.getEntity()),
                        new HashSet<String>(){{add("field");}}
                )
        );

        jeGenerator.addHandler(
                false,
                DBErrorCode.WRONG_LOGIN_PASSWORD.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(DBErrorCode.WRONG_LOGIN_PASSWORD.getCode(), false, client.getEntity())
                )
        );

        jeGenerator.addHandler(
                false,
                DBErrorCode.EMPTY_LOGIN_PASSWORD.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(DBErrorCode.EMPTY_LOGIN_PASSWORD.getCode(), false, client.getEntity())
                )
        );

        jeGenerator.addHandler(
                false,
                DBErrorCode.WRONG_RIGHTS.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(DBErrorCode.WRONG_RIGHTS.getCode(), false, client.getEntity())
                )
        );

        jeGenerator.addHandler(
                false,
                DBErrorCode.USER_ALREADY_EXIST.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(DBErrorCode.USER_ALREADY_EXIST.getCode(), false, client.getEntity())
                )
        );

        jeGenerator.addHandler(
                false,
                DBErrorCode.USER_DOESNT_EXIST.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(DBErrorCode.USER_DOESNT_EXIST.getCode(), false, client.getEntity())
                )
        );

        return jeGenerator;
    }
}
