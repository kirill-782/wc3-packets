package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalByteSizeException;
import ru.irinabot.util.exceptions.IllegalMapPathException;
import ru.irinabot.util.exceptions.PacketBuildException;
import ru.irinabot.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class W3GSMessageMapCheck implements W3GSMessage {
    private String mapPath;
    private long mapSize;
    private byte[] mapInfo;
    private byte[] mapCRC;
    private byte[] mapSHA1;

    public W3GSMessageMapCheck()
    {

    }

    public W3GSMessageMapCheck(ByteBuffer b) throws IllegalMapPathException {

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
        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.MAPCHECK);
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

    public W3GSMessageMapCheck setMapPath(String mapPath) {
        this.mapPath = mapPath;
        return this;
    }

    public long getMapSize() {
        return mapSize;
    }

    public W3GSMessageMapCheck setMapSize(long mapSize) {
        this.mapSize = mapSize;
        return this;
    }

    public byte[] getMapInfo() {
        return mapInfo;
    }

    public W3GSMessageMapCheck setMapInfo(byte[] mapInfo) {
        this.mapInfo = mapInfo;
        return this;
    }

    public byte[] getMapCRC() {
        return mapCRC;
    }

    public W3GSMessageMapCheck setMapCRC(byte[] mapCRC) {
        this.mapCRC = mapCRC;
        return this;
    }

    public byte[] getMapSHA1() {
        return mapSHA1;
    }

    public W3GSMessageMapCheck setMapSHA1(byte[] mapSHA1) {
        this.mapSHA1 = mapSHA1;
        return this;
    }


}
