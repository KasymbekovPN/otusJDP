package ru.otus.kasymbekovPN.zuiNotesFE.messageController;

import java.util.Optional;

public interface Registrar {
//    void set(String requestUUID, String UIId);
//    String get(String requestUUID);
    //<
    void setUIIdByRequestUUID(String requestUUID, String UIId);
    String getUIIdByRequestUUID(String requestUUID);
    void setLoginByUIId(String UIId, String login);
    Optional<String> getLoginBuUIId(String UIId);
    void delLoginBuUIId(String UIId);
}
