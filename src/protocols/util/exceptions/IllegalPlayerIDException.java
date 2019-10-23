package protocols.util.exceptions;

import protocols.warcraft.Constants;

public class IllegalPlayerIDException extends PacketBuildException {
    private String field;
    private byte playerID;

    public IllegalPlayerIDException(String field, byte playerID) {
        super(field + " must have range from 1 to " + Constants.MAXPLAYERS + ". Got value: " + playerID);

        this.field = field;
        this.playerID = playerID;
    }

    public String getField() {
        return field;
    }

    public byte getPlayerID() {
        return playerID;
    }
}
