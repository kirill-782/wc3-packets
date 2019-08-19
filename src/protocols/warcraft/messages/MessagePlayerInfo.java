package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
import protocols.warcraft.exceptions.IllegalPlayerNameSizeException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MessagePlayerInfo implements WC3Message {
    private int playerPeerKey = 0;
    private byte playerID = 0;
    private String playerName = "Symmetra";
    private int externalPort = 0;
    private byte[] extrenalIP = null;
    private int intrenalPort = 0;
    private byte[] internalIP = null;

    public MessagePlayerInfo(ByteBuffer b) throws IllegalPlayerNameSizeException {
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.position(4);

        this.playerPeerKey = b.getInt();
        this.playerID = b.get();

        byte[] playerNameByte = Util.getNullTremilaned(b);

        if(playerNameByte.length > 15)
            throw new IllegalPlayerNameSizeException("playerName");

        this.playerName = new String(playerNameByte, Charset.forName("UTF-8"));

        b.position(b.position() + 4); // 2 unknown + 2 AF_NET

        this.externalPort = Short.toUnsignedInt(b.getShort());

        this.extrenalIP = new byte[4];
        b.get(this.extrenalIP);

        b.position(b.position() + 12); // 8 zeros + 2 unknown + 2 AF_NET

        this.intrenalPort = Short.toUnsignedInt(b.getShort());

        this.internalIP = new byte[4];
        b.get(this.internalIP);

    }

    public MessagePlayerInfo()
    {

    }

    @Override
    public byte[] assemble() throws WC3Exception {

        int playerNameSize = this.playerName.getBytes(StandardCharsets.UTF_8).length;

        if(playerNameSize > 15 || playerNameSize == 0) {
            throw new IllegalPlayerNameSizeException("playerName");
        }

        if(this.internalIP != null && this.extrenalIP.length != 4)
            throw new IllegalByteSizeException("externalIP", 4);

        if(this.internalIP != null && this.internalIP.length != 4)
            throw new IllegalByteSizeException("internalIP", 4);

        ByteBuffer b = ByteBuffer.allocate(playerNameSize + 40 );
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.PLAYERINFO);
        b.putShort((short) (playerNameSize + 40));

        b.putInt(this.playerPeerKey);
        b.put(this.playerID);
        b.put(this.playerName.getBytes(StandardCharsets.UTF_8));
        b.put((byte) 0); // End string byte

        b.put((byte) 1);
        b.put((byte) 0);

        b.put((byte) 2);
        b.put((byte) 0);
        b.putShort((short) this.externalPort);

        if (this.extrenalIP != null)
            b.put(this.extrenalIP);
        else
            b.putInt(0);

        b.putLong(0);


        b.put((byte) 2);
        b.put((byte) 0);
        b.putShort((short) this.intrenalPort);

        if (this.internalIP != null)
            b.put(this.internalIP);
        else
            b.putInt(0);

        b.putLong(0);

        return b.array();
    }

    public int getPlayerPeerKey() {
        return playerPeerKey;
    }

    public MessagePlayerInfo setPlayerPeerKey(int playerPeerKey) {
        this.playerPeerKey = playerPeerKey;
        return this;
    }

    public byte getPlayerID() {
        return playerID;
    }

    public MessagePlayerInfo setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public String getPlayerName() {
        return playerName;
    }

    public MessagePlayerInfo setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public int getExternalPort() {
        return externalPort;
    }

    public MessagePlayerInfo setExternalPort(int externalPort) {
        this.externalPort = externalPort;
        return this;
    }

    public byte[] getExtrenalIP() {
        return extrenalIP;
    }

    public MessagePlayerInfo setExtrenalIP(byte[] extrenalIP) {
        this.extrenalIP = extrenalIP;
        return this;
    }

    public int getIntrenalPort() {
        return intrenalPort;
    }

    public MessagePlayerInfo setIntrenalPort(int intrenalPort) {
        this.intrenalPort = intrenalPort;
        return this;
    }

    public byte[] getInternalIP() {
        return internalIP;
    }

    public MessagePlayerInfo setInternalIP(byte[] internalIP) {
        this.internalIP = internalIP;
        return this;
    }
}
