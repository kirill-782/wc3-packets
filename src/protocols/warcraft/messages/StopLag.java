package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalPlayerIDException;
import protocols.warcraft.exceptions.WC3Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StopLag implements WC3Message {

    private byte playerID;
    private long lagTimeMS;

    private StopLag( )
    {

    }

    private StopLag(ByteBuffer b) throws IllegalPlayerIDException {
        this.playerID = b.get();

        if (this.playerID > Constants.MAXPLAYERS || this.playerID < 1)
            throw new IllegalPlayerIDException("playerID", this.playerID);

        this.lagTimeMS = Integer.toUnsignedLong(b.getInt());
    }

    @Override
    public byte[] assemble() throws WC3Exception {

        if (this.playerID > Constants.MAXPLAYERS || this.playerID < 1)
            throw new IllegalPlayerIDException("playerID", this.playerID);

        ByteBuffer b = ByteBuffer.allocate(9);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.STOPLAG);
        b.putShort((short) 9);

        b.put(this.playerID);
        b.putInt((int) this.lagTimeMS);

        return b.array();
    }

    public byte getPlayerID() {
        return playerID;
    }

    public StopLag setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public long getLagTimeMS() {
        return lagTimeMS;
    }

    public StopLag setLagTimeMS(long lagTimeMS) {
        this.lagTimeMS = lagTimeMS;
        return this;
    }
}
