package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.util.exceptions.IllegalByteSizeException;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageOutgongAction implements WC3Message {

    private byte[] crc = null;
    private byte[] action = null;

    public MessageOutgongAction()
    {

    }

    public MessageOutgongAction(ByteBuffer b)
    {
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.position(4);

        this.crc = new byte[4];
        b.get(this.crc);

        this.action = new byte[b.capacity() - 8];
        b.get(this.action);
    }

    @Override
    public byte[] assemble() throws IllegalByteSizeException {

        if(crc.length != 4)
            throw new IllegalByteSizeException("crc", 4);

        ByteBuffer b = ByteBuffer.allocate(this.action.length + 8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.OUTGOINGACTION);
        b.putShort((short) (this.action.length + 8));

        b.put(this.crc);
        b.put(this.action);

        return b.array();
    }

    public byte[] getCrc() {
        return crc;
    }

    public MessageOutgongAction setCrc(byte[] crc) {
        this.crc = crc;
        return this;
    }

    public byte[] getAction() {
        return action;
    }

    public MessageOutgongAction setAction(byte[] action) {
        this.action = action;
        return this;
    }
}
