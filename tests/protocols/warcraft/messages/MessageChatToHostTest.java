package protocols.warcraft.messages;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;
import protocols.warcraft.exceptions.WC3Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class MessageChatToHostTest {

    @Test
    public void assembleMessage() throws WC3Exception {
        byte[] messageChatToHost = HexBin.decode("f7281400020102031043686174546f486f737400");
        ByteBuffer b = ByteBuffer.wrap(messageChatToHost);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        MessageChatToHost messageChatToHost1 = new MessageChatToHost(b);

        Assert.assertArrayEquals(messageChatToHost, messageChatToHost1.assemble());
    }

    @Test
    public void checkMessage()
    {
        byte[] messageChatToHost = HexBin.decode("f7281400020102031043686174546f486f737400");
        ByteBuffer b = ByteBuffer.wrap(messageChatToHost);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        MessageChatToHost messageChatToHost1 = new MessageChatToHost(b);

        Assert.assertEquals(messageChatToHost1.getMessage(), "ChatToHost");
    }

    @Test
    public void assembleByte() throws WC3Exception {
        byte[] messageChatToHost = HexBin.decode("f728090001ff031101");
        ByteBuffer b = ByteBuffer.wrap(messageChatToHost);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        MessageChatToHost messageChatToHost1 = new MessageChatToHost(b);

        Assert.assertArrayEquals(messageChatToHost, messageChatToHost1.assemble());

    }

    @Test
    public void checkTeam()
    {
        byte[] messageChatToHost = HexBin.decode("f728090001ff031101");
        ByteBuffer b = ByteBuffer.wrap(messageChatToHost);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        MessageChatToHost messageChatToHost1 = new MessageChatToHost(b);

        Assert.assertEquals(messageChatToHost1.getByteValue(), 1);
    }
}