package protocols.warcraft.exceptions;

public class IllegalPlayerNameSizeException extends WC3Exception {

    public IllegalPlayerNameSizeException()
    {
        super("PlayerName size too long");
    }
}
