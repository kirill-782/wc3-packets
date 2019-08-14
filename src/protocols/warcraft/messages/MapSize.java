package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.MapSizeFlag;

import java.nio.ByteBuffer;

public class MapSize implements WC3Message {

    MapSizeFlag flag;
    long mapSize;

    public MapSize()
    {

    }

    public MapSize(ByteBuffer b)
    {
        b.getInt();
        this.flag = MapSizeFlag.getInstance(b.get());
        this.mapSize = Integer.toUnsignedLong(b.getInt());
    }

    @Override
    public byte[] assemble() throws WC3Exception {
        byte[] unknown = {1,0,0,0};

        ByteBuffer b = ByteBuffer.allocate(13);

        b.put(HEADER);
        b.put(MAPSIZE);
        b.putShort((short) 13);

        b.put(unknown);
        b.put(this.flag.getFlag());
        b.putInt((int) this.mapSize);

        return b.array();
    }
}
