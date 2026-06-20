// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class MaterialReadTest {

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void loadAllMaterials() {
        // Materials are lazy loaded now so this is a sanity check that they all load
        // Boucle : répète un bloc
        for (Material material : Material.values()) {
            // Just loading the material should be enough to test that it exists
            // Appelle une méthode
            assertNotNull(material.prototype());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
