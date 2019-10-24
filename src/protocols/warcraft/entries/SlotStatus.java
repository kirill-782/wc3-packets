package protocols.warcraft.entries;

public enum SlotStatus {
    OPEN((byte) 0),
    CLOSED((byte) 1),
    OCCUPIED((byte) 2);

    private byte status;

    SlotStatus(byte status) {
        this.status = status;
    }

    public static SlotStatus getInstance(byte type) {
        switch (type) {
            case 0:
                return SlotStatus.OPEN;
            case 1:
                return SlotStatus.CLOSED;
            case 2:
                return SlotStatus.OCCUPIED;
        }

        throw new IllegalArgumentException("Type incorrect. Valid values: 0,1,2");
    }

    static SlotStatus getInstance(String type) {
        switch (type.toLowerCase()) {
            case "open":
                return SlotStatus.OPEN;
            case "closed":
                return SlotStatus.CLOSED;
            case "occupied":
                return SlotStatus.OCCUPIED;
        }

        throw new IllegalArgumentException("Type incorrect. Valid values: open, closed, occupied");
    }

    public byte getStatus() {
        return status;
    }

}
