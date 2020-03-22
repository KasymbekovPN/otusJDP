package ru.otus.kasymbekovPN.zuiNotesMS.messageSystem;

import ru.otus.kasymbekovPN.zuiNotesMS.messageSystem.client.MsClientUrl;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class MSMessage {
    static final MSMessage VOID_MS_MESSAGE = new MSMessage();

    private final UUID id = UUID.randomUUID();
    private final MsClientUrl fromUrl;
    private final MsClientUrl toUrl;
    private final String type;
    private final int payloadLength;
    private final byte[] payload;

    public static MSMessage getVoidMsMessage() {
        return VOID_MS_MESSAGE;
    }

    public UUID getId() {
        return id;
    }

    public MsClientUrl getFromUrl() {
        return fromUrl;
    }

    public MsClientUrl getToUrl() {
        return toUrl;
    }

    public String getType() {
        return type;
    }

    public int getPayloadLength() {
        return payloadLength;
    }

    public byte[] getPayload() {
        return payload;
    }

    public MSMessage() {
        this.fromUrl = null;
        this.toUrl = null;
        this.type = "voidTechnicalMessage";
        this.payload = new byte[1];
        this.payloadLength = this.payload.length;
    }

    public MSMessage(MsClientUrl fromUrl, MsClientUrl toUrl, String type, byte[] payload)
    {
        this.fromUrl = fromUrl;
        this.toUrl = toUrl;
        this.type = type;
        this.payload = payload;
        this.payloadLength = this.payload.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MSMessage MSMessage = (MSMessage) o;
        return Objects.equals(id, MSMessage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromUrl='" + fromUrl + '\'' +
                ", toUrl='" + toUrl + '\'' +
                ", type='" + type + '\'' +
                ", payloadLength=" + payloadLength +
                ", payload=" + Arrays.toString(payload) +
                '}';
    }
}

