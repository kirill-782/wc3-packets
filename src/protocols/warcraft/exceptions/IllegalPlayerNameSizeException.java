package protocols.warcraft.exceptions;

public class IllegalPlayerNameSizeException extends WC3Exception {

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
