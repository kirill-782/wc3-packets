package protocols.warcraft.entries;

public enum ChatFlag {
    MESSAGE((byte) 16),
    MESSAGEEXTRA((byte) 32),
    TEAMCHANGE((byte) 17),
    COLOURCHANGE((byte) 18),
    RACECHANGE((byte) 19),
    HANDICAPCHANGE((byte) 20);

    private byte type;

    ChatFlag(byte type)
    {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public static ChatFlag getInstance(byte type) {
        switch (type)
        {
            case 16:
                return MESSAGE;
            case 32:
                return MESSAGEEXTRA;
            case 17:
                return TEAMCHANGE;
            case 18:
                return COLOURCHANGE;
            case 19:
                return RACECHANGE;
            case 20:
                return HANDICAPCHANGE;
        }

        throw new IllegalArgumentException("Type must have number from 0 to 5");
    }

    public static ChatFlag getInstance(String type) {
        switch (type.toUpperCase())
        {
            case "MESSAGE":
                return MESSAGE;
            case "MESSAGEEXTRA":
                return MESSAGEEXTRA;
            case "TEAMCHANGE":
                return TEAMCHANGE;
            case "COLOURCHANGE":
                return COLOURCHANGE;
            case "RACECHANGE":
                return RACECHANGE;
            case "HANDICAPCHANGE":
                return HANDICAPCHANGE;
        }

        throw new IllegalArgumentException("Type must have valid constant");
    }
}
