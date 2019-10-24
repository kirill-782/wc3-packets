package protocols.warcraft.entries;

public enum SlotComputerType {
    EASY((byte)0),
    NORMAL((byte)1),
    HARD((byte)2);

    private byte type;

    SlotComputerType(byte type) {
        this.type = type;
    }

    public static SlotComputerType getInstance(byte type) {
        switch (type) {
            case 0:
                return SlotComputerType.EASY;
            case 1:
                return SlotComputerType.NORMAL;
            case 2:
                return SlotComputerType.HARD;
        }

        throw new IllegalArgumentException("Type incorrect");
    }

    static SlotComputerType getInstance(String type) {
        switch (type.toLowerCase()) {
            case "easy":
                return SlotComputerType.EASY;
            case "normal":
                return SlotComputerType.NORMAL;
            case "hard":
                return SlotComputerType.HARD;
        }

        throw new IllegalArgumentException("Type incorrect. Valid values: easy, normal, hard");
    }

    public byte toByte() {
        return type;
    }

}
