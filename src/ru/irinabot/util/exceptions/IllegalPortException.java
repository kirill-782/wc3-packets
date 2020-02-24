package ru.irinabot.util.exceptions;

public class IllegalPortException extends PacketBuildException {

    public IllegalPortException(int port)
    {
        super("Port " + port + " not valid");
    }
}
