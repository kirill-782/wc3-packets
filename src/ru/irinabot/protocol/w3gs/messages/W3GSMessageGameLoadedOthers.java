package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageGameLoadedOthers implements W3GSMessage {

    private byte playerID = 0;

    public W3GSMessageGameLoadedOthers()
    {

    }

    public W3GSMessageGameLoadedOthers(ByteBuffer b)
    {
        this.playerID = b.get();
    }

    @Override
    public byte[] assemble()
    {
        ByteBuffer b = ByteBuffer.allocate(5);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.GAMELOADEDOTHERS);
        b.putShort((short) 5);

        b.put(this.playerID);

        return b.array();
    }

    public byte getPlayerID() {
        return playerID;
    }

    public W3GSMessageGameLoadedOthers setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }
}