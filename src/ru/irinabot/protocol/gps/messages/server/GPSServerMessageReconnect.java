package ru.irinabot.protocol.gps.messages.server;

import ru.irinabot.protocol.gps.GPSIMessageConstant;
import ru.irinabot.protocol.gps.GPSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GPSServerMessageReconnect implements GPSMessage {

    private int lastPacket;

    public GPSServerMessageReconnect() {
    }

    public GPSServerMessageReconnect(ByteBuffer b)
    {
        this.lastPacket = b.getInt();
    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSIMessageConstant.HEADER);
        b.put(GPSIMessageConstant.RECONNECT);
        b.putShort((short) 8);

        b.putInt(this.lastPacket);

        return b.array();
    }

    public int getLastPacket() {
        return lastPacket;
    }

    public GPSServerMessageReconnect setLastPacket(int lastPacket) {
        this.lastPacket = lastPacket;
        return this;
    }


}
