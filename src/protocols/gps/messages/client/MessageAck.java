package protocols.gps.messages.client;

import protocols.gps.GPSIMessageConstant;
import protocols.gps.GPSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageAck implements GPSMessage {

    private int lastPacket;

    public MessageAck() {
    }

    public MessageAck(ByteBuffer b)
    {
        this.lastPacket = b.getInt();
    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSIMessageConstant.HEADER);
        b.put(GPSIMessageConstant.ACK);
        b.putShort((short) 8);

        b.putInt(this.lastPacket);

        return b.array();
    }

    public int getLastPacket() {
        return lastPacket;
    }

    public MessageAck setLastPacket(int lastPacket) {
        this.lastPacket = lastPacket;
        return this;
    }
}
