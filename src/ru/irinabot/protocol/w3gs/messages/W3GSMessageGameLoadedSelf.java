package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageGameLoadedSelf implements W3GSMessage {

    public W3GSMessageGameLoadedSelf()
    {

    }

    public W3GSMessageGameLoadedSelf(ByteBuffer b)
    {

    }

    @Override
    public byte[] assemble()
    {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.GAMELOADEDSELF);

        return b.array();
    }
}