package ru.irinabot.protocol.w3gs;

import ru.irinabot.util.exceptions.PacketBuildException;

public interface W3GSMessage {

    /**
     * The message primer.
     */


    byte[] assemble() throws PacketBuildException;
}
