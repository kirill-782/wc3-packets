package protocols.warcraft.messages;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;
import ru.irinabot.protocol.w3gs.messages.W3GSMessageOutgongAction;
import ru.irinabot.util.exceptions.IllegalByteSizeException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class MessageOutgongActionTest {

    @Test
    public void assemble() throws IllegalByteSizeException {
        byte[] bytes = HexBin.decode("f7261200550a6c411b014d2b00007c2b0000");
        ByteBuffer b = ByteBuffer.wrap(bytes);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        W3GSMessageOutgongAction messageOutgongAction = new W3GSMessageOutgongAction(b);

        Assert.assertArrayEquals(bytes, messageOutgongAction.assemble());
    }
}