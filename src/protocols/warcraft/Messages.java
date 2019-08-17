package protocols.warcraft;

public interface Messages {

    byte HEADER = (byte) 0xf7;

    /**
     * UDP Messages.
     */
    byte SEARCHGAME = (byte) 0x2F;
    byte GAMEINFO = (byte) 0x30;
    byte CREATEGAME = (byte) 0x31;
    byte REFRESHGAME = (byte) 0x32;
    byte DECREATEGAME = (byte) 0x33;

    /**
     * TCP Messages.
     */
    byte PINGFROMHOST = (byte) 0x01;
    byte SLOTINFOJOIN = (byte) 0x04;
    byte REJECTJOIN = (byte) 0x05;
    byte PLAYERINFO = (byte) 0x06;
    byte PLAYERLEAVEOTHERS = (byte) 0x07;
    byte GAMELOADEDOTHERS = (byte) 0x08;
    byte SLOTINFO = (byte) 0x09;
    byte COUNTDOWNSTART = (byte) 0x0A;
    byte COUNTDOWNEND = (byte) 0x0B;
    byte INCOMINGACTION = (byte) 0x0C;
    byte CHATFROMHOST = (byte) 0x0F;
    byte STARTLAG = (byte) 0x10;
    byte STOPLAG = (byte) 0x11;
    byte HOSTKICKPLAYER = (byte) 0x1C;
    byte REQJOIN = (byte) 0x1E;
    byte LEAVEGAME = (byte) 0x21;
    byte GAMELOADEDSELF = (byte) 0x23;
    byte OUTGOINGACTION = (byte) 0x26;
    byte OUTGOINGKEEPALIVE = (byte) 0x27;
    byte CHATTOHOST = (byte) 0x28;
    byte DROPREQ = (byte) 0x29;
    byte CHATOTHERS = (byte) 0x34;
    byte PINGFROMOTHERS = (byte) 0x35;
    byte PONGTOOTHERS = (byte) 0x36;
    byte MAPCHECK = (byte) 0x3D;
    byte STARTDOWNLOAD = (byte) 0x3F;
    byte MAPSIZE = (byte) 0x42;
    byte MAPPART = (byte) 0x43;
    byte MAPPARTOK = (byte) 0x44;
    byte MAPPARTNOTOK = (byte) 69;
    byte PONGTOHOST = (byte) 0x46;
    byte INCOMINGACTION2 = (byte) 72;
}
