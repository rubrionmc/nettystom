// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
class UUIDUtilsTest {
    // Appelle une méthode
    private static final UUID TEST_UUID = UUID.fromString("d2ac7139-76a6-435b-b659-7852d34dd7a3");
    // Affecte une valeur
    private static final int[] TEST_INT_ARRAY = new int[]{
            // Instruction de code
            0xd2ac7139,
            // Instruction de code
            0x76a6435b,
            // Instruction de code
            0xb6597852,
            // Instruction de code
            0xd34dd7a3
    // Fin d'un bloc/d'une expression
    };

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void isUuid() {
        // Appelle une méthode
        assertTrue(UUIDUtils.isUuid("d2ac7139-76a6-435b-b659-7852d34dd7a3"));
        // Appelle une méthode
        assertFalse(UUIDUtils.isUuid("This is not a UUID"));
        // Appelle une méthode
        assertFalse(UUIDUtils.isUuid("d2acL139-76a6-435b-b659-7852d34dd7a3"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void uuidToIntArray() {
        // Appelle une méthode
        assertArrayEquals(TEST_INT_ARRAY, UUIDUtils.uuidToIntArray(TEST_UUID));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void intArrayToUuid() {
        // Appelle une méthode
        assertEquals(TEST_UUID, UUIDUtils.intArrayToUuid(TEST_INT_ARRAY));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}