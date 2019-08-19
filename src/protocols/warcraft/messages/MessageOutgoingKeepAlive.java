package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageOutgoingKeepAlive implements WC3Message {
    private int checksum;

    public MessageOutgoingKeepAlive(  )
    {

    }

    public MessageOutgoingKeepAlive(ByteBuffer b)
    {
        b.get();
        this.checksum = b.getInt();
    }


    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(9);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.OUTGOINGKEEPALIVE);
        b.putShort((short) 9);

        b.put((byte) 1);
        b.putInt(this.checksum);

        return b.array();

    }

    public int getChecksum() {
        return checksum;
    }

    public MessageOutgoingKeepAlive setChecksum(int checksum) {
        this.checksum = checksum;
        return this;
    }
}
