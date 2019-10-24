package ru.irinabot.protocol.gps.entries;

public enum RejectReconnectReason {

    INVALID(1),
    NOTFOUND(2),
    DELETED(3);

    private int reason;

    RejectReconnectReason(int reason)
    {
        this.reason = reason;
    }

    public int getReason() {
        return reason;
    }

    public static RejectReconnectReason getInstance(int reason) {
        switch (reason) {
            case 1:
                return INVALID;
            case 2:
                return NOTFOUND;
            case 3:
                return DELETED;
        }

        throw new IllegalArgumentException("Flag must have valid number");
    }

    public static RejectReconnectReason getInstance(String s) {
        if(s.equalsIgnoreCase("INVALID"))
            return INVALID;
        else if(s.equalsIgnoreCase("NOTFOUND"))
            return NOTFOUND;
        else if( s.equalsIgnoreCase("DELETED") )
            return DELETED;

        throw new IllegalArgumentException("Flag must have valid constant");
    }
}
