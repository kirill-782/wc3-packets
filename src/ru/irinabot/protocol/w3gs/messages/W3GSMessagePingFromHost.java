package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessagePingFromHost implements W3GSMessage {

    private int pingValue;

    public W3GSMessagePingFromHost(ByteBuffer b) {
        this.pingValue = b.getInt(4);
    }

    public W3GSMessagePingFromHost()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.PINGFROMHOST);
        b.putShort((short) 8);

        b.putInt(this.pingValue);

        return b.array();
    }

    public int getPingValue() {
        return pingValue;
    }

    public W3GSMessagePingFromHost setPingValue(int pingValue) {
        this.pingValue = pingValue;

        return this;
    }
}
