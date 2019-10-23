package protocols.gcbi;


import protocols.util.exceptions.PacketBuildException;

public interface GCBIMessage {

    /**
     * The message primer.
     */

    byte[] assemble() throws PacketBuildException;
}
