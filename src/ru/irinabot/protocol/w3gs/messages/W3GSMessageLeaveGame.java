package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.protocol.w3gs.entries.PlayerLeaveCode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageLeaveGame implements W3GSMessage {
    private PlayerLeaveCode reason = PlayerLeaveCode.LOBBY;

    public W3GSMessageLeaveGame()
    {

    }

    public W3GSMessageLeaveGame(ByteBuffer b)
    {
        this.reason = PlayerLeaveCode.getInstance(b.getInt());
    }


    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.LEAVEGAME);
        b.putShort((short) 8);

        b.putInt(this.reason.getCode());

        return b.array();
    }

    public PlayerLeaveCode getReason() {
        return reason;
    }

    public W3GSMessageLeaveGame setReason(PlayerLeaveCode reason) {
        this.reason = reason;
        return this;
    }
}
