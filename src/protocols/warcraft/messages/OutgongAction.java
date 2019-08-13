package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OutgongAction implements WC3Message {

    private byte[] crc = null;
    private byte[] action = null;

    public OutgongAction()
    {

    }

    public OutgongAction(ByteBuffer b)
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

        if(action == null || action.length != 4)
            throw new IllegalByteSizeException("crc must have size 4 byte.");

        ByteBuffer b = ByteBuffer.allocate(this.action.length + 8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(HEADER);
        b.put(OUTGOINGACTION);
        b.putShort((short) (this.action.length + 8));

        b.put(this.crc);
        b.put(this.action);

        return b.array();
    }

    public byte[] getCrc() {
        return crc;
    }

    public OutgongAction setCrc(byte[] crc) {
        this.crc = crc;
        return this;
    }

    public byte[] getAction() {
        return action;
    }

    public OutgongAction setAction(byte[] action) {
        this.action = action;
        return this;
    }
}
