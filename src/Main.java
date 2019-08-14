import protocols.warcraft.WC3Message;
import protocols.warcraft.messages.*;
import protocols.warcraft.util.RejectReason;
import protocols.warcraft.util.Util;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main2( String[] args) throws Exception
    {
        //ip 185.248.103.124
        //port 6177


        Scanner s = new Scanner(System.in);
//        String ip = "195.88.208.203";
//        int port = 6110;

        String ip = "185.248.103.124";
        int port = 6177;

        InetSocketAddress hostBotAddress = new InetSocketAddress(ip, port);
        SocketChannel remoteBot = SocketChannel.open(hostBotAddress);

        ReqJoin reqJoin = new ReqJoin().setName("JinX").setHostCounterID(0x1018fb6d);
        byte[] reqJoinb = reqJoin.assemble();
        ByteBuffer b = ByteBuffer.allocate(reqJoinb.length);
        b.put(reqJoinb);
        b.position(0);
        remoteBot.write(b);

        ByteBuffer readBuffer = ByteBuffer.allocate(2048);
        readBuffer.limit(4);
        readBuffer.order(ByteOrder.LITTLE_ENDIAN);

        while (true)
        {
            remoteBot.read(readBuffer);

            if(readBuffer.position() == readBuffer.limit())
            {
                // Check complete package

                readBuffer.limit(readBuffer.getShort(2));

                if(readBuffer.position() != readBuffer.limit())
                    continue;
            }
            else
                continue;

            readBuffer.position(0);

            try {
                if(readBuffer.get(1) == WC3Message.SLOTINFOJOIN)
                {
                    SlotInfoJoin slotInfoJoin = new SlotInfoJoin(readBuffer);
                    System.err.println("slotInfoJoin");
                }
                else if(readBuffer.get(1) == WC3Message.CHATFROMHOST)
                {
                    ChatFromHost chatFromHost = new ChatFromHost(readBuffer);
                    System.out.println(chatFromHost.getMessage());


                }
                else if(readBuffer.get(1) == WC3Message.REJECTJOIN)
                {
                    System.out.println("Reject");
                    RejectJoin rejectReason = new RejectJoin(readBuffer);
                    break;
                }
                else if(readBuffer.get(1) == WC3Message.PLAYERINFO)
                {
                    PlayerInfo playerInfo = new PlayerInfo(readBuffer);
                    System.out.println("Player: " + playerInfo.getPlayerName() + " joined");
                }
                else if(readBuffer.get(1) == WC3Message.PINGFROMHOST)
                {
                    PingFromHost pingFromHost = new PingFromHost(readBuffer);
                    ByteBuffer pong = ByteBuffer.allocate(8);
                    byte[] pongRaw = new PongToHost().setPongValue(pingFromHost.getPingValue()).assemble();
                    pong.put(pongRaw);
                    pong.position(0);
                    remoteBot.write(pong);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            readBuffer.position(0);
            readBuffer.limit(4);
        }

    }

    static Selector selector = null;

    public static void main( String[] args) throws Exception
    {
        ByteBuffer b = ByteBuffer.allocate((int) (1024*1024*1024*1.5));



        String ip = "185.248.103.124";
        int port = 6177;

        selector = Selector.open();


        InetSocketAddress hostBotAddress = new InetSocketAddress(ip, port);
        SocketChannel remoteBot = SocketChannel.open();
        remoteBot.configureBlocking(false);
        remoteBot.connect(hostBotAddress);

        SelectionKey key = remoteBot.register(selector, SelectionKey.OP_CONNECT, "asuna");

        System.out.println("11");
        int count = selector.select();
        System.out.println("12");

        SelectionKey key2 = remoteBot.register(selector, SelectionKey.OP_READ, "asuna22");

        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(123);
                    Thread.sleep(500);
                    InetSocketAddress hostBotAddress = new InetSocketAddress(ip, port);
                    SocketChannel remoteBot2 = SocketChannel.open();
                    remoteBot2.configureBlocking(false);
                    remoteBot2.connect(hostBotAddress);
                    remoteBot2.register(selector, SelectionKey.OP_CONNECT, "loi");
                    System.out.println(123321);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };

        new Thread(thread).start();

        count = selector.select();

        return;
    }
}
