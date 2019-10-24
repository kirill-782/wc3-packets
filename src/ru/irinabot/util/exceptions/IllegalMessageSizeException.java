package ru.irinabot.util.exceptions;

public class IllegalMessageSizeException extends PacketBuildException {
    private int messageSize;
    private int allowSize;

    public IllegalMessageSizeException(int messageSize, int allowSize) {
        super("Message to long. Allow size: " + allowSize + ". Got size: " + messageSize);
        this.messageSize = messageSize;
        this.allowSize = allowSize;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public int getAllowSize() {
        return allowSize;
    }
}
