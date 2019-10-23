package protocols.util.exceptions;

public class IllegalMapPathException extends PacketBuildException {
    public IllegalMapPathException() {
        super("MapPath cannot be empty");
    }
}
