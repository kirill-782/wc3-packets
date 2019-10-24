package ru.irinabot.socket;

import ru.irinabot.socket.exceptions.ForbiddenHeaderConstant;
import ru.irinabot.socket.exceptions.InvalidPacketSize;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WarcraftLikeSocket {

    private SocketChannel socket;
    private Consumer<WarcraftLikePacket> onPacket;
    private Consumer<Exception> onError;

    private Map<Byte, Object> allowHeaderConstant = new HashMap<>();
    private ByteBuffer sendBuffer;
    private ByteBuffer readBuffer;
    private SelectionKey selectionKey;

    public WarcraftLikeSocket(SocketChannel socket, Selector selector, int outBufferSize, int inBufferSize) throws IOException {
        this.readBuffer = ByteBuffer.allocate(inBufferSize);
        this.readBuffer.limit(4);

        this.sendBuffer = ByteBuffer.allocate(outBufferSize);
        this.sendBuffer.limit(0);

        this.socket = socket;
        this.socket.configureBlocking(false);

        this.selectionKey = socket.register(selector, SelectionKey.OP_READ);
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

        // Переносим данные в начало буффера

        for (int i = 0; this.sendBuffer.hasRemaining(); ++i)
            this.sendBuffer.put(i, this.sendBuffer.get());

        this.sendBuffer.position(0);
        this.sendBuffer.limit(newLimit);
    }

    private void read() throws ForbiddenHeaderConstant, IOException, InvalidPacketSize {
        this.socket.read(this.readBuffer);

        if (this.readBuffer.hasRemaining()) // Прочитано все, что запланировано
        {

            if (this.readBuffer.position() == 4) {
                // Прочитан заголовок

                int packetSize = Short.toUnsignedInt(this.readBuffer.getShort(2));

                if (packetSize > this.readBuffer.capacity())
                    throw new InvalidPacketSize(this.readBuffer.capacity(), packetSize);

                if (this.allowHeaderConstant.get(this.readBuffer.get(0)) == null)
                    throw new ForbiddenHeaderConstant(this.readBuffer.get(0));

                // Ставим метку на конец пакета

                this.readBuffer.limit(Short.toUnsignedInt(this.readBuffer.getShort(2)));
            }

            if (this.readBuffer.position() == Short.toUnsignedInt(this.readBuffer.getShort(2))) {
                // Прочитан весь пакет

                this.onPacket.accept(new WarcraftLikePacket(
                        this.readBuffer.get(0),
                        this.readBuffer.get(1),
                        ByteBuffer.wrap(this.readBuffer.array(), 4, Short.toUnsignedInt(this.readBuffer.getShort(2) ) - 4)
                ) ); // Закостылю позже

                // Настраимваем на чтение заголовка

                this.readBuffer.position(0);
                this.readBuffer.limit(4);
            }
        }
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
}

