package io.github.andyalvarezdev.mmocore;

import static java.lang.Byte.toUnsignedInt;

public class PacketBuffer {
    byte[] data;
    int index = Client.HEADER_SIZE;

    private PacketBuffer() {
        // no direct instances
    }

    static PacketBuffer of(int size) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.data = new byte[size];
        return buffer;
    }

    static PacketBuffer of(final byte[] data, int dataIndex) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.data = data;
        buffer.index = dataIndex;
        return buffer;
    }

    /**
     * Reads raw <B>byte</B> from the buffer
     * @return byte read
     */
    public byte read() {
        return data[index++];
    }

    /**
     * Reads <B>short</B> from the buffer. <BR>
     * 16bit integer (00 00)
     * @return short read
     */
    public short readShort()  {
        return (short) (readUnsigned() | readUnsigned() << 8);
    }

    private int readUnsigned() {
        return toUnsignedInt(read());
    }

    /**
     * Reads <B>int</B> from the buffer. <BR>
     * 32bit integer (00 00 00 00)
     * @return int read
     */
    public final int readInt() {
        return readUnsigned()  |
                            readUnsigned() << 8  |
                            readUnsigned() << 16 |
                            readUnsigned() << 24;

    }

    public byte[] expose() {
        return data;
    }

    public int remaining() {
        return data.length - index;
    }
}