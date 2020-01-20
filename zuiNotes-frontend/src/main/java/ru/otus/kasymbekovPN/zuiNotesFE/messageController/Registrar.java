package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

import java.util.Optional;

public interface Registrar {
    void setUIIdByRequestUUID(String requestUUID, String UIId);
    String getUIIdByRequestUUID(String requestUUID);
    void delUIIdByRequestUUID(String requestUUID);
    void setLoginByUIId(String UIId, String login);
    Optional<String> getLoginBuUIId(String UIId);
    void delLoginBuUIId(String UIId);
}
