package ru.otus.kasymbekovPN.zuiNotesCommon.message;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.address.MessageAddress;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.data.MessageData;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.error.MessageError;
import ru.otus.kasymbekovPN.zuiNotesCommon.message.header.MessageHeader;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@JsonTypeName("MessageImpl")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageImpl implements Message {

    private MessageHeader header;
    private MessageAddress from;
    private MessageAddress to;
    private MessageData data;
    private Set<MessageError> errors;

    //<
//    private static Optional<String> getAsJson(MessageImpl message){
//
//        //<
//        System.out.println(11.5);
//
//        try{
//
//            //<
//            System.out.println(12);
//
//            String line = new ObjectMapper().writeValueAsString(message);
//
//            //<
//            log.info("{}", line);
//
//            return Optional.of(line);
//        } catch (JsonProcessingException ex){
//
////            log.error("{}", ex.getMessage());
//            //<
//            ex.printStackTrace();
//
//            return Optional.empty();
//        }
//    }

    @JsonGetter("header")
    @Override
    public MessageHeader getHeader() {
        return header;
    }

    @JsonGetter("from")
    @Override
    public MessageAddress getFrom() {
        return from;
    }

    @JsonGetter("to")
    @Override
    public MessageAddress getTo() {
        return to;
    }

    @JsonGetter("data")
    @Override
    public MessageData getData() {
        return data;
    }

    @JsonGetter("errors")
    @Override
    public Set<MessageError> getErrors() {
        return errors;
    }

    @Override
    public void setFrom(MessageAddress from) {
        this.from = from;
    }

    @Override
    public void setTo(MessageAddress to) {
        this.to = to;
    }

//    @Override
//    public Optional<String> getAsJson() {
//        MessageImpl instance = new MessageImpl(header, from, to, data, errors);
//        try{
//            String json = new ObjectMapper().writeValueAsString(instance);
//            return Optional.of(json);
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//            return Optional.empty();
//        }
//    }

    //<
//    @Override
//    public Optional<String> getAsJson() {
//
//        //<
//        System.out.println(11);
//
//        return getAsJson(this);
//
////        try{
////
////            //<
////            System.out.println(12);
////
////            String line = new ObjectMapper().writeValueAsString((MessageImpl)this);
////
////            //<
////            log.info("{}", line);
////
////            return Optional.of(line);
////        } catch (JsonProcessingException ex){
////
//////            log.error("{}", ex.getMessage());
////            //<
////            ex.printStackTrace();
////
////            return Optional.empty();
////        }
//    }

    @JsonCreator
    public MessageImpl(
            @JsonProperty("header") MessageHeader header,
            @JsonProperty("from") MessageAddress from,
            @JsonProperty("to") MessageAddress to,
            @JsonProperty("data") MessageData data,
            @JsonProperty("errors") Set<MessageError> errors) {
        this.header = header;
        this.from = from;
        this.to = to;
        this.data = data;
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageImpl message = (MessageImpl) o;
        return Objects.equals(header, message.header) &&
                Objects.equals(from, message.from) &&
                Objects.equals(to, message.to) &&
                Objects.equals(data, message.data) &&
                Objects.equals(errors, message.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, from, to, data, errors);
    }

    @Override
    public String toString() {
        return "MessageImpl{" +
                "header=" + header +
                ", from=" + from +
                ", to=" + to +
                ", data=" + data +
                ", errors=" + errors +
                '}';
    }
}
