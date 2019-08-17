package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.util.PlayerLeaveCode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PlayerLeaveOther implements WC3Message {

    private PlayerLeaveCode leftCode;

    public PlayerLeaveOther(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        this.leftCode = PlayerLeaveCode.getInstance(b.getInt(4));
    }

    public PlayerLeaveOther()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.PLAYERLEAVEOTHERS);
        b.putShort((short) 8);

        b.putInt(this.leftCode.getCode( ));

        return b.array();
    }

    public PlayerLeaveCode getLeftCode() {
        return leftCode;
    }

    public PlayerLeaveOther setLeftCode(PlayerLeaveCode leftCode) {
        this.leftCode = leftCode;

        return this;
    }
}
