package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.warcraft.util.PlayerLeaveCode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageLeaveGame implements WC3Message {
    private PlayerLeaveCode reason = PlayerLeaveCode.LOBBY;

    public MessageLeaveGame()
    {

    }

    public MessageLeaveGame(ByteBuffer b)
    {
        this.reason = PlayerLeaveCode.getInstance(b.getInt());
    }


    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.LEAVEGAME);
        b.putShort((short) 8);

        b.putInt(this.reason.getCode());

        return b.array();
    }

    public PlayerLeaveCode getReason() {
        return reason;
    }

    public MessageLeaveGame setReason(PlayerLeaveCode reason) {
        this.reason = reason;
        return this;
    }
}
