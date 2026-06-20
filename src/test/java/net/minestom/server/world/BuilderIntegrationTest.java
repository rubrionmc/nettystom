// Déclaration du paquet de ce fichier
package net.minestom.server.world;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class BuilderIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBiome(Env ignored) {
        // Appelle une méthode
        Biome existing = MinecraftServer.getBiomeRegistry().get(Biome.CHERRY_GROVE);
        // Appelle une méthode
        assertNotNull(existing);
        // Appelle une méthode
        Biome.Builder builder = Biome.builder(existing);
        // Appelle une méthode
        assertEquals(existing, builder.build());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDimensionType(Env ignored) {
        // Appelle une méthode
        DimensionType existing = MinecraftServer.getDimensionTypeRegistry().get(DimensionType.THE_NETHER);
        // Appelle une méthode
        assertNotNull(existing);
        // Appelle une méthode
        DimensionType.Builder builder = DimensionType.builder(existing);
        // Appelle une méthode
        assertEquals(existing, builder.build());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEnvironmentAttributeMap(Env ignored) {
        // Appelle une méthode
        DimensionType existing = MinecraftServer.getDimensionTypeRegistry().get(DimensionType.OVERWORLD);
        // Appelle une méthode
        assertNotNull(existing);
        // Appelle une méthode
        EnvironmentAttributeMap.Builder builder = EnvironmentAttributeMap.builder(existing.attributes());
        // Appelle une méthode
        assertEquals(existing.attributes(), builder.build());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
