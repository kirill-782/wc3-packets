package ru.irinabot.protocol.gcbi.messages;

import ru.irinabot.protocol.gcbi.GCBIMessage;
import ru.irinabot.protocol.gcbi.GCBIMessageConstant;
import ru.irinabot.util.exceptions.IllegalByteSizeException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GCBIMessageInit implements GCBIMessage {

    private byte[] remoteIP = new byte[4];
    private int userID;
    private int roomID;
    private int userLevel;
    private String country;

    public GCBIMessageInit() {
    }

    public GCBIMessageInit(ByteBuffer b) {
        b.order(ByteOrder.BIG_ENDIAN);
        b.get(this.remoteIP);
        b.getInt(this.userID);
        b.getInt(this.roomID);
        b.getInt(this.userLevel);

        byte[] country = new byte[2];
        b.get(country);

        this.country = new String(country);
        b.order(ByteOrder.LITTLE_ENDIAN);
    }

    @Override
    public byte[] assemble() throws IllegalByteSizeException {

        byte[] country = this.country.getBytes();

        if(country.length != 2)
            throw new IllegalByteSizeException("country", 2);

        if(remoteIP.length != 4)
            throw new IllegalByteSizeException("remoteIP", 4);

        ByteBuffer b = ByteBuffer.allocate(22);
        b.put(GCBIMessageConstant.HEADER);
        b.put(GCBIMessageConstant.INIT);
        b.putShort((short) 22);
        b.put(this.remoteIP);
        b.putInt(this.userID);
        b.putInt(this.roomID);
        b.putInt(this.userLevel);
        b.put(country);

        return b.array();
    }

    public byte[] getRemoteIP() {
        return remoteIP;
    }

    public GCBIMessageInit setRemoteIP(byte[] remoteIP) {
        this.remoteIP = remoteIP;
        return this;
    }

    public int getUserID() {
        return userID;
    }

    public GCBIMessageInit setUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public int getRoomID() {
        return roomID;
    }

    public GCBIMessageInit setRoomID(int roomID) {
        this.roomID = roomID;
        return this;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public GCBIMessageInit setUserLevel(int userLevel) {
        this.userLevel = userLevel;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public GCBIMessageInit setCountry(String country) {
        this.country = country;
        return this;
    }
}
