package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageGameLoadedOthers implements WC3Message {

    private byte playerID = 0;

    public MessageGameLoadedOthers()
    {

    }

    public MessageGameLoadedOthers(ByteBuffer b)
    {
        this.playerID = b.get();
    }

    @Override
    public byte[] assemble()
    {
        ByteBuffer b = ByteBuffer.allocate(5);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.GAMELOADEDOTHERS);
        b.putShort((short) 5);

        b.put(this.playerID);

        return b.array();
    }

    public byte getPlayerID() {
        return playerID;
    }

    public MessageGameLoadedOthers setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }
}