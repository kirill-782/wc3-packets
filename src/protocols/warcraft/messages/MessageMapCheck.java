package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.util.exceptions.IllegalByteSizeException;
import protocols.util.exceptions.IllegalMapPathException;
import protocols.util.exceptions.PacketBuildException;
import protocols.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class MessageMapCheck implements WC3Message {
    private String mapPath;
    private long mapSize;
    private byte[] mapInfo;
    private byte[] mapCRC;
    private byte[] mapSHA1;

    public MessageMapCheck()
    {

    }

    public MessageMapCheck(ByteBuffer b) throws IllegalMapPathException {

        b.getInt();

        byte[] mapPathRaw = Util.getNullTremilaned(b);

        if( mapPathRaw.length == 0)
            throw new IllegalMapPathException();

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
    public byte[] assemble() throws PacketBuildException {
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
        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.MAPCHECK);
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

    public MessageMapCheck setMapPath(String mapPath) {
        this.mapPath = mapPath;
        return this;
    }

    public long getMapSize() {
        return mapSize;
    }

    public MessageMapCheck setMapSize(long mapSize) {
        this.mapSize = mapSize;
        return this;
    }

    public byte[] getMapInfo() {
        return mapInfo;
    }

    public MessageMapCheck setMapInfo(byte[] mapInfo) {
        this.mapInfo = mapInfo;
        return this;
    }

    public byte[] getMapCRC() {
        return mapCRC;
    }

    public MessageMapCheck setMapCRC(byte[] mapCRC) {
        this.mapCRC = mapCRC;
        return this;
    }

    public byte[] getMapSHA1() {
        return mapSHA1;
    }

    public MessageMapCheck setMapSHA1(byte[] mapSHA1) {
        this.mapSHA1 = mapSHA1;
        return this;
    }


}
