package protocols.gps.messages.client;

import protocols.gps.GPSIMessageConstant;
import protocols.gps.GPSMessage;
import protocols.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageInit implements GPSMessage {

    private int version;

    public MessageInit() {
    }

    public MessageInit(ByteBuffer b) {
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

    public MessageInit setVersion(int version) {
        this.version = version;
        return this;
    }
}
