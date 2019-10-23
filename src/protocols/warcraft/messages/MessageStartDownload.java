package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.util.exceptions.IllegalPlayerIDException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageStartDownload implements WC3Message {

    private byte fromPlayerID = 0;

    public MessageStartDownload()
    {

    }

    public MessageStartDownload(ByteBuffer b) throws IllegalPlayerIDException {
        b.getInt();
        this.fromPlayerID = b.get();

        if (this.fromPlayerID > Constants.MAXPLAYERS || this.fromPlayerID < 1)
            throw new IllegalPlayerIDException("fromPlayerID", this.fromPlayerID);
    }

    @Override
    public byte[] assemble() throws IllegalPlayerIDException {
        if (this.fromPlayerID > Constants.MAXPLAYERS || this.fromPlayerID < 1)
            throw new IllegalPlayerIDException("fromPlayerID", this.fromPlayerID);

        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.STARTDOWNLOAD);
        b.putShort((short) 2);
        b.putInt(1);

        b.put(this.fromPlayerID);

        return b.array();
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public MessageStartDownload setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }
}