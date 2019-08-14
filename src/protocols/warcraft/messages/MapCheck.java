package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
import protocols.warcraft.exceptions.IllegalMapPathException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class MapCheck implements WC3Message {
    String mapPath;
    long mapSize;
    byte[] mapInfo;
    byte[] mapCRC;
    byte[] mapSHA1;

    public MapCheck()
    {

    }

    public MapCheck(ByteBuffer b) throws IllegalMapPathException {
        byte[] mapPathRaw = Util.getNullTremilaned(b);

        if( mapPathRaw.length == 0)
            throw new IllegalMapPathException();

        b.getInt();

        this.mapPath = new String(mapPathRaw, StandardCharsets.UTF_8);
        this.mapSize = Integer.toUnsignedLong(b.getInt());

        this.mapInfo = new byte[4];
        this.mapCRC = new byte[4];
        this.mapSHA1 = new byte[20];

        b.get(this.mapInfo);
        b.get(this.mapCRC);
        b.get(this.mapSHA1);
    }


    @Override
    public byte[] assemble() throws WC3Exception {
        byte[] mapPathRaw = this.mapPath.getBytes(StandardCharsets.UTF_8);

        if( mapPathRaw.length == 0)
            throw new IllegalMapPathException();

        if (this.mapCRC.length != 4)
            throw new IllegalByteSizeException("mapCRC", 4);

        if (this.mapInfo.length != 4)
            throw new IllegalByteSizeException("mapInfo", 4);

        if (this.mapSHA1.length != 20)
            throw new IllegalByteSizeException("mapSHA1", 20);

        byte[] unknown = {1,0,0,0};

        ByteBuffer b = ByteBuffer.allocate(33 + mapPathRaw.length);
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.put(HEADER);
        b.put(MAPCHECK);
        b.putShort((short) (33 + mapPathRaw.length));

        b.put(unknown);

        b.put(mapPathRaw);
        b.put((byte) 0);

        b.putInt((int) this.mapSize);

        b.put( this.mapInfo );
        b.put( this.mapCRC );
        b.put( this.mapSHA1 );

        return b.array();
    }

    public String getMapPath() {
        return mapPath;
    }

    public MapCheck setMapPath(String mapPath) {
        this.mapPath = mapPath;
        return this;
    }

    public long getMapSize() {
        return mapSize;
    }

    public MapCheck setMapSize(long mapSize) {
        this.mapSize = mapSize;
        return this;
    }

    public byte[] getMapInfo() {
        return mapInfo;
    }

    public MapCheck setMapInfo(byte[] mapInfo) {
        this.mapInfo = mapInfo;
        return this;
    }

    public byte[] getMapCRC() {
        return mapCRC;
    }

    public MapCheck setMapCRC(byte[] mapCRC) {
        this.mapCRC = mapCRC;
        return this;
    }

    public byte[] getMapSHA1() {
        return mapSHA1;
    }

    public MapCheck setMapSHA1(byte[] mapSHA1) {
        this.mapSHA1 = mapSHA1;
        return this;
    }
}
