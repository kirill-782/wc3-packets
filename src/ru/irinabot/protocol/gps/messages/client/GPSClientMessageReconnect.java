package ru.irinabot.protocol.gps.messages.client;

import ru.irinabot.protocol.gps.GPSMessage;
import ru.irinabot.protocol.gps.GPSMessageConstant;
import ru.irinabot.protocol.w3gs.Constants;
import ru.irinabot.util.exceptions.IllegalPlayerIDException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GPSClientMessageReconnect implements GPSMessage {

    private byte playerID;
    private int reconnectKey;
    private int lastPacket;

    public GPSClientMessageReconnect() {
    }

    public GPSClientMessageReconnect(ByteBuffer b)
    {
        this.playerID = b.get();
        this.reconnectKey = b.getInt();
        this.lastPacket = b.getInt();
    }

    @Override
    public byte[] assemble() throws IllegalPlayerIDException {

        if(this.playerID > Constants.MAXPLAYERS || this.playerID < 1)
            throw new IllegalPlayerIDException("playerID", this.playerID);

        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSMessageConstant.HEADER);
        b.put(GPSMessageConstant.RECONNECT);
        b.putShort((short) 13);

        b.put(this.playerID);
        b.putInt(this.reconnectKey);
        b.putInt(this.lastPacket);

        return b.array();
    }

    public int getLastPacket() {
        return lastPacket;
    }

    public GPSClientMessageReconnect setLastPacket(int lastPacket) {
        this.lastPacket = lastPacket;
        return this;
    }

    public byte getPlayerID() {
        return playerID;
    }

    public GPSClientMessageReconnect setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public int getReconnectKey() {
        return reconnectKey;
    }

    public GPSClientMessageReconnect setReconnectKey(int reconnectKey) {
        this.reconnectKey = reconnectKey;
        return this;
    }
}
