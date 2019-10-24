package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.Constants;
import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalPlayerIDException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageStartDownload implements W3GSMessage {

    private byte fromPlayerID = 0;

    public W3GSMessageStartDownload()
    {

    }

    public W3GSMessageStartDownload(ByteBuffer b) throws IllegalPlayerIDException {
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

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.STARTDOWNLOAD);
        b.putShort((short) 2);
        b.putInt(1);

        b.put(this.fromPlayerID);

        return b.array();
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public W3GSMessageStartDownload setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }
}