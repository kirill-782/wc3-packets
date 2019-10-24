package ru.irinabot.protocol.w3gs;

public class Action {
    private byte playerID = 0;
    private byte[] action = null;

    public Action()
    {

    }

    public byte getPlayerID() {
        return playerID;
    }

    public Action setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public byte[] getAction() {
        return action;
    }

    public Action setAction(byte[] action) {
        this.action = action;
        return this;
    }
}
