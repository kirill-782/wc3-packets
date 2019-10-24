package ru.irinabot.protocol.gps;


import ru.irinabot.util.exceptions.PacketBuildException;

public interface GPSMessage {

    /**
     * The message primer.
     */

    byte[] assemble() throws PacketBuildException;
}
