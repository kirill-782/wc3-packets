package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalByteSizeException;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageOutgongAction implements W3GSMessage {

    private byte[] crc = null;
    private byte[] action = null;

    public W3GSMessageOutgongAction()
    {

    }

    public W3GSMessageOutgongAction(ByteBuffer b)
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

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.OUTGOINGACTION);
        b.putShort((short) (this.action.length + 8));

        b.put(this.crc);
        b.put(this.action);

        return b.array();
    }

    public byte[] getCrc() {
        return crc;
    }

    public W3GSMessageOutgongAction setCrc(byte[] crc) {
        this.crc = crc;
        return this;
    }

    public byte[] getAction() {
        return action;
    }

    public W3GSMessageOutgongAction setAction(byte[] action) {
        this.action = action;
        return this;
    }
}
