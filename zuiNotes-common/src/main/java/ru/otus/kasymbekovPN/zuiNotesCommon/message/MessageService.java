package ru.otus.kasymbekovPN.zuiNotesCommon.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public class MessageService {

    static public Optional<String> getAsString(Object message){
        try{
            String json = new ObjectMapper().writeValueAsString(message);
            return Optional.of(json);
        } catch (JsonProcessingException ex){
            return Optional.empty();
        }
    }

    static public Optional<Object> getAsInstance(Class clazz, String json){
        try {
            Object instance = new ObjectMapper().readerFor(clazz).readValue(json);
            return Optional.of(instance);
        } catch (IOException ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }
    //<
//    static public <T extends Message> Optional<T> getAsInstance(Class<T> clazz, String json){
//        try {
//            T instance = new ObjectMapper().readerFor(clazz).readValue(json);
//            return Optional.of(instance);
//        } catch (IOException ex){
//            ex.printStackTrace();
//            return Optional.empty();
//        }
//    }
}
