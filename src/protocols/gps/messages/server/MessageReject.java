package protocols.gps.messages.server;

import protocols.gps.GPSIMessageConstant;
import protocols.gps.GPSMessage;
import protocols.gps.entries.RejectReconnectReason;
import protocols.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageReject implements GPSMessage {
    private RejectReconnectReason rejectReconnectReason;

    public MessageReject()
    {

    }

    public MessageReject(ByteBuffer b)
    {
        this.rejectReconnectReason = RejectReconnectReason.getInstance(b.getInt());
    }


    @Override
    public byte[] assemble() throws PacketBuildException {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSIMessageConstant.HEADER);
        b.put(GPSIMessageConstant.REJECT);
        b.putShort((short) 8);

        b.putInt(rejectReconnectReason.getReason());

        return b.array();
    }

    public RejectReconnectReason getRejectReconnectReason() {
        return rejectReconnectReason;
    }

    public MessageReject setRejectReconnectReason(RejectReconnectReason rejectReconnectReason) {
        this.rejectReconnectReason = rejectReconnectReason;
        return this;
    }
}
