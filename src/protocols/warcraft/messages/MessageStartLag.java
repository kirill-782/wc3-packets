package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.util.exceptions.IllegalPlayerIDException;
import protocols.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class MessageStartLag implements WC3Message {
    private ArrayList<Byte> laggers = new ArrayList<>();

    public MessageStartLag() {

    }

    public MessageStartLag(ByteBuffer b) throws IllegalPlayerIDException {
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

        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.STARTLAG);
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

    public MessageStartLag setLaggers(ArrayList<Byte> laggers) {
        this.laggers = laggers;
        return this;
    }
}
