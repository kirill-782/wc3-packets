package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageDropReq implements W3GSMessage {

    public W3GSMessageDropReq() {

    }

    public W3GSMessageDropReq(ByteBuffer b)
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.DROPREQ);

        b.putShort((short) 4);

        return b.array();
    }
}
