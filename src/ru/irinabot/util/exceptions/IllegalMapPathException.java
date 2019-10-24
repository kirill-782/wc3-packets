package ru.irinabot.util.exceptions;

public class IllegalMapPathException extends PacketBuildException {
    public IllegalMapPathException() {
        super("MapPath cannot be empty");
    }
}
