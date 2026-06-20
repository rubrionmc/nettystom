// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.lang.foreign.Arena;
// Import of a required class
import java.lang.foreign.MemorySegment;
// Import of a required class
import java.lang.foreign.ValueLayout;
// Import of a required class
import java.lang.ref.WeakReference;
// Import of a required class
import java.nio.charset.StandardCharsets;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Function;

// Static import of a member
import static net.kyori.adventure.nbt.IntBinaryTag.intBinaryTag;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;
// Static import of a member
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class NetworkBufferTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void resize() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer(6);
        // Calls a method
        buffer.write(INT, 6);
        // Calls a method
        assertEquals(4, buffer.writeIndex());

        // Calls a method
        buffer.write(INT, 7);
        // Calls a method
        assertEquals(8, buffer.writeIndex());

        // Calls a method
        assertEquals(6, buffer.read(INT));
        // Calls a method
        assertEquals(7, buffer.read(INT));

        // Test one-off length
        // Calls a method
        buffer = NetworkBuffer.resizableBuffer(1);
        // Calls a method
        buffer.write(BYTE, (byte) 3);
        // Calls a method
        assertEquals(1, buffer.writeIndex());

        // Calls a method
        buffer.write(BYTE, (byte) 4);
        // Calls a method
        assertEquals(2, buffer.writeIndex());

        // Calls a method
        assertEquals((byte) 3, buffer.read(BYTE));
        // Calls a method
        assertEquals((byte) 4, buffer.read(BYTE));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void garbageCollected() {
        // Calls a method
        waitUntilCleared(new WeakReference<>(NetworkBuffer.staticBuffer(1024)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void resizeRead() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer(4);
        // Calls a method
        buffer.write(INT, 6);
        // Calls a method
        assertEquals(4, buffer.capacity());
        // Calls a method
        assertEquals(4, buffer.writeIndex());

        // Calls a method
        buffer.resize(8);
        // Calls a method
        assertEquals(8, buffer.capacity());
        // Calls a method
        assertEquals(6, buffer.read(INT));

        // Calls a method
        buffer.write(INT, 7);
        // Calls a method
        assertEquals(8, buffer.capacity());
        // Calls a method
        assertEquals(8, buffer.writeIndex());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyClone() {
        // Calls a method
        var buffer = NetworkBuffer.staticBuffer(10);
        // Calls a method
        buffer.write(INT, 6);
        // Calls a method
        buffer.write(SHORT, (short) 2);
        // Calls a method
        buffer.write(FLOAT, 3.5f);
        // Calls a method
        assertEquals(10, buffer.writeIndex());
        // Calls a method
        assertEquals(10, buffer.capacity());

        // Calls a method
        var copy = buffer.copy(0, 10);
        // Calls a method
        assertEquals(10, copy.writeIndex());
        // Calls a method
        assertEquals(10, copy.capacity());

        // Calls a method
        assertTrue(NetworkBuffer.equals(buffer, copy));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyDirectZeroIndex() {
        // Calls a method
        var buffer1 = NetworkBuffer.staticBuffer(10);
        // Calls a method
        buffer1.write(INT, 6);
        // Calls a method
        buffer1.write(SHORT, (short) 2);
        // Calls a method
        buffer1.write(FLOAT, 3.5f);
        // Calls a method
        assertEquals(10, buffer1.writeIndex());
        // Calls a method
        assertEquals(10, buffer1.capacity());

        // Calls a method
        var buffer2 = NetworkBuffer.staticBuffer(10);
        // Calls a method
        NetworkBuffer.copy(buffer1, 0, buffer2, 0, 10);
        // Calls a method
        assertEquals(10, buffer2.capacity());

        // Calls a method
        assertEquals(6, buffer2.read(INT));
        // Calls a method
        assertEquals((short) 2, buffer2.read(SHORT));
        // Calls a method
        assertEquals(3.5f, buffer2.read(FLOAT));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyDirectIndex() {
        // Calls a method
        var buffer1 = NetworkBuffer.staticBuffer(10);
        // Calls a method
        buffer1.write(INT, 6);
        // Calls a method
        buffer1.write(SHORT, (short) 2);
        // Calls a method
        buffer1.write(FLOAT, 3.5f);
        // Calls a method
        assertEquals(10, buffer1.writeIndex());
        // Calls a method
        assertEquals(10, buffer1.capacity());

        // Calls a method
        var buffer2 = NetworkBuffer.staticBuffer(4);
        // Calls a method
        NetworkBuffer.copy(buffer1, 6, buffer2, 0, 4);
        // Calls a method
        assertEquals(4, buffer2.capacity());

        // Calls a method
        assertEquals(3.5f, buffer2.read(FLOAT));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyDirectIndexOffset() {
        // Calls a method
        var buffer1 = NetworkBuffer.staticBuffer(10);
        // Calls a method
        buffer1.write(INT, 6);
        // Calls a method
        buffer1.write(SHORT, (short) 2);
        // Calls a method
        buffer1.write(FLOAT, 3.5f);
        // Calls a method
        assertEquals(10, buffer1.writeIndex());
        // Calls a method
        assertEquals(10, buffer1.capacity());

        // Calls a method
        var buffer2 = NetworkBuffer.staticBuffer(8);
        // Calls a method
        buffer2.write(INT, 5);
        // Calls a method
        NetworkBuffer.copy(buffer1, 6, buffer2, 4, 4);
        // Calls a method
        assertEquals(8, buffer2.capacity());

        // Calls a method
        assertEquals(5, buffer2.read(INT));
        // Calls a method
        assertEquals(3.5f, buffer2.read(FLOAT));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compact() {
        // Calls a method
        var buffer = NetworkBuffer.staticBuffer(256);
        // Calls a method
        buffer.write(INT, 6);
        // Calls a method
        buffer.write(SHORT, (short) 2);
        // Calls a method
        buffer.write(FLOAT, 3.5f);

        // Calls a method
        buffer.read(INT);
        // Calls a method
        buffer.compact();
        // Short should be copied at index 0
        // Calls a method
        assertEquals(256, buffer.capacity());
        // Calls a method
        assertEquals(6, buffer.writeIndex());
        // Calls a method
        assertEquals(0, buffer.readIndex());

        // Calls a method
        assertEquals((short) 2, buffer.read(SHORT));
        // Calls a method
        assertEquals(3.5f, buffer.read(FLOAT));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void outOfBound() {
        // Calls a method
        var buffer = NetworkBuffer.staticBuffer(3);
        // Calls a method
        buffer.write(SHORT, (short) 2);
        // Calls a method
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.write(INT, 6));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void readableBytes() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        assertEquals(0, buffer.readableBytes());

        // Calls a method
        buffer.write(BYTE, (byte) 0);
        // Calls a method
        assertEquals(1, buffer.readableBytes());

        // Calls a method
        buffer.write(LONG, 50L);
        // Calls a method
        assertEquals(9, buffer.readableBytes());

        // Calls a method
        assertEquals((byte) 0, buffer.read(BYTE));
        // Calls a method
        assertEquals(8, buffer.readableBytes());

        // Calls a method
        assertEquals(50L, buffer.read(LONG));
        // Calls a method
        assertEquals(0, buffer.readableBytes());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void extractBytes() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer();

        // Calls a method
        buffer.write(BYTE, (byte) 25);
        // Calls a method
        assertEquals(1, buffer.writeIndex());
        // Calls a method
        assertEquals(0, buffer.readIndex());

        // Calls a method
        var array = buffer.extractBytes(extractor -> extractor.read(BYTE));
        // Calls a method
        assertArrayEquals(new byte[]{25}, array, "Unequal array: " + Arrays.toString(array));
        // Calls a method
        assertEquals(1, buffer.writeIndex());
        // Calls a method
        assertEquals(1, buffer.readIndex());

        // Calls a method
        buffer.write(BYTE, (byte) 25);
        // Calls a method
        buffer.write(LONG, 50L);
        // Calls a method
        assertEquals(10, buffer.writeIndex());
        // Calls a method
        assertEquals(1, buffer.readIndex());

        // Assigns a value
        array = buffer.extractBytes(extractor -> {
            // Calls a method
            extractor.read(BYTE);
            // Calls a method
            extractor.read(LONG);
        // End of a block/expression
        });
        // Calls a method
        assertArrayEquals(new byte[]{25, 0, 0, 0, 0, 0, 0, 0, 50}, array, "Unequal array: " + Arrays.toString(array));
        // Calls a method
        assertEquals(10, buffer.writeIndex());
        // Calls a method
        assertEquals(10, buffer.readIndex());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void makeArray() {
        // Start of a method/block
        assertArrayEquals(new byte[0], NetworkBuffer.makeArray(buffer -> {
        // Code statement
        }));

        // Calls a method
        assertArrayEquals(new byte[]{1}, NetworkBuffer.makeArray(BYTE, (byte) 1));

        // Start of a method/block
        assertArrayEquals(new byte[]{1, 0, 0, 0, 0, 0, 0, 0, 50}, NetworkBuffer.makeArray(buffer -> {
            // Calls a method
            buffer.write(BYTE, (byte) 1);
            // Calls a method
            buffer.write(LONG, 50L);
        // Code statement
        }));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void arrayWrap() {
        // Assigns a value
        byte[] array = new byte[]{1, 0, 0, 0, 0, 0, 0, 0, 50};
        // Calls a method
        var buffer = NetworkBuffer.wrap(array, 0, array.length);
        // Calls a method
        assertEquals(9, buffer.capacity());
        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Calls a method
        assertEquals(array.length, buffer.writeIndex());

        // Calls a method
        assertEquals((byte) 1, buffer.read(BYTE));
        // Calls a method
        assertEquals(50L, buffer.read(LONG));

        // Calls a method
        assertEquals(9, buffer.readIndex());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void segmentWrap() {
        // Exception handling
        try (var arena = Arena.ofConfined()) {
            // Calls a method
            final MemorySegment segment = arena.allocate(512);
            // Calls a method
            var buffer = NetworkBuffer.wrap(segment, 0L, 0L);
            // Calls a method
            assertEquals(0, buffer.writeIndex());
            // Calls a method
            assertEquals(0, buffer.readIndex());
            // Calls a method
            assertEquals(512,  buffer.capacity());
            // Calls a method
            assertFalse(buffer.isReadOnly());

            // Calls a method
            buffer.write(BYTE, (byte) 1);
            // Calls a method
            buffer.write(LONG, 50L);
            // Calls a method
            buffer.write(FLOAT, 3.5f);

            // Calls a method
            assertEquals(0, buffer.readIndex());
            // Calls a method
            assertEquals(13, buffer.writeIndex());

            // Calls a method
            assertEquals((byte) 1, buffer.read(BYTE));
            // Calls a method
            assertEquals(50L, buffer.read(LONG));
            // Calls a method
            assertEquals(3.5f, buffer.read(FLOAT));

            // Calls a method
            assertEquals(13, buffer.readIndex());
            // Calls a method
            assertEquals(13, buffer.writeIndex());

            // Rewrapping shouldn't carry anything except the data
            // Calls a method
            var buffer2 = NetworkBuffer.wrap(segment.asReadOnly(), 0L, 0L);
            // Calls a method
            assertEquals(0, buffer2.writeIndex());
            // Calls a method
            assertEquals(0, buffer2.readIndex());
            // Calls a method
            assertTrue(buffer2.isReadOnly());

            // Calls a method
            assertFalse(buffer.isReadOnly(), "OG buffer should still be writeable");

            // Calls a method
            buffer2.writeIndex(buffer.writeIndex());
            // Calls a method
            assertEquals(13,  buffer2.writeIndex());

            // Calls a method
            assertEquals((byte) 1, buffer2.read(BYTE));
            // Calls a method
            assertEquals(50L, buffer2.read(LONG));
            // Calls a method
            assertEquals(3.5f, buffer2.read(FLOAT));

            // Calls a method
            assertEquals(13, buffer2.readIndex());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void segmentWrapScope() {
        // Code statement
        NetworkBuffer buffer;
        // Exception handling
        try (var arena = Arena.ofConfined()) {
            // Calls a method
            final MemorySegment segment = arena.allocate(512);
            // Calls a method
            buffer = NetworkBuffer.wrap(segment, 0L, 0L);
            // Calls a method
            buffer.write(BYTE, (byte) 1);
        // End of a block/expression
        }
        // Calls a method
        assertThrows(Exception.class, () -> buffer.read(BYTE));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sizeOfPrimitives() {
        // Calls a method
        assertEquals(1, BYTE.sizeOf((byte) 1));
        // Calls a method
        assertEquals(2, SHORT.sizeOf((short) 1));
        // Calls a method
        assertEquals(4, INT.sizeOf(1));
        // Calls a method
        assertEquals(8, LONG.sizeOf(1L));
        // Calls a method
        assertEquals(4, FLOAT.sizeOf(1f));
        // Calls a method
        assertEquals(8, DOUBLE.sizeOf(1d));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sizeOfCompounds() {
        // Assigns a value
        var type = new Type<Integer>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, Integer value) {
                // Calls a method
                buffer.write(INT, value);
                // Calls a method
                buffer.write(INT, value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Integer read(NetworkBuffer buffer) {
                // Throws an exception
                throw new UnsupportedOperationException();
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        assertEquals(8, type.sizeOf(1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sizeOfThrow() {
        // Assigns a value
        Function<Consumer<NetworkBuffer>, Type<Integer>> fn = networkBufferConsumer -> new Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, Integer value) {
                // Calls a method
                networkBufferConsumer.accept(buffer);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Integer read(NetworkBuffer buffer) {
                // Throws an exception
                throw new UnsupportedOperationException();
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(buffer -> buffer.resize(2)).sizeOf(1));
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(buffer -> buffer.read(INT)).sizeOf(1));
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(buffer -> buffer.readAt(0, INT)).sizeOf(1));
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(NetworkBuffer::compact).sizeOf(1));
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(buffer -> buffer.copy(0, 0, 0, 0)).sizeOf(1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void numbers() {
        // Calls a method
        assertBufferType(BOOLEAN, false, new byte[]{0x00});
        // Calls a method
        assertBufferType(BOOLEAN, true, new byte[]{0x01});

        // Calls a method
        assertBufferType(BYTE, (byte) 0x00, new byte[]{0x00});
        // Calls a method
        assertBufferType(BYTE, (byte) 0x01, new byte[]{0x01});
        // Calls a method
        assertBufferType(BYTE, (byte) 0x7F, new byte[]{0x7F});
        // Calls a method
        assertBufferType(BYTE, (byte) 0x80, new byte[]{(byte) 0x80});
        // Calls a method
        assertBufferType(BYTE, (byte) 0xFF, new byte[]{(byte) 0xFF});

        // Calls a method
        assertBufferType(SHORT, (short) 0x0000, new byte[]{0x00, 0x00});
        // Calls a method
        assertBufferType(SHORT, (short) 0x0001, new byte[]{0x00, 0x01});
        // Calls a method
        assertBufferType(SHORT, (short) 0x7FFF, new byte[]{0x7F, (byte) 0xFF});
        // Calls a method
        assertBufferType(SHORT, (short) 0x8000, new byte[]{(byte) 0x80, 0x00});
        // Calls a method
        assertBufferType(SHORT, (short) 0xFFFF, new byte[]{(byte) 0xFF, (byte) 0xFF});

        // Calls a method
        assertBufferType(UNSIGNED_SHORT, 0x0000, new byte[]{0x00, 0x00});
        // Calls a method
        assertBufferType(UNSIGNED_SHORT, 0x0001, new byte[]{0x00, 0x01});
        // Calls a method
        assertBufferType(UNSIGNED_SHORT, 0x7FFF, new byte[]{0x7F, (byte) 0xFF});
        // Calls a method
        assertBufferType(UNSIGNED_SHORT, 0x8000, new byte[]{(byte) 0x80, 0x00});
        // Calls a method
        assertBufferType(UNSIGNED_SHORT, 0xFFFF, new byte[]{(byte) 0xFF, (byte) 0xFF});

        // Calls a method
        assertBufferType(INT, 0, new byte[]{0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(INT, 1, new byte[]{0x00, 0x00, 0x00, 0x01});
        // Calls a method
        assertBufferType(INT, 2, new byte[]{0x00, 0x00, 0x00, 0x02});
        // Calls a method
        assertBufferType(INT, 127, new byte[]{0x00, 0x00, 0x00, 0x7F});
        // Calls a method
        assertBufferType(INT, 128, new byte[]{0x00, 0x00, 0x00, (byte) 0x80});
        // Calls a method
        assertBufferType(INT, 255, new byte[]{0x00, 0x00, 0x00, (byte) 0xFF});
        // Calls a method
        assertBufferType(INT, 256, new byte[]{0x00, 0x00, 0x01, 0x00});
        // Calls a method
        assertBufferType(INT, 25565, new byte[]{0x00, 0x00, 0x63, (byte) 0xDD});
        // Calls a method
        assertBufferType(INT, 32767, new byte[]{0x00, 0x00, 0x7F, (byte) 0xFF});
        // Calls a method
        assertBufferType(INT, 32768, new byte[]{0x00, 0x00, (byte) 0x80, 0x00});
        // Calls a method
        assertBufferType(INT, 65535, new byte[]{0x00, 0x00, (byte) 0xFF, (byte) 0xFF});
        // Calls a method
        assertBufferType(INT, 65536, new byte[]{0x00, 0x01, 0x00, 0x00});
        // Calls a method
        assertBufferType(INT, 2147483647, new byte[]{0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Calls a method
        assertBufferType(INT, -1, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Calls a method
        assertBufferType(INT, -2147483648, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00});

        // Calls a method
        assertBufferType(LONG, 0L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(LONG, 1L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01});
        // Calls a method
        assertBufferType(LONG, 2L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02});
        // Calls a method
        assertBufferType(LONG, 127L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F});
        // Calls a method
        assertBufferType(LONG, 128L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80});
        // Calls a method
        assertBufferType(LONG, 255L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF});
        // Calls a method
        assertBufferType(LONG, 256L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00});
        // Calls a method
        assertBufferType(LONG, 25565L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x63, (byte) 0xDD});
        // Calls a method
        assertBufferType(LONG, 32767L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF});
        // Calls a method
        assertBufferType(LONG, 32768L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, 0x00});
        // Calls a method
        assertBufferType(LONG, 65535L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF});
        // Calls a method
        assertBufferType(LONG, 65536L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00});
        // Calls a method
        assertBufferType(LONG, 2147483647L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Calls a method
        assertBufferType(LONG, 2147483648L, new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0x80, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(LONG, 4294967295L, new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Calls a method
        assertBufferType(LONG, 4294967296L, new byte[]{0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(LONG, 9223372036854775807L, new byte[]{0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Calls a method
        assertBufferType(LONG, -1L, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Calls a method
        assertBufferType(LONG, -2147483648L, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(LONG, -4294967296L, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(LONG, -9223372036854775808L, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

        // Calls a method
        assertBufferType(FLOAT, 0f, new byte[]{0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 1f, new byte[]{0x3F, (byte) 0x80, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 1.1f, new byte[]{0x3F, (byte) 0x8C, (byte) 0xCC, (byte) 0xCD});
        // Calls a method
        assertBufferType(FLOAT, 1.5f, new byte[]{0x3F, (byte) 0xC0, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 1.6f, new byte[]{0x3F, (byte) 0xCC, (byte) 0xCC, (byte) 0xCD});
        // Calls a method
        assertBufferType(FLOAT, 2f, new byte[]{0x40, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 2.5f, new byte[]{0x40, 0x20, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 3f, new byte[]{0x40, 0x40, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 4f, new byte[]{0x40, (byte) 0x80, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 5f, new byte[]{0x40, (byte) 0xA0, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 10f, new byte[]{0x41, 0x20, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 100f, new byte[]{0x42, (byte) 0xC8, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 1000f, new byte[]{0x44, 0x7a, 0x00, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 10000f, new byte[]{0x46, 0x1C, 0x40, 0x00});
        // Calls a method
        assertBufferType(FLOAT, 100000f, new byte[]{0x47, (byte) 0xC3, 0x50, 0x00});

        // Calls a method
        assertBufferType(DOUBLE, 0d, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 1d, new byte[]{0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 1.1d, new byte[]{0x3F, (byte) 0xF1, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x9A});
        // Calls a method
        assertBufferType(DOUBLE, 1.5d, new byte[]{0x3F, (byte) 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 1.6d, new byte[]{0x3F, (byte) 0xF9, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x9A});
        // Calls a method
        assertBufferType(DOUBLE, 2d, new byte[]{0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 2.5d, new byte[]{0x40, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 3d, new byte[]{0x40, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 4d, new byte[]{0x40, 0x10, (byte) 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 5d, new byte[]{0x40, 0x14, (byte) 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 10d, new byte[]{0x40, 0x24, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 100d, new byte[]{0x40, 0x59, (byte) 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 1000d, new byte[]{0x40, (byte) 0x8F, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Calls a method
        assertBufferType(DOUBLE, 10000d, new byte[]{0x40, (byte) 0xC3, (byte) 0x88, 0x00, 0x00, 0x00, 0x00, 0x00});
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void varInt() {
        // Calls a method
        assertBufferType(VAR_INT, 0, new byte[]{0});
        // Calls a method
        assertBufferType(VAR_INT, 1, new byte[]{0x01});
        // Calls a method
        assertBufferType(VAR_INT, 2, new byte[]{0x02});
        // Calls a method
        assertBufferType(VAR_INT, 11, new byte[]{0x0B});
        // Calls a method
        assertBufferType(VAR_INT, 127, new byte[]{0x7f});
        // Calls a method
        assertBufferType(VAR_INT, 128, new byte[]{(byte) 0x80, 0x01});
        // Calls a method
        assertBufferType(VAR_INT, 255, new byte[]{(byte) 0xff, 0x01});
        // Calls a method
        assertBufferType(VAR_INT, 25565, new byte[]{(byte) 0xdd, (byte) 0xc7, 0x01});
        // Calls a method
        assertBufferType(VAR_INT, 2097151, new byte[]{(byte) 0xff, (byte) 0xff, 0x7f});
        // Calls a method
        assertBufferType(VAR_INT, 2147483647, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x07});
        // Calls a method
        assertBufferType(VAR_INT, -1, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x0f});
        // Calls a method
        assertBufferType(VAR_INT, -2147483648, new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08});
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void varLong() {
        // Calls a method
        assertBufferType(VAR_LONG, 0L, new byte[]{0});
        // Calls a method
        assertBufferType(VAR_LONG, 1L, new byte[]{0x01});
        // Calls a method
        assertBufferType(VAR_LONG, 2L, new byte[]{0x02});
        // Calls a method
        assertBufferType(VAR_LONG, 127L, new byte[]{0x7f});
        // Calls a method
        assertBufferType(VAR_LONG, 128L, new byte[]{(byte) 0x80, 0x01});
        // Calls a method
        assertBufferType(VAR_LONG, 255L, new byte[]{(byte) 0xff, 0x01});
        // Calls a method
        assertBufferType(VAR_LONG, 2147483647L, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x07});
        // Calls a method
        assertBufferType(VAR_LONG, 9223372036854775807L, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x7f});
        // Calls a method
        assertBufferType(VAR_LONG, -1L, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x01});
        // Calls a method
        assertBufferType(VAR_LONG, -2147483648L, new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0xf8, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x01});
        // Calls a method
        assertBufferType(VAR_LONG, -9223372036854775808L, new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x01});
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void variableLengthBounds() {
        // Calls a method
        var buffer = NetworkBuffer.staticBuffer(16);
        // Calls a method
        buffer.write(LONG, 0x8080808080808080L);
        // Calls a method
        buffer.write(LONG, 0x8080808080808080L);

        // Calls a method
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.read(VAR_INT));
        // Calls a method
        buffer.readIndex(0);
        // Code statement
        assertThrows(Exception.class, () -> buffer.read(VAR_LONG)); //todo: convert from runtime to index out of bounds
        // Calls a method
        buffer.readIndex(0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void rawBytes() {
        // Assigns a value
        var array = new byte[]{0x0B, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x57, 0x6f, 0x72, 0x6c, 0x64};
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        buffer.write(RAW_BYTES, array);
        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Calls a method
        assertEquals(array.length, buffer.writeIndex());

        // Calls a method
        var readArray = buffer.read(RAW_BYTES);
        // Calls a method
        assertArrayEquals(array, readArray);
        // Calls a method
        assertEquals(array.length, buffer.readIndex());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyTo() {
        // Assigns a value
        var array = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
        // Calls a method
        var buffer = NetworkBuffer.wrap(array, 0, 5);
        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Calls a method
        assertEquals(array.length, buffer.writeIndex());

        // Assigns a value
        byte[] dest = new byte[6];
        // Calls a method
        buffer.copyTo(1, dest, 2, 3);

        // Calls a method
        assertArrayEquals(new byte[]{0, 0, 0x02, 0x03, 0x04, 0}, dest);

        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Calls a method
        assertEquals(array.length, buffer.writeIndex());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyToSegment() {
        // Assigns a value
        var array = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
        // Calls a method
        var buffer = NetworkBuffer.wrap(array, 0, 5);
        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Calls a method
        assertEquals(array.length, buffer.writeIndex());

        // Exception handling
        try (var arena = Arena.ofConfined()) {
            // Calls a method
            MemorySegment dest = arena.allocate(6);
            // Calls a method
            buffer.copyTo(1, dest, 2, 3);

            // Calls a method
            assertEquals(0, buffer.readIndex());
            // Calls a method
            assertEquals((byte) 0x02, dest.get(ValueLayout.JAVA_BYTE, 2));
            // Calls a method
            assertEquals((byte) 0x03, dest.get(ValueLayout.JAVA_BYTE, 3));
            // Calls a method
            assertEquals((byte) 0x04, dest.get(ValueLayout.JAVA_BYTE, 4));
        // End of a block/expression
        }

        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Calls a method
        assertEquals(array.length, buffer.writeIndex());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void string() {
        // Calls a method
        assertBufferType(STRING, "Hello World", new byte[]{0x0B, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x57, 0x6f, 0x72, 0x6c, 0x64});
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nbt() {
        // Calls a method
        assertBufferType(NetworkBuffer.NBT, intBinaryTag(5));
        // Calls a method
        assertBufferType(NetworkBuffer.NBT, CompoundBinaryTag.from(Map.of("key", intBinaryTag(5))));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void component() {
        // Calls a method
        assertBufferType(COMPONENT, Component.text("Hello world"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void uuid() {
        // Calls a method
        assertBufferType(UUID, new UUID(0, 0), new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        // Calls a method
        assertBufferType(UUID, new UUID(1, 1), new byte[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1});
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void item() {
        // Calls a method
        assertBufferType(ItemStack.NETWORK_TYPE, ItemStack.AIR);
        // Calls a method
        assertBufferType(ItemStack.NETWORK_TYPE, ItemStack.of(Material.STONE, 1));
        // Calls a method
        assertBufferType(ItemStack.NETWORK_TYPE, ItemStack.of(Material.DIAMOND_AXE, 1).with(DataComponents.DAMAGE, 1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void optional() {
        // Calls a method
        assertBufferTypeOptional(BOOLEAN, null, new byte[]{0});
        // Calls a method
        assertBufferTypeOptional(BOOLEAN, true, new byte[]{1, 1});
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void collection() {
        // Calls a method
        assertBufferTypeCollection(BOOLEAN, List.of(), new byte[]{0});
        // Calls a method
        assertBufferTypeCollection(BOOLEAN, List.of(true), new byte[]{0x01, 0x01});
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void collectionMaxSize() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        var list = new ArrayList<Boolean>();
        // Loop: repeats a block
        for (int i = 0; i < 1000; i++)
            // Calls a method
            list.add(true);
        // Calls a method
        buffer.write(BOOLEAN.list(), list);

        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> buffer.read(BOOLEAN.list(10)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void maxLength() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer();

        // Calls a method
        buffer.write(BOOLEAN.maxLength(1), true);
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> buffer.write(INT.maxLength(3), 1));
        // Calls a method
        buffer.write(INT.maxLength(4), 1);

        // Calls a method
        assertTrue(buffer.read(BOOLEAN.maxLength(1)));
        // Calls a method
        assertEquals(1, buffer.read(INT.maxLength(4)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void maxLengthList() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> buffer.write(INT.list().maxLength(3), List.of(1)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> buffer.write(INT.list().maxLength(4), List.of(1)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> buffer.write(INT.list().maxLength(8), List.of(1, 2)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> buffer.write(INT.list().maxLength(4), List.of(1, 2)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> buffer.write(INT.list().maxLength(5), List.of(1, 2)));

        // Calls a method
        buffer.write(INT.list().maxLength(9), List.of(1));
        // Calls a method
        buffer.write(INT.list().maxLength(14), List.of(1, 2));

        // Calls a method
        assertEquals(List.of(1), buffer.read(INT.list().maxLength(9)));
        // Calls a method
        assertEquals(List.of(1, 2), buffer.read(INT.list().maxLength(14)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void oomStringRegression() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer(100);
        // Code statement
        buffer.write(VAR_INT, Integer.MAX_VALUE); // String length
        // Code statement
        buffer.write(RAW_BYTES, "Hello".getBytes(StandardCharsets.UTF_8)); // String data

        // Code statement
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING)); // oom
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void oomStringUtf8Regression() {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer(100);
        // Code statement
        buffer.write(UNSIGNED_SHORT, 65535); // String length
        // Code statement
        buffer.write(RAW_BYTES, "Hello".getBytes(StandardCharsets.UTF_8)); // String data

        // Code statement
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING)); // oom
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStringUtf8ModifiedWrite() throws IOException {
        // Calls a method
        var stream = new java.io.ByteArrayOutputStream();
        // Calls a method
        java.io.DataOutputStream out = new java.io.DataOutputStream(stream);
        // Calls a method
        out.writeUTF("Hello");

        // Calls a method
        assertBufferType(STRING_IO_UTF8, "Hello", stream.toByteArray());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStringUtf8ModifiedRead() throws IOException {
        // Calls a method
        var stream = new java.io.ByteArrayOutputStream();
        // Calls a method
        java.io.DataOutputStream out = new java.io.DataOutputStream(stream);
        // Calls a method
        out.writeUTF("Hello");
        // Calls a method
        var buffer = NetworkBuffer.wrap(stream.toByteArray(), 0, stream.size());
        // Calls a method
        assertEquals("Hello", buffer.read(STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void oomStringUtf8ModfiedRegression() throws IOException {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer(100);
        // Code statement
        buffer.write(UNSIGNED_SHORT, 65535); // String length
        // Write the raw bytes that are invalid
        // Code statement
        buffer.write(RAW_BYTES, new byte[]{(byte) 0xC0, (byte) 0x80}); // Invalid UTF-8

        // Code statement
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING_IO_UTF8)); // oom
        // Calls a method
        buffer.clear();

        // Calls a method
        var stream = new java.io.ByteArrayOutputStream();
        // Calls a method
        java.io.DataOutputStream out = new java.io.DataOutputStream(stream);
        // Calls a method
        out.writeUTF("Hello");
        // Calls a method
        var byteArray = stream.toByteArray();

        // Mess with the length to 0
        // Calls a method
        byteArray[0] = (byte) 0x00;
        // Calls a method
        byteArray[1] = (byte) 0x00;

        // Code statement
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING_IO_UTF8)); // oom

        // Calls a method
        buffer.clear();
        // Calls a method
        buffer.write(UNSIGNED_SHORT, 5);
        // Code statement
        buffer.write(RAW_BYTES, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}); // Invalid utf8

        // Code statement
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING_IO_UTF8)); // oom
    // End of a block/expression
    }

    // Start of a method/block
    static <T> void assertBufferType(NetworkBuffer.Type<T> type, @UnknownNullability T value, byte[] expected, Action<T> action) {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer(MinecraftServer.process());
        // Calls a method
        action.write(buffer, type, value);
        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Branch: checks a condition
        if (expected != null) assertEquals(expected.length, buffer.writeIndex());

        // Calls a method
        var actual = action.read(buffer, type);

        // Calls a method
        assertEquals(value, actual);
        // Branch: checks a condition
        if (expected != null) assertEquals(expected.length, buffer.readIndex(), "Invalid read index");
        // Branch: checks a condition
        if (expected != null) assertEquals(expected.length, buffer.writeIndex());

        // Branch: checks a condition
        if (expected != null) {
            // Assigns a value
            var bytes = new byte[expected.length];
            // Calls a method
            buffer.copyTo(0, bytes, 0, bytes.length);
            // Calls a method
            assertArrayEquals(expected, bytes, "Invalid bytes: " + Arrays.toString(expected) + " != " + Arrays.toString(bytes));
        // End of a block/expression
        }

        // Ensure resize support
        // Start of a block
        {
            // Calls a method
            var tmp = NetworkBuffer.resizableBuffer(0);
            // Calls a method
            action.write(tmp, type, value);
            // Calls a method
            assertEquals(0, tmp.readIndex());
            // Branch: checks a condition
            if (expected != null) assertEquals(expected.length, tmp.writeIndex());

            // Calls a method
            var tmpRead = action.read(tmp, type);

            // Calls a method
            assertEquals(value, tmpRead);
            // Branch: checks a condition
            if (expected != null) assertEquals(expected.length, tmp.readIndex(), "Invalid read index");
            // Branch: checks a condition
            if (expected != null) assertEquals(expected.length, tmp.writeIndex());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    static <T> void assertBufferType(NetworkBuffer.Type<T> type, T value, byte @Nullable [] expected) {
        // Start of a method/block
        assertBufferType(type, value, expected, new Action<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, NetworkBuffer.Type<T> type, @UnknownNullability T value) {
                // Calls a method
                buffer.write(type, value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public T read(NetworkBuffer buffer, NetworkBuffer.Type<T> type) {
                // Returns a value to the caller
                return buffer.read(type);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    static <T> void assertBufferType(NetworkBuffer.Type<T> type, T value) {
        // Calls a method
        assertBufferType(type, value, null);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> void assertBufferTypeOptional(NetworkBuffer.Type<T> type, @Nullable T value, byte @Nullable [] expected) {
        // Start of a method/block
        assertBufferType(type, value, expected, new Action<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, NetworkBuffer.Type<T> type, @UnknownNullability T value) {
                // Calls a method
                buffer.write(type.optional(), value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public T read(NetworkBuffer buffer, NetworkBuffer.Type<T> type) {
                // Returns a value to the caller
                return buffer.read(type.optional());
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    static <T> void assertBufferTypeOptional(NetworkBuffer.Type<T> type, @Nullable T value) {
        // Calls a method
        assertBufferTypeOptional(type, value, null);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> void assertBufferTypeCollection(NetworkBuffer.Type<T> type, List<T> values, byte @Nullable [] expected) {
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer(MinecraftServer.process());
        // Calls a method
        buffer.write(type.list(), values);
        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Branch: checks a condition
        if (expected != null) assertEquals(expected.length, buffer.writeIndex());

        // Calls a method
        var actual = buffer.read(type.list(Integer.MAX_VALUE));

        // Calls a method
        assertEquals(values, actual);
        // Branch: checks a condition
        if (expected != null) assertEquals(expected.length, buffer.readIndex());
        // Branch: checks a condition
        if (expected != null) assertEquals(expected.length, buffer.writeIndex());

        // Branch: checks a condition
        if (expected != null) {
            // Assigns a value
            var bytes = new byte[expected.length];
            // Calls a method
            buffer.copyTo(0, bytes, 0, bytes.length);
            // Calls a method
            assertArrayEquals(expected, bytes, "Invalid bytes: " + Arrays.toString(expected) + " != " + Arrays.toString(bytes));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    static <T> void assertBufferTypeCollection(NetworkBuffer.Type<T> type, List<T> value) {
        // Calls a method
        assertBufferTypeCollection(type, value, null);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    interface Action<T> {
        // Calls a method
        void write(NetworkBuffer buffer, NetworkBuffer.Type<T> type, @UnknownNullability T value);

        // Calls a method
        T read(NetworkBuffer buffer, NetworkBuffer.Type<T> type);
    // End of a block/expression
    }
// End of a block/expression
}
