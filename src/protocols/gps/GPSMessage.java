package protocols.gps;


import protocols.util.exceptions.PacketBuildException;

public interface GPSMessage {

    /**
     * The message primer.
     */

    byte[] assemble() throws PacketBuildException;
}
