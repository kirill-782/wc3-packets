package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
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

    public StartDownload(ByteBuffer b)
    {
        b.getInt();
        this.fromPlayerID = b.get();
    }

    @Override
    public byte[] assemble()
    {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.STARTDOWNLOAD);
        b.putShort((short) 2);
        b.putInt(1);

        b.put(this.fromPlayerID);

        return b.array();
    }
}