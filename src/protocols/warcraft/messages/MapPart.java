package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalPlayerIDException;
import protocols.warcraft.exceptions.WC3Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;

public class MapPart implements WC3Message {

    private byte toPlayerID;
    private byte fromPlayerID;
    private long startPosition;
    private byte[] crc32;
    private byte[] data;

    public MapPart( )
    {

    }

    public MapPart(ByteBuffer b) throws IllegalPlayerIDException {
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

        b.put(Messages.HEADER);
        b.put(Messages.MAPPART);
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
}
