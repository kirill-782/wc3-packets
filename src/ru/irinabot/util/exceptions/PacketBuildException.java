package ru.irinabot.util.exceptions;

public class PacketBuildException extends RuntimeException {

    protected PacketBuildException(String description)
    {
        super(description);
    }
}
