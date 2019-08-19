package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageDropReq implements WC3Message {

    public MessageDropReq() {

    }

    public MessageDropReq(ByteBuffer b)
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.DROPREQ);

        b.putShort((short) 4);

        return b.array();
    }
}
