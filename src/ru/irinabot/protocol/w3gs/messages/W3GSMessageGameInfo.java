package ru.irinabot.protocol.w3gs.messages;

import com.sun.deploy.ui.UITextArea;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import ru.irinabot.protocol.w3gs.StatString;
import ru.irinabot.protocol.w3gs.W3GSMessage;
import ru.irinabot.protocol.w3gs.W3GSMessageConstant;
import ru.irinabot.util.Util;
import ru.irinabot.util.exceptions.IllegalProduct;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class W3GSMessageGameInfo implements W3GSMessage {

    private byte[] product;
    private byte version;
    private int hostCounter;
    private int entryKey;
    private String gameName;
    private StatString statString;
    private int slotsTotal;
    private byte[] mapGameType;
    private int slotsOpen;
    private int upTime;
    private int port;

    public W3GSMessageGameInfo() {

    }

    public W3GSMessageGameInfo(ByteBuffer b) {
        this.product = new byte[4];
        b.get(this.product);

        if (!Arrays.equals(this.product, W3GSMessageConstant.ROC) && !Arrays.equals(this.product, W3GSMessageConstant.TFT))
            throw new IllegalProduct(HexBin.encode(this.product));

        this.version = b.get();
        b.position(b.position() + 3); // version continue

        this.hostCounter = b.getInt();
        this.entryKey = b.getInt();

        byte[] rawGameName = Util.getNullTremilaned(b);
        this.gameName = new String(rawGameName, StandardCharsets.UTF_8);

        // Throw game name

        b.get(); // null byte -_-

        this.statString = new StatString(b, false);
        this.slotsTotal = b.getInt();

        this.mapGameType = new byte[4];
        b.get(this.mapGameType);

        b.getInt(); // Unknown

        this.slotsOpen = b.getInt();
        this.upTime = b.getInt();
        this.port = Short.toUnsignedInt(b.getShort());
    }

    @Override
    public byte[] assemble() throws PacketBuildException {
        // Throws

        byte[] rawStatString = statString.assembly();
        byte[] rawGameName = this.gameName.getBytes(StandardCharsets.UTF_8);

        ByteBuffer b = ByteBuffer.allocate(44 + rawGameName.length + rawStatString.length);
        b.order(ByteOrder.LITTLE_ENDIAN);

        b.put(W3GSMessageConstant.HEADER);
        b.put(W3GSMessageConstant.GAMEINFO);
        b.putShort((short) (44 + rawGameName.length + rawStatString.length));

        b.put(this.product);
        b.put(new byte[]{this.version, 0, 0, 0});
        b.putInt(this.hostCounter);
        b.putInt(this.entryKey);

        b.put(rawGameName);
        b.put((byte) 0);

        b.put((byte) 0);

        b.put(rawStatString);
        b.putInt(this.slotsTotal);
        b.put(this.mapGameType);

        b.put(new byte[]{1, 0, 0, 0});

        b.putInt(this.slotsOpen);
        b.putInt(this.upTime);
        b.putShort((short) this.port);

        return b.array();

    }
}
