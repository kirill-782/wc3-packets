package protocols.warcraft.entries;

public enum RejectReason {
    FULL(9),
    STARTED(10),
    WRONGPASSWORD(27);

    int reason;

    RejectReason(int reason) {
        this.reason = reason;
    }

    public static RejectReason getInstance(int reason) {

        switch (reason) {
            case 9:
                return FULL;
            case 10:
                return STARTED;
            case 27:
                return WRONGPASSWORD;
        }

        throw new IllegalArgumentException("Invalid reason.");
    }

    public static RejectReason getInstance(String reason) {
        switch (reason.toLowerCase()) {
            case "full":
                return FULL;
            case "started":
                return STARTED;
            case "wrongpassword":
                return WRONGPASSWORD;
        }

        throw new IllegalArgumentException("Invalid reason. Select one: full, started, wrongpassword.");
    }

    public int getReason() {
        return reason;
    }
}
