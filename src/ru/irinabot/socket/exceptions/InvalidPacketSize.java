package ru.irinabot.socket.exceptions;

public class InvalidPacketSize extends Exception {
    int maxSize;
    int gotSize;

    public InvalidPacketSize(int maxSize, int gotSize) {

        super( "Accepted packet size (" + gotSize + ") larger buffer capacity (" + maxSize + ")" );

        this.maxSize = maxSize;
        this.gotSize = gotSize;
    }

    public InvalidPacketSize() {
        super( "Accepted packet size larger buffer capacity" );
    }
}
