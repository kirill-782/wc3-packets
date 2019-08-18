package protocols.warcraft.messages;

import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.util.RejectReason;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageRejectJoin implements WC3Message {

    private RejectReason reason = RejectReason.STARTED;

    public MessageRejectJoin()
    {

    }

    public MessageRejectJoin(ByteBuffer b)
    {
        this.reason = RejectReason.getInstance(b.getInt());
    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.REJECTJOIN);
        b.putShort((short) 8);

        b.putInt(this.reason.getReason());

        return b.array();
    }

    public RejectReason getReason() {
        return reason;
    }

    public MessageRejectJoin setReason(RejectReason reason) {
        this.reason = reason;
        return this;
    }
}
