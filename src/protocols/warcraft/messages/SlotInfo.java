package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.Slot;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class SlotInfo implements WC3Message {

    private ArrayList<Slot> slots = new ArrayList<>();
    private int randomSeed = 0;
    private byte layoutStyle = 0;
    private byte slotsForPlayers = 0;

    public SlotInfo( )
    {

    }

    public SlotInfo(ByteBuffer b)
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

        b.put(Messages.HEADER);
        b.put(Messages.SLOTINFO);
        b.putShort((short) (encodedSlotInfo.length + 6));

        b.putShort((short) encodedSlotInfo.length);
        b.put(encodedSlotInfo);

        return b.array();
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public SlotInfo setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
        return this;
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public SlotInfo setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
        return this;
    }

    public byte getLayoutStyle() {
        return layoutStyle;
    }

    public SlotInfo setLayoutStyle(byte layoutStyle) {
        this.layoutStyle = layoutStyle;
        return this;
    }

    public byte getSlotsForPlayers() {
        return slotsForPlayers;
    }

    public SlotInfo setSlotsForPlayers(byte slotsForPlayers) {
        this.slotsForPlayers = slotsForPlayers;
        return this;
    }
}
