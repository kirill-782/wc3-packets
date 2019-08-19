package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.warcraft.util.MapSizeFlag;

import java.nio.ByteBuffer;

public class MessageMapSize implements WC3Message {

    private MapSizeFlag flag;
    private long mapSize;

    public MessageMapSize()
    {

    }

    public MessageMapSize(ByteBuffer b)
    {
        b.getInt();
        this.flag = MapSizeFlag.getInstance(b.get());
        this.mapSize = Integer.toUnsignedLong(b.getInt());
    }

    @Override
    public byte[] assemble() {
        byte[] unknown = {1,0,0,0};

        ByteBuffer b = ByteBuffer.allocate(13);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.MAPSIZE);
        b.putShort((short) 13);

        b.put(unknown);
        b.put(this.flag.getFlag());
        b.putInt((int) this.mapSize);

        return b.array();
    }

    public MapSizeFlag getFlag() {
        return flag;
    }

    public MessageMapSize setFlag(MapSizeFlag flag) {
        this.flag = flag;
        return this;
    }

    public long getMapSize() {
        return mapSize;
    }

    public MessageMapSize setMapSize(long mapSize) {
        this.mapSize = mapSize;
        return this;
    }
}
