package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.protocol.w3gs.entries.PlayerLeaveCode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessagePlayerLeaveOther implements W3GSMessage {

    private PlayerLeaveCode leftCode;
    private byte playerID;

    public W3GSMessagePlayerLeaveOther(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        this.playerID = b.get();
        this.leftCode = PlayerLeaveCode.getInstance(b.getInt());
    }

    public W3GSMessagePlayerLeaveOther()
    {

    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.PLAYERLEAVEOTHERS);
        b.putShort((short) 8);
        b.put(this.playerID);
        b.putInt(this.leftCode.getCode( ));

        return b.array();
    }

    public PlayerLeaveCode getLeftCode() {
        return leftCode;
    }

    public W3GSMessagePlayerLeaveOther setLeftCode(PlayerLeaveCode leftCode) {
        this.leftCode = leftCode;

        return this;
    }

    public byte getPlayerID() {
        return playerID;
    }

    public W3GSMessagePlayerLeaveOther setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }
}
