package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PingFromHost implements WC3Message {

    private int pingValue;

    public PingFromHost(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        this.pingValue = b.getInt(4);
    }

    public PingFromHost()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3Message.HEADER);
        b.put(WC3Message.PINGFROMHOST);
        b.putShort((short) 8);

        b.putInt(this.pingValue);

        return b.array();
    }

    public int getPingValue() {
        return pingValue;
    }

    public PingFromHost setPingValue(int pingValue) {
        this.pingValue = pingValue;

        return this;
    }
}
