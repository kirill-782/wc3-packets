package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.util.exceptions.IllegalMessageSizeException;
import protocols.util.exceptions.IllegalPlayerIDException;
import protocols.util.exceptions.PacketBuildException;
import protocols.warcraft.entries.ChatExtraFlag;
import protocols.warcraft.entries.ChatFlag;
import protocols.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;


public class MessageChatToHost implements WC3Message {

    private ArrayList<Byte> toPlayerIDs = new ArrayList<>();
    private byte fromPlayerID;
    private ChatFlag flag = ChatFlag.MESSAGE;
    private byte byteValue;
    private String message;
    private ChatExtraFlag extraFlag = ChatExtraFlag.ALL;

    public MessageChatToHost() {

    }

    public MessageChatToHost(ByteBuffer b) {

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
    public byte[] assemble() throws PacketBuildException {

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
            b.order(ByteOrder.LITTLE_ENDIAN);

            b.put(WC3MessageConstant.HEADER);
            b.put(WC3MessageConstant.CHATTOHOST);
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
            b.order(ByteOrder.LITTLE_ENDIAN);
            b.put(WC3MessageConstant.HEADER);

            b.put(WC3MessageConstant.CHATTOHOST);
            b.putShort((short) size);

            b.put((byte) toPlayerIDs.size());
            for (Byte i :
                    toPlayerIDs) {
                // ToPlayerIDS FF allowed here
//                if( i > Constants.MAXPLAYERS || i < 1)
//                    throw new IllegalPlayerIDException("toPlayerID", i);

                b.put(i);
            }

            b.put(this.fromPlayerID);
            b.put(this.flag.getType());
            b.put(this.byteValue);

            return b.array();
        }

    }

    public ArrayList<Byte> getToPlayerIDs() {
        return toPlayerIDs;
    }

    public MessageChatToHost setToPlayerIDs(ArrayList<Byte> toPlayerIDs) {
        this.toPlayerIDs = toPlayerIDs;
        return this;
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public MessageChatToHost setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }

    public ChatFlag getFlag() {
        return flag;
    }

    public MessageChatToHost setFlag(ChatFlag flag) {
        this.flag = flag;
        return this;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public MessageChatToHost setByteValue(byte byteValue) {
        this.byteValue = byteValue;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageChatToHost setMessage(String message) {
        this.message = message;
        return this;
    }

    public ChatExtraFlag getExtraFlag() {
        return extraFlag;
    }

    public MessageChatToHost setExtraFlag(ChatExtraFlag extraFlag) {
        this.extraFlag = extraFlag;
        return this;
    }


}
