package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PlayerLeaveOther implements WC3Message {

    private int leftCode;

    public PlayerLeaveOther(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        this.leftCode = b.getInt(4);
    }

    public PlayerLeaveOther()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3Message.HEADER);
        b.put(WC3Message.PLAYERLEAVEOTHERS);
        b.putShort((short) 8);

        b.putInt(this.leftCode);

        return b.array();
    }

    public int getLeftCode() {
        return leftCode;
    }

    public PlayerLeaveOther setLeftCode(int leftCode) {
        this.leftCode = leftCode;

        return this;
    }
}
