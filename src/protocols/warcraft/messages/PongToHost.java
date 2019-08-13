package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PongToHost implements WC3Message {
    private int pongValue;

    public PongToHost(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        this.pongValue = b.getInt(4);
    }

    public PongToHost()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3Message.HEADER);
        b.put(WC3Message.PONGTOHOST);
        b.putShort((short) 8);

        b.putInt(this.pongValue);

        return b.array();
    }

    public int getPongValue() {
        return pongValue;
    }

    public PongToHost setPongValue(int pongValue) {
        this.pongValue = pongValue;
        return this;
    }
}
