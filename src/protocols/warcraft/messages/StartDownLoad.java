package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
import protocols.warcraft.exceptions.IllegalPlayerNameSizeException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class StartDownLoad implements WC3Message {

    private byte fromPlayerID = 0;

    public StartDownLoad()
    {

    }

    public StartDownLoad(ByteBuffer b)
    {
        //
        //

        this.fromPlayerID = b.get();
    }

    @Override
    public byte[] assemble()
    {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3Message.HEADER);
        b.put(WC3Message.STARTDOWNLOAD);
        b.putShort((short) 2);
        b.putInt(1);

        b.put(this.fromPlayerID);

        return b.array();
    }
}