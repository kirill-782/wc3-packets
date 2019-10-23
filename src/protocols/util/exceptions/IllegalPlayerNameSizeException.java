package protocols.util.exceptions;

public class IllegalPlayerNameSizeException extends PacketBuildException {

    private String field;

    public IllegalPlayerNameSizeException(String field)
    {
        super( field + " size too long");
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
