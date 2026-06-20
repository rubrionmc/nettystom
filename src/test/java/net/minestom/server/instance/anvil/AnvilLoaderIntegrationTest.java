// Package declaration for this file
package net.minestom.server.instance.anvil;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.Section;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.ChunkData;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.nio.file.*;
// Import of a required class
import java.nio.file.attribute.BasicFileAttributes;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.function.Consumer;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class AnvilLoaderIntegrationTest {
    // Calls a method
    private static final Path WORLD_RESOURCES = Path.of("src", "test", "resources", "net", "minestom", "server", "instance");

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadVanillaRegion(Env env) throws IOException {
        // load a full vanilla region, not checking any content just making sure it loads without issues.
        // Calls a method
        var worldFolder = extractWorld("anvil_vanilla_sample");
        // Assigns a value
        AnvilLoader chunkLoader = new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelLoading() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelSaving() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        Instance instance = env.createFlatInstance(chunkLoader);

        // Loop: repeats a block
        for (int chunkX = 0; chunkX < 32; chunkX++) {
            // Loop: repeats a block
            for (int chunkZ = 0; chunkZ < 32; chunkZ++) {
                // Calls a method
                Chunk chunk = instance.loadChunk(chunkX, chunkZ).join();
                // Calls a method
                instance.unloadChunk(chunk);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void parallelSaveNonexistentFiles(Env env) throws Exception {
        // Calls a method
        var worldFolder = Files.createTempDirectory("minestom-test-world-parallel-save");
        // Calls a method
        AnvilLoader chunkLoader = new AnvilLoader(worldFolder);
        // Calls a method
        Instance instance = env.createFlatInstance(chunkLoader);

        // Loop: repeats a block
        for (int chunkX = 0; chunkX < 32; chunkX++) {
            // Loop: repeats a block
            for (int chunkZ = 0; chunkZ < 32; chunkZ++) {
                // Calls a method
                instance.loadChunk(chunkX, chunkZ).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        AtomicReference<Throwable> exception = new AtomicReference<>();
        // Start of a method/block
        env.process().exception().setExceptionHandler((throwable) -> {
            // Calls a method
            exception.set(throwable);
            // Calls a method
            throwable.printStackTrace();
        // End of a block/expression
        });
        // Calls a method
        instance.saveChunksToStorage().join();
        // Calls a method
        assertNull(exception.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadHouse(Env env) throws IOException {
        // load a world that contains only a basic house and make sure it is loaded properly

        // Calls a method
        var worldFolder = extractWorld("anvil_loader");
        // Assigns a value
        AnvilLoader chunkLoader = new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelLoading() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelSaving() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        Instance instance = env.createFlatInstance(chunkLoader);

        // Assigns a value
        Consumer<Chunk> checkChunk = chunk -> {
            // Calls a method
            chunk.lockReadLock();
            // Exception handling
            try {
                // Calls a method
                assertEquals(-4, chunk.getMinSection());
                // Calls a method
                assertEquals(20, chunk.getMaxSection());

                // Loop: repeats a block
                for (int y = 0; y < 16; y++) {
                    // Loop: repeats a block
                    for (int x = 0; x < 16; x++) {
                        // Loop: repeats a block
                        for (int z = 0; z < 16; z++) {
                            // Calls a method
                            RegistryKey<Biome> b = chunk.getBiome(x, y, z);
                            // Calls a method
                            assertEquals(Biome.PLAINS, b);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // Start of a method/block
            } finally {
                // Calls a method
                chunk.unlockReadLock();
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Loop: repeats a block
        for (int x = -2; x < 2; x++) {
            // Loop: repeats a block
            for (int z = -2; z < 2; z++) {
                // Code statement
                checkChunk.accept(instance.loadChunk(x, z).join()); // this is a test so we don't care too much about waiting for each chunk
            // End of a block/expression
            }
        // End of a block/expression
        }

        // wooden house with nylium ground. Open world inside MC to check out

        // center of world
        // Calls a method
        assertEquals(Block.BEDROCK, instance.getBlock(0, 0, 0));
        // nylium stripes in front and back of house
        // Loop: repeats a block
        for (int z = -4; z <= 0; z++) {
            // Calls a method
            assertEquals(Block.WARPED_NYLIUM, instance.getBlock(4, 0, z));
            // Calls a method
            assertEquals(Block.WARPED_NYLIUM, instance.getBlock(-3, 0, z));
            // Calls a method
            assertEquals(Block.WARPED_NYLIUM, instance.getBlock(-4, 0, z));
        // End of a block/expression
        }

        // side walls
        // Loop: repeats a block
        for (int x = -2; x <= 3; x++) {
            // Branch: checks a condition
            if (x != 0) { // bedrock block at center
                // Calls a method
                assertEquals(Block.NETHERRACK, instance.getBlock(x, 0, 0));
            // End of a block/expression
            }
            // Calls a method
            assertEquals(Block.NETHERRACK, instance.getBlock(x, 0, -4));

            // Calls a method
            assertEquals(Block.OAK_PLANKS, instance.getBlock(x, 1, 0));
            // Calls a method
            assertEquals(Block.OAK_PLANKS, instance.getBlock(x, 1, -4));
            // Calls a method
            assertEquals(Block.OAK_PLANKS, instance.getBlock(x, 2, 0));
            // Calls a method
            assertEquals(Block.OAK_PLANKS, instance.getBlock(x, 2, -4));
        // End of a block/expression
        }

        // back wall
        // Loop: repeats a block
        for (int z = -4; z <= 0; z++) {
            // Calls a method
            assertEquals(Block.NETHERRACK, instance.getBlock(-2, 0, z));

            // Calls a method
            assertEquals(Block.OAK_PLANKS, instance.getBlock(-2, 1, z));
            // Calls a method
            assertEquals(Block.OAK_PLANKS, instance.getBlock(-2, 2, z));
        // End of a block/expression
        }

        // door
        // Assigns a value
        Block baseDoor = Block.ACACIA_DOOR
                // Code statement
                .withProperty("facing", "west")
                // Code statement
                .withProperty("hinge", "left")
                // Code statement
                .withProperty("open", "false")
                // Calls a method
                .withProperty("powered", "false");
        // Calls a method
        Block bottomDoorPart = baseDoor.withProperty("half", "lower");
        // Calls a method
        Block topDoorPart = baseDoor.withProperty("half", "upper");
        // Calls a method
        assertEquals(bottomDoorPart, instance.getBlock(3, 1, -3));
        // Calls a method
        assertEquals(topDoorPart, instance.getBlock(3, 2, -3));

        // light blocks
        // Calls a method
        Block endRod = Block.END_ROD.withProperty("facing", "up");
        // Calls a method
        assertEquals(endRod, instance.getBlock(-1, 1, -1));
        // Calls a method
        assertEquals(Block.TORCH, instance.getBlock(-1, 2, -1));

        // flower pot
        // Calls a method
        assertEquals(Block.OAK_PLANKS, instance.getBlock(-1, 1, -3));
        // Calls a method
        assertEquals(Block.POTTED_POPPY, instance.getBlock(-1, 2, -3));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadAndSaveChunk(Env env) throws IOException {
        // Calls a method
        var worldFolder = extractWorld("anvil_loader");
        // Assigns a value
        Instance instance = env.createFlatInstance(new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelLoading() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelSaving() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Calls a method
        Chunk originalChunk = instance.loadChunk(0, 0).join();

        // Calls a method
        instance.saveChunkToStorage(originalChunk);
        // Calls a method
        instance.unloadChunk(originalChunk);
        // Calls a method
        assertNull(instance.getChunk(0, 0));

        // Calls a method
        Chunk reloadedChunk = instance.loadChunk(0, 0).join();
        // Loop: repeats a block
        for (int section = reloadedChunk.getMinSection(); section < reloadedChunk.getMaxSection(); section++) {
            // Calls a method
            Section originalSection = originalChunk.getSection(section);
            // Calls a method
            Section reloadedSection = reloadedChunk.getSection(section);

            // Calls a method
            NetworkBuffer.Type<ChunkData.Section> sectionSerializer = ChunkData.Section.networkType(MinecraftServer.getBiomeRegistry().size());
            // easiest equality check to write is a memory compare on written output
            // Assigns a value
            var original = NetworkBuffer.makeArray(buffer ->
                    // Calls a method
                    buffer.write(sectionSerializer, new ChunkData.Section((short) originalSection.blockPalette().count(), (short) 0, originalSection.blockPalette(), originalSection.biomePalette())));
            // Assigns a value
            var reloaded = NetworkBuffer.makeArray(buffer ->
                    // Calls a method
                    buffer.write(sectionSerializer, new ChunkData.Section((short) reloadedSection.blockPalette().count(), (short) 0, reloadedSection.blockPalette(), reloadedSection.biomePalette())));
            // Calls a method
            Assertions.assertArrayEquals(original, reloaded);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadAndSaveBlockNBT(Env env) throws IOException {
        // Calls a method
        var worldFolder = extractWorld("anvil_loader");
        // Calls a method
        Instance instance = env.createFlatInstance(new AnvilLoader(worldFolder));
        // Calls a method
        Chunk originalChunk = instance.loadChunk(0, 0).join();

        // Assigns a value
        var nbt = CompoundBinaryTag.builder()
                // Code statement
                .putString("hello", "world")
                // Calls a method
                .build();
        // Calls a method
        var block = Block.STONE.withNbt(nbt);
        // Calls a method
        instance.setBlock(BlockVec.ZERO, block);

        // Calls a method
        instance.saveChunkToStorage(originalChunk).join();
        // Calls a method
        instance.unloadChunk(originalChunk);
        // Calls a method
        assertNull(instance.getChunk(0, 0));

        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(block, instance.getBlock(BlockVec.ZERO));
    // End of a block/expression
    }

    // Start of a method/block
    private static Collection<BlockVec> provideLocationsForLoadAndSaveBlockHandler() {
        // Returns a value to the caller
        return List.of(BlockVec.ZERO,
                // Creates a new object
                new BlockVec(0, 15, 0),
                // Creates a new object
                new BlockVec(0, 16, 0),
                // Creates a new object
                new BlockVec(0, -15, 0),
                // Creates a new object
                new BlockVec(0, -16, 0),
                // Creates a new object
                new BlockVec(0, 64, 0),
                // Creates a new object
                new BlockVec(15, 0, 15),
                // Creates a new object
                new BlockVec(16, 0, 16),
                // Creates a new object
                new BlockVec(-15, 0, -15),
                // Creates a new object
                new BlockVec(-16, 0, -16)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("provideLocationsForLoadAndSaveBlockHandler")
    // Start of a method/block
    public void loadAndSaveBlockHandler(Point point, Env env) throws IOException {
        // Calls a method
        var worldFolder = extractWorld("anvil_loader");
        // Calls a method
        Instance instance = env.createFlatInstance(new AnvilLoader(worldFolder));
        // Calls a method
        Chunk originalChunk = instance.loadChunk(point).join();

        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("test");
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        env.process().block().registerHandler(Block.STONE.key(), () -> handler);

        // Assigns a value
        var nbt = CompoundBinaryTag.builder()
                // Code statement
                .putString("hello", "world")
                // Calls a method
                .build();
        // Calls a method
        var block = Block.STONE.withNbt(nbt);
        // Calls a method
        instance.setBlock(point, block);

        // Calls a method
        instance.saveChunkToStorage(originalChunk).join();
        // Calls a method
        instance.unloadChunk(originalChunk);
        // Calls a method
        assertNull(instance.getChunkAt(point));

        // Calls a method
        instance.loadChunk(point).join();
        // Calls a method
        assertEquals(block, instance.getBlock(point));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadAndSaveBlockHandlerWithPlacement(Env env) throws IOException {
        // Calls a method
        final Point point = new BlockVec(100_000, 16, 100_000);
        // Calls a method
        var worldFolder = extractWorld("anvil_loader");
        // Calls a method
        Instance instance = env.createFlatInstance(new AnvilLoader(worldFolder));
        // Calls a method
        Chunk originalChunk = instance.loadChunk(point).join();

        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Block.DIAMOND_BLOCK.key();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void onPlace(Placement placement) {
                // Calls a method
                assertEquals(point.x(), placement.getBlockPosition().x());
                // Calls a method
                assertEquals(point.y(), placement.getBlockPosition().y());
                // Calls a method
                assertEquals(point.z(), placement.getBlockPosition().z());
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        env.process().block().registerHandler(Block.DIAMOND_BLOCK.key(), () -> handler);

        // Calls a method
        final Block block = Block.DIAMOND_BLOCK.withHandler(handler);
        // Calls a method
        instance.setBlock(point, block);

        // Calls a method
        instance.saveChunkToStorage(originalChunk).join();
        // Calls a method
        instance.unloadChunk(originalChunk);
        // Calls a method
        assertNull(instance.getChunkAt(point));

        // Calls a method
        instance.loadChunk(point).join();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void saveChunks(Env env) throws IOException {
        // load a full vanilla region, not checking any content just making sure it loads without issues.
        // Calls a method
        var worldFolder = Files.createTempDirectory("minestom-test-world-save-chunks");
        // Assigns a value
        AnvilLoader chunkLoader = new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelLoading() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelSaving() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        Instance instance = env.createFlatInstance(chunkLoader);

        // Loop: repeats a block
        for (int chunkX = 0; chunkX < 16; chunkX++) {
            // Loop: repeats a block
            for (int chunkZ = 0; chunkZ < 16; chunkZ++) {
                // Calls a method
                Chunk chunk = instance.loadChunk(chunkX, chunkZ).join();
                // Calls a method
                instance.saveChunkToStorage(chunk).join();
                // Calls a method
                instance.unloadChunk(chunk);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Assigns a value
        final AnvilLoader secondChunkLoader = new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelLoading() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelSaving() {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        final var secondInstance = env.createEmptyInstance(secondChunkLoader);
        // Loop: repeats a block
        for (int chunkX = 0; chunkX < 16; chunkX++) {
            // Loop: repeats a block
            for (int chunkZ = 0; chunkZ < 16; chunkZ++) {
                // Calls a method
                final Chunk originalChunk = instance.loadChunk(chunkX, chunkZ).join();
                // Calls a method
                final Chunk chunk = secondInstance.loadChunk(chunkX, chunkZ).join();
                // Loop: repeats a block
                for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                    // Loop: repeats a block
                    for (int y = secondInstance.getCachedDimensionType().minY(); y < secondInstance.getCachedDimensionType().maxY(); y++) {
                        // Loop: repeats a block
                        for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                            // Calls a method
                            final Block originalBlock = instance.getBlock(x, y, z);
                            // Calls a method
                            final Block block = secondInstance.getBlock(x, y, z);
                            // Calls a method
                            assertEquals(originalBlock, block);
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

    // Start of a method/block
    private static Path extractWorld(String resourceName) throws IOException {
        // Calls a method
        final Path worldFolder = Files.createTempDirectory("minestom-test-world-" + resourceName);

        // https://stackoverflow.com/a/60621544
        // Start of a method/block
        Files.walkFileTree(WORLD_RESOURCES.resolve(resourceName), new SimpleFileVisitor<>() {
            // Annotation for the following element
            @Override
            // Code statement
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    // Start of a method/block
                    throws IOException {
                // Calls a method
                Files.createDirectories(worldFolder.resolve(WORLD_RESOURCES.relativize(dir)));
                // Returns a value to the caller
                return FileVisitResult.CONTINUE;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Code statement
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    // Start of a method/block
                    throws IOException {
                // Calls a method
                Files.copy(file, worldFolder.resolve(WORLD_RESOURCES.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                // Returns a value to the caller
                return FileVisitResult.CONTINUE;
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Returns a value to the caller
        return worldFolder.resolve(resourceName);
    // End of a block/expression
    }
// End of a block/expression
}
