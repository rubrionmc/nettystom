// Déclaration du paquet de ce fichier
package net.minestom.server.instance.generator;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.GeneratorImpl.GenSection;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.BeforeAll;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Arrays;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class BiomeIntegrationTest {

    // Instruction de code
    private static int PLAINS_ID, BADLANDS_ID;

    // Annotation pour l'élément suivant
    @BeforeAll
    // Début d'une méthode/d'un bloc
    public static void prepareTest(Env env) {
        // Appelle une méthode
        PLAINS_ID = env.process().biome().getId(Biome.PLAINS);
        // Appelle une méthode
        BADLANDS_ID = env.process().biome().getId(Biome.BADLANDS);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkBiomeSet(Env env) {
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(MinecraftServer.getBiomeRegistry(), sections, chunkX, minSection, chunkZ);
        // Affecte une valeur
        Generator generator = unit -> {
            // Appelle une méthode
            var modifier = unit.modifier();
            // Appelle une méthode
            modifier.setBiome(48, -16, -32, Biome.BADLANDS);
            // Appelle une méthode
            modifier.setBiome(48 + 8, 0, -32, Biome.BADLANDS);
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        generator.generate(chunkUnits);

        // Reminder because I (matt) forgot: biome palettes are 4x4x4 sections, so x=2 is really x=8 in the chunk.
        // Appelle une méthode
        assertEquals(BADLANDS_ID, sections[0].biomes().get(0, 0, 0));
        // Appelle une méthode
        assertEquals(PLAINS_ID, sections[1].biomes().get(1, 0, 0));
        // Appelle une méthode
        assertEquals(BADLANDS_ID, sections[1].biomes().get(2, 0, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkBiomeFill(Env env) {
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(MinecraftServer.getBiomeRegistry(), sections, chunkX, minSection, chunkZ);
        // Affecte une valeur
        Generator generator = chunk -> {
            // Appelle une méthode
            var modifier = chunk.modifier();
            // Appelle une méthode
            modifier.fillBiome(Biome.PLAINS);
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        generator.generate(chunkUnits);
        // Boucle : répète un bloc
        for (var section : sections) {
            // Instruction de code
            section.biomes().getAll((x, y, z, value) ->
                    // Appelle une méthode
                    assertEquals(PLAINS_ID, value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
