package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.WC3Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DropReq implements WC3Message {

    public DropReq() {

    }

    public DropReq(ByteBuffer b)
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.DROPREQ);

        b.putShort((short) 4);

        return b.array();
    }
}
