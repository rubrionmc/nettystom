// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import io.netty.buffer.ByteBuf;
// Import of a required class
import io.netty.buffer.ByteBufAllocator;
// Import of a required class
import io.netty.buffer.Unpooled;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.utils.ObjectPool;
// Import of a required class
import net.minestom.server.utils.nbt.BinaryTagReader;
// Import of a required class
import net.minestom.server.utils.nbt.BinaryTagWriter;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import javax.crypto.Cipher;
// Import of a required class
import javax.crypto.ShortBufferException;
// Import of a required class
import java.io.*;
// Import of a required class
import java.lang.foreign.MemorySegment;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.zip.DataFormatException;
// Import of a required class
import java.util.zip.Deflater;
// Import of a required class
import java.util.zip.Inflater;

// Type declaration (class/interface/enum/record)
final class NetworkBufferImpl implements NetworkBuffer {

    // Code statement
    private ByteBuf buf;

    // Assigns a value
    private static final ByteBuf DUMMY_BUF = Unpooled.EMPTY_BUFFER;

    // Code statement
    private long readIndex;
    // Code statement
    private long writeIndex;
    // Code statement
    private boolean readOnly;

    // Code statement
    private @Nullable BinaryTagWriter nbtWriter;
    // Code statement
    private @Nullable BinaryTagReader nbtReader;

    // Code statement
    final @Nullable AutoResize autoResize;
    // Annotation for the following element
    @Nullable Registries registries;

    // Code statement
    NetworkBufferImpl(ByteBuf buf,
                      // Code statement
                      long readIndex, long writeIndex,
                      // Annotation for the following element
                      @Nullable AutoResize autoResize,
                      // Annotation for the following element
                      @Nullable Registries registries) {
        // Access to the current/parent object
        this.buf = buf;
        // Access to the current/parent object
        this.readIndex = readIndex;
        // Access to the current/parent object
        this.writeIndex = writeIndex;
        // Access to the current/parent object
        this.autoResize = autoResize;
        // Access to the current/parent object
        this.registries = registries;
    // End of a block/expression
    }

    // Start of a method/block
    private boolean isDummy() {
        // Returns a value to the caller
        return buf == DUMMY_BUF;
    // End of a block/expression
    }

    // Start of a method/block
    void assertDummy() {
        // Branch: checks a condition
        if (isDummy()) throw new UnsupportedOperationException("Buffer is a dummy buffer");
    // End of a block/expression
    }

    // Start of a method/block
    void assertReadOnly() {
        // Branch: checks a condition
        if (readOnly) throw new UnsupportedOperationException("Buffer is read-only");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> void write(Type<T> type, @UnknownNullability T value) {
        // Calls a method
        assertReadOnly();
        // Calls a method
        type.write(this, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @UnknownNullability T read(Type<T> type) {
        // Calls a method
        assertDummy();
        // Returns a value to the caller
        return type.read(this);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> void writeAt(long index, Type<T> type, @UnknownNullability T value) {
        // Calls a method
        assertReadOnly();
        // Assigns a value
        final long oldWriteIndex = writeIndex;
        // Assigns a value
        writeIndex = index;
        // Exception handling
        try {
            // Calls a method
            write(type, value);
        // Start of a method/block
        } finally {
            // Assigns a value
            writeIndex = oldWriteIndex;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @UnknownNullability T readAt(long index, Type<T> type) {
        // Calls a method
        assertDummy();
        // Assigns a value
        final long oldReadIndex = readIndex;
        // Assigns a value
        readIndex = index;
        // Exception handling
        try {
            // Returns a value to the caller
            return read(type);
        // Start of a method/block
        } finally {
            // Assigns a value
            readIndex = oldReadIndex;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void copyTo(long srcOffset, byte[] dest, long destOffset, long length) {
        // Calls a method
        copyTo(srcOffset, dest, (int) destOffset, (int) length);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void copyTo(long srcOffset, byte[] dest, int destOffset, int length) {
        // Calls a method
        assertDummy();
        // Branch: checks a condition
        if (length == 0) return;
        // Branch: checks a condition
        if (dest.length < destOffset + length)
            // Throws an exception
            throw new IndexOutOfBoundsException("Destination array is too small");
        // Calls a method
        buf.getBytes((int) srcOffset, dest, destOffset, length);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void copyTo(long srcOffset, MemorySegment dest, long destOffset, long length) {
        // Calls a method
        assertDummy();
        // Calls a method
        final byte[] tmp = new byte[(int) length];
        // Calls a method
        buf.getBytes((int) srcOffset, tmp);
        // Calls a method
        MemorySegment.copy(MemorySegment.ofArray(tmp), 0, dest, destOffset, length);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte[] extractBytes(Consumer<NetworkBuffer> extractor) {
        // Calls a method
        assertDummy();
        // Calls a method
        final long start = readIndex();
        // Calls a method
        extractor.accept(this);
        // Calls a method
        final long end = readIndex();
        // Calls a method
        final int length = (int) (end - start);
        // Assigns a value
        final byte[] out = new byte[length];
        // Calls a method
        buf.getBytes((int) start, out);
        // Returns a value to the caller
        return out;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public NetworkBuffer clear() {
        // Returns a value to the caller
        return index(0, 0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long writeIndex() {
        // Returns a value to the caller
        return writeIndex;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long readIndex() {
        // Returns a value to the caller
        return readIndex;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public NetworkBuffer writeIndex(long writeIndex) {
        // Access to the current/parent object
        this.writeIndex = writeIndex;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public NetworkBuffer readIndex(long readIndex) {
        // Access to the current/parent object
        this.readIndex = readIndex;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public NetworkBuffer index(long readIndex, long writeIndex) {
        // Access to the current/parent object
        this.readIndex = readIndex;
        // Access to the current/parent object
        this.writeIndex = writeIndex;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long advanceWrite(long length) {
        // Assigns a value
        final long oldWriteIndex = writeIndex;
        // Assigns a value
        writeIndex = oldWriteIndex + length;
        // Returns a value to the caller
        return oldWriteIndex;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long advanceRead(long length) {
        // Assigns a value
        final long oldReadIndex = readIndex;
        // Assigns a value
        readIndex = oldReadIndex + length;
        // Returns a value to the caller
        return oldReadIndex;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long readableBytes() {
        // Returns a value to the caller
        return writeIndex - readIndex;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long writableBytes() {
        // Returns a value to the caller
        return capacity() - writeIndex;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long capacity() {
        // Returns a value to the caller
        return isDummy() ? Long.MAX_VALUE : buf.capacity();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void readOnly() {
        // Access to the current/parent object
        this.readOnly = true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isReadOnly() {
        // Returns a value to the caller
        return readOnly;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void resize(long newSize) {
        // Calls a method
        assertDummy();
        // Calls a method
        assertReadOnly();
        // Calls a method
        final long capacity = capacity();
        // Branch: checks a condition
        if (newSize <= capacity)
            // Throws an exception
            throw new IllegalArgumentException("New size must be larger than current capacity");
        // Calls a method
        buf.capacity((int) newSize);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void ensureWritable(long length) {
        // Calls a method
        assertReadOnly();
        // Branch: checks a condition
        if (writableBytes() >= length) return;
        // Calls a method
        final long newCapacity = newCapacity(length, capacity());
        // Branch: checks a condition
        if (isDummy()) return;
        // Calls a method
        buf.capacity((int) newCapacity);
    // End of a block/expression
    }

    // Start of a method/block
    private long newCapacity(long length, long capacity) {
        // Assigns a value
        final long targetSize = writeIndex + length;
        // Assigns a value
        final AutoResize strategy = this.autoResize;
        // Branch: checks a condition
        if (strategy == null)
            // Throws an exception
            throw new IndexOutOfBoundsException("Buffer is full and cannot be resized: " + capacity + " -> " + targetSize);
        // Calls a method
        final long newCapacity = strategy.resize(capacity, targetSize);
        // Branch: checks a condition
        if (newCapacity == capacity)
            // Throws an exception
            throw new IndexOutOfBoundsException("Buffer resized to the same capacity: " + capacity + " -> " + targetSize);
        // Returns a value to the caller
        return newCapacity;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void compact() {
        // Calls a method
        assertDummy();
        // Calls a method
        assertReadOnly();
        // Branch: checks a condition
        if (readIndex == 0) return;
        // Calls a method
        buf.discardReadBytes();
        // Code statement
        writeIndex -= readIndex;
        // Assigns a value
        readIndex = 0;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public NetworkBuffer copy(long index, long length, long readIndex, long writeIndex) {
        // Calls a method
        assertDummy();
        // Calls a method
        Objects.checkFromIndexSize((int) index, (int) length, (int) capacity());
        // Calls a method
        final ByteBuf newBuf = ByteBufAllocator.DEFAULT.buffer((int) length);
        // Calls a method
        buf.getBytes((int) index, newBuf, 0, (int) length);
        // Calls a method
        newBuf.writerIndex((int) length);
        // Returns a value to the caller
        return new NetworkBufferImpl(newBuf, readIndex, writeIndex, autoResize, registries);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int readFromByteBuf(ByteBuf in) {
        // Calls a method
        assertDummy();
        // Calls a method
        assertReadOnly();
        // Calls a method
        final int readable = in.readableBytes();
        // Branch: checks a condition
        if (readable == 0) return 0;
        // Calls a method
        ensureWritable(readable);
        // Calls a method
        in.readBytes(buf, (int) writeIndex, readable);
        // Calls a method
        advanceWrite(readable);
        // Returns a value to the caller
        return readable;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean writeToByteBuf(ByteBuf out) {
        // Calls a method
        assertDummy();
        // Calls a method
        final int readable = (int) readableBytes();
        // Branch: checks a condition
        if (readable == 0) return true;
        // Calls a method
        out.writeBytes(buf, (int) readIndex, readable);
        // Calls a method
        advanceRead(readable);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void cipher(Cipher cipher, long start, long length) {
        // Calls a method
        assertDummy();
        // Calls a method
        final byte[] plain = new byte[(int) length];
        // Calls a method
        buf.getBytes((int) start, plain);
        // Calls a method
        final byte[] result = new byte[(int) length];
        // Exception handling
        try {
            // Calls a method
            final int written = cipher.update(plain, 0, (int) length, result);
            // Calls a method
            buf.setBytes((int) start, result, 0, written);
        // Start of a method/block
        } catch (ShortBufferException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static class CompressionHolder {
        // Calls a method
        private static final ObjectPool<Deflater> DEFLATER_POOL = ObjectPool.pool(Deflater::new);
        // Calls a method
        private static final ObjectPool<Inflater> INFLATER_POOL = ObjectPool.pool(Inflater::new);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long compress(long start, long length, NetworkBuffer output) {
        // Calls a method
        assertDummy();
        // Calls a method
        impl(output).assertReadOnly();

        // Calls a method
        final byte[] input = new byte[(int) length];
        // Calls a method
        buf.getBytes((int) start, input);

        // Calls a method
        final ByteBuf outBuf = impl(output).buf;
        // Calls a method
        impl(output).ensureWritable(length + 64);

        // Calls a method
        Deflater deflater = CompressionHolder.DEFLATER_POOL.get();
        // Exception handling
        try {
            // Calls a method
            deflater.setInput(input);
            // Calls a method
            deflater.finish();
            // Assigns a value
            final byte[] tmp = new byte[8192];
            // Assigns a value
            int total = 0;
            // Loop: repeats a block
            while (!deflater.finished()) {
                // Calls a method
                final int n = deflater.deflate(tmp);
                // Branch: checks a condition
                if (n == 0) break;
                // Calls a method
                impl(output).ensureWritable(n);
                // Calls a method
                outBuf.setBytes((int) (output.writeIndex() + total), tmp, 0, n);
                // Code statement
                total += n;
            // End of a block/expression
            }
            // Calls a method
            output.advanceWrite(total);
            // Returns a value to the caller
            return total;
        // Start of a method/block
        } finally {
            // Calls a method
            deflater.reset();
            // Calls a method
            CompressionHolder.DEFLATER_POOL.add(deflater);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long decompress(long start, long length, NetworkBuffer output) {
        // Calls a method
        assertDummy();
        // Calls a method
        impl(output).assertReadOnly();

        // Calls a method
        final byte[] input = new byte[(int) length];
        // Calls a method
        buf.getBytes((int) start, input);

        // Calls a method
        final ByteBuf outBuf = impl(output).buf;

        // Calls a method
        Inflater inflater = CompressionHolder.INFLATER_POOL.get();
        // Exception handling
        try {
            // Calls a method
            inflater.setInput(input);
            // Assigns a value
            final byte[] tmp = new byte[8192];
            // Assigns a value
            int total = 0;
            // Loop: repeats a block
            while (!inflater.finished() && !inflater.needsInput()) {
                // Calls a method
                final int n = inflater.inflate(tmp);
                // Branch: checks a condition
                if (n == 0) break;
                // Calls a method
                impl(output).ensureWritable(n);
                // Calls a method
                outBuf.setBytes((int) (output.writeIndex() + total), tmp, 0, n);
                // Code statement
                total += n;
            // End of a block/expression
            }
            // Calls a method
            output.advanceWrite(total);
            // Returns a value to the caller
            return total;
        // Start of a method/block
        } catch (DataFormatException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // Start of a method/block
        } finally {
            // Calls a method
            inflater.reset();
            // Calls a method
            CompressionHolder.INFLATER_POOL.add(inflater);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Registries registries() {
        // Returns a value to the caller
        return registries;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void registries(@Nullable Registries registries) {
        // Access to the current/parent object
        this.registries = registries;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("NetworkBuffer{r%d|w%d->%d, registries=%s, autoResize=%s, readOnly=%s}",
                // Calls a method
                readIndex, writeIndex, capacity(), registries != null, autoResize != null, isReadOnly());
    // End of a block/expression
    }

    // Start of a method/block
    void _putBytes(long index, byte[] value) {
        // Branch: checks a condition
        if (isDummy()) return;
        // Calls a method
        assertReadOnly();
        // Calls a method
        buf.setBytes((int) index, value);
    // End of a block/expression
    }

    // Start of a method/block
    void _getBytes(long index, byte[] value) {
        // Calls a method
        assertDummy();
        // Calls a method
        buf.getBytes((int) index, value);
    // End of a block/expression
    }

    // Start of a method/block
    void _putByte(long index, byte value) {
        // Branch: checks a condition
        if (isDummy()) return;
        // Calls a method
        assertReadOnly();
        // Calls a method
        buf.setByte((int) index, value);
    // End of a block/expression
    }

    // Start of a method/block
    byte _getByte(long index) {
        // Calls a method
        assertDummy();
        // Returns a value to the caller
        return buf.getByte((int) index);
    // End of a block/expression
    }

    // Start of a method/block
    void _putShort(long index, short value) {
        // Branch: checks a condition
        if (isDummy()) return;
        // Calls a method
        assertReadOnly();
        // Calls a method
        buf.setShort((int) index, value);
    // End of a block/expression
    }

    // Start of a method/block
    short _getShort(long index) {
        // Calls a method
        assertDummy();
        // Returns a value to the caller
        return buf.getShort((int) index);
    // End of a block/expression
    }

    // Start of a method/block
    void _putInt(long index, int value) {
        // Branch: checks a condition
        if (isDummy()) return;
        // Calls a method
        assertReadOnly();
        // Calls a method
        buf.setInt((int) index, value);
    // End of a block/expression
    }

    // Start of a method/block
    int _getInt(long index) {
        // Calls a method
        assertDummy();
        // Returns a value to the caller
        return buf.getInt((int) index);
    // End of a block/expression
    }

    // Start of a method/block
    void _putLong(long index, long value) {
        // Branch: checks a condition
        if (isDummy()) return;
        // Calls a method
        assertReadOnly();
        // Calls a method
        buf.setLong((int) index, value);
    // End of a block/expression
    }

    // Start of a method/block
    long _getLong(long index) {
        // Calls a method
        assertDummy();
        // Returns a value to the caller
        return buf.getLong((int) index);
    // End of a block/expression
    }

    // Start of a method/block
    void _putFloat(long index, float value) {
        // Branch: checks a condition
        if (isDummy()) return;
        // Calls a method
        assertReadOnly();
        // Calls a method
        buf.setFloat((int) index, value);
    // End of a block/expression
    }

    // Start of a method/block
    float _getFloat(long index) {
        // Calls a method
        assertDummy();
        // Returns a value to the caller
        return buf.getFloat((int) index);
    // End of a block/expression
    }

    // Start of a method/block
    void _putDouble(long index, double value) {
        // Branch: checks a condition
        if (isDummy()) return;
        // Calls a method
        assertReadOnly();
        // Calls a method
        buf.setDouble((int) index, value);
    // End of a block/expression
    }

    // Start of a method/block
    double _getDouble(long index) {
        // Calls a method
        assertDummy();
        // Returns a value to the caller
        return buf.getDouble((int) index);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer wrap(byte[] bytes, long readIndex, long writeIndex, @Nullable Registries registries) {
        // Calls a method
        final ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(bytes.length);
        // Calls a method
        buf.writeBytes(bytes);
        // Returns a value to the caller
        return new NetworkBufferImpl(buf, readIndex, writeIndex, null, registries);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer fromByteBuf(ByteBuf buf, @Nullable Registries registries) {
        // Returns a value to the caller
        return new NetworkBufferImpl(buf, buf.readerIndex(), buf.writerIndex(), null, registries);
    // End of a block/expression
    }

    // Code statement
    static void copy(NetworkBuffer srcBuffer, long srcOffset,
                     // Start of a method/block
                     NetworkBuffer dstBuffer, long dstOffset, long length) {
        // Calls a method
        final NetworkBufferImpl src = impl(srcBuffer);
        // Calls a method
        final NetworkBufferImpl dst = impl(dstBuffer);
        // Calls a method
        dst.assertReadOnly();
        // Calls a method
        src.buf.getBytes((int) srcOffset, dst.buf, (int) dstOffset, (int) length);
    // End of a block/expression
    }

    // Start of a method/block
    static boolean equals(NetworkBuffer buffer1, NetworkBuffer buffer2) {
        // Calls a method
        final NetworkBufferImpl b1 = impl(buffer1);
        // Calls a method
        final NetworkBufferImpl b2 = impl(buffer2);
        // Calls a method
        final int cap = (int) b1.capacity();
        // Branch: checks a condition
        if (cap != b2.capacity()) return false;
        // Loop: repeats a block
        for (int i = 0; i < cap; i++) {
            // Branch: checks a condition
            if (b1.buf.getByte(i) != b2.buf.getByte(i)) return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBufferImpl dummy(Registries registries) {
        // Returns a value to the caller
        return new NetworkBufferImpl(DUMMY_BUF, 0, 0, null, registries);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBufferImpl impl(NetworkBuffer buffer) {
        // Returns a value to the caller
        return (NetworkBufferImpl) buffer;
    // End of a block/expression
    }

    // Start of a method/block
    BinaryTagWriter nbtWriter() {
        // Branch: checks a condition
        if (this.nbtWriter == null) {
            // Access to the current/parent object
            this.nbtWriter = new BinaryTagWriter(new DataOutputStream(new OutputStream() {
                // Annotation for the following element
                @Override
                // Start of a method/block
                public void write(int b) {
                    // Calls a method
                    NetworkBufferImpl.this.write(BYTE, (byte) b);
                // End of a block/expression
                }
            // Code statement
            }));
        // End of a block/expression
        }
        // Returns a value to the caller
        return this.nbtWriter;
    // End of a block/expression
    }

    // Start of a method/block
    BinaryTagReader nbtReader() {
        // Branch: checks a condition
        if (nbtReader == null) {
            // Access to the current/parent object
            this.nbtReader = new BinaryTagReader(new DataInputStream(new InputStream() {
                // Annotation for the following element
                @Override
                // Start of a method/block
                public int read() {
                    // Returns a value to the caller
                    return NetworkBufferImpl.this.read(BYTE) & 0xFF;
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public int available() {
                    // Returns a value to the caller
                    return (int) NetworkBufferImpl.this.readableBytes();
                // End of a block/expression
                }
            // Code statement
            }));
        // End of a block/expression
        }
        // Returns a value to the caller
        return nbtReader;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class Builder implements NetworkBuffer.Builder {
        // Code statement
        private final long initialSize;
        // Code statement
        private @Nullable AutoResize autoResize;
        // Code statement
        private @Nullable Registries registries;

        // Start of a method/block
        Builder(long initialSize) {
            // Access to the current/parent object
            this.initialSize = initialSize;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public NetworkBuffer.Builder autoResize(@Nullable AutoResize autoResize) {
            // Access to the current/parent object
            this.autoResize = autoResize;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public NetworkBuffer.Builder registry(@Nullable Registries registries) {
            // Access to the current/parent object
            this.registries = registries;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public NetworkBuffer build() {
            // Calls a method
            final ByteBuf buf = ByteBufAllocator.DEFAULT.buffer((int) initialSize, Integer.MAX_VALUE);
            // Returns a value to the caller
            return new NetworkBufferImpl(buf, 0, 0, autoResize, registries);
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}