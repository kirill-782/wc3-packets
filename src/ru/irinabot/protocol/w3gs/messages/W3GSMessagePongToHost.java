package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessagePongToHost implements W3GSMessage {
    private int pongValue;

    public W3GSMessagePongToHost(ByteBuffer b) {

        this.pongValue = b.getInt(4);
    }

    public W3GSMessagePongToHost()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.PONGTOHOST);
        b.putShort((short) 8);

        b.putInt(this.pongValue);

        return b.array();
    }

    public int getPongValue() {
        return pongValue;
    }

    public W3GSMessagePongToHost setPongValue(int pongValue) {
        this.pongValue = pongValue;
        return this;
    }
}
