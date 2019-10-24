package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.Constants;
import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalPlayerIDException;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageStopLag implements W3GSMessage {

    private byte playerID;
    private long lagTimeMS;

    private W3GSMessageStopLag( )
    {

    }

    private W3GSMessageStopLag(ByteBuffer b) throws IllegalPlayerIDException {
        this.playerID = b.get();

        if (this.playerID > Constants.MAXPLAYERS || this.playerID < 1)
            throw new IllegalPlayerIDException("playerID", this.playerID);

        this.lagTimeMS = Integer.toUnsignedLong(b.getInt());
    }

    @Override
    public byte[] assemble() throws PacketBuildException {

        if (this.playerID > Constants.MAXPLAYERS || this.playerID < 1)
            throw new IllegalPlayerIDException("playerID", this.playerID);

        ByteBuffer b = ByteBuffer.allocate(9);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.STOPLAG);
        b.putShort((short) 9);

        b.put(this.playerID);
        b.putInt((int) this.lagTimeMS);

        return b.array();
    }

    public byte getPlayerID() {
        return playerID;
    }

    public W3GSMessageStopLag setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public long getLagTimeMS() {
        return lagTimeMS;
    }

    public W3GSMessageStopLag setLagTimeMS(long lagTimeMS) {
        this.lagTimeMS = lagTimeMS;
        return this;
    }
}
