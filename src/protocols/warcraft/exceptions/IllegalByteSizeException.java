package protocols.warcraft.exceptions;

public class IllegalByteSizeException extends WC3Exception {

    private String field;
    private int requestSize;

    public IllegalByteSizeException(String field, int requestSize) {
        super(field + " must have size " + requestSize);
        this.field = field;
        this.requestSize = requestSize;
    }

    public String getField() {
        return field;
    }

    public int getRequestSize() {
        return requestSize;
    }
}
