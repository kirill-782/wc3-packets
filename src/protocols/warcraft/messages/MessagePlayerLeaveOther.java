package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.warcraft.util.PlayerLeaveCode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessagePlayerLeaveOther implements WC3Message {

    private PlayerLeaveCode leftCode;

    public MessagePlayerLeaveOther(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        this.leftCode = PlayerLeaveCode.getInstance(b.getInt(4));
    }

    public MessagePlayerLeaveOther()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.PLAYERLEAVEOTHERS);
        b.putShort((short) 8);

        b.putInt(this.leftCode.getCode( ));

        return b.array();
    }

    public PlayerLeaveCode getLeftCode() {
        return leftCode;
    }

    public MessagePlayerLeaveOther setLeftCode(PlayerLeaveCode leftCode) {
        this.leftCode = leftCode;

        return this;
    }
}
