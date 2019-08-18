package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalPlayerIDException;
import protocols.warcraft.exceptions.WC3Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MapPartOK implements WC3Message {

    private byte fromPlayerID;
    private byte toPlayerID;

    private long mapSize;

    public MapPartOK()
    {

    }

    public MapPartOK(ByteBuffer b) throws IllegalPlayerIDException {
        this.fromPlayerID = b.get();
        this.toPlayerID = b.get();

        if (this.toPlayerID > Constants.MAXPLAYERS || this.toPlayerID < 1)
            throw new IllegalPlayerIDException("toPlayerID", this.toPlayerID);

        if (this.fromPlayerID > Constants.MAXPLAYERS || this.fromPlayerID < 1)
            throw new IllegalPlayerIDException("fromPlayerID", this.fromPlayerID);

        b.getInt();

        this.mapSize = Integer.toUnsignedLong(b.getInt());
    }

    @Override
    public byte[] assemble() throws IllegalPlayerIDException {

        if (this.toPlayerID > Constants.MAXPLAYERS || this.toPlayerID < 1)
            throw new IllegalPlayerIDException("toPlayerID", this.toPlayerID);

        if (this.fromPlayerID > Constants.MAXPLAYERS || this.fromPlayerID < 1)
            throw new IllegalPlayerIDException("fromPlayerID", this.fromPlayerID);

        ByteBuffer b = ByteBuffer.allocate(14);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.MAPPARTOK);
        b.putShort((short) 14);

        b.put(this.fromPlayerID);
        b.put(this.toPlayerID);
        b.putInt(1);
        b.putInt((int) this.mapSize);

        return b.array();
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public MapPartOK setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }

    public byte getToPlayerID() {
        return toPlayerID;
    }

    public MapPartOK setToPlayerID(byte toPlayerID) {
        this.toPlayerID = toPlayerID;
        return this;
    }

    public long getMapSize() {
        return mapSize;
    }

    public MapPartOK setMapSize(long mapSize) {
        this.mapSize = mapSize;
        return this;
    }
}
