package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.Constants;
import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalPlayerIDException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageMapPartOK implements W3GSMessage {

    private byte fromPlayerID;
    private byte toPlayerID;

    private long mapSize;

    public W3GSMessageMapPartOK()
    {

    }

    public W3GSMessageMapPartOK(ByteBuffer b) throws IllegalPlayerIDException {
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

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.MAPPARTOK);
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

    public W3GSMessageMapPartOK setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }

    public byte getToPlayerID() {
        return toPlayerID;
    }

    public W3GSMessageMapPartOK setToPlayerID(byte toPlayerID) {
        this.toPlayerID = toPlayerID;
        return this;
    }

    public long getMapSize() {
        return mapSize;
    }

    public W3GSMessageMapPartOK setMapSize(long mapSize) {
        this.mapSize = mapSize;
        return this;
    }
}
