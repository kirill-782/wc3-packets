package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessagePingFromHost implements WC3Message {

    private int pingValue;

    public MessagePingFromHost(ByteBuffer b) {
        this.pingValue = b.getInt(4);
    }

    public MessagePingFromHost()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.PINGFROMHOST);
        b.putShort((short) 8);

        b.putInt(this.pingValue);

        return b.array();
    }

    public int getPingValue() {
        return pingValue;
    }

    public MessagePingFromHost setPingValue(int pingValue) {
        this.pingValue = pingValue;

        return this;
    }
}
