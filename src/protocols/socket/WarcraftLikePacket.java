package protocols.socket;

import java.nio.ByteBuffer;

public class WarcraftLikePacket {
    byte headerConstant;
    byte command;
    ByteBuffer data;

    public WarcraftLikePacket(byte headerConstant, byte command, ByteBuffer data) {
        this.headerConstant = headerConstant;
        this.command = command;
        this.data = data;
    }

    public byte getHeaderConstant() {
        return headerConstant;
    }

    public byte getCommand() {
        return command;
    }

    public ByteBuffer getData() {
        return data;
    }
}
