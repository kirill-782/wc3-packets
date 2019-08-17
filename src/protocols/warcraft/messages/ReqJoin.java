package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
import protocols.warcraft.exceptions.IllegalPlayerNameSizeException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class ReqJoin implements WC3Message {

    private int hostCounterID = 0;
    private int entryKey = 0;
    private int listenPort = 0;
    private int peerKey = 0;
    private String name = "Symmetra";
    private byte[] internalIP = null;

    public ReqJoin() {

    }

    public ReqJoin(ByteBuffer b) throws IllegalPlayerNameSizeException {

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
    public byte[] assemble() throws WC3Exception {
        byte[] nameRaw = this.name.getBytes(StandardCharsets.UTF_8);

        if( nameRaw.length > 15 || nameRaw.length == 0)
            throw new IllegalPlayerNameSizeException("playerName");

        ByteBuffer b = ByteBuffer.allocate( nameRaw.length + 30);
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.put(Messages.HEADER);
        b.put(Messages.REQJOIN);
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

    public ReqJoin setHostCounterID(int hostCounterID) {
        this.hostCounterID = hostCounterID;
        return this;
    }

    public int getEntryKey() {
        return entryKey;
    }

    public ReqJoin setEntryKey(int entryKey) {
        this.entryKey = entryKey;
        return this;
    }

    public int getListenPort() {
        return listenPort;
    }

    public ReqJoin setListenPort(int listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public int getPeerKey() {
        return peerKey;
    }

    public ReqJoin setPeerKey(int peerKey) {
        this.peerKey = peerKey;
        return this;
    }

    public String getName() {
        return name;
    }

    public ReqJoin setName(String name) {
        this.name = name;
        return this;
    }

    public byte[] getInternalIP() {
        return internalIP;
    }

    public ReqJoin setInternalIP(byte[] internalIP) {
        this.internalIP = internalIP;
        return this;
    }
}
