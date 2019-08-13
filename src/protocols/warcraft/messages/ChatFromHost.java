package protocols.warcraft.messages;

import protocols.warcraft.util.ChatExtraFlag;
import protocols.warcraft.util.ChatFlag;
import protocols.warcraft.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChatFromHost {

    private ArrayList<Byte> toPlayerIDs = new ArrayList<>();
    private byte fromPlayerID = 0;
    private ChatFlag flag = ChatFlag.MESSAGE;
    private ChatExtraFlag flagExtra = null;
    private String message = "";

    public ChatFromHost() {

    }

    public ChatFromHost(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.position(4);

        int toPlayerIDsSize = Byte.toUnsignedInt(b.get());

        for (int i = 0; i < toPlayerIDsSize; ++i)
            this.toPlayerIDs.add(b.get());

        this.fromPlayerID = b.get();
        this.flag = ChatFlag.getInstance(b.get());

        if(this.flag == ChatFlag.MESSAGEEXTRA)
        {
            byte[] rawExtra = new byte[4];
            b.get(rawExtra);
            this.flagExtra = ChatExtraFlag.getInstance(rawExtra);
        }

        byte[] messageByte = Util.getNullTremilaned(b);

        this.message = new String(messageByte, StandardCharsets.UTF_8);
    }

    public ArrayList<Byte> getToPlayerIDs() {
        return toPlayerIDs;
    }

    public ChatFromHost setToPlayerIDs(ArrayList<Byte> toPlayerIDs) {
        this.toPlayerIDs = toPlayerIDs;
        return this;
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public ChatFromHost setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }

    public ChatFlag getFlag() {
        return flag;
    }

    public ChatFromHost setFlag(ChatFlag flag) {
        this.flag = flag;
        return this;
    }

    public ChatExtraFlag getFlagExtra() {
        return flagExtra;
    }

    public ChatFromHost setFlagExtra(ChatExtraFlag flagExtra) {
        this.flagExtra = flagExtra;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ChatFromHost setMessage(String message) {
        this.message = message;
        return this;
    }
}
