package protocols.gps.messages.client;

import protocols.gps.GPSIMessageConstant;
import protocols.gps.GPSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageReconnect implements GPSMessage {

    private byte playerID;
    private int reconnectKey;
    private int lastPacket;

    public MessageReconnect() {
    }

    public MessageReconnect(ByteBuffer b)
    {
        this.playerID = b.get();
        this.reconnectKey = b.getInt();
        this.lastPacket = b.getInt();
    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSIMessageConstant.HEADER);
        b.put(GPSIMessageConstant.RECONNECT);
        b.putShort((short) 13);

        b.put(this.playerID);
        b.putInt(this.reconnectKey);
        b.putInt(this.lastPacket);

        return b.array();
    }

    public int getLastPacket() {
        return lastPacket;
    }

    public MessageReconnect setLastPacket(int lastPacket) {
        this.lastPacket = lastPacket;
        return this;
    }

    public byte getPlayerID() {
        return playerID;
    }

    public MessageReconnect setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public int getReconnectKey() {
        return reconnectKey;
    }

    public MessageReconnect setReconnectKey(int reconnectKey) {
        this.reconnectKey = reconnectKey;
        return this;
    }
}
