package protocols.warcraft.messages;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.irinabot.protocol.w3gs.messages.W3GSMessageChatFromHost;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class MessageChatFromHostTest {

    byte[] chatFromHostData;

    @Before
    public void init()
    {
        this.chatFromHostData = HexBin.decode("f70f7700020304042000000000d09fd0bed0b6d0b0d0bbd183d0b9d181d182d0b020d0bed0b6d0b8d0b4d0b0d0b9d182d0b520d0bfd0b5d180d0b5d0bfd0bed0b4d0bad0bbd18ed187d0b5d0bdd0b8d18f202835323520d181d0b5d0bad183d0bdd0b420d0bed181d182d0b0d0bbd0bed181d18c292e00");
    }

    @After
    public void clear()
    {
        this.chatFromHostData = null;
    }

    @Test
    public void assemble() throws PacketBuildException {
        ByteBuffer b = ByteBuffer.wrap(this.chatFromHostData);
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.position(4);
        W3GSMessageChatFromHost messageChatFromHost = new W3GSMessageChatFromHost (b);
        Assert.assertArrayEquals(this.chatFromHostData, messageChatFromHost.assemble());
    }


}