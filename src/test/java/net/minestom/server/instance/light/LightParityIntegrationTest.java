// Package declaration for this file
package net.minestom.server.instance.light;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.InstanceContainer;
// Import of a required class
import net.minestom.server.instance.LightingChunk;
// Import of a required class
import net.minestom.server.instance.Section;
// Import of a required class
import net.minestom.server.instance.anvil.AnvilLoader;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.net.URISyntaxException;
// Import of a required class
import java.nio.file.Files;
// Import of a required class
import java.nio.file.Path;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CompletableFuture;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class LightParityIntegrationTest {
    // Assigns a value
    private static final int REGION_SIZE = 3;

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test(Env env) throws URISyntaxException, IOException {
        // Calls a method
        Map<Vec, SectionEntry> sections = retrieveSections();
        // Generate our own light

        // Calls a method
        InstanceContainer instance = (InstanceContainer) env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Calls a method
        instance.setChunkLoader(new AnvilLoader(Path.of("./src/test/resources/net/minestom/server/instance/lighting")));

        // Calls a method
        List<CompletableFuture<Chunk>> futures = new ArrayList<>();

        // Assigns a value
        int end = REGION_SIZE;
        // Load the chunks
        // Loop: repeats a block
        for (int x = 0; x < end; x++) {
            // Loop: repeats a block
            for (int z = 0; z < end; z++) {
                // Calls a method
                futures.add(instance.loadChunk(x, z));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Loop: repeats a block
        for (CompletableFuture<Chunk> future : futures) {
            // Calls a method
            future.join();
        // End of a block/expression
        }

        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());

        // Assigns a value
        int differences = 0;
        // Assigns a value
        int differencesZero = 0;
        // Assigns a value
        int blocks = 0;
        // Assigns a value
        int sky = 0;

        // Loop: repeats a block
        for (Chunk chunk : instance.getChunks()) {
            // Branch: checks a condition
            if (chunk.getChunkX() == 0 || chunk.getChunkZ() == 0) {
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (chunk.getChunkX() == end - 1 || chunk.getChunkZ() == end - 1) {
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Loop: repeats a block
            for (int sectionIndex = chunk.getMinSection(); sectionIndex < chunk.getMaxSection(); sectionIndex++) {
                // Branch: checks a condition
                if (sectionIndex > 6) break;

                // Calls a method
                Section section = chunk.getSection(sectionIndex);

                // Calls a method
                Light sectionLight = section.blockLight();
                // Calls a method
                Light sectionSkyLight = section.skyLight();
                // Calls a method
                SectionEntry sectionEntry = sections.get(new Vec(chunk.getChunkX(), sectionIndex, chunk.getChunkZ()));
                // Branch: checks a condition
                if (sectionEntry == null) {
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }

                // Calls a method
                byte[] serverBlock = sectionLight.array();
                // Assigns a value
                byte[] mcaBlock = sectionEntry.block;

                // Calls a method
                byte[] serverSky = sectionSkyLight.array();
                // Assigns a value
                byte[] mcaSky = sectionEntry.sky;

                // Loop: repeats a block
                for (int x = 0; x < 16; ++x) {
                    // Loop: repeats a block
                    for (int y = 0; y < 16; ++y) {
                        // Loop: repeats a block
                        for (int z = 0; z < 16; ++z) {
                            // Calls a method
                            int index = x | (z << 4) | (y << 8);

                            // Start of a block
                            {
                                // Calls a method
                                int serverBlockValue = LightCompute.getLight(serverBlock, index);
                                // Calls a method
                                int mcaBlockValue = mcaBlock.length == 0 ? 0 : LightCompute.getLight(mcaBlock, index);

                                // Branch: checks a condition
                                if (serverBlockValue != mcaBlockValue) {
                                    // Branch: checks a condition
                                    if (serverBlockValue == 0) differencesZero++;
                                    // Alternative branch of the condition
                                    else differences++;
                                    // Code statement
                                    blocks++;
                                // End of a block/expression
                                }
                            // End of a block/expression
                            }

                            // Mojang's sky lighting is wrong
                            // Start of a block
                            {
                                // Calls a method
                                int serverSkyValue = LightCompute.getLight(serverSky, index);
                                // Calls a method
                                int mcaSkyValue = mcaSky.length == 0 ? 0 : LightCompute.getLight(mcaSky, index);

                                // Branch: checks a condition
                                if (serverSkyValue != mcaSkyValue) {
                                    // Branch: checks a condition
                                    if (serverSkyValue == 0) differencesZero++;
                                    // Alternative branch of the condition
                                    else differences++;
                                    // Code statement
                                    sky++;
                                // End of a block/expression
                                }
                            // End of a block/expression
                            }
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        assertEquals(0, blocks);
        // Calls a method
        assertEquals(0, sky);
        // Calls a method
        assertEquals(0, differences);
        // Calls a method
        assertEquals(0, differencesZero);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SectionEntry(Palette blocks, byte[] sky, byte[] block) {
    // End of a block/expression
    }

    // Start of a method/block
    private static Map<Vec, SectionEntry> retrieveSections() throws IOException, URISyntaxException {
        // Calls a method
        var worldDir = Files.createTempDirectory("minestom-light-parity-test");
        // Calls a method
        var mcaFile = worldDir.resolve("region").resolve("r.0.0.mca");
        // Calls a method
        Files.createDirectories(mcaFile.getParent());
        // Exception handling
        try (var is = LightParityIntegrationTest.class.getResourceAsStream("/net/minestom/server/instance/lighting/region/r.0.0.mca")) {
            // Calls a method
            Files.copy(Objects.requireNonNull(is), mcaFile);
        // End of a block/expression
        }

        // Assigns a value
        var instance = new InstanceContainer(UUID.randomUUID(), DimensionType.OVERWORLD); // Never registered
        // Calls a method
        var anvilLoader = new AnvilLoader(worldDir);

        // Calls a method
        Map<Vec, SectionEntry> sections = new HashMap<>();
        // Read from anvil
        // Loop: repeats a block
        for (int x = 1; x < REGION_SIZE - 1; x++) {
            // Loop: repeats a block
            for (int z = 1; z < REGION_SIZE - 1; z++) {
                // Calls a method
                var chunk = anvilLoader.loadChunk(instance, x, z);
                // Branch: checks a condition
                if (chunk == null) continue;

                // Loop: repeats a block
                for (int sectionY = chunk.getMinSection(); sectionY < chunk.getMaxSection(); sectionY++) {
                    // Calls a method
                    var section = chunk.getSection(sectionY);
                    // Calls a method
                    sections.put(new Vec(x, sectionY, z), new SectionEntry(section.blockPalette(), section.skyLight().array(), section.blockLight().array()));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return sections;
    // End of a block/expression
    }
// End of a block/expression
}