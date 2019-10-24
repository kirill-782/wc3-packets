package protocols.gps.messages.server;

import protocols.gps.GPSIMessageConstant;
import protocols.gps.GPSMessage;
import protocols.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageInit implements GPSMessage {

    private int reconnectPort;
    private byte playerID;
    private int reconnectKey;
    private byte numEmptyActions;

    public MessageInit() {
    }

    public MessageInit(ByteBuffer b) {
        this.reconnectPort = Short.toUnsignedInt(b.getShort());
        this.playerID = b.get();
        this.reconnectKey = b.getInt();
        this.numEmptyActions = b.get();
    }

    @Override
    public byte[] assemble() throws PacketBuildException {
        ByteBuffer b = ByteBuffer.allocate(14);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSIMessageConstant.HEADER);
        b.put(GPSIMessageConstant.INIT);
        b.putShort((short) 14);
        b.putShort((short) reconnectPort);
        b.put(this.playerID);
        b.putInt(this.reconnectKey);
        b.put(this.numEmptyActions);

        return b.array();
    }

    public int getReconnectPort() {
        return reconnectPort;
    }

    public MessageInit setReconnectPort(int reconnectPort) {
        this.reconnectPort = reconnectPort;
        return this;
    }

    public byte getPlayerID() {
        return playerID;
    }

    public MessageInit setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public int getReconnectKey() {
        return reconnectKey;
    }

    public MessageInit setReconnectKey(int reconnectKey) {
        this.reconnectKey = reconnectKey;
        return this;
    }

    public byte getNumEmptyActions() {
        return numEmptyActions;
    }

    public MessageInit setNumEmptyActions(byte numEmptyActions) {
        this.numEmptyActions = numEmptyActions;
        return this;
    }
}
