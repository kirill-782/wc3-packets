package protocols.warcraft.messages;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;
import ru.irinabot.protocol.w3gs.messages.W3GSMessagePlayerInfo;
import ru.irinabot.util.exceptions.IllegalPlayerNameSizeException;
import ru.irinabot.util.exceptions.PacketBuildException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class MessagePlayerInfoTest {


    //f709670061000a04640200000001016405640200000101016406640200000201016407640200000301016403640200010401016400ff0000010501016400ff0000010601016400ff0000010701016400ff0201000801016400ff02010109010164c494158b030a

    @Test
    public void assemble() throws PacketBuildException {
        byte[] need = HexBin.decode("f7063b0000000000077c6366666666613530304c655379410001000200000000000000000000000000000002000000000000000000000000000000");

        ByteBuffer b = ByteBuffer.wrap(need);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        W3GSMessagePlayerInfo messagePlayerInfo = new W3GSMessagePlayerInfo(b);

        Assert.assertArrayEquals(need, messagePlayerInfo.assemble());
    }

    @Test
    public void checkPlayerInfo() throws IllegalPlayerNameSizeException {
        byte[] need = HexBin.decode("f7063b0000000000077c6366666666613530304c655379410001000200000000000000000000000000000002000000000000000000000000000000");

        ByteBuffer b = ByteBuffer.wrap(need);
        b.position(4);
        b.order(ByteOrder.LITTLE_ENDIAN);

        W3GSMessagePlayerInfo messagePlayerInfo = new W3GSMessagePlayerInfo(b);

        Assert.assertEquals(messagePlayerInfo.getPlayerName(), "|cffffa500LeSyA");
    }
}