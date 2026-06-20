// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static net.kyori.adventure.nbt.IntBinaryTag.intBinaryTag;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class NetworkBufferTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void resize() {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer(6);
        // Appelle une méthode
        buffer.write(INT, 6);
        // Appelle une méthode
        assertEquals(4, buffer.writeIndex());

        // Appelle une méthode
        buffer.write(INT, 7);
        // Appelle une méthode
        assertEquals(8, buffer.writeIndex());

        // Appelle une méthode
        assertEquals(6, buffer.read(INT));
        // Appelle une méthode
        assertEquals(7, buffer.read(INT));

        // Test one-off length
        // Appelle une méthode
        buffer = NetworkBuffer.resizableBuffer(1);
        // Appelle une méthode
        buffer.write(BYTE, (byte) 3);
        // Appelle une méthode
        assertEquals(1, buffer.writeIndex());

        // Appelle une méthode
        buffer.write(BYTE, (byte) 4);
        // Appelle une méthode
        assertEquals(2, buffer.writeIndex());

        // Appelle une méthode
        assertEquals((byte) 3, buffer.read(BYTE));
        // Appelle une méthode
        assertEquals((byte) 4, buffer.read(BYTE));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void resizeRead() {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer(4);
        // Appelle une méthode
        buffer.write(INT, 6);
        // Appelle une méthode
        assertEquals(4, buffer.capacity());
        // Appelle une méthode
        assertEquals(4, buffer.writeIndex());

        // Appelle une méthode
        buffer.resize(8);
        // Appelle une méthode
        assertEquals(8, buffer.capacity());
        // Appelle une méthode
        assertEquals(6, buffer.read(INT));

        // Appelle une méthode
        buffer.write(INT, 7);
        // Appelle une méthode
        assertEquals(8, buffer.capacity());
        // Appelle une méthode
        assertEquals(8, buffer.writeIndex());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyClone() {
        // Appelle une méthode
        var buffer = NetworkBuffer.staticBuffer(10);
        // Appelle une méthode
        buffer.write(INT, 6);
        // Appelle une méthode
        buffer.write(SHORT, (short) 2);
        // Appelle une méthode
        buffer.write(FLOAT, 3.5f);
        // Appelle une méthode
        assertEquals(10, buffer.writeIndex());
        // Appelle une méthode
        assertEquals(10, buffer.capacity());

        // Appelle une méthode
        var copy = buffer.copy(0, 10);
        // Appelle une méthode
        assertEquals(10, copy.writeIndex());
        // Appelle une méthode
        assertEquals(10, copy.capacity());

        // Appelle une méthode
        assertTrue(NetworkBuffer.equals(buffer, copy));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyDirectZeroIndex() {
        // Appelle une méthode
        var buffer1 = NetworkBuffer.staticBuffer(10);
        // Appelle une méthode
        buffer1.write(INT, 6);
        // Appelle une méthode
        buffer1.write(SHORT, (short) 2);
        // Appelle une méthode
        buffer1.write(FLOAT, 3.5f);
        // Appelle une méthode
        assertEquals(10, buffer1.writeIndex());
        // Appelle une méthode
        assertEquals(10, buffer1.capacity());

        // Appelle une méthode
        var buffer2 = NetworkBuffer.staticBuffer(10);
        // Appelle une méthode
        NetworkBuffer.copy(buffer1, 0, buffer2, 0, 10);
        // Appelle une méthode
        assertEquals(10, buffer2.capacity());

        // Appelle une méthode
        assertEquals(6, buffer2.read(INT));
        // Appelle une méthode
        assertEquals((short) 2, buffer2.read(SHORT));
        // Appelle une méthode
        assertEquals(3.5f, buffer2.read(FLOAT));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyDirectIndex() {
        // Appelle une méthode
        var buffer1 = NetworkBuffer.staticBuffer(10);
        // Appelle une méthode
        buffer1.write(INT, 6);
        // Appelle une méthode
        buffer1.write(SHORT, (short) 2);
        // Appelle une méthode
        buffer1.write(FLOAT, 3.5f);
        // Appelle une méthode
        assertEquals(10, buffer1.writeIndex());
        // Appelle une méthode
        assertEquals(10, buffer1.capacity());

        // Appelle une méthode
        var buffer2 = NetworkBuffer.staticBuffer(4);
        // Appelle une méthode
        NetworkBuffer.copy(buffer1, 6, buffer2, 0, 4);
        // Appelle une méthode
        assertEquals(4, buffer2.capacity());

        // Appelle une méthode
        assertEquals(3.5f, buffer2.read(FLOAT));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyDirectIndexOffset() {
        // Appelle une méthode
        var buffer1 = NetworkBuffer.staticBuffer(10);
        // Appelle une méthode
        buffer1.write(INT, 6);
        // Appelle une méthode
        buffer1.write(SHORT, (short) 2);
        // Appelle une méthode
        buffer1.write(FLOAT, 3.5f);
        // Appelle une méthode
        assertEquals(10, buffer1.writeIndex());
        // Appelle une méthode
        assertEquals(10, buffer1.capacity());

        // Appelle une méthode
        var buffer2 = NetworkBuffer.staticBuffer(8);
        // Appelle une méthode
        buffer2.write(INT, 5);
        // Appelle une méthode
        NetworkBuffer.copy(buffer1, 6, buffer2, 4, 4);
        // Appelle une méthode
        assertEquals(8, buffer2.capacity());

        // Appelle une méthode
        assertEquals(5, buffer2.read(INT));
        // Appelle une méthode
        assertEquals(3.5f, buffer2.read(FLOAT));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void compact() {
        // Appelle une méthode
        var buffer = NetworkBuffer.staticBuffer(256);
        // Appelle une méthode
        buffer.write(INT, 6);
        // Appelle une méthode
        buffer.write(SHORT, (short) 2);
        // Appelle une méthode
        buffer.write(FLOAT, 3.5f);

        // Appelle une méthode
        buffer.read(INT);
        // Appelle une méthode
        buffer.compact();
        // Short should be copied at index 0
        // Appelle une méthode
        assertEquals(256, buffer.capacity());
        // Appelle une méthode
        assertEquals(6, buffer.writeIndex());
        // Appelle une méthode
        assertEquals(0, buffer.readIndex());

        // Appelle une méthode
        assertEquals((short) 2, buffer.read(SHORT));
        // Appelle une méthode
        assertEquals(3.5f, buffer.read(FLOAT));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void outOfBound() {
        // Appelle une méthode
        var buffer = NetworkBuffer.staticBuffer(3);
        // Appelle une méthode
        buffer.write(SHORT, (short) 2);
        // Appelle une méthode
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.write(INT, 6));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void readableBytes() {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        assertEquals(0, buffer.readableBytes());

        // Appelle une méthode
        buffer.write(BYTE, (byte) 0);
        // Appelle une méthode
        assertEquals(1, buffer.readableBytes());

        // Appelle une méthode
        buffer.write(LONG, 50L);
        // Appelle une méthode
        assertEquals(9, buffer.readableBytes());

        // Appelle une méthode
        assertEquals((byte) 0, buffer.read(BYTE));
        // Appelle une méthode
        assertEquals(8, buffer.readableBytes());

        // Appelle une méthode
        assertEquals(50L, buffer.read(LONG));
        // Appelle une méthode
        assertEquals(0, buffer.readableBytes());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void extractBytes() {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer();

        // Appelle une méthode
        buffer.write(BYTE, (byte) 25);
        // Appelle une méthode
        assertEquals(1, buffer.writeIndex());
        // Appelle une méthode
        assertEquals(0, buffer.readIndex());

        // Appelle une méthode
        var array = buffer.extractBytes(extractor -> extractor.read(BYTE));
        // Appelle une méthode
        assertArrayEquals(new byte[]{25}, array, "Unequal array: " + Arrays.toString(array));
        // Appelle une méthode
        assertEquals(1, buffer.writeIndex());
        // Appelle une méthode
        assertEquals(1, buffer.readIndex());

        // Appelle une méthode
        buffer.write(BYTE, (byte) 25);
        // Appelle une méthode
        buffer.write(LONG, 50L);
        // Appelle une méthode
        assertEquals(10, buffer.writeIndex());
        // Appelle une méthode
        assertEquals(1, buffer.readIndex());

        // Affecte une valeur
        array = buffer.extractBytes(extractor -> {
            // Appelle une méthode
            extractor.read(BYTE);
            // Appelle une méthode
            extractor.read(LONG);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertArrayEquals(new byte[]{25, 0, 0, 0, 0, 0, 0, 0, 50}, array, "Unequal array: " + Arrays.toString(array));
        // Appelle une méthode
        assertEquals(10, buffer.writeIndex());
        // Appelle une méthode
        assertEquals(10, buffer.readIndex());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void makeArray() {
        // Début d'une méthode/d'un bloc
        assertArrayEquals(new byte[0], NetworkBuffer.makeArray(buffer -> {
        // Instruction de code
        }));

        // Appelle une méthode
        assertArrayEquals(new byte[]{1}, NetworkBuffer.makeArray(BYTE, (byte) 1));

        // Début d'une méthode/d'un bloc
        assertArrayEquals(new byte[]{1, 0, 0, 0, 0, 0, 0, 0, 50}, NetworkBuffer.makeArray(buffer -> {
            // Appelle une méthode
            buffer.write(BYTE, (byte) 1);
            // Appelle une méthode
            buffer.write(LONG, 50L);
        // Instruction de code
        }));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void arrayWrap() {
        // Affecte une valeur
        byte[] array = new byte[]{1, 0, 0, 0, 0, 0, 0, 0, 50};
        // Appelle une méthode
        var buffer = NetworkBuffer.wrap(array, 0, array.length);
        // Appelle une méthode
        assertEquals(9, buffer.capacity());
        // Appelle une méthode
        assertEquals(0, buffer.readIndex());
        // Appelle une méthode
        assertEquals(array.length, buffer.writeIndex());

        // Appelle une méthode
        assertEquals((byte) 1, buffer.read(BYTE));
        // Appelle une méthode
        assertEquals(50L, buffer.read(LONG));

        // Appelle une méthode
        assertEquals(9, buffer.readIndex());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sizeOfPrimitives() {
        // Appelle une méthode
        assertEquals(1, BYTE.sizeOf((byte) 1));
        // Appelle une méthode
        assertEquals(2, SHORT.sizeOf((short) 1));
        // Appelle une méthode
        assertEquals(4, INT.sizeOf(1));
        // Appelle une méthode
        assertEquals(8, LONG.sizeOf(1L));
        // Appelle une méthode
        assertEquals(4, FLOAT.sizeOf(1f));
        // Appelle une méthode
        assertEquals(8, DOUBLE.sizeOf(1d));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sizeOfCompounds() {
        // Affecte une valeur
        var type = new Type<Integer>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Integer value) {
                // Appelle une méthode
                buffer.write(INT, value);
                // Appelle une méthode
                buffer.write(INT, value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Integer read(NetworkBuffer buffer) {
                // Lève une exception
                throw new UnsupportedOperationException();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        assertEquals(8, type.sizeOf(1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sizeOfThrow() {
        // Affecte une valeur
        Function<Consumer<NetworkBuffer>, Type<Integer>> fn = networkBufferConsumer -> new Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Integer value) {
                // Appelle une méthode
                networkBufferConsumer.accept(buffer);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Integer read(NetworkBuffer buffer) {
                // Lève une exception
                throw new UnsupportedOperationException();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(buffer -> buffer.resize(2)).sizeOf(1));
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(buffer -> buffer.read(INT)).sizeOf(1));
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(buffer -> buffer.readAt(0, INT)).sizeOf(1));
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(NetworkBuffer::compact).sizeOf(1));
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> fn.apply(buffer -> buffer.copy(0, 0, 0, 0)).sizeOf(1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void numbers() {
        // Appelle une méthode
        assertBufferType(BOOLEAN, false, new byte[]{0x00});
        // Appelle une méthode
        assertBufferType(BOOLEAN, true, new byte[]{0x01});

        // Appelle une méthode
        assertBufferType(BYTE, (byte) 0x00, new byte[]{0x00});
        // Appelle une méthode
        assertBufferType(BYTE, (byte) 0x01, new byte[]{0x01});
        // Appelle une méthode
        assertBufferType(BYTE, (byte) 0x7F, new byte[]{0x7F});
        // Appelle une méthode
        assertBufferType(BYTE, (byte) 0x80, new byte[]{(byte) 0x80});
        // Appelle une méthode
        assertBufferType(BYTE, (byte) 0xFF, new byte[]{(byte) 0xFF});

        // Appelle une méthode
        assertBufferType(SHORT, (short) 0x0000, new byte[]{0x00, 0x00});
        // Appelle une méthode
        assertBufferType(SHORT, (short) 0x0001, new byte[]{0x00, 0x01});
        // Appelle une méthode
        assertBufferType(SHORT, (short) 0x7FFF, new byte[]{0x7F, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(SHORT, (short) 0x8000, new byte[]{(byte) 0x80, 0x00});
        // Appelle une méthode
        assertBufferType(SHORT, (short) 0xFFFF, new byte[]{(byte) 0xFF, (byte) 0xFF});

        // Appelle une méthode
        assertBufferType(UNSIGNED_SHORT, 0x0000, new byte[]{0x00, 0x00});
        // Appelle une méthode
        assertBufferType(UNSIGNED_SHORT, 0x0001, new byte[]{0x00, 0x01});
        // Appelle une méthode
        assertBufferType(UNSIGNED_SHORT, 0x7FFF, new byte[]{0x7F, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(UNSIGNED_SHORT, 0x8000, new byte[]{(byte) 0x80, 0x00});
        // Appelle une méthode
        assertBufferType(UNSIGNED_SHORT, 0xFFFF, new byte[]{(byte) 0xFF, (byte) 0xFF});

        // Appelle une méthode
        assertBufferType(INT, 0, new byte[]{0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(INT, 1, new byte[]{0x00, 0x00, 0x00, 0x01});
        // Appelle une méthode
        assertBufferType(INT, 2, new byte[]{0x00, 0x00, 0x00, 0x02});
        // Appelle une méthode
        assertBufferType(INT, 127, new byte[]{0x00, 0x00, 0x00, 0x7F});
        // Appelle une méthode
        assertBufferType(INT, 128, new byte[]{0x00, 0x00, 0x00, (byte) 0x80});
        // Appelle une méthode
        assertBufferType(INT, 255, new byte[]{0x00, 0x00, 0x00, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(INT, 256, new byte[]{0x00, 0x00, 0x01, 0x00});
        // Appelle une méthode
        assertBufferType(INT, 25565, new byte[]{0x00, 0x00, 0x63, (byte) 0xDD});
        // Appelle une méthode
        assertBufferType(INT, 32767, new byte[]{0x00, 0x00, 0x7F, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(INT, 32768, new byte[]{0x00, 0x00, (byte) 0x80, 0x00});
        // Appelle une méthode
        assertBufferType(INT, 65535, new byte[]{0x00, 0x00, (byte) 0xFF, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(INT, 65536, new byte[]{0x00, 0x01, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(INT, 2147483647, new byte[]{0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(INT, -1, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(INT, -2147483648, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00});

        // Appelle une méthode
        assertBufferType(LONG, 0L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(LONG, 1L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01});
        // Appelle une méthode
        assertBufferType(LONG, 2L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02});
        // Appelle une méthode
        assertBufferType(LONG, 127L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F});
        // Appelle une méthode
        assertBufferType(LONG, 128L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80});
        // Appelle une méthode
        assertBufferType(LONG, 255L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(LONG, 256L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00});
        // Appelle une méthode
        assertBufferType(LONG, 25565L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x63, (byte) 0xDD});
        // Appelle une méthode
        assertBufferType(LONG, 32767L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(LONG, 32768L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, 0x00});
        // Appelle une méthode
        assertBufferType(LONG, 65535L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(LONG, 65536L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(LONG, 2147483647L, new byte[]{0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(LONG, 2147483648L, new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0x80, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(LONG, 4294967295L, new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(LONG, 4294967296L, new byte[]{0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(LONG, 9223372036854775807L, new byte[]{0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(LONG, -1L, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        // Appelle une méthode
        assertBufferType(LONG, -2147483648L, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(LONG, -4294967296L, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(LONG, -9223372036854775808L, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

        // Appelle une méthode
        assertBufferType(FLOAT, 0f, new byte[]{0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 1f, new byte[]{0x3F, (byte) 0x80, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 1.1f, new byte[]{0x3F, (byte) 0x8C, (byte) 0xCC, (byte) 0xCD});
        // Appelle une méthode
        assertBufferType(FLOAT, 1.5f, new byte[]{0x3F, (byte) 0xC0, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 1.6f, new byte[]{0x3F, (byte) 0xCC, (byte) 0xCC, (byte) 0xCD});
        // Appelle une méthode
        assertBufferType(FLOAT, 2f, new byte[]{0x40, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 2.5f, new byte[]{0x40, 0x20, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 3f, new byte[]{0x40, 0x40, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 4f, new byte[]{0x40, (byte) 0x80, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 5f, new byte[]{0x40, (byte) 0xA0, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 10f, new byte[]{0x41, 0x20, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 100f, new byte[]{0x42, (byte) 0xC8, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 1000f, new byte[]{0x44, 0x7a, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 10000f, new byte[]{0x46, 0x1C, 0x40, 0x00});
        // Appelle une méthode
        assertBufferType(FLOAT, 100000f, new byte[]{0x47, (byte) 0xC3, 0x50, 0x00});

        // Appelle une méthode
        assertBufferType(DOUBLE, 0d, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 1d, new byte[]{0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 1.1d, new byte[]{0x3F, (byte) 0xF1, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x9A});
        // Appelle une méthode
        assertBufferType(DOUBLE, 1.5d, new byte[]{0x3F, (byte) 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 1.6d, new byte[]{0x3F, (byte) 0xF9, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x9A});
        // Appelle une méthode
        assertBufferType(DOUBLE, 2d, new byte[]{0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 2.5d, new byte[]{0x40, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 3d, new byte[]{0x40, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 4d, new byte[]{0x40, 0x10, (byte) 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 5d, new byte[]{0x40, 0x14, (byte) 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 10d, new byte[]{0x40, 0x24, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 100d, new byte[]{0x40, 0x59, (byte) 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 1000d, new byte[]{0x40, (byte) 0x8F, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00});
        // Appelle une méthode
        assertBufferType(DOUBLE, 10000d, new byte[]{0x40, (byte) 0xC3, (byte) 0x88, 0x00, 0x00, 0x00, 0x00, 0x00});
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void varInt() {
        // Appelle une méthode
        assertBufferType(VAR_INT, 0, new byte[]{0});
        // Appelle une méthode
        assertBufferType(VAR_INT, 1, new byte[]{0x01});
        // Appelle une méthode
        assertBufferType(VAR_INT, 2, new byte[]{0x02});
        // Appelle une méthode
        assertBufferType(VAR_INT, 11, new byte[]{0x0B});
        // Appelle une méthode
        assertBufferType(VAR_INT, 127, new byte[]{0x7f});
        // Appelle une méthode
        assertBufferType(VAR_INT, 128, new byte[]{(byte) 0x80, 0x01});
        // Appelle une méthode
        assertBufferType(VAR_INT, 255, new byte[]{(byte) 0xff, 0x01});
        // Appelle une méthode
        assertBufferType(VAR_INT, 25565, new byte[]{(byte) 0xdd, (byte) 0xc7, 0x01});
        // Appelle une méthode
        assertBufferType(VAR_INT, 2097151, new byte[]{(byte) 0xff, (byte) 0xff, 0x7f});
        // Appelle une méthode
        assertBufferType(VAR_INT, 2147483647, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x07});
        // Appelle une méthode
        assertBufferType(VAR_INT, -1, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x0f});
        // Appelle une méthode
        assertBufferType(VAR_INT, -2147483648, new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08});
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void varLong() {
        // Appelle une méthode
        assertBufferType(VAR_LONG, 0L, new byte[]{0});
        // Appelle une méthode
        assertBufferType(VAR_LONG, 1L, new byte[]{0x01});
        // Appelle une méthode
        assertBufferType(VAR_LONG, 2L, new byte[]{0x02});
        // Appelle une méthode
        assertBufferType(VAR_LONG, 127L, new byte[]{0x7f});
        // Appelle une méthode
        assertBufferType(VAR_LONG, 128L, new byte[]{(byte) 0x80, 0x01});
        // Appelle une méthode
        assertBufferType(VAR_LONG, 255L, new byte[]{(byte) 0xff, 0x01});
        // Appelle une méthode
        assertBufferType(VAR_LONG, 2147483647L, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x07});
        // Appelle une méthode
        assertBufferType(VAR_LONG, 9223372036854775807L, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x7f});
        // Appelle une méthode
        assertBufferType(VAR_LONG, -1L, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x01});
        // Appelle une méthode
        assertBufferType(VAR_LONG, -2147483648L, new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0xf8, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x01});
        // Appelle une méthode
        assertBufferType(VAR_LONG, -9223372036854775808L, new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x01});
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void rawBytes() {
        // Affecte une valeur
        var array = new byte[]{0x0B, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x57, 0x6f, 0x72, 0x6c, 0x64};
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        buffer.write(RAW_BYTES, array);
        // Appelle une méthode
        assertEquals(0, buffer.readIndex());
        // Appelle une méthode
        assertEquals(array.length, buffer.writeIndex());

        // Appelle une méthode
        var readArray = buffer.read(RAW_BYTES);
        // Appelle une méthode
        assertArrayEquals(array, readArray);
        // Appelle une méthode
        assertEquals(array.length, buffer.readIndex());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void string() {
        // Appelle une méthode
        assertBufferType(STRING, "Hello World", new byte[]{0x0B, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x57, 0x6f, 0x72, 0x6c, 0x64});
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nbt() {
        // Appelle une méthode
        assertBufferType(NetworkBuffer.NBT, intBinaryTag(5));
        // Appelle une méthode
        assertBufferType(NetworkBuffer.NBT, CompoundBinaryTag.from(Map.of("key", intBinaryTag(5))));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void component() {
        // Appelle une méthode
        assertBufferType(COMPONENT, Component.text("Hello world"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void uuid() {
        // Appelle une méthode
        assertBufferType(UUID, new UUID(0, 0), new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        // Appelle une méthode
        assertBufferType(UUID, new UUID(1, 1), new byte[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1});
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void item() {
        // Appelle une méthode
        assertBufferType(ItemStack.NETWORK_TYPE, ItemStack.AIR);
        // Appelle une méthode
        assertBufferType(ItemStack.NETWORK_TYPE, ItemStack.of(Material.STONE, 1));
        // Appelle une méthode
        assertBufferType(ItemStack.NETWORK_TYPE, ItemStack.of(Material.DIAMOND_AXE, 1).with(DataComponents.DAMAGE, 1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void optional() {
        // Appelle une méthode
        assertBufferTypeOptional(BOOLEAN, null, new byte[]{0});
        // Appelle une méthode
        assertBufferTypeOptional(BOOLEAN, true, new byte[]{1, 1});
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void collection() {
        // Appelle une méthode
        assertBufferTypeCollection(BOOLEAN, List.of(), new byte[]{0});
        // Appelle une méthode
        assertBufferTypeCollection(BOOLEAN, List.of(true), new byte[]{0x01, 0x01});
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void collectionMaxSize() {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer();
        // Affecte une valeur
        var list = new ArrayList<Boolean>();
        // Boucle : répète un bloc
        for (int i = 0; i < 1000; i++)
            // Appelle une méthode
            list.add(true);
        // Appelle une méthode
        buffer.write(BOOLEAN.list(), list);

        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> buffer.read(BOOLEAN.list(10)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void oomStringRegression() {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer(100);
        // Instruction de code
        buffer.write(VAR_INT, Integer.MAX_VALUE); // String length
        // Instruction de code
        buffer.write(RAW_BYTES, "Hello".getBytes(StandardCharsets.UTF_8)); // String data

        // Instruction de code
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING)); // oom
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void oomStringUtf8Regression() {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer(100);
        // Instruction de code
        buffer.write(UNSIGNED_SHORT, 65535); // String length
        // Instruction de code
        buffer.write(RAW_BYTES, "Hello".getBytes(StandardCharsets.UTF_8)); // String data

        // Instruction de code
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING)); // oom
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStringUtf8ModifiedWrite() throws IOException {
        // Appelle une méthode
        var stream = new java.io.ByteArrayOutputStream();
        // Appelle une méthode
        java.io.DataOutputStream out = new java.io.DataOutputStream(stream);
        // Appelle une méthode
        out.writeUTF("Hello");

        // Appelle une méthode
        assertBufferType(STRING_IO_UTF8, "Hello", stream.toByteArray());
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStringUtf8ModifiedRead() throws IOException {
        // Appelle une méthode
        var stream = new java.io.ByteArrayOutputStream();
        // Appelle une méthode
        java.io.DataOutputStream out = new java.io.DataOutputStream(stream);
        // Appelle une méthode
        out.writeUTF("Hello");
        // Appelle une méthode
        var buffer = NetworkBuffer.wrap(stream.toByteArray(), 0, stream.size());
        // Appelle une méthode
        assertEquals("Hello", buffer.read(STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void oomStringUtf8ModfiedRegression() throws IOException {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer(100);
        // Instruction de code
        buffer.write(UNSIGNED_SHORT, 65535); // String length
        // Write the raw bytes that are invalid
        // Instruction de code
        buffer.write(RAW_BYTES, new byte[]{(byte) 0xC0, (byte) 0x80}); // Invalid UTF-8

        // Instruction de code
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING_IO_UTF8)); // oom
        // Appelle une méthode
        buffer.clear();

        // Appelle une méthode
        var stream = new java.io.ByteArrayOutputStream();
        // Appelle une méthode
        java.io.DataOutputStream out = new java.io.DataOutputStream(stream);
        // Appelle une méthode
        out.writeUTF("Hello");
        // Appelle une méthode
        var byteArray = stream.toByteArray();

        // Mess with the length to 0
        // Affecte une valeur
        byteArray[0] = (byte) 0x00;
        // Affecte une valeur
        byteArray[1] = (byte) 0x00;

        // Instruction de code
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING_IO_UTF8)); // oom

        // Appelle une méthode
        buffer.clear();
        // Appelle une méthode
        buffer.write(UNSIGNED_SHORT, 5);
        // Instruction de code
        buffer.write(RAW_BYTES, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}); // Invalid utf8

        // Instruction de code
        assertThrows(IllegalArgumentException.class, () -> buffer.read(STRING_IO_UTF8)); // oom
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> void assertBufferType(NetworkBuffer.Type<T> type, @UnknownNullability T value, byte[] expected, Action<T> action) {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer(MinecraftServer.process());
        // Appelle une méthode
        action.write(buffer, type, value);
        // Appelle une méthode
        assertEquals(0, buffer.readIndex());
        // Embranchement : vérifie une condition
        if (expected != null) assertEquals(expected.length, buffer.writeIndex());

        // Appelle une méthode
        var actual = action.read(buffer, type);

        // Appelle une méthode
        assertEquals(value, actual);
        // Embranchement : vérifie une condition
        if (expected != null) assertEquals(expected.length, buffer.readIndex(), "Invalid read index");
        // Embranchement : vérifie une condition
        if (expected != null) assertEquals(expected.length, buffer.writeIndex());

        // Embranchement : vérifie une condition
        if (expected != null) {
            // Affecte une valeur
            var bytes = new byte[expected.length];
            // Appelle une méthode
            buffer.copyTo(0, bytes, 0, bytes.length);
            // Appelle une méthode
            assertArrayEquals(expected, bytes, "Invalid bytes: " + Arrays.toString(expected) + " != " + Arrays.toString(bytes));
        // Fin d'un bloc/d'une expression
        }

        // Ensure resize support
        // Début d'un bloc
        {
            // Appelle une méthode
            var tmp = NetworkBuffer.resizableBuffer(0);
            // Appelle une méthode
            action.write(tmp, type, value);
            // Appelle une méthode
            assertEquals(0, tmp.readIndex());
            // Embranchement : vérifie une condition
            if (expected != null) assertEquals(expected.length, tmp.writeIndex());

            // Appelle une méthode
            var tmpRead = action.read(tmp, type);

            // Appelle une méthode
            assertEquals(value, tmpRead);
            // Embranchement : vérifie une condition
            if (expected != null) assertEquals(expected.length, tmp.readIndex(), "Invalid read index");
            // Embranchement : vérifie une condition
            if (expected != null) assertEquals(expected.length, tmp.writeIndex());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> void assertBufferType(NetworkBuffer.Type<T> type, T value, byte @Nullable [] expected) {
        // Début d'une méthode/d'un bloc
        assertBufferType(type, value, expected, new Action<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, NetworkBuffer.Type<T> type, @UnknownNullability T value) {
                // Appelle une méthode
                buffer.write(type, value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public T read(NetworkBuffer buffer, NetworkBuffer.Type<T> type) {
                // Renvoie une valeur à l'appelant
                return buffer.read(type);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> void assertBufferType(NetworkBuffer.Type<T> type, T value) {
        // Appelle une méthode
        assertBufferType(type, value, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> void assertBufferTypeOptional(NetworkBuffer.Type<T> type, @Nullable T value, byte @Nullable [] expected) {
        // Début d'une méthode/d'un bloc
        assertBufferType(type, value, expected, new Action<T>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, NetworkBuffer.Type<T> type, @UnknownNullability T value) {
                // Appelle une méthode
                buffer.write(type.optional(), value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public T read(NetworkBuffer buffer, NetworkBuffer.Type<T> type) {
                // Renvoie une valeur à l'appelant
                return buffer.read(type.optional());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> void assertBufferTypeOptional(NetworkBuffer.Type<T> type, @Nullable T value) {
        // Appelle une méthode
        assertBufferTypeOptional(type, value, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> void assertBufferTypeCollection(NetworkBuffer.Type<T> type, List<T> values, byte @Nullable [] expected) {
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer(MinecraftServer.process());
        // Appelle une méthode
        buffer.write(type.list(), values);
        // Appelle une méthode
        assertEquals(0, buffer.readIndex());
        // Embranchement : vérifie une condition
        if (expected != null) assertEquals(expected.length, buffer.writeIndex());

        // Appelle une méthode
        var actual = buffer.read(type.list(Integer.MAX_VALUE));

        // Appelle une méthode
        assertEquals(values, actual);
        // Embranchement : vérifie une condition
        if (expected != null) assertEquals(expected.length, buffer.readIndex());
        // Embranchement : vérifie une condition
        if (expected != null) assertEquals(expected.length, buffer.writeIndex());

        // Embranchement : vérifie une condition
        if (expected != null) {
            // Affecte une valeur
            var bytes = new byte[expected.length];
            // Appelle une méthode
            buffer.copyTo(0, bytes, 0, bytes.length);
            // Appelle une méthode
            assertArrayEquals(expected, bytes, "Invalid bytes: " + Arrays.toString(expected) + " != " + Arrays.toString(bytes));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> void assertBufferTypeCollection(NetworkBuffer.Type<T> type, List<T> value) {
        // Appelle une méthode
        assertBufferTypeCollection(type, value, null);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    interface Action<T> {
        // Appelle une méthode
        void write(NetworkBuffer buffer, NetworkBuffer.Type<T> type, @UnknownNullability T value);

        // Appelle une méthode
        T read(NetworkBuffer buffer, NetworkBuffer.Type<T> type);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
