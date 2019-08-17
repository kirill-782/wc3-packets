package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.Slot;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalByteSizeException;
import protocols.warcraft.util.Util;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class SlotInfoJoin implements WC3Message {
    private byte playerID = 0;
    private ArrayList<Slot> slots = new ArrayList<>();
    private int randomSeed = 0;
    private byte layoutStyle = 0;
    private byte slotsForPlayers = 0;
    private int port = 6112;
    private byte[] externalIP = null;


    public SlotInfoJoin()
    {

    }

    public SlotInfoJoin(ByteBuffer b) {

        int endSlotInfo = b.position() + 2 + Short.toUnsignedInt(b.getShort());

        // Encoded SlotInfo

        int countSlots = Byte.toUnsignedInt(b.get());

        for( int i = 0; i < countSlots; ++i)
            this.slots.add(new Slot(b));

        this.randomSeed = b.getInt();
        this.layoutStyle = b.get();
        this.slotsForPlayers = b.get();

        // Encoded SlotInfo end.

        //b.position(endSlotInfo);

        this.playerID = b.get();

        // AF_NET constants
        b.position(b.position() + 2);

        this.port = Short.toUnsignedInt(b.getShort());
        this.externalIP = new byte[4];

        for (int i = 0; i < 4; ++i)
            this.externalIP[i] = b.get();

        // 8 byte zeros
    }

    @Override
    public byte[] assemble() throws IllegalByteSizeException {

        if(this.externalIP != null && this.externalIP.length != 4)
            throw new IllegalByteSizeException("externalIP", 4);

        byte[] encodedSlotInfo = Util.EncodeSlotInfo(this.slots, this.randomSeed, this.layoutStyle, this.slotsForPlayers);

        ByteBuffer b = ByteBuffer.allocate(encodedSlotInfo.length + 23);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.SLOTINFOJOIN);
        b.putShort((short) (encodedSlotInfo.length + 23));

        b.putShort((short) encodedSlotInfo.length);
        b.put(encodedSlotInfo);

        b.put(this.playerID);
        b.put((byte) 2);
        b.put((byte) 0);

        b.putShort((short) this.port);

        if(this.externalIP != null)
            b.put(this.externalIP);
        else
            b.putInt(0);

        b.putLong(0);

        return b.array();
    }

    public byte getPlayerID() {
        return playerID;
    }

    public SlotInfoJoin setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public SlotInfoJoin setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
        return this;
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public SlotInfoJoin setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
        return this;
    }

    public byte getLayoutStyle() {
        return layoutStyle;
    }

    public SlotInfoJoin setLayoutStyle(byte layoutStyle) {
        this.layoutStyle = layoutStyle;
        return this;
    }

    public byte getSlotsForPlayers() {
        return slotsForPlayers;
    }

    public SlotInfoJoin setSlotsForPlayers(byte slotsForPlayers) {
        this.slotsForPlayers = slotsForPlayers;
        return this;
    }

    public int getPort() {
        return port;
    }

    public SlotInfoJoin setPort(int port) {
        this.port = port;
        return this;
    }

    public byte[] getExternalIP() {
        return externalIP;
    }

    public SlotInfoJoin setExternalIP(byte[] externalIP) {
        this.externalIP = externalIP;
        return this;
    }
}
