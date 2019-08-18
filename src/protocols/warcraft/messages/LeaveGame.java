package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.PlayerLeaveCode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LeaveGame implements WC3Message {
    private PlayerLeaveCode reason = PlayerLeaveCode.LOBBY;

    public LeaveGame()
    {

    }

    public LeaveGame(ByteBuffer b)
    {
        this.reason = PlayerLeaveCode.getInstance(b.getInt());
    }


    @Override
    public byte[] assemble() throws WC3Exception {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.LEAVEGAME);
        b.putShort((short) 8);

        b.putInt(this.reason.getCode());

        return b.array();
    }

    public PlayerLeaveCode getReason() {
        return reason;
    }

    public LeaveGame setReason(PlayerLeaveCode reason) {
        this.reason = reason;
        return this;
    }
}
