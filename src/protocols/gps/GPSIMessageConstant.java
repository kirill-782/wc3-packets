package protocols.gps;

public interface GPSIMessageConstant {

    byte HEADER = (byte) 0xf8;

    byte INIT = 1;
    byte RECONNECT = 2;
    byte ACK = 3;
    byte REJECT = 4;

}
