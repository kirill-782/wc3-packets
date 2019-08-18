package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
import protocols.warcraft.exceptions.IllegalPlayerIDException;
import protocols.warcraft.exceptions.IllegalPlayerNameSizeException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class StartDownload implements WC3Message {

    private byte fromPlayerID = 0;

    public StartDownload()
    {

    }

    public StartDownload(ByteBuffer b) throws IllegalPlayerIDException {
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

        b.put(Messages.HEADER);
        b.put(Messages.STARTDOWNLOAD);
        b.putShort((short) 2);
        b.putInt(1);

        b.put(this.fromPlayerID);

        return b.array();
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public StartDownload setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }
}