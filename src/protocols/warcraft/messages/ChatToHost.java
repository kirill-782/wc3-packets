package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalMessageSizeException;
import protocols.warcraft.exceptions.IllegalPlayerIDException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.ChatExtraFlag;
import protocols.warcraft.util.ChatFlag;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;


public class ChatToHost implements WC3Message {

    ArrayList<Byte> toPlayerIDs = new ArrayList<>();
    byte fromPlayerID;
    ChatFlag flag = ChatFlag.MESSAGE;
    byte byteValue;
    String message;
    ChatExtraFlag extraFlag = ChatExtraFlag.ALL;

    public ChatToHost() {

    }

    public ChatToHost(ByteBuffer b) {
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        int toPlayerIDsSize = Byte.toUnsignedInt(b.get());

        for (int i = 0; i < toPlayerIDsSize; ++i)
            this.toPlayerIDs.add(b.get());

        this.fromPlayerID = b.get();
        this.flag = ChatFlag.getInstance(b.get());

        switch (flag) {
            case COLOURCHANGE:
                this.byteValue = b.get();
                break;
            case RACECHANGE:
                this.byteValue = b.get();
                break;
            case TEAMCHANGE:
                this.byteValue = b.get();
                break;
            case HANDICAPCHANGE:
                this.byteValue = b.get();
                break;
            case MESSAGE:
                this.message = Util.getNullTremilanedString(b);
                break;
            case MESSAGEEXTRA:
                byte[] extraRaw = new byte[4];
                b.get(extraRaw);
                this.extraFlag = ChatExtraFlag.getInstance(extraRaw);
                this.message = Util.getNullTremilanedString(b);
                break;
            default:
                    throw new IllegalArgumentException();
        }
    }

    @Override
    public byte[] assemble() throws WC3Exception {

        if(flag == ChatFlag.MESSAGE || flag == ChatFlag.MESSAGEEXTRA)
        {
            byte[] messageRaw = this.message.getBytes(UTF_8);

            if( messageRaw.length > 254 )
                throw new IllegalMessageSizeException(messageRaw.length, 254);

            int size = 8 + toPlayerIDs.size() + messageRaw.length;

            if(flag == ChatFlag.MESSAGEEXTRA)
            {
                size += 4;

                if( messageRaw.length > 127 )
                    throw new IllegalMessageSizeException(messageRaw.length, 127);
            }

            ByteBuffer b = ByteBuffer.allocate(size);

            b.put(HEADER);
            b.put(CHATTOHOST);
            b.putShort((short) size);

            b.put((byte) toPlayerIDs.size());
            for (Byte i :
                    toPlayerIDs) {
                if( i > Constants.MAXPLAYERS || i < 1)
                    throw new IllegalPlayerIDException("toPlayerID", i);

                b.put(i);
            }

            b.put(this.fromPlayerID);
            b.put(this.flag.getType());

            if(this.flag == ChatFlag.MESSAGEEXTRA)
                b.put(this.extraFlag.getFlag());

            b.put(messageRaw);
            b.put((byte) 0);

            return b.array();
        }
        else
        {
            int size = 8 + toPlayerIDs.size();

            ByteBuffer b = ByteBuffer.allocate(size);
            b.put(HEADER);
            b.put(CHATTOHOST);
            b.putShort((short) size);

            b.put((byte) toPlayerIDs.size());
            for (Byte i :
                    toPlayerIDs) {
                if( i > Constants.MAXPLAYERS || i < 1)
                    throw new IllegalPlayerIDException("toPlayerID", i);

                b.put(i);
            }

            b.put(this.fromPlayerID);
            b.put(this.flag.getType());
            b.put(this.byteValue);

            return b.array();
        }

    }
}
