package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.Constants;
import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalPlayerIDException;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class W3GSMessageStartLag implements W3GSMessage {
    private ArrayList<Byte> laggers = new ArrayList<>();

    public W3GSMessageStartLag() {

    }

    public W3GSMessageStartLag(ByteBuffer b) throws IllegalPlayerIDException {
        int numLaggers = Byte.toUnsignedInt(b.get());

        for (int i = 0; i < numLaggers; ++i)
        {
            byte playerID = b.get();

            if (playerID > Constants.MAXPLAYERS || playerID < 1)
                throw new IllegalPlayerIDException("playerID", playerID);

            this.laggers.add(playerID);
        }
    }


    @Override
    public byte[] assemble() throws PacketBuildException {
        ByteBuffer b = ByteBuffer.allocate(5 + this.laggers.size());
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.STARTLAG);
        b.putShort((short) (5 + this.laggers.size()));

        b.put((byte) this.laggers.size());

        for (Byte i:
            this.laggers ) {

            if (i > Constants.MAXPLAYERS || i < 1)
                throw new IllegalPlayerIDException("playerID", i);

            b.put(i);
        }

        return b.array();
    }

    public ArrayList<Byte> getLaggers() {
        return laggers;
    }

    public W3GSMessageStartLag setLaggers(ArrayList<Byte> laggers) {
        this.laggers = laggers;
        return this;
    }
}
