package ru.irinabot.protocol.w3gs.entries;

public enum ChatExtraFlag {

    ALL(new byte[] {0,0,0,0}),
    ALLIES(new byte[] {1,0,0,0}),
    OBS(new byte[] {2,0,0,0}),
    PRIVATE(new byte[] {3,0,0,0});


    private byte[] flag;

    ChatExtraFlag(byte[] flag)
    {
        this.flag = flag;
    }

    public byte[] getFlag() {
        return flag;
    }

    public static ChatExtraFlag getInstance(String flag) {

        switch (flag.toUpperCase())
        {
            case "ALL":
                return ALL;
            case "ALLIES":
                return ALLIES;
            case "OBS":
                return OBS;
            case "PRIVATE":
                return PRIVATE;
        }

        throw new IllegalArgumentException("Flag must have valid constant");
    }

    public static ChatExtraFlag getInstance(byte[] flag) {

        switch (flag[0])
        {
            case 0:
                return ALL;
            case 1:
                return ALLIES;
            case 2:
                return OBS;
            case 3:
                return PRIVATE;
        }

        throw new IllegalArgumentException("Flag must have valid number");
    }
}
