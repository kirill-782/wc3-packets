package protocols.warcraft.exceptions;

public class IllegalMapPathException extends WC3Exception {
    public IllegalMapPathException() {
        super("MapPath cannot be empty");
    }
}
