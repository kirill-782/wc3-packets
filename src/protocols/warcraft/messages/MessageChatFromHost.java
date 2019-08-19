package protocols.warcraft.messages;

import protocols.warcraft.Constants;
import protocols.warcraft.WC3MessageConstant;
import protocols.warcraft.WC3Message;
import protocols.warcraft.exceptions.IllegalMessageSizeException;
import protocols.warcraft.exceptions.IllegalPlayerIDException;
import protocols.warcraft.exceptions.WC3Exception;
import protocols.warcraft.util.ChatExtraFlag;
import protocols.warcraft.util.ChatFlag;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MessageChatFromHost implements WC3Message {

    private ArrayList<Byte> toPlayerIDs = new ArrayList<>();
    private byte fromPlayerID = 0;
    private ChatFlag flag = ChatFlag.MESSAGE;
    private ChatExtraFlag flagExtra = null;
    private String message = "";

    public MessageChatFromHost() {

    }

    public MessageChatFromHost(ByteBuffer b) throws WC3Exception {

        int toPlayerIDsSize = Byte.toUnsignedInt(b.get());

        for (int i = 0; i < toPlayerIDsSize; ++i) {
            byte toPlayerID = b.get();

            if (toPlayerID > Constants.MAXPLAYERS || toPlayerID < 1)
                throw new IllegalPlayerIDException("toPlayerID", toPlayerID);

            this.toPlayerIDs.add(toPlayerID);
        }

        this.fromPlayerID = b.get();

        if (this.fromPlayerID > Constants.MAXPLAYERS || this.fromPlayerID < 1)
            throw new IllegalPlayerIDException("fromPlayerID", this.fromPlayerID);

        this.flag = ChatFlag.getInstance(b.get());

        if (this.flag == ChatFlag.MESSAGEEXTRA) {
            byte[] rawExtra = new byte[4];
            b.get(rawExtra);
            this.flagExtra = ChatExtraFlag.getInstance(rawExtra);
        }

        byte[] messageRaw = Util.getNullTremilaned(b);

        if (messageRaw.length > 254)
            throw new IllegalMessageSizeException(messageRaw.length, 254);
        else if (messageRaw.length > 127 && this.flag == ChatFlag.MESSAGEEXTRA)
            throw new IllegalMessageSizeException(messageRaw.length, 127);


        this.message = new String(messageRaw, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] assemble() throws WC3Exception {

        byte[] messageRaw = this.message.getBytes(StandardCharsets.UTF_8);

        if (messageRaw.length > 254)
            throw new IllegalMessageSizeException(messageRaw.length, 254);
        else if (messageRaw.length > 127 && this.flag == ChatFlag.MESSAGEEXTRA)
            throw new IllegalMessageSizeException(messageRaw.length, 127);

        if (this.fromPlayerID > Constants.MAXPLAYERS || this.fromPlayerID < 1)
            throw new IllegalPlayerIDException("fromPlayerID", this.fromPlayerID);

        int size = 8 + this.toPlayerIDs.size() + messageRaw.length;

        if( this.flag == ChatFlag.MESSAGEEXTRA )
            size += 4;

        ByteBuffer b = ByteBuffer.allocate(size);
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.put(WC3MessageConstant.HEADER);
        b.put(WC3MessageConstant.CHATFROMHOST);
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
            b.put(this.flagExtra.getFlag());

        b.put(messageRaw);
        b.put((byte) 0);

        return b.array();

    }

    public ArrayList<Byte> getToPlayerIDs() {
        return toPlayerIDs;
    }

    public MessageChatFromHost setToPlayerIDs(ArrayList<Byte> toPlayerIDs) {
        this.toPlayerIDs = toPlayerIDs;
        return this;
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public MessageChatFromHost setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }

    public ChatFlag getFlag() {
        return flag;
    }

    public MessageChatFromHost setFlag(ChatFlag flag) {
        this.flag = flag;
        return this;
    }

    public ChatExtraFlag getFlagExtra() {
        return flagExtra;
    }

    public MessageChatFromHost setFlagExtra(ChatExtraFlag flagExtra) {
        this.flagExtra = flagExtra;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageChatFromHost setMessage(String message) {
        this.message = message;
        return this;
    }


}
