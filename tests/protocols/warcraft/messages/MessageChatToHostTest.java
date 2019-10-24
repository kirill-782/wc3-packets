package protocols.warcraft.messages;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;
import ru.irinabot.protocol.w3gs.messages.W3GSMessageChatToHost;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class MessageChatToHostTest {

    @Test
    public void assembleMessage() throws PacketBuildException {
        byte[] messageChatToHost = HexBin.decode("f7281400020102031043686174546f486f737400");
        ByteBuffer b = ByteBuffer.wrap(messageChatToHost);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        W3GSMessageChatToHost messageChatToHost1 = new W3GSMessageChatToHost(b);

        Assert.assertArrayEquals(messageChatToHost, messageChatToHost1.assemble());
    }

    @Test
    public void checkMessage()
    {
        byte[] messageChatToHost = HexBin.decode("f7281400020102031043686174546f486f737400");
        ByteBuffer b = ByteBuffer.wrap(messageChatToHost);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        W3GSMessageChatToHost messageChatToHost1 = new W3GSMessageChatToHost(b);

        Assert.assertEquals(messageChatToHost1.getMessage(), "ChatToHost");
    }

    @Test
    public void assembleByte() throws PacketBuildException {
        byte[] messageChatToHost = HexBin.decode("f728090001ff031101");
        ByteBuffer b = ByteBuffer.wrap(messageChatToHost);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        W3GSMessageChatToHost messageChatToHost1 = new W3GSMessageChatToHost(b);

        Assert.assertArrayEquals(messageChatToHost, messageChatToHost1.assemble());

    }

    @Test
    public void checkTeam()
    {
        byte[] messageChatToHost = HexBin.decode("f728090001ff031101");
        ByteBuffer b = ByteBuffer.wrap(messageChatToHost);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        W3GSMessageChatToHost messageChatToHost1 = new W3GSMessageChatToHost(b);

        Assert.assertEquals(messageChatToHost1.getByteValue(), 1);
    }
}