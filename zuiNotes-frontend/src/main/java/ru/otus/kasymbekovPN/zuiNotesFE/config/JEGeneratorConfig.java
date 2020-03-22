package ru.otus.kasymbekovPN.zuiNotesFE.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorBase;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGeneratorImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonErrorCode;
import ru.otus.kasymbekovPN.zuiNotesFE.json.error.data.FEErrorCode;

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
                        new HashSet<>(){{add("type");}}
                )
        );

        jeGenerator.addHandler(
                true,
                CommonErrorCode.INVALID_FIELD_TYPE.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(CommonErrorCode.INVALID_FIELD_TYPE.getCode(), true, client.getEntity()),
                        new HashSet<>(){{
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
                        new HashSet<>(){{add("field");}}
                )
        );

        jeGenerator.addHandler(
                false,
                FEErrorCode.INVALID_LOGIN.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(FEErrorCode.INVALID_LOGIN.getCode(), false, client.getEntity()),
                        new HashSet<>(){{add("login");}}
                )
        );

        return jeGenerator;
    }
}
