package protocols.warcraft.messages;

import protocols.warcraft.WC3Message;
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
            int size = 8 + toPlayerIDs.size() + messageRaw.length;

            if(flag == ChatFlag.MESSAGEEXTRA)
                size += 4;

            ByteBuffer b = ByteBuffer.allocate(size);

            b.put(HEADER);
            b.put(CHATTOHOST);
            b.putShort((short) size);

            // TODO

        }
        return new byte[0];
    }
}
