package ru.irinabot.protocol.gps.messages.server;

import ru.irinabot.protocol.gps.GPSMessage;
import ru.irinabot.protocol.gps.GPSMessageConstant;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GPSServerMessageInit implements GPSMessage {

    private int reconnectPort;
    private byte playerID;
    private int reconnectKey;
    private byte numEmptyActions;

    public GPSServerMessageInit() {
    }

    public GPSServerMessageInit(ByteBuffer b) {
        this.reconnectPort = Short.toUnsignedInt(b.getShort());
        this.playerID = b.get();
        this.reconnectKey = b.getInt();
        this.numEmptyActions = b.get();
    }

    @Override
    public byte[] assemble() throws PacketBuildException {
        ByteBuffer b = ByteBuffer.allocate(14);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSMessageConstant.HEADER);
        b.put(GPSMessageConstant.INIT);
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

    public GPSServerMessageInit setReconnectPort(int reconnectPort) {
        this.reconnectPort = reconnectPort;
        return this;
    }

    public byte getPlayerID() {
        return playerID;
    }

    public GPSServerMessageInit setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public int getReconnectKey() {
        return reconnectKey;
    }

    public GPSServerMessageInit setReconnectKey(int reconnectKey) {
        this.reconnectKey = reconnectKey;
        return this;
    }

    public byte getNumEmptyActions() {
        return numEmptyActions;
    }

    public GPSServerMessageInit setNumEmptyActions(byte numEmptyActions) {
        this.numEmptyActions = numEmptyActions;
        return this;
    }
}
