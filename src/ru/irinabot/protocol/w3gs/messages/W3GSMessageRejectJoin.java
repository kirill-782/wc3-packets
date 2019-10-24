package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.protocol.w3gs.entries.RejectReason;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class W3GSMessageRejectJoin implements W3GSMessage {

    private RejectReason reason = RejectReason.STARTED;

    public W3GSMessageRejectJoin()
    {

    }

    public W3GSMessageRejectJoin(ByteBuffer b)
    {
        this.reason = RejectReason.getInstance(b.getInt());
    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.REJECTJOIN);
        b.putShort((short) 8);

        b.putInt(this.reason.getReason());

        return b.array();
    }

    public RejectReason getReason() {
        return reason;
    }

    public W3GSMessageRejectJoin setReason(RejectReason reason) {
        this.reason = reason;
        return this;
    }
}
