package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalPlayerIDException;
import protocols.warcraft.exceptions.WC3Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class StartLag implements WC3Message {
    private ArrayList<Byte> laggers = new ArrayList<>();

    public StartLag() {

    }

    public StartLag(ByteBuffer b) throws IllegalPlayerIDException {
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
    public byte[] assemble() throws WC3Exception {
        ByteBuffer b = ByteBuffer.allocate(5 + this.laggers.size());
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(Messages.HEADER);
        b.put(Messages.STARTLAG);
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

    public StartLag setLaggers(ArrayList<Byte> laggers) {
        this.laggers = laggers;
        return this;
    }
}
