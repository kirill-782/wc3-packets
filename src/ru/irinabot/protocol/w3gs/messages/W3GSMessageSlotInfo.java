package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.Slot;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class W3GSMessageSlotInfo implements W3GSMessage {

    private ArrayList<Slot> slots = new ArrayList<>();
    private int randomSeed = 0;
    private byte layoutStyle = 0;
    private byte slotsForPlayers = 0;

    public W3GSMessageSlotInfo( )
    {

    }

    public W3GSMessageSlotInfo(ByteBuffer b)
    {
        b.getShort();

        int countSlots = Byte.toUnsignedInt(b.get());

        for( int i = 0; i < countSlots; ++i)
            this.slots.add(new Slot(b));

        this.randomSeed = b.getInt();
        this.layoutStyle = b.get();
        this.slotsForPlayers = b.get();
    }

    @Override
    public byte[] assemble() {
        byte[] encodedSlotInfo = Util.EncodeSlotInfo(this.slots, this.randomSeed, this.layoutStyle, this.slotsForPlayers);

        ByteBuffer b = ByteBuffer.allocate(encodedSlotInfo.length + 23);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.SLOTINFO);
        b.putShort((short) (encodedSlotInfo.length + 6));

        b.putShort((short) encodedSlotInfo.length);
        b.put(encodedSlotInfo);

        return b.array();
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public W3GSMessageSlotInfo setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
        return this;
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public W3GSMessageSlotInfo setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
        return this;
    }

    public byte getLayoutStyle() {
        return layoutStyle;
    }

    public W3GSMessageSlotInfo setLayoutStyle(byte layoutStyle) {
        this.layoutStyle = layoutStyle;
        return this;
    }

    public byte getSlotsForPlayers() {
        return slotsForPlayers;
    }

    public W3GSMessageSlotInfo setSlotsForPlayers(byte slotsForPlayers) {
        this.slotsForPlayers = slotsForPlayers;
        return this;
    }
}
