package ru.irinabot.protocol.gps.messages.server;

import ru.irinabot.protocol.gps.GPSMessage;
import ru.irinabot.protocol.gps.GPSMessageConstant;
import ru.irinabot.protocol.gps.entries.RejectReconnectReason;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GPSServerMessageReject implements GPSMessage {
    private RejectReconnectReason rejectReconnectReason;

    public GPSServerMessageReject()
    {

    }

    public GPSServerMessageReject(ByteBuffer b)
    {
        this.rejectReconnectReason = RejectReconnectReason.getInstance(b.getInt());
    }


    @Override
    public byte[] assemble() throws PacketBuildException {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSMessageConstant.HEADER);
        b.put(GPSMessageConstant.REJECT);
        b.putShort((short) 8);

        b.putInt(rejectReconnectReason.getReason());

        return b.array();
    }

    public RejectReconnectReason getRejectReconnectReason() {
        return rejectReconnectReason;
    }

    public GPSServerMessageReject setRejectReconnectReason(RejectReconnectReason rejectReconnectReason) {
        this.rejectReconnectReason = rejectReconnectReason;
        return this;
    }
}
