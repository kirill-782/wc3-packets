package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
import protocols.warcraft.exceptions.IllegalPlayerNameSizeException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class CountDownStart implements WC3Message {

    public CountDownStart()
    {

    }

    public CountDownStart(ByteBuffer b)
    {
        //
        //
    }

    @Override
    public byte[] assemble()
    {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3Message.HEADER);
        b.put(WC3Message.COUNTDOWNSTART);
        b.putShort((short) 2);

        return b.array();
    }
}