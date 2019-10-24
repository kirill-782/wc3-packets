package ru.irinabot.protocol.gcbi;


import ru.irinabot.util.exceptions.PacketBuildException;

public interface GCBIMessage {

    /**
     * The message primer.
     */

    byte[] assemble() throws PacketBuildException;
}
