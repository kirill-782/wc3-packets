package ru.irinabot.protocol.w3gs.messages;

import ru.irinabot.protocol.w3gs.Constants;
import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.util.exceptions.IllegalMessageSizeException;
import ru.irinabot.util.exceptions.IllegalPlayerIDException;
import ru.irinabot.util.exceptions.PacketBuildException;
import ru.irinabot.protocol.w3gs.entries.ChatExtraFlag;
import ru.irinabot.protocol.w3gs.entries.ChatFlag;
import ru.irinabot.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;


public class W3GSMessageChatToHost implements W3GSMessage {

    private ArrayList<Byte> toPlayerIDs = new ArrayList<>();
    private byte fromPlayerID;
    private ChatFlag flag = ChatFlag.MESSAGE;
    private byte byteValue;
    private String message;
    private ChatExtraFlag extraFlag = ChatExtraFlag.ALL;

    public W3GSMessageChatToHost() {

    }

    public W3GSMessageChatToHost(ByteBuffer b) {

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

            b.put(W3GSMessageConstant.HEADER);
            b.put(W3GSMessageConstant.CHATTOHOST);
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
            b.put(W3GSMessageConstant.HEADER);

            b.put(W3GSMessageConstant.CHATTOHOST);
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

    public W3GSMessageChatToHost setToPlayerIDs(ArrayList<Byte> toPlayerIDs) {
        this.toPlayerIDs = toPlayerIDs;
        return this;
    }

    public byte getFromPlayerID() {
        return fromPlayerID;
    }

    public W3GSMessageChatToHost setFromPlayerID(byte fromPlayerID) {
        this.fromPlayerID = fromPlayerID;
        return this;
    }

    public ChatFlag getFlag() {
        return flag;
    }

    public W3GSMessageChatToHost setFlag(ChatFlag flag) {
        this.flag = flag;
        return this;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public W3GSMessageChatToHost setByteValue(byte byteValue) {
        this.byteValue = byteValue;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public W3GSMessageChatToHost setMessage(String message) {
        this.message = message;
        return this;
    }

    public ChatExtraFlag getExtraFlag() {
        return extraFlag;
    }

    public W3GSMessageChatToHost setExtraFlag(ChatExtraFlag extraFlag) {
        this.extraFlag = extraFlag;
        return this;
    }


}
