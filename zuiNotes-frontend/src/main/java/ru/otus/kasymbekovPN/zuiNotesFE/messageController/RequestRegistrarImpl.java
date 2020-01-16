package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestRegistrarImpl implements RequestRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(RequestRegistrarImpl.class);

    private final Map<String, String> requestUIMatching = new ConcurrentHashMap<>();

    @Override
    public void set(String requestUUID, String UIId) {
        requestUIMatching.put(requestUUID, UIId);
    }

    @Override
    public String get(String requestUUID) {
        return requestUIMatching.remove(requestUUID);
    }
}
