package ru.irinabot.protocol.w3gs;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import ru.irinabot.util.Util;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StatString {

    static private Decoder decoder = new Decoder();
    static private Encoder encoder = new Encoder();

    public static Decoder getDecoder() {
        return decoder;
    }

    public static Encoder getEncoder() {
        return encoder;
    }

    private byte[] mapFlags;
    private byte[] mapWidth;
    private byte[] mapHeight;
    private byte[] mapCRC;
    private byte[] mapSHA1;
    private String mapPath;
    private String hostName;

    public StatString() {
    }

    public StatString(ByteBuffer b, boolean hasSHA1) {

        byte[] decoded = decoder.decode(b);
        parseDecodedStattring(decoded, hasSHA1);
    }

    public StatString(byte[] b, boolean hasSHA1) {
        byte[] decoded = decoder.decode(b);
        parseDecodedStattring(decoded, hasSHA1);

    }

    public void parseDecodedStattring(byte[] decoded, boolean hasSHA1) {
        this.mapFlags = new byte[4];
        this.mapWidth = new byte[2];
        this.mapHeight = new byte[2];
        this.mapCRC = new byte[4];


        //Fill

        System.arraycopy(decoded, 0, this.mapFlags, 0, 4);
        // Next Null byte
        System.arraycopy(decoded, 5, this.mapWidth, 0, 2);
        System.arraycopy(decoded, 7, this.mapHeight, 0, 2);
        System.arraycopy(decoded, 9, this.mapCRC, 0, 4);

        int mapPathLength = Util.strlen(decoded, 13);
        int hostNameLength = Util.strlen(decoded, 14 + mapPathLength);

        this.mapPath = new String(decoded, 13, mapPathLength, StandardCharsets.UTF_8);
        this.hostName = new String(decoded, 14 + mapPathLength, hostNameLength, StandardCharsets.UTF_8);
        // Next Null byte

        if (hasSHA1) {
            this.mapSHA1 = new byte[20];
            System.arraycopy(decoded, 15 + mapPathLength + hostNameLength, this.mapSHA1, 0, 20);
        }
    }

    public byte[] assembly() throws PacketBuildException
    {
        ByteBuffer statString = ByteBuffer.allocate( 14 +
                this.mapPath.length( ) + 1 +
                this.hostName.length( ) + 1 +
                ( this.mapSHA1 == null ? 0 : 20 )
        );

        statString.put( this.mapFlags );
        statString.put( (byte)0x00 );
        statString.put( this.mapWidth );
        statString.put( this.mapHeight );
        statString.put( this.mapCRC );
        statString.put( this.mapPath.getBytes( StandardCharsets.UTF_8 ) );
        statString.put( (byte)0x00 );
        statString.put( this.hostName.getBytes( StandardCharsets.UTF_8 ) );
        statString.put( (byte)0x00 );

        statString.put( (byte)0x00 );

        if( this.mapSHA1 != null )
            statString.put( this.mapSHA1 );

        byte[] encoded = encoder.encode( statString.array( ) );

        byte[] response = new byte[encoded.length + 1];
        System.arraycopy(encoded, 0, response, 0, encoded.length);



        return response;
    }

    public byte[] getMapFlags() {
        return mapFlags;
    }

    public StatString setMapFlags(byte[] mapFlags) {
        this.mapFlags = mapFlags;
        return this;
    }

    public byte[] getMapWidth() {
        return mapWidth;
    }

    public StatString setMapWidth(byte[] mapWidth) {
        this.mapWidth = mapWidth;
        return this;
    }

    public byte[] getMapHeight() {
        return mapHeight;
    }

    public StatString setMapHeight(byte[] mapHeight) {
        this.mapHeight = mapHeight;
        return this;
    }

    public byte[] getMapCRC() {
        return mapCRC;
    }

    public StatString setMapCRC(byte[] mapCRC) {
        this.mapCRC = mapCRC;
        return this;
    }

    public byte[] getMapSHA1() {
        return mapSHA1;
    }

    public StatString setMapSHA1(byte[] mapSHA1) {
        this.mapSHA1 = mapSHA1;
        return this;
    }

    public String getMapPath() {
        return mapPath;
    }

    public StatString setMapPath(String mapPath) {
        this.mapPath = mapPath;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public StatString setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }


    static public class Decoder {
        public byte[] decode(ByteBuffer b) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte currentByte;
            byte mask = 0;

            for (int i = 0; (currentByte = b.get()) != 0; i++) {
                if ((i % 8) == 0)
                    mask = currentByte;
                else {
                    if ((mask & (1 << (i % 8))) == 0)
                        bos.write(currentByte - 1);
                    else
                        bos.write(currentByte);
                }

            }

            return bos.toByteArray();
        }

        public byte[] decode(byte[] b) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte mask = 0;

            for (int i = 0; i < b.length; i++) {
                if ((i % 8) == 0)
                    mask = b[i];
                else {
                    if ((mask & (1 << (i % 8))) == 0)
                        bos.write(b[i] - 1);
                    else
                        bos.write(b[i]);
                }

            }

            return bos.toByteArray();
        }
    }

    static public class Encoder {
        public byte[] encode(byte[] b) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] currentBlock = new byte[8];
            byte mask = 1;

            for (int i = 0; i < b.length; ++i) {
                if (b[i] % 2 == 0)
                    currentBlock[i % 7 + 1] = (byte) (b[i] + 1);
                else {
                    currentBlock[i % 7 + 1] = b[i];
                    mask |= 1 << ((i % 7) + 1);
                }

                if (i % 7 == 6 || i == b.length - 1) {
                    currentBlock[0] = mask;
                    mask = 1;

                    bos.write(currentBlock, 0, i % 7 + 2);
                }
            }

            return bos.toByteArray();
        }
    }
}
