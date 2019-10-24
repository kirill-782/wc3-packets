package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageCountDownStart implements W3GSMessage {

    public W3GSMessageCountDownStart()
    {

    }

    public W3GSMessageCountDownStart(ByteBuffer b)
    {

    }

    @Override
    public byte[] assemble()
    {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.COUNTDOWNSTART);
        b.putShort((short) 4);

        return b.array();
    }
}