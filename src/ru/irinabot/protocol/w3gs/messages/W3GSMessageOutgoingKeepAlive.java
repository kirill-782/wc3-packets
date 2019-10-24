package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageOutgoingKeepAlive implements W3GSMessage {
    private int checksum;

    public W3GSMessageOutgoingKeepAlive(  )
    {

    }

    public W3GSMessageOutgoingKeepAlive(ByteBuffer b)
    {
        b.get();
        this.checksum = b.getInt();
    }


    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(9);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.OUTGOINGKEEPALIVE);
        b.putShort((short) 9);

        b.put((byte) 1);
        b.putInt(this.checksum);

        return b.array();

    }

    public int getChecksum() {
        return checksum;
    }

    public W3GSMessageOutgoingKeepAlive setChecksum(int checksum) {
        this.checksum = checksum;
        return this;
    }
}
