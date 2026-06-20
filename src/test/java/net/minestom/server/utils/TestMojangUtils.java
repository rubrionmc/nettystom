// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.utils.mojang.MojangUtils;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Disabled;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TestMojangUtils {
    // Appelle une méthode
    private final UUID JEB_UUID = UUID.fromString("853c80ef-3c37-49fd-aa49-938b674adae6");

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testValidNameWorks() {
        // Appelle une méthode
        var result = MojangUtils.fromUsername("jeb_");
        // Appelle une méthode
        assertNotNull(result);
        // Appelle une méthode
        assertEquals("jeb_", result.get("name").getAsString());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testInvalidNameReturnsNull() {
        // Affecte une valeur
        var result = MojangUtils.fromUsername("jfdsa84vvcxadubasdfcvn"); // Longer than 16, always invalid
        // Appelle une méthode
        assertNull(result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testValidUuidWorks() {
        // Appelle une méthode
        var result = MojangUtils.fromUuid(JEB_UUID.toString());
        // Appelle une méthode
        assertNotNull(result);
        // Appelle une méthode
        assertEquals("jeb_", result.get("name").getAsString());
        // Appelle une méthode
        assertEquals("853c80ef3c3749fdaa49938b674adae6", result.get("id").getAsString());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testInvalidUuidReturnsNull() {
        // Affecte une valeur
        var result = MojangUtils.fromUuid("853c80ef3c3749fdaa49938b674adae6a"); // Longer than 32, always invalid
        // Appelle une méthode
        assertNull(result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testNonExistentUuidReturnsNull() {
        // Appelle une méthode
        var result = MojangUtils.fromUuid("00000000-0000-0000-0000-000000000000");
        // Appelle une méthode
        assertNull(result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testValidUUIDWorks() {
        // Appelle une méthode
        var result = MojangUtils.fromUuid(JEB_UUID);
        // Appelle une méthode
        assertNotNull(result);
        // Appelle une méthode
        assertEquals("jeb_", result.get("name").getAsString());
        // Appelle une méthode
        assertEquals("853c80ef3c3749fdaa49938b674adae6", result.get("id").getAsString());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testGetValidNameWorks() throws IOException {
        // Appelle une méthode
        assertEquals(JEB_UUID, MojangUtils.getUUID("jeb_"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testGetValidUUIDWorks() throws IOException {
        // Appelle une méthode
        assertEquals("jeb_", MojangUtils.getUsername(JEB_UUID));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testGetInvalidNameThrows() {
        // Instruction de code
        assertThrows(IOException.class, () -> MojangUtils.getUUID("a")); // Too short
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
