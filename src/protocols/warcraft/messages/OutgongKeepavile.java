package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.WC3Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OutgongKeepavile implements WC3Message {
    private int checksum;

    public OutgongKeepavile(  )
    {

    }

    public OutgongKeepavile(ByteBuffer b)
    {
        b.get();
        this.checksum = b.getInt();
    }


    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(9);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.OUTGOINGKEEPALIVE);
        b.putShort((short) 9);

        b.put((byte) 1);
        b.putInt(this.checksum);

        return b.array();

    }

    public int getChecksum() {
        return checksum;
    }

    public OutgongKeepavile setChecksum(int checksum) {
        this.checksum = checksum;
        return this;
    }
}
