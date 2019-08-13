package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
import protocols.warcraft.exceptions.IllegalPlayerNameSizeException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class GameLoadedSelf implements WC3Message {

    public GameLoadedSelf()
    {

    }

    public GameLoadedSelf(ByteBuffer b)
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
        b.put(WC3Message.GAMELOADEDSELF);
//        b.putShort((short) 4);


        return b.array();
    }
}