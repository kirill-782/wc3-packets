package protocols.socket.exceptions;

public class ForbiddenHeaderConstant extends Exception {
    byte headerConstant;

    public ForbiddenHeaderConstant(byte headerConstant) {
        super("Got invalid header constant " + headerConstant);
        this.headerConstant = headerConstant;
    }

    public ForbiddenHeaderConstant( )
    {
        super("Got invalid header constant");
    }

    public byte getHeaderConstant() {
        return headerConstant;
    }
}
