package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.Constants;
import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalPlayerIDException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;

public class W3GSMessageMapPart implements W3GSMessage {

    private byte toPlayerID;
    private byte fromPlayerID;
    private long startPosition;
    private byte[] crc32;
    private byte[] data;

    public W3GSMessageMapPart( )
    {

    }

    public W3GSMessageMapPart(ByteBuffer b) throws IllegalPlayerIDException {
        this.toPlayerID = b.get();
        this.fromPlayerID = b.get();

        if (this.toPlayerID > Constants.MAXPLAYERS || this.toPlayerID < 1)
            throw new IllegalPlayerIDException("toPlayerID", this.toPlayerID);

        if (this.fromPlayerID > Constants.MAXPLAYERS || this.fromPlayerID < 1)
            throw new IllegalPlayerIDException("fromPlayerID", this.fromPlayerID);

        this.startPosition = Integer.toUnsignedLong(b.getInt());

        this.crc32 = new byte[4];
        b.get(this.crc32);

        this.data = new byte[b.limit() - b.position()];
        b.get(this.data);
    }

    @Override
    public byte[] assemble() throws IllegalPlayerIDException {

        if (this.toPlayerID > Constants.MAXPLAYERS || this.toPlayerID < 1)
            throw new IllegalPlayerIDException("toPlayerID", this.toPlayerID);

        if (this.fromPlayerID > Constants.MAXPLAYERS || this.fromPlayerID < 1)
            throw new IllegalPlayerIDException("fromPlayerID", this.fromPlayerID);

        ByteBuffer b = ByteBuffer.allocate(data.length + 14);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.MAPPART);
        b.putShort((short) (data.length + 14));

        b.put(this.toPlayerID);
        b.put(this.fromPlayerID);

        b.putInt((int) this.startPosition);

        CRC32 crc32 = new CRC32();
        crc32.update(this.data);

        b.putInt((int) crc32.getValue());

        b.put(this.data);

        return b.array();
    }

    public byte getToPlayerID() {
        return toPlayerID;
    }

    public W3GSMessageMapPart setToPlayerID(byte toPlayerID) {
        this.toPlayerID = toPlayerID;
        return this;
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public W3GSMessageMapPart setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public W3GSMessageMapPart setStartPosition(long startPosition) {
        this.startPosition = startPosition;
        return this;
    }

    public byte[] getCrc32() {
        return crc32;
    }

    public W3GSMessageMapPart setCrc32(byte[] crc32) {
        this.crc32 = crc32;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public W3GSMessageMapPart setData(byte[] data) {
        this.data = data;
        return this;
    }
}
