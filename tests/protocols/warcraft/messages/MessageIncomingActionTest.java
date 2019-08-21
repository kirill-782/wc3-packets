package protocols.warcraft.messages;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class MessageIncomingActionTest {

    @Test
    public void assemble() {


        byte[] bytes = HexBin.decode("f70ca7004b009c60039c0062428c000096e500003900000062718c000048e6000030000000626b8c0000c1e7000025000000627c6e0000dfe700002100000062268d0000d0e800001800000062a86500007beb00000f00000062cd8d000091e900001400000062a28e000013ed00000d00000062378f000078f100000800000062588f000021f200000700000062ee8f000025f600000100000062f88f000062f6000001000000");
        ByteBuffer b = ByteBuffer.wrap(bytes);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        MessageIncomingAction messageIncomingAction = new MessageIncomingAction(b);

        Assert.assertArrayEquals(bytes, messageIncomingAction.assemble());
    }
}