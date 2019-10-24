package ru.irinabot.protocol.gps.messages.client;

import ru.irinabot.protocol.gps.GPSIMessageConstant;
import ru.irinabot.protocol.gps.GPSMessage;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GPSClientMessageInit implements GPSMessage {

    private int version;

    public GPSClientMessageInit() {
    }

    public GPSClientMessageInit(ByteBuffer b) {
        this.version = b.getInt();
    }

    @Override
    public byte[] assemble() throws PacketBuildException {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(GPSIMessageConstant.HEADER);
        b.put(GPSIMessageConstant.INIT);
        b.putShort((short) 8);

        b.putInt(this.version);

        return b.array();
    }

    public int getVersion() {
        return version;
    }

    public GPSClientMessageInit setVersion(int version) {
        this.version = version;
        return this;
    }
}
