package ru.irinabot.util;



import ru.irinabot.protocol.w3gs.Slot;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Util {

    private Util()
    {

    }

    public static byte[] EncodeSlotInfo(ArrayList<Slot> slots, int randomSeed, byte layoutStyle, byte playersCount)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(slots.size() * 9 + 7);

        byteBuffer.put((byte)slots.size());

        for (Slot i:
             slots) {
            byteBuffer.put(i.assemble());
        }

        byteBuffer.putInt(randomSeed);
        byteBuffer.put(layoutStyle);
        byteBuffer.put(playersCount);

        return byteBuffer.array();
    }

    public static byte[] getNullTremilaned(ByteBuffer b)
    {
        int startpos = b.position();
        int length = 0;

        while ( b.get() != 0)
            length++;

        byte[] response = new byte[length];
        b.position(startpos);

        b.get(response);
        b.get();

        return response;
    }

    public static String getNullTremilanedString(ByteBuffer b)
    {
        return new String(getNullTremilaned(b), Charset.forName("UTF-8"));
    }


}
