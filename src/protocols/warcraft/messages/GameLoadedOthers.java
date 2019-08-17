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

public class GameLoadedOthers implements WC3Message {

    private byte playerID = 0;

    public GameLoadedOthers()
    {

    }

    public GameLoadedOthers(ByteBuffer b)
    {
        this.playerID = b.get();
    }

    @Override
    public byte[] assemble()
    {
        ByteBuffer b = ByteBuffer.allocate(5);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.GAMELOADEDOTHERS);
        b.putShort((short) 5);

        b.put(this.playerID);

        return b.array();
    }
}