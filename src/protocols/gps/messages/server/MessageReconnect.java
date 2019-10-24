package protocols.gps.messages.server;

import protocols.gps.GPSIMessageConstant;
import protocols.gps.GPSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageReconnect implements GPSMessage {

    private int lastPacket;

    public MessageReconnect() {
    }

    public MessageReconnect(ByteBuffer b)
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

    public MessageReconnect setLastPacket(int lastPacket) {
        this.lastPacket = lastPacket;
        return this;
    }


}
