package protocols.warcraft.messages;

import protocols.warcraft.Action;
import protocols.warcraft.Messages;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.WC3Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.zip.CRC32;

public class IncomingAction2 implements WC3Message {

    private ArrayList<Action> actions = new ArrayList<>();
    private byte[] crc32 = null;



    public IncomingAction2( )
    {

    }

    public IncomingAction2(ByteBuffer b)
    {
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.position(6);


        this.crc32 = new byte[2];
        b.get(this.crc32);

        while(b.hasRemaining())
        {
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

        b.put(Messages.HEADER);
        b.put(Messages.INCOMINGACTION2);
        b.putShort((short) 1); //size

        b.putShort((short) 0);
        b.putShort((short) 2); // crc

        int currentSize = 8;

        for (Action i:
                this.actions) {
            b.put(i.getPlayerID());
            b.putShort((short) i.getAction().length);
            b.put(i.getAction());
            currentSize += 1 +i.getAction().length;
        }

        CRC32 crc = new CRC32();
        byte[] response = new byte[currentSize];

        b.putShort(2, (short) currentSize);
        b.get(response, 0, currentSize);

        crc.update(response, 8, currentSize - 8 );

        ByteBuffer tempByffer = ByteBuffer.allocate(8);
        b.putLong(crc.getValue());

        response[6] = tempByffer.get(6);
        response[7] = tempByffer.get(7);

        return response;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public IncomingAction2 setActions(ArrayList<Action> actions) {
        this.actions = actions;
        return this;
    }

    public byte[] getCrc32() {
        return crc32;
    }

    public IncomingAction2 setCrc32(byte[] crc32) {
        this.crc32 = crc32;
        return this;
    }

}
