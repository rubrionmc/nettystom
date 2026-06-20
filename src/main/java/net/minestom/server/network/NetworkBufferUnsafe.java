// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import sun.misc.Unsafe;

// Import d'une classe nécessaire
import java.lang.reflect.Field;
// Import d'une classe nécessaire
import java.nio.Buffer;
// Import d'une classe nécessaire
import java.nio.ByteBuffer;

// Déclaration de type (classe/interface/enum/record)
final class NetworkBufferUnsafe {
    // Instruction de code
    static final Unsafe UNSAFE;

    // Instruction de code
    static final Field ADDRESS, CAPACITY;
    // Instruction de code
    static final long ADDRESS_OFFSET, CAPACITY_OFFSET;

    // Début d'une méthode/d'un bloc
    static {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            // Appelle une méthode
            theUnsafe.setAccessible(true);
            // Appelle une méthode
            UNSAFE = (Unsafe) theUnsafe.get(null);
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }

        // Gestion des exceptions
        try {
            // Appelle une méthode
            ADDRESS = Buffer.class.getDeclaredField("address");
            // Appelle une méthode
            CAPACITY = Buffer.class.getDeclaredField("capacity");
            // Use Unsafe to read value of the address field. This way it will not fail on JDK9+ which
            // will forbid changing the access level via reflection.
            // Appelle une méthode
            ADDRESS_OFFSET = UNSAFE.objectFieldOffset(ADDRESS);
            // Appelle une méthode
            CAPACITY_OFFSET = UNSAFE.objectFieldOffset(CAPACITY);
        // Début d'une méthode/d'un bloc
        } catch (NoSuchFieldException e) {
            // Lève une exception
            throw new AssertionError(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * The offset, in bytes, between the base memory address of a byte array and its first element.
     */
    // Appelle une méthode
    static final long BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);

    // Début d'une méthode/d'un bloc
    static void updateAddress(ByteBuffer buffer, long address) {
        // Appelle une méthode
        UNSAFE.putLong(buffer, ADDRESS_OFFSET, address);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static void updateCapacity(ByteBuffer buffer, int capacity) {
        // Appelle une méthode
        UNSAFE.putInt(buffer, CAPACITY_OFFSET, capacity);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
