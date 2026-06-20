// Déclaration du paquet de ce fichier
package net.minestom.server.instance.light;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.InstanceContainer;
// Import d'une classe nécessaire
import net.minestom.server.instance.LightingChunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Section;
// Import d'une classe nécessaire
import net.minestom.server.instance.anvil.AnvilLoader;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.net.URISyntaxException;
// Import d'une classe nécessaire
import java.nio.file.Files;
// Import d'une classe nécessaire
import java.nio.file.Path;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class LightParityIntegrationTest {
    // Affecte une valeur
    private static final int REGION_SIZE = 3;

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test(Env env) throws URISyntaxException, IOException {
        // Appelle une méthode
        Map<Vec, SectionEntry> sections = retrieveSections();
        // Generate our own light

        // Appelle une méthode
        InstanceContainer instance = (InstanceContainer) env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Appelle une méthode
        instance.setChunkLoader(new AnvilLoader(Path.of("./src/test/resources/net/minestom/server/instance/lighting")));

        // Appelle une méthode
        List<CompletableFuture<Chunk>> futures = new ArrayList<>();

        // Affecte une valeur
        int end = REGION_SIZE;
        // Load the chunks
        // Boucle : répète un bloc
        for (int x = 0; x < end; x++) {
            // Boucle : répète un bloc
            for (int z = 0; z < end; z++) {
                // Appelle une méthode
                futures.add(instance.loadChunk(x, z));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (CompletableFuture<Chunk> future : futures) {
            // Appelle une méthode
            future.join();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        LightingChunk.relight(instance, instance.getChunks());

        // Affecte une valeur
        int differences = 0;
        // Affecte une valeur
        int differencesZero = 0;
        // Affecte une valeur
        int blocks = 0;
        // Affecte une valeur
        int sky = 0;

        // Boucle : répète un bloc
        for (Chunk chunk : instance.getChunks()) {
            // Embranchement : vérifie une condition
            if (chunk.getChunkX() == 0 || chunk.getChunkZ() == 0) {
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (chunk.getChunkX() == end - 1 || chunk.getChunkZ() == end - 1) {
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Boucle : répète un bloc
            for (int sectionIndex = chunk.getMinSection(); sectionIndex < chunk.getMaxSection(); sectionIndex++) {
                // Embranchement : vérifie une condition
                if (sectionIndex > 6) break;

                // Appelle une méthode
                Section section = chunk.getSection(sectionIndex);

                // Appelle une méthode
                Light sectionLight = section.blockLight();
                // Appelle une méthode
                Light sectionSkyLight = section.skyLight();
                // Appelle une méthode
                SectionEntry sectionEntry = sections.get(new Vec(chunk.getChunkX(), sectionIndex, chunk.getChunkZ()));
                // Embranchement : vérifie une condition
                if (sectionEntry == null) {
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                byte[] serverBlock = sectionLight.array();
                // Affecte une valeur
                byte[] mcaBlock = sectionEntry.block;

                // Appelle une méthode
                byte[] serverSky = sectionSkyLight.array();
                // Affecte une valeur
                byte[] mcaSky = sectionEntry.sky;

                // Boucle : répète un bloc
                for (int x = 0; x < 16; ++x) {
                    // Boucle : répète un bloc
                    for (int y = 0; y < 16; ++y) {
                        // Boucle : répète un bloc
                        for (int z = 0; z < 16; ++z) {
                            // Appelle une méthode
                            int index = x | (z << 4) | (y << 8);

                            // Début d'un bloc
                            {
                                // Appelle une méthode
                                int serverBlockValue = LightCompute.getLight(serverBlock, index);
                                // Appelle une méthode
                                int mcaBlockValue = mcaBlock.length == 0 ? 0 : LightCompute.getLight(mcaBlock, index);

                                // Embranchement : vérifie une condition
                                if (serverBlockValue != mcaBlockValue) {
                                    // Embranchement : vérifie une condition
                                    if (serverBlockValue == 0) differencesZero++;
                                    // Branche alternative de la condition
                                    else differences++;
                                    // Instruction de code
                                    blocks++;
                                // Fin d'un bloc/d'une expression
                                }
                            // Fin d'un bloc/d'une expression
                            }

                            // Mojang's sky lighting is wrong
                            // Début d'un bloc
                            {
                                // Appelle une méthode
                                int serverSkyValue = LightCompute.getLight(serverSky, index);
                                // Appelle une méthode
                                int mcaSkyValue = mcaSky.length == 0 ? 0 : LightCompute.getLight(mcaSky, index);

                                // Embranchement : vérifie une condition
                                if (serverSkyValue != mcaSkyValue) {
                                    // Embranchement : vérifie une condition
                                    if (serverSkyValue == 0) differencesZero++;
                                    // Branche alternative de la condition
                                    else differences++;
                                    // Instruction de code
                                    sky++;
                                // Fin d'un bloc/d'une expression
                                }
                            // Fin d'un bloc/d'une expression
                            }
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertEquals(0, blocks);
        // Appelle une méthode
        assertEquals(0, sky);
        // Appelle une méthode
        assertEquals(0, differences);
        // Appelle une méthode
        assertEquals(0, differencesZero);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SectionEntry(Palette blocks, byte[] sky, byte[] block) {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Map<Vec, SectionEntry> retrieveSections() throws IOException, URISyntaxException {
        // Appelle une méthode
        var worldDir = Files.createTempDirectory("minestom-light-parity-test");
        // Appelle une méthode
        var mcaFile = worldDir.resolve("region").resolve("r.0.0.mca");
        // Appelle une méthode
        Files.createDirectories(mcaFile.getParent());
        // Gestion des exceptions
        try (var is = LightParityIntegrationTest.class.getResourceAsStream("/net/minestom/server/instance/lighting/region/r.0.0.mca")) {
            // Appelle une méthode
            Files.copy(Objects.requireNonNull(is), mcaFile);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var instance = new InstanceContainer(UUID.randomUUID(), DimensionType.OVERWORLD); // Never registered
        // Appelle une méthode
        var anvilLoader = new AnvilLoader(worldDir);

        // Appelle une méthode
        Map<Vec, SectionEntry> sections = new HashMap<>();
        // Read from anvil
        // Boucle : répète un bloc
        for (int x = 1; x < REGION_SIZE - 1; x++) {
            // Boucle : répète un bloc
            for (int z = 1; z < REGION_SIZE - 1; z++) {
                // Appelle une méthode
                var chunk = anvilLoader.loadChunk(instance, x, z);
                // Embranchement : vérifie une condition
                if (chunk == null) continue;

                // Boucle : répète un bloc
                for (int sectionY = chunk.getMinSection(); sectionY < chunk.getMaxSection(); sectionY++) {
                    // Appelle une méthode
                    var section = chunk.getSection(sectionY);
                    // Appelle une méthode
                    sections.put(new Vec(x, sectionY, z), new SectionEntry(section.blockPalette(), section.skyLight().array(), section.blockLight().array()));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return sections;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}