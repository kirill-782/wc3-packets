package protocols.warcraft.util;

public enum PlayerLeaveCode {
    DISCONNECT(1),
    LOST(7),
    LOSTBUILDINGS(8),
    WON(9),
    DRAW(10),
    OBSERVER(11),
    LOBBY(13),
    GPROXY(100);

    private int code;

    PlayerLeaveCode(int code) {
        this.code = code;
    }

    public static PlayerLeaveCode getInstance(int code) {
        switch (code)
        {
            case 1:
                return DISCONNECT;
            case 7:
                return LOST;
            case 8:
                return LOSTBUILDINGS;
            case 9:
                return WON;
            case 10:
                return DRAW;
            case 11:
                return OBSERVER;
            case 13:
                return LOBBY;
            case 100:
                return GPROXY;
        }

        throw new IllegalArgumentException("Code is invalid");
    }

    public static PlayerLeaveCode getInstance(String code) {
        switch (code.toUpperCase())
        {
            case "DISCONNECT":
                return DISCONNECT;
            case "LOST":
                return LOST;
            case "LOSTBUILDINGS":
                return LOSTBUILDINGS;
            case "WON":
                return WON;
            case "DRAW":
                return DRAW;
            case "OBSERVER":
                return OBSERVER;
            case "LOBBY":
                return LOBBY;
            case "GPROXY":
                return GPROXY;
        }

        throw new IllegalArgumentException("Code is invalid");
    }

    public int getCode() {
        return code;
    }
}


