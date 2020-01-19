package ru.otus.kasymbekovPN.zuiNotesCommon.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializers {

    private static final Logger logger = LoggerFactory.getLogger(Serializers.class);

    public Serializers() {
    }

    public static byte[] serialize(Object data){
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(baos)){

            os.writeObject(data);
            os.flush();
            return baos.toByteArray();
        } catch (Exception ex){
            logger.error("Serialization error, data : {}, {}", data, ex);
            throw new RuntimeException("Serialization error : " + ex.getMessage());
        }
    }

    public static <T> T deserialize(byte[] data, Class<T> classOfT){
        try(ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(bis)){

            Object obj = is.readObject();
            return classOfT.cast(obj);
        } catch (Exception ex){
            logger.error("Deserialization error, classOfT : {}, {}", classOfT, ex);
            throw new RuntimeException("Deserialization error : " + ex.getMessage());
        }
    }
}

