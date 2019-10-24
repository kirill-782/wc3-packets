package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.protocol.w3gs.entries.MapSizeFlag;

import java.nio.ByteBuffer;

public class W3GSMessageMapSize implements W3GSMessage {

    private MapSizeFlag flag;
    private long mapSize;

    public W3GSMessageMapSize()
    {

    }

    public W3GSMessageMapSize(ByteBuffer b)
    {
        b.getInt();
        this.flag = MapSizeFlag.getInstance(b.get());
        this.mapSize = Integer.toUnsignedLong(b.getInt());
    }

    @Override
    public byte[] assemble() {
        byte[] unknown = {1,0,0,0};

        ByteBuffer b = ByteBuffer.allocate(13);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.MAPSIZE);
        b.putShort((short) 13);

        b.put(unknown);
        b.put(this.flag.getFlag());
        b.putInt((int) this.mapSize);

        return b.array();
    }

    public MapSizeFlag getFlag() {
        return flag;
    }

    public W3GSMessageMapSize setFlag(MapSizeFlag flag) {
        this.flag = flag;
        return this;
    }

    public long getMapSize() {
        return mapSize;
    }

    public W3GSMessageMapSize setMapSize(long mapSize) {
        this.mapSize = mapSize;
        return this;
    }
}
