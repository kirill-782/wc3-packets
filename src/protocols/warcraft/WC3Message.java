package protocols.warcraft;

import protocols.warcraft.exceptions.WC3Exception;

public interface WC3Message {

    /**
     * The message primer.
     */


    byte[] assemble() throws WC3Exception;
}
