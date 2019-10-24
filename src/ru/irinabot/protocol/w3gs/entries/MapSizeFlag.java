package ru.irinabot.protocol.w3gs.entries;

public enum MapSizeFlag {
    HAVEMAP((byte) 1),
    DOWNLOAD((byte) 3);

    byte flag;

    MapSizeFlag(byte flag) {
        this.flag = flag;
    }

    public static MapSizeFlag getInstance(byte flag) {
        switch (flag)
        {
            case 1:
                return HAVEMAP;
            case 3:
                return DOWNLOAD;
        }

        throw new IllegalArgumentException("Invalid flag.");
    }

    public byte getFlag() {
        return flag;
    }
}
