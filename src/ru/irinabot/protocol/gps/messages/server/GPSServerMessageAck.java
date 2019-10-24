package ru.irinabot.protocol.gps.messages.server;

import ru.irinabot.protocol.gps.GPSMessage;
import ru.irinabot.protocol.gps.GPSMessageConstant;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GPSServerMessageAck implements GPSMessage {

    private int lastPacket;

    public GPSServerMessageAck() {
    }

    public GPSServerMessageAck(ByteBuffer b)
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

    public GPSServerMessageAck setLastPacket(int lastPacket) {
        this.lastPacket = lastPacket;
        return this;
    }


}
