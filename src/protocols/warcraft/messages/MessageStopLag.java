package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.util.exceptions.IllegalPlayerIDException;
import protocols.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageStopLag implements WC3Message {

    private byte playerID;
    private long lagTimeMS;

    private MessageStopLag( )
    {

    }

    private MessageStopLag(ByteBuffer b) throws IllegalPlayerIDException {
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

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.STOPLAG);
        b.putShort((short) 9);

        b.put(this.playerID);
        b.putInt((int) this.lagTimeMS);

        return b.array();
    }

    public byte getPlayerID() {
        return playerID;
    }

    public MessageStopLag setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public long getLagTimeMS() {
        return lagTimeMS;
    }

    public MessageStopLag setLagTimeMS(long lagTimeMS) {
        this.lagTimeMS = lagTimeMS;
        return this;
    }
}
