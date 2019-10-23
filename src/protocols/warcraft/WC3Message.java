package protocols.warcraft;

import protocols.util.exceptions.PacketBuildException;

public interface WC3Message {

    /**
     * The message primer.
     */


    byte[] assemble() throws PacketBuildException;
}
