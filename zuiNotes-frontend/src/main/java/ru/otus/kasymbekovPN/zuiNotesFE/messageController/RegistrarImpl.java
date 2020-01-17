package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrarImpl implements Registrar {

    private static final Logger logger = LoggerFactory.getLogger(RegistrarImpl.class);

    private final Map<String, String> UIIdByRequestUUID = new ConcurrentHashMap<>();
    private final Map<String, String> loginByUIId = new ConcurrentHashMap<>();

    @Override
    public void setUIIdByRequestUUID(String requestUUID, String UIId) {
        logger.info("set UIId by requestUUID pair : {} - {}", requestUUID, UIId);
        UIIdByRequestUUID.put(requestUUID, UIId);
    }

    @Override
    public String getUIIdByRequestUUID(String requestUUID) {
        logger.info("get UIId by requestUUID pair : {}", requestUUID);
        return UIIdByRequestUUID.remove(requestUUID);
    }

    @Override
    public void setLoginByUIId(String UIId, String login) {
        logger.info("set login by UIId pair : {} - {}", UIId, login);
        loginByUIId.put(UIId, login);
    }

    @Override
    public Optional<String> getLoginBuUIId(String UIId) {
        logger.info("get login by UIId pair : {} - {}", UIId, loginByUIId.getOrDefault(UIId, "null"));
        return loginByUIId.containsKey(UIId)
                ? Optional.of(loginByUIId.get(UIId))
                : Optional.empty();
    }

    @Override
    public void delLoginBuUIId(String UIId) {
        logger.info("del login by UIId pair : {}", UIId);
        loginByUIId.remove(UIId);
    }
}
