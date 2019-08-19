package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessagePongToHost implements WC3Message {
    private int pongValue;

    public MessagePongToHost(ByteBuffer b) {

        this.pongValue = b.getInt(4);
    }

    public MessagePongToHost()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.PONGTOHOST);
        b.putShort((short) 8);

        b.putInt(this.pongValue);

        return b.array();
    }

    public int getPongValue() {
        return pongValue;
    }

    public MessagePongToHost setPongValue(int pongValue) {
        this.pongValue = pongValue;
        return this;
    }
}
