package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalByteSizeException;
import ru.irinabot.util.exceptions.IllegalPlayerNameSizeException;
import ru.irinabot.util.exceptions.IllegalPortException;
import ru.irinabot.util.exceptions.PacketBuildException;
import ru.irinabot.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class W3GSMessageReqJoin implements W3GSMessage {

    private int hostCounterID = 0;
    private int entryKey = 0;
    private int listenPort = 0;
    private int peerKey = 0;
    private String name = "Symmetra";
    private byte[] internalIP = null;

    public W3GSMessageReqJoin() {

    }

    public W3GSMessageReqJoin(ByteBuffer b) throws IllegalPlayerNameSizeException {

        this.hostCounterID = b.getInt();
        this.entryKey = b.getInt();

        b.get();

        this.listenPort = Short.toUnsignedInt(b.getShort());
        this.peerKey = b.getInt();

        byte[] nameRaw = Util.getNullTremilaned(b);

        if (nameRaw.length > 15 || nameRaw.length == 0)
            throw new IllegalPlayerNameSizeException("playerName");

        this.name = new String(nameRaw, StandardCharsets.UTF_8);
        b.position(b.position() + 6);

        this.internalIP = new byte[4];
        b.get(this.internalIP);
    }

    @Override
    public byte[] assemble() throws PacketBuildException {
        byte[] nameRaw = this.name.getBytes(StandardCharsets.UTF_8);

        if( nameRaw.length > 15 || nameRaw.length == 0)
            throw new IllegalPlayerNameSizeException("playerName");

        if(this.listenPort > 0xffff)
            throw new IllegalPortException( this.listenPort );

        ByteBuffer b = ByteBuffer.allocate( nameRaw.length + 30);
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.REQJOIN);
        b.putShort((short) (nameRaw.length + 30));
        b.putInt(this.hostCounterID);
        b.putInt(this.entryKey);

        b.put((byte) 0);
        b.putShort((short) this.listenPort);
        b.putInt(this.peerKey);

        b.put(nameRaw);
        b.put((byte) 0);

        b.putInt(0);
        b.putShort((short) this.listenPort);

        if(this.internalIP == null)
            b.putInt(0);
        else if( this.internalIP.length != 4 )
            throw new IllegalByteSizeException("internalIP", 4);
        else
            b.put(this.internalIP);

        return b.array();
    }

    public int getHostCounterID() {
        return hostCounterID;
    }

    public W3GSMessageReqJoin setHostCounterID(int hostCounterID) {
        this.hostCounterID = hostCounterID;
        return this;
    }

    public int getEntryKey() {
        return entryKey;
    }

    public W3GSMessageReqJoin setEntryKey(int entryKey) {
        this.entryKey = entryKey;
        return this;
    }

    public int getListenPort() {
        return listenPort;
    }

    public W3GSMessageReqJoin setListenPort(int listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public int getPeerKey() {
        return peerKey;
    }

    public W3GSMessageReqJoin setPeerKey(int peerKey) {
        this.peerKey = peerKey;
        return this;
    }

    public String getName() {
        return name;
    }

    public W3GSMessageReqJoin setName(String name) {
        this.name = name;
        return this;
    }

    public byte[] getInternalIP() {
        return internalIP;
    }

    public W3GSMessageReqJoin setInternalIP(byte[] internalIP) {
        this.internalIP = internalIP;
        return this;
    }
}
