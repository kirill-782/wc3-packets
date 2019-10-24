package ru.irinabot.protocol.gps.messages.client;

import ru.irinabot.protocol.gps.GPSMessage;
import ru.irinabot.protocol.gps.GPSMessageConstant;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GPSClientMessageAck implements GPSMessage {

    private int lastPacket;

    public GPSClientMessageAck() {
    }

    public GPSClientMessageAck(ByteBuffer b)
    {
        this.lastPacket = b.getInt();
    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSMessageConstant.HEADER);
        b.put(GPSMessageConstant.ACK);
        b.putShort((short) 8);

        b.putInt(this.lastPacket);

        return b.array();
    }

    public int getLastPacket() {
        return lastPacket;
    }

    public GPSClientMessageAck setLastPacket(int lastPacket) {
        this.lastPacket = lastPacket;
        return this;
    }
}
