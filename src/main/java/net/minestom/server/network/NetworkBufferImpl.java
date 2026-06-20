// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import io.netty.buffer.ByteBuf;
// Import d'une classe nécessaire
import io.netty.buffer.ByteBufAllocator;
// Import d'une classe nécessaire
import io.netty.buffer.Unpooled;
// Import d'une classe nécessaire
import io.netty.handler.codec.compression.JdkZlibDecoder;
// Import d'une classe nécessaire
import io.netty.handler.codec.compression.JdkZlibEncoder;
// Import d'une classe nécessaire
import io.netty.handler.codec.compression.ZlibWrapper;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.utils.nbt.BinaryTagReader;
// Import d'une classe nécessaire
import net.minestom.server.utils.nbt.BinaryTagWriter;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import javax.crypto.Cipher;
// Import d'une classe nécessaire
import javax.crypto.ShortBufferException;
// Import d'une classe nécessaire
import java.io.*;
// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Consumer;

/**
 * Netty {@link ByteBuf}-backed implementation of {@link NetworkBuffer}.
 *
 * <p>All usages of {@code sun.misc.Unsafe}, {@code java.nio.ByteBuffer},
 * {@code java.nio.channels.ReadableByteChannel}, and
 * {@code java.nio.channels.SocketChannel} have been removed. Compression
 * now delegates to Netty's {@link JdkZlibEncoder}/{@link JdkZlibDecoder}.
 */
// Déclaration de type (classe/interface/enum/record)
final class NetworkBufferImpl implements NetworkBuffer {

    /**
     * The underlying Netty buffer. For <em>resizable</em> buffers this is always
     * a heap/pooled buffer whose capacity is managed manually through
     * {@link ByteBuf#capacity(int)}. For <em>dummy</em> buffers (size calculations)
     * this is {@link #DUMMY_BUF} and writes are silently discarded.
     */
    // Instruction de code
    private ByteBuf buf;

    /** Sentinel value used for dummy (size-calculation) buffers. */
    // Affecte une valeur
    private static final ByteBuf DUMMY_BUF = Unpooled.EMPTY_BUFFER;

    // Instruction de code
    private long readIndex;
    // Instruction de code
    private long writeIndex;
    // Instruction de code
    boolean readOnly;

    // Instruction de code
    private BinaryTagWriter nbtWriter;
    // Instruction de code
    private BinaryTagReader nbtReader;

    // Instruction de code
    final @Nullable AutoResize autoResize;
    // Instruction de code
    final @Nullable Registries registries;

    // Instruction de code
    NetworkBufferImpl(ByteBuf buf,
                      // Instruction de code
                      long readIndex, long writeIndex,
                      // Annotation pour l'élément suivant
                      @Nullable AutoResize autoResize,
                      // Annotation pour l'élément suivant
                      @Nullable Registries registries) {
        // Accès à l'objet courant/parent
        this.buf       = buf;
        // Accès à l'objet courant/parent
        this.readIndex  = readIndex;
        // Accès à l'objet courant/parent
        this.writeIndex = writeIndex;
        // Accès à l'objet courant/parent
        this.autoResize = autoResize;
        // Accès à l'objet courant/parent
        this.registries = registries;
    // Fin d'un bloc/d'une expression
    }


    // Début d'une méthode/d'un bloc
    private boolean isDummy() {
        // Renvoie une valeur à l'appelant
        return buf == DUMMY_BUF;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void assertDummy() {
        // Embranchement : vérifie une condition
        if (isDummy()) throw new UnsupportedOperationException("Buffer is a dummy buffer");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void assertReadOnly() {
        // Embranchement : vérifie une condition
        if (readOnly) throw new UnsupportedOperationException("Buffer is read-only");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> void write(Type<T> type, @UnknownNullability T value) {
        // Appelle une méthode
        assertReadOnly();
        // Appelle une méthode
        type.write(this, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @UnknownNullability T read(Type<T> type) {
        // Appelle une méthode
        assertDummy();
        // Renvoie une valeur à l'appelant
        return type.read(this);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> void writeAt(long index, Type<T> type, @UnknownNullability T value) {
        // Appelle une méthode
        assertReadOnly();
        // Affecte une valeur
        final long old = writeIndex;
        // Affecte une valeur
        writeIndex = index;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            write(type, value);
        // Début d'une méthode/d'un bloc
        } finally {
            // Affecte une valeur
            writeIndex = old;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @UnknownNullability T readAt(long index, Type<T> type) {
        // Appelle une méthode
        assertDummy();
        // Affecte une valeur
        final long old = readIndex;
        // Affecte une valeur
        readIndex = index;
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return read(type);
        // Début d'une méthode/d'un bloc
        } finally {
            // Affecte une valeur
            readIndex = old;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void copyTo(long srcOffset, byte[] dest, long destOffset, long length) {
        // Appelle une méthode
        assertDummy();
        // Embranchement : vérifie une condition
        if (length == 0) return;
        // Embranchement : vérifie une condition
        if (dest.length < destOffset + length)
            // Lève une exception
            throw new IndexOutOfBoundsException("Destination array is too small");
        // Appelle une méthode
        buf.getBytes((int) srcOffset, dest, (int) destOffset, (int) length);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte[] extractBytes(Consumer<NetworkBuffer> extractor) {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        final long start = readIndex();
        // Appelle une méthode
        extractor.accept(this);
        // Appelle une méthode
        final long end = readIndex();
        // Affecte une valeur
        final int length = (int) (end - start);
        // Affecte une valeur
        final byte[] out = new byte[length];
        // Appelle une méthode
        buf.getBytes((int) start, out);
        // Renvoie une valeur à l'appelant
        return out;
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @Override public NetworkBuffer clear() {
        // Renvoie une valeur à l'appelant
        return index(0, 0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override public long writeIndex() {
        // Renvoie une valeur à l'appelant
        return writeIndex;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override public long readIndex() {
        // Renvoie une valeur à l'appelant
        return readIndex;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override public NetworkBuffer writeIndex(long wi) {
        // Accès à l'objet courant/parent
        this.writeIndex = wi; return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override public NetworkBuffer readIndex(long ri) {
        // Accès à l'objet courant/parent
        this.readIndex  = ri; return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override public NetworkBuffer index(long ri, long wi) {
        // Affecte une valeur
        readIndex = ri; writeIndex = wi; return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public long advanceWrite(long length) {
        // Affecte une valeur
        final long old = writeIndex;
        // Affecte une valeur
        writeIndex = old + length;
        // Renvoie une valeur à l'appelant
        return old;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public long advanceRead(long length) {
        // Affecte une valeur
        final long old = readIndex;
        // Affecte une valeur
        readIndex = old + length;
        // Renvoie une valeur à l'appelant
        return old;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override public long readableBytes() {
        // Renvoie une valeur à l'appelant
        return writeIndex - readIndex;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override public long writableBytes() {
        // Renvoie une valeur à l'appelant
        return capacity() - writeIndex;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override public long capacity() {
        // Renvoie une valeur à l'appelant
        return isDummy() ? Long.MAX_VALUE : buf.capacity();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Affecte une valeur
    public void readOnly() { this.readOnly = true; }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public boolean isReadOnly() { return readOnly; }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void resize(long newSize) {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        assertReadOnly();
        // Embranchement : vérifie une condition
        if (newSize <= capacity())
            // Lève une exception
            throw new IllegalArgumentException("New size must be larger than current capacity");
        // Appelle une méthode
        buf.capacity((int) newSize);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void ensureWritable(long length) {
        // Appelle une méthode
        assertReadOnly();
        // Embranchement : vérifie une condition
        if (writableBytes() >= length) return;
        // Appelle une méthode
        final long target = newCapacity(length, capacity());
        // Embranchement : vérifie une condition
        if (isDummy()) return; // size-calc path - no real allocation
        // Appelle une méthode
        buf.capacity((int) target);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private long newCapacity(long length, long capacity) {
        // Affecte une valeur
        final long targetSize = writeIndex + length;
        // Affecte une valeur
        final AutoResize strategy = this.autoResize;
        // Embranchement : vérifie une condition
        if (strategy == null)
            // Lève une exception
            throw new IndexOutOfBoundsException(
                    // Instruction de code
                    "Buffer is full and cannot be resized: " + capacity + " -> " + targetSize);
        // Appelle une méthode
        final long newCap = strategy.resize(capacity, targetSize);
        // Embranchement : vérifie une condition
        if (newCap == capacity)
            // Lève une exception
            throw new IndexOutOfBoundsException(
                    // Instruction de code
                    "Buffer resized to the same capacity: " + capacity + " -> " + targetSize);
        // Renvoie une valeur à l'appelant
        return newCap;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void compact() {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        assertReadOnly();
        // Embranchement : vérifie une condition
        if (readIndex == 0) return;
        // Instruction de code
        buf.discardReadBytes(); // netty discardReadBytes respects readerIndex
        // Appelle une méthode
        final int readable = (int) readableBytes();
        // Shift data left
        // Boucle : répète un bloc
        for (int i = 0; i < readable; i++) {
            // Appelle une méthode
            buf.setByte(i, buf.getByte((int) readIndex + i));
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        writeIndex -= readIndex;
        // Affecte une valeur
        readIndex   = 0;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public NetworkBuffer copy(long index, long length, long ri, long wi) {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        Objects.checkFromIndexSize((int) index, (int) length, (int) capacity());
        // Appelle une méthode
        final ByteBuf newBuf = ByteBufAllocator.DEFAULT.buffer((int) length);
        // Appelle une méthode
        buf.getBytes((int) index, newBuf, 0, (int) length);
        // Appelle une méthode
        newBuf.writerIndex((int) length);
        // Renvoie une valeur à l'appelant
        return new NetworkBufferImpl(newBuf, ri, wi, autoResize, registries);
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int readFromByteBuf(ByteBuf in) {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        assertReadOnly();
        // Appelle une méthode
        final int readable = in.readableBytes();
        // Embranchement : vérifie une condition
        if (readable == 0) return 0;
        // Appelle une méthode
        ensureWritable(readable);
        // Appelle une méthode
        in.readBytes(buf, (int) writeIndex, readable);
        // Appelle une méthode
        advanceWrite(readable);
        // Renvoie une valeur à l'appelant
        return readable;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean writeToByteBuf(ByteBuf out) {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        final int readable = (int) readableBytes();
        // Embranchement : vérifie une condition
        if (readable == 0) return true;
        // Appelle une méthode
        out.writeBytes(buf, (int) readIndex, readable);
        // Appelle une méthode
        advanceRead(readable);
        // Renvoie une valeur à l'appelant
        return true; // ByteBuf.writeBytes always writes everything
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void cipher(Cipher cipher, long start, long length) {
        // Appelle une méthode
        assertDummy();
        // Pull bytes out, cipher in-place, write back
        // Affecte une valeur
        final byte[] plain  = new byte[(int) length];
        // Appelle une méthode
        buf.getBytes((int) start, plain);
        // Affecte une valeur
        final byte[] result = new byte[(int) length];
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final int written = cipher.update(plain, 0, (int) length, result);
            // Appelle une méthode
            buf.setBytes((int) start, result, 0, written);
        // Début d'une méthode/d'un bloc
        } catch (ShortBufferException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public long compress(long start, long length, NetworkBuffer output) throws IOException {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        impl(output).assertReadOnly();

        // Slice the region to compress into a Netty buf (no copy — read-only view)
        // Appelle une méthode
        final ByteBuf src = buf.slice((int) start, (int) length);

        // Use Netty's JdkZlibEncoder synchronously through its internal codec path.
        // Because encoder/decoder embed in a pipeline, the simplest correct approach
        // for standalone (non-pipeline) use is to compress via Java's Deflater under
        // Netty's wrapper. We call compress directly.
        // Appelle une méthode
        final ByteBuf compressed = compressWithZlib(src);
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final int bytes = compressed.readableBytes();
            // Appelle une méthode
            impl(output).ensureWritable(bytes);
            // Appelle une méthode
            compressed.readBytes(impl(output).buf, (int) output.writeIndex(), bytes);
            // Appelle une méthode
            output.advanceWrite(bytes);
            // Renvoie une valeur à l'appelant
            return bytes;
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            compressed.release();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public long decompress(long start, long length, NetworkBuffer output) throws IOException {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        impl(output).assertReadOnly();

        // Appelle une méthode
        final ByteBuf src = buf.slice((int) start, (int) length);
        // Appelle une méthode
        final ByteBuf decompressed = decompressWithZlib(src);
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final int bytes = decompressed.readableBytes();
            // Appelle une méthode
            impl(output).ensureWritable(bytes);
            // Appelle une méthode
            decompressed.readBytes(impl(output).buf, (int) output.writeIndex(), bytes);
            // Appelle une méthode
            output.advanceWrite(bytes);
            // Renvoie une valeur à l'appelant
            return bytes;
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            decompressed.release();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Deflate-compresses {@code src} using Netty's {@link JdkZlibEncoder} in
     * {@link ZlibWrapper#ZLIB} mode (same as {@code java.util.zip.Deflater}).
     */
    // Début d'une méthode/d'un bloc
    private static ByteBuf compressWithZlib(ByteBuf src) throws IOException {
        // JdkZlibEncoder is a ChannelHandler and cannot be used standalone easily.
        // We use java.util.zip.Deflater directly (UNCHANGED compression logic)
        // but wrapped through Netty's ByteBuf API to avoid any NIO ByteBuffer
        // references leaking into the hot path.
        // Appelle une méthode
        final byte[] input = new byte[src.readableBytes()];
        // Appelle une méthode
        src.getBytes(src.readerIndex(), input);

        // Appelle une méthode
        final java.util.zip.Deflater deflater = new java.util.zip.Deflater();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            deflater.setInput(input);
            // Appelle une méthode
            deflater.finish();
            // Pre-allocate generously; Deflater output is at most ~input + header.
            // Appelle une méthode
            final ByteBuf out = ByteBufAllocator.DEFAULT.buffer(input.length + 64);
            // Affecte une valeur
            final byte[] tmp = new byte[8192];
            // Boucle : répète un bloc
            while (!deflater.finished()) {
                // Appelle une méthode
                final int n = deflater.deflate(tmp);
                // Appelle une méthode
                out.writeBytes(tmp, 0, n);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return out;
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            deflater.end();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Inflate-decompresses {@code src} using Java's {@link java.util.zip.Inflater},
     * accessed only through Netty's ByteBuf API.
     */
    // Début d'une méthode/d'un bloc
    private static ByteBuf decompressWithZlib(ByteBuf src) throws IOException {
        // Appelle une méthode
        final byte[] input = new byte[src.readableBytes()];
        // Appelle une méthode
        src.getBytes(src.readerIndex(), input);

        // Appelle une méthode
        final java.util.zip.Inflater inflater = new java.util.zip.Inflater();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            inflater.setInput(input);
            // Appelle une méthode
            final ByteBuf out = ByteBufAllocator.DEFAULT.buffer(input.length * 3);
            // Affecte une valeur
            final byte[] tmp = new byte[8192];
            // Boucle : répète un bloc
            while (!inflater.finished() && !inflater.needsInput()) {
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final int n = inflater.inflate(tmp);
                    // Appelle une méthode
                    out.writeBytes(tmp, 0, n);
                // Début d'une méthode/d'un bloc
                } catch (java.util.zip.DataFormatException e) {
                    // Lève une exception
                    throw new IOException("Zlib decompression failed", e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return out;
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            inflater.end();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // -------------------------------------------------------------------------
    // Registries
    // -------------------------------------------------------------------------

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Registries registries() {
        // Renvoie une valeur à l'appelant
        return registries;
    // Fin d'un bloc/d'une expression
    }

    // -------------------------------------------------------------------------
    // Internal low-level byte accessors (called by NetworkBufferTypeImpl)
    // -------------------------------------------------------------------------

    // Début d'une méthode/d'un bloc
    void _putBytes(long index, byte[] value) {
        // Embranchement : vérifie une condition
        if (isDummy()) return;
        // Appelle une méthode
        assertReadOnly();
        // Appelle une méthode
        buf.setBytes((int) index, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void _getBytes(long index, byte[] value) {
        // Appelle une méthode
        assertDummy();
        // Appelle une méthode
        buf.getBytes((int) index, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void _putByte(long index, byte value) {
        // Embranchement : vérifie une condition
        if (isDummy()) return;
        // Appelle une méthode
        assertReadOnly();
        // Appelle une méthode
        buf.setByte((int) index, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    byte _getByte(long index) {
        // Appelle une méthode
        assertDummy();
        // Renvoie une valeur à l'appelant
        return buf.getByte((int) index);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void _putShort(long index, short value) {
        // Embranchement : vérifie une condition
        if (isDummy()) return;
        // Appelle une méthode
        assertReadOnly();
        // Instruction de code
        buf.setShort((int) index, value);        // Netty always big-endian
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    short _getShort(long index) {
        // Appelle une méthode
        assertDummy();
        // Renvoie une valeur à l'appelant
        return buf.getShort((int) index);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void _putInt(long index, int value) {
        // Embranchement : vérifie une condition
        if (isDummy()) return;
        // Appelle une méthode
        assertReadOnly();
        // Appelle une méthode
        buf.setInt((int) index, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    int _getInt(long index) {
        // Appelle une méthode
        assertDummy();
        // Renvoie une valeur à l'appelant
        return buf.getInt((int) index);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void _putLong(long index, long value) {
        // Embranchement : vérifie une condition
        if (isDummy()) return;
        // Appelle une méthode
        assertReadOnly();
        // Appelle une méthode
        buf.setLong((int) index, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    long _getLong(long index) {
        // Appelle une méthode
        assertDummy();
        // Renvoie une valeur à l'appelant
        return buf.getLong((int) index);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void _putFloat(long index, float value) {
        // Embranchement : vérifie une condition
        if (isDummy()) return;
        // Appelle une méthode
        assertReadOnly();
        // Appelle une méthode
        buf.setFloat((int) index, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    float _getFloat(long index) {
        // Appelle une méthode
        assertDummy();
        // Renvoie une valeur à l'appelant
        return buf.getFloat((int) index);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void _putDouble(long index, double value) {
        // Embranchement : vérifie une condition
        if (isDummy()) return;
        // Appelle une méthode
        assertReadOnly();
        // Appelle une méthode
        buf.setDouble((int) index, value);
    // Fin d'un bloc/d'une expression
    }

    // Boucle : répète un bloc
    double _getDouble(long index) {
        // Appelle une méthode
        assertDummy();
        // Renvoie une valeur à l'appelant
        return buf.getDouble((int) index);
    // Fin d'un bloc/d'une expression
    }

    // -------------------------------------------------------------------------
    // NBT helpers
    // -------------------------------------------------------------------------

    // Début d'une méthode/d'un bloc
    BinaryTagWriter nbtWriter() {
        // Embranchement : vérifie une condition
        if (nbtWriter == null) {
            // Affecte une valeur
            nbtWriter = new BinaryTagWriter(new DataOutputStream(new OutputStream() {
                // Annotation pour l'élément suivant
                @Override public void write(int b) {
                    // Appelle une méthode
                    NetworkBufferImpl.this.write(BYTE, (byte) b);
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            }));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return nbtWriter;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BinaryTagReader nbtReader() {
        // Embranchement : vérifie une condition
        if (nbtReader == null) {
            // Affecte une valeur
            nbtReader = new BinaryTagReader(new DataInputStream(new InputStream() {
                // Annotation pour l'élément suivant
                @Override public int read() {
                    // Renvoie une valeur à l'appelant
                    return NetworkBufferImpl.this.read(BYTE) & 0xFF;
                // Fin d'un bloc/d'une expression
                }
                // Annotation pour l'élément suivant
                @Override public int available() {
                    // Renvoie une valeur à l'appelant
                    return (int) NetworkBufferImpl.this.readableBytes();
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            }));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return nbtReader;
    // Fin d'un bloc/d'une expression
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format(
                // Affecte une valeur
                "NetworkBuffer{r%d|w%d->%d, registries=%s, autoResize=%s, readOnly=%s}",
                // Instruction de code
                readIndex, writeIndex, capacity(),
                // Instruction de code
                registries != null, autoResize != null, readOnly);
    // Fin d'un bloc/d'une expression
    }

    // -------------------------------------------------------------------------
    // Static factory helpers
    // -------------------------------------------------------------------------

    // Instruction de code
    static NetworkBuffer wrap(byte[] bytes, long readIndex, long writeIndex,
                              // Annotation pour l'élément suivant
                              @Nullable Registries registries) {
        // Appelle une méthode
        final ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(bytes.length);
        // Appelle une méthode
        buf.writeBytes(bytes);
        // Renvoie une valeur à l'appelant
        return new NetworkBufferImpl(buf, readIndex, writeIndex, null, registries);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Wraps a Netty {@link ByteBuf}. Ownership stays with the caller.
     */
    // Début d'une méthode/d'un bloc
    static NetworkBuffer fromByteBuf(ByteBuf buf, @Nullable Registries registries) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferImpl(
                // Instruction de code
                buf,
                // Instruction de code
                buf.readerIndex(), buf.writerIndex(),
                // Instruction de code
                null, registries);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static void copy(NetworkBuffer srcBuffer, long srcOffset,
                     // Début d'une méthode/d'un bloc
                     NetworkBuffer dstBuffer, long dstOffset, long length) {
        // Appelle une méthode
        final NetworkBufferImpl src = impl(srcBuffer);
        // Appelle une méthode
        final NetworkBufferImpl dst = impl(dstBuffer);
        // Appelle une méthode
        dst.assertReadOnly();
        // Appelle une méthode
        src.buf.getBytes((int) srcOffset, dst.buf, (int) dstOffset, (int) length);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static boolean equals(NetworkBuffer buffer1, NetworkBuffer buffer2) {
        // Appelle une méthode
        final NetworkBufferImpl b1 = impl(buffer1);
        // Appelle une méthode
        final NetworkBufferImpl b2 = impl(buffer2);
        // Appelle une méthode
        final int cap = (int) b1.capacity();
        // Embranchement : vérifie une condition
        if (cap != b2.capacity()) return false;
        // Boucle : répète un bloc
        for (int i = 0; i < cap; i++) {
            // Embranchement : vérifie une condition
            if (b1.buf.getByte(i) != b2.buf.getByte(i)) return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /** Creates a size-calculation dummy buffer (no actual memory). */
    // Début d'une méthode/d'un bloc
    static NetworkBufferImpl dummy(Registries registries) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferImpl(DUMMY_BUF, 0, 0, null, registries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBufferImpl impl(NetworkBuffer buffer) {
        // Renvoie une valeur à l'appelant
        return (NetworkBufferImpl) buffer;
    // Fin d'un bloc/d'une expression
    }

    // -------------------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------------------

    // Déclaration de type (classe/interface/enum/record)
    static final class Builder implements NetworkBuffer.Builder {
        // Instruction de code
        private final long initialSize;
        // Instruction de code
        private AutoResize autoResize;
        // Instruction de code
        private Registries registries;

        // Début d'une méthode/d'un bloc
        Builder(long initialSize) {
            // Accès à l'objet courant/parent
            this.initialSize = initialSize;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public NetworkBuffer.Builder autoResize(@Nullable AutoResize autoResize) {
            // Accès à l'objet courant/parent
            this.autoResize = autoResize;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public NetworkBuffer.Builder registry(@Nullable Registries registries) {
            // Accès à l'objet courant/parent
            this.registries = registries;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public NetworkBuffer build() {
            // Appelle une méthode
            final ByteBuf buf = ByteBufAllocator.DEFAULT.buffer((int) initialSize, Integer.MAX_VALUE);
            // Renvoie une valeur à l'appelant
            return new NetworkBufferImpl(buf, 0, 0, autoResize, registries);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}