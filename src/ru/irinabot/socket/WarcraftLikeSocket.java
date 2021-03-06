package ru.irinabot.socket;

import ru.irinabot.socket.exceptions.ForbiddenHeaderConstant;
import ru.irinabot.socket.exceptions.InvalidPacketSize;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.function.Consumer;

public class WarcraftLikeSocket implements Closeable {

    private SocketChannel socket;
    private Consumer<WarcraftLikePacket> onPacket;
    private Consumer<Exception> onError;
    private Runnable onClose;

    private Map<Byte, Object> allowHeaderConstant = new HashMap<>();
    private ByteBuffer sendBuffer;
    private ByteBuffer readBuffer;
    private SelectionKey selectionKey;

    private Queue<WarcraftLikePacket> packetQueue = new PriorityQueue<>();

    private long lastPacketRecvTime = 0;
    private long connectionTime = System.currentTimeMillis();

    public WarcraftLikeSocket(SocketChannel socket, int outBufferSize, int inBufferSize) throws IOException {
        this.readBuffer = ByteBuffer.allocate(inBufferSize);
        this.readBuffer.limit(4);
        this.readBuffer.order(ByteOrder.LITTLE_ENDIAN);

        this.sendBuffer = ByteBuffer.allocate(outBufferSize);
        this.sendBuffer.limit(0);
        this.sendBuffer.order(ByteOrder.LITTLE_ENDIAN);

        this.socket = socket;
        this.socket.configureBlocking(false);
    }

    public SelectionKey setSelector( Selector selector ) throws ClosedChannelException {

        if(this.selectionKey != null)
            this.selectionKey.cancel();

        this.selectionKey = socket.register(selector, SelectionKey.OP_READ, this);


        return this.selectionKey;
    }

    public void clearPacketBuffer( )
    {
        this.packetQueue.clear();
    }

    public void onSelected() {
        try {
            if (this.selectionKey.isValid() && this.selectionKey.isWritable())
                send();
            else if (this.selectionKey.isValid() && this.selectionKey.isReadable())
                read();
        } catch (Exception e) {
            this.onError.accept(e);
        }
    }

    public void send(ByteBuffer b) throws IOException {

        int currentPosition = this.sendBuffer.position();

        this.sendBuffer.limit(b.remaining() + this.sendBuffer.limit());
        this.sendBuffer.put(b);
        this.sendBuffer.position(currentPosition);

        send();
    }

    public void send(byte[] data) throws IOException {
        int currentPosition = this.sendBuffer.position();

        this.sendBuffer.limit(data.length + this.sendBuffer.limit());
        this.sendBuffer.put(data);
        this.sendBuffer.position(currentPosition);

        send();
    }

    private void send() throws IOException {
        this.socket.write(this.sendBuffer);
        flushSendedData();

        if (!this.sendBuffer.hasRemaining())
            this.selectionKey.interestOps(SelectionKey.OP_READ);
        else
            this.selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    private void flushSendedData() {
        int newLimit = this.sendBuffer.limit() - this.sendBuffer.position();

        // ?????????????????? ???????????? ?? ???????????? ??????????????

        for (int i = 0; this.sendBuffer.hasRemaining(); ++i)
            this.sendBuffer.put(i, this.sendBuffer.get());

        this.sendBuffer.position(0);
        this.sendBuffer.limit(newLimit);
    }

    private void read() throws ForbiddenHeaderConstant, IOException, InvalidPacketSize {

        int readResult = 0;

        while ( ( readResult = this.socket.read(this.readBuffer) ) > 0)
        {
            if (!this.readBuffer.hasRemaining()) // ?????????????????? ??????, ?????? ??????????????????????????
            {
                if (this.readBuffer.position() == 4) {
                    // ???????????????? ??????????????????

                    int packetSize = Short.toUnsignedInt(this.readBuffer.getShort(2));

                    if (packetSize > this.readBuffer.capacity())
                        throw new InvalidPacketSize(this.readBuffer.capacity(), packetSize);

                    if (this.allowHeaderConstant.get(this.readBuffer.get(0)) == null)
                        throw new ForbiddenHeaderConstant(this.readBuffer.get(0));

                    // ???????????? ?????????? ???? ?????????? ????????????

                    this.readBuffer.limit(packetSize);
                }

                if (this.readBuffer.position() == Short.toUnsignedInt(this.readBuffer.getShort(2))) {
                    // ???????????????? ???????? ??????????

                    this.lastPacketRecvTime = System.currentTimeMillis();

                    WarcraftLikePacket packet = new WarcraftLikePacket(
                            this.readBuffer.get(0),
                            this.readBuffer.get(1),
                            ByteBuffer.wrap(this.readBuffer.array(), 4, Short.toUnsignedInt(this.readBuffer.getShort(2) ) - 4)
                    );

                    if(this.onPacket != null && this.packetQueue.isEmpty())
                        this.onPacket.accept( packet );
                    else
                        this.packetQueue.add(packet);

                    // ???????????????????????? ???? ???????????? ??????????????????

                    this.readBuffer.position(0);
                    this.readBuffer.limit(4);
                }
            }
        }

        if(readResult == -1 && this.onClose != null)
            this.onClose.run( );
    }

    public void releaseQueue( )
    {
        while (!this.packetQueue.isEmpty())
        {
            WarcraftLikePacket packet = this.packetQueue.remove();
            this.onPacket.accept(packet);
        }
    }

    public boolean hasConnected( )
    {
        return this.socket.isConnected();
    }

    @Override
    public void close( ) throws IOException {
        this.socket.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarcraftLikeSocket that = (WarcraftLikeSocket) o;
        return Objects.equals(socket, that.socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket);
    }

    public WarcraftLikeSocket allowHeaderConstant(byte headerConstant) {
        this.allowHeaderConstant.put(headerConstant, true);
        return this;
    }

    public WarcraftLikeSocket disallowHeaderConstant(byte headerConstant) {
        this.allowHeaderConstant.remove(headerConstant);
        return this;
    }

    public WarcraftLikeSocket onError(Consumer<Exception> onError) {
        this.onError = onError;
        return this;
    }

    public WarcraftLikeSocket onPacket(Consumer<WarcraftLikePacket> onPacket) {
        this.onPacket = onPacket;
        return this;
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public long getLastPacketRecvTime() {
        return lastPacketRecvTime;
    }

    public long getConnectionTime() {
        return connectionTime;
    }

    public WarcraftLikeSocket setOnClose(Runnable onClose) {
        this.onClose = onClose;
        return this;
    }
}

