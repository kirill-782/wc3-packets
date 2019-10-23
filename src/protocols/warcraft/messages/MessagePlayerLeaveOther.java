package protocols.warcraft.messages;

import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.warcraft.util.PlayerLeaveCode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessagePlayerLeaveOther implements WC3Message {

    private PlayerLeaveCode leftCode;
    private byte playerID;

    public MessagePlayerLeaveOther(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        this.playerID = b.get();
        this.leftCode = PlayerLeaveCode.getInstance(b.getInt());
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
        b.put(this.playerID);
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

    public byte getPlayerID() {
        return playerID;
    }

    public MessagePlayerLeaveOther setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }
}
