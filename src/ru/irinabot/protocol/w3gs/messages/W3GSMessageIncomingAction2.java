package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.Action;
import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.zip.CRC32;

public class W3GSMessageIncomingAction2 implements W3GSMessage {

    private ArrayList<Action> actions = new ArrayList<>();
    private byte[] crc32 = null;


    public W3GSMessageIncomingAction2() {

    }

    public W3GSMessageIncomingAction2(ByteBuffer b) {
        b.getShort();

        this.crc32 = new byte[2];
        b.get(this.crc32);

        while (b.hasRemaining()) {
            Action action = new Action();
            byte playerID = b.get();

            int actionSize = Short.toUnsignedInt(b.getShort());
            byte[] data = new byte[actionSize];
            b.get(data);

            actions.add(action);
        }
    }

    @Override
    public byte[] assemble() {
        ByteBuffer b = ByteBuffer.allocate(3000);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.INCOMINGACTION2);
        b.putShort((short) 0); //size

        b.putShort((short) 0);
        b.putShort((short) 2); // crc

        int currentSize = 8;

        for (Action i :
                this.actions) {
            b.put(i.getPlayerID());
            b.putShort((short) i.getAction().length);
            b.put(i.getAction());
            currentSize += 3 + i.getAction().length;
        }

        CRC32 crc = new CRC32();
        byte[] response = new byte[currentSize];

        b.putShort(2, (short) currentSize);
        b.position(0);
        b.get(response, 0, currentSize);

        crc.update(response, 8, currentSize - 8 );

        ByteBuffer tempBuffer = ByteBuffer.allocate(4);
        tempBuffer.order(ByteOrder.LITTLE_ENDIAN);
        tempBuffer.putInt((int) crc.getValue());

        response[6] = tempBuffer.get(0);
        response[7] = tempBuffer.get(1);

        return response;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public W3GSMessageIncomingAction2 setActions(ArrayList<Action> actions) {
        this.actions = actions;
        return this;
    }

    public byte[] getCrc32() {
        return crc32;
    }

    public W3GSMessageIncomingAction2 setCrc32(byte[] crc32) {
        this.crc32 = crc32;
        return this;
    }



}
