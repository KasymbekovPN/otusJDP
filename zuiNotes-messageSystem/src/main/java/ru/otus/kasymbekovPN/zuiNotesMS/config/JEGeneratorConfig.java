package ru.otus.kasymbekovPN.zuiNotesMS.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kasymbekovPN.zuiNotesCommon.client.Client;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorBase;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGenerator;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorGeneratorImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.JsonErrorHandlerImpl;
import ru.otus.kasymbekovPN.zuiNotesCommon.json.error.data.CommonErrorCode;
import ru.otus.kasymbekovPN.zuiNotesMS.json.error.data.MSErrorCode;

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
                CommonErrorCode.FIELD_DOESNT_EXIST.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(CommonErrorCode.FIELD_DOESNT_EXIST.getCode(), true, client.getEntity()),
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
                MSErrorCode.FROM_MS_CLIENT_DOESNT_EXIST.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(MSErrorCode.FROM_MS_CLIENT_DOESNT_EXIST.getCode(), false, client.getEntity()),
                        new HashSet<>(){{add("client");}}
                )
        );

        jeGenerator.addHandler(
                false,
                MSErrorCode.TO_MS_CLIENT_DOESNT_EXIST.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(MSErrorCode.TO_MS_CLIENT_DOESNT_EXIST.getCode(), false, client.getEntity()),
                        new HashSet<>(){{add("client");}}
                )
        );

        jeGenerator.addHandler(
                false,
                MSErrorCode.MS_CLIENT_ALREADY_EXIST.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(MSErrorCode.MS_CLIENT_ALREADY_EXIST.getCode(), false, client.getEntity()),
                        new HashSet<>(){{add("url");}}
                )
        );

        jeGenerator.addHandler(
                false,
                MSErrorCode.MS_CLIENT_HAS_WRONG_ENTITY.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(MSErrorCode.MS_CLIENT_HAS_WRONG_ENTITY.getCode(), false, client.getEntity()),
                        new HashSet<>(){{add("entity");}}
                )
        );

        jeGenerator.addHandler(
                false,
                MSErrorCode.SOLUS_REG.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(MSErrorCode.SOLUS_REG.getCode(), false, client.getEntity()),
                        new HashSet<>(){{add("entity");}}
                )
        );

        jeGenerator.addHandler(
                false,
                MSErrorCode.FIELD_REQUEST_IS_WRONG.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(MSErrorCode.FIELD_REQUEST_IS_WRONG.getCode(), false, client.getEntity())
                )
        );

        jeGenerator.addHandler(
                false,
                MSErrorCode.MS_CLIENT_ALREADY_DEL.getCode(),
                new JsonErrorHandlerImpl(
                        new JsonErrorBase(MSErrorCode.MS_CLIENT_ALREADY_DEL.getCode(), false, client.getEntity()),
                        new HashSet<>(){{add("url");}}
                )
        );

        return jeGenerator;
    }
}
