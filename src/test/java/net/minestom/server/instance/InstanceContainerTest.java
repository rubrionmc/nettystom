// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class InstanceContainerTest {

    // Début d'une méthode/d'un bloc
    static {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyPreservesTag() {
        // Appelle une méthode
        var tag = Tag.String("test");
        // Appelle une méthode
        var instance = new InstanceContainer(UUID.randomUUID(), DimensionType.OVERWORLD);
        // Appelle une méthode
        instance.setTag(tag, "123");

        // Appelle une méthode
        var copyInstance = instance.copy();
        // Appelle une méthode
        var result = copyInstance.getTag(tag);
        // Appelle une méthode
        assertEquals("123", result);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
