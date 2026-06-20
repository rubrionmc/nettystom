// Déclaration du paquet de ce fichier
package net.minestom.server.instance.anvil;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.Section;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSource;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSources;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.nio.file.*;
// Import d'une classe nécessaire
import java.nio.file.attribute.BasicFileAttributes;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.SHORT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class AnvilLoaderIntegrationTest {
    // Appelle une méthode
    private static final Path WORLD_RESOURCES = Path.of("src", "test", "resources", "net", "minestom", "server", "instance");

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadVanillaRegion(Env env) throws IOException {
        // load a full vanilla region, not checking any content just making sure it loads without issues.
        // Appelle une méthode
        var worldFolder = extractWorld("anvil_vanilla_sample");
        // Affecte une valeur
        AnvilLoader chunkLoader = new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelLoading() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelSaving() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        Instance instance = env.createFlatInstance(chunkLoader);

        // Boucle : répète un bloc
        for (int chunkX = 0; chunkX < 32; chunkX++) {
            // Boucle : répète un bloc
            for (int chunkZ = 0; chunkZ < 32; chunkZ++) {
                // Appelle une méthode
                Chunk chunk = instance.loadChunk(chunkX, chunkZ).join();
                // Appelle une méthode
                instance.unloadChunk(chunk);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void parallelSaveNonexistentFiles(Env env) throws Exception {
        // Appelle une méthode
        var worldFolder = Files.createTempDirectory("minestom-test-world-parallel-save");
        // Appelle une méthode
        AnvilLoader chunkLoader = new AnvilLoader(worldFolder);
        // Appelle une méthode
        Instance instance = env.createFlatInstance(chunkLoader);

        // Boucle : répète un bloc
        for (int chunkX = 0; chunkX < 32; chunkX++) {
            // Boucle : répète un bloc
            for (int chunkZ = 0; chunkZ < 32; chunkZ++) {
                // Appelle une méthode
                instance.loadChunk(chunkX, chunkZ).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        AtomicReference<Throwable> exception = new AtomicReference<>();
        // Début d'une méthode/d'un bloc
        env.process().exception().setExceptionHandler((throwable) -> {
            // Appelle une méthode
            exception.set(throwable);
            // Appelle une méthode
            throwable.printStackTrace();
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.saveChunksToStorage().join();
        // Appelle une méthode
        assertNull(exception.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadHouse(Env env) throws IOException {
        // load a world that contains only a basic house and make sure it is loaded properly

        // Appelle une méthode
        var worldFolder = extractWorld("anvil_loader");
        // Affecte une valeur
        AnvilLoader chunkLoader = new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelLoading() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelSaving() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        Instance instance = env.createFlatInstance(chunkLoader);

        // Affecte une valeur
        Consumer<Chunk> checkChunk = chunk -> {
            // Début d'une méthode/d'un bloc
            synchronized (chunk) {
                // Appelle une méthode
                assertEquals(-4, chunk.getMinSection());
                // Appelle une méthode
                assertEquals(20, chunk.getMaxSection());

                // Boucle : répète un bloc
                for (int y = 0; y < 16; y++) {
                    // Boucle : répète un bloc
                    for (int x = 0; x < 16; x++) {
                        // Boucle : répète un bloc
                        for (int z = 0; z < 16; z++) {
                            // Appelle une méthode
                            RegistryKey<Biome> b = chunk.getBiome(x, y, z);
                            // Appelle une méthode
                            assertEquals(Biome.PLAINS, b);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Boucle : répète un bloc
        for (int x = -2; x < 2; x++) {
            // Boucle : répète un bloc
            for (int z = -2; z < 2; z++) {
                // Instruction de code
                checkChunk.accept(instance.loadChunk(x, z).join()); // this is a test so we don't care too much about waiting for each chunk
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // wooden house with nylium ground. Open world inside MC to check out

        // center of world
        // Appelle une méthode
        assertEquals(Block.BEDROCK, instance.getBlock(0, 0, 0));
        // nylium stripes in front and back of house
        // Boucle : répète un bloc
        for (int z = -4; z <= 0; z++) {
            // Appelle une méthode
            assertEquals(Block.WARPED_NYLIUM, instance.getBlock(4, 0, z));
            // Appelle une méthode
            assertEquals(Block.WARPED_NYLIUM, instance.getBlock(-3, 0, z));
            // Appelle une méthode
            assertEquals(Block.WARPED_NYLIUM, instance.getBlock(-4, 0, z));
        // Fin d'un bloc/d'une expression
        }

        // side walls
        // Boucle : répète un bloc
        for (int x = -2; x <= 3; x++) {
            // Embranchement : vérifie une condition
            if (x != 0) { // bedrock block at center
                // Appelle une méthode
                assertEquals(Block.NETHERRACK, instance.getBlock(x, 0, 0));
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            assertEquals(Block.NETHERRACK, instance.getBlock(x, 0, -4));

            // Appelle une méthode
            assertEquals(Block.OAK_PLANKS, instance.getBlock(x, 1, 0));
            // Appelle une méthode
            assertEquals(Block.OAK_PLANKS, instance.getBlock(x, 1, -4));
            // Appelle une méthode
            assertEquals(Block.OAK_PLANKS, instance.getBlock(x, 2, 0));
            // Appelle une méthode
            assertEquals(Block.OAK_PLANKS, instance.getBlock(x, 2, -4));
        // Fin d'un bloc/d'une expression
        }

        // back wall
        // Boucle : répète un bloc
        for (int z = -4; z <= 0; z++) {
            // Appelle une méthode
            assertEquals(Block.NETHERRACK, instance.getBlock(-2, 0, z));

            // Appelle une méthode
            assertEquals(Block.OAK_PLANKS, instance.getBlock(-2, 1, z));
            // Appelle une méthode
            assertEquals(Block.OAK_PLANKS, instance.getBlock(-2, 2, z));
        // Fin d'un bloc/d'une expression
        }

        // door
        // Affecte une valeur
        Block baseDoor = Block.ACACIA_DOOR
                // Instruction de code
                .withProperty("facing", "west")
                // Instruction de code
                .withProperty("hinge", "left")
                // Instruction de code
                .withProperty("open", "false")
                // Appelle une méthode
                .withProperty("powered", "false");
        // Appelle une méthode
        Block bottomDoorPart = baseDoor.withProperty("half", "lower");
        // Appelle une méthode
        Block topDoorPart = baseDoor.withProperty("half", "upper");
        // Appelle une méthode
        assertEquals(bottomDoorPart, instance.getBlock(3, 1, -3));
        // Appelle une méthode
        assertEquals(topDoorPart, instance.getBlock(3, 2, -3));

        // light blocks
        // Appelle une méthode
        Block endRod = Block.END_ROD.withProperty("facing", "up");
        // Appelle une méthode
        assertEquals(endRod, instance.getBlock(-1, 1, -1));
        // Appelle une méthode
        assertEquals(Block.TORCH, instance.getBlock(-1, 2, -1));

        // flower pot
        // Appelle une méthode
        assertEquals(Block.OAK_PLANKS, instance.getBlock(-1, 1, -3));
        // Appelle une méthode
        assertEquals(Block.POTTED_POPPY, instance.getBlock(-1, 2, -3));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadAndSaveChunk(Env env) throws IOException {
        // Appelle une méthode
        var worldFolder = extractWorld("anvil_loader");
        // Affecte une valeur
        Instance instance = env.createFlatInstance(new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelLoading() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelSaving() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        Chunk originalChunk = instance.loadChunk(0, 0).join();

        // Appelle une méthode
        instance.saveChunkToStorage(originalChunk);
        // Appelle une méthode
        instance.unloadChunk(originalChunk);
        // Appelle une méthode
        assertNull(instance.getChunk(0, 0));

        // Appelle une méthode
        Chunk reloadedChunk = instance.loadChunk(0, 0).join();
        // Boucle : répète un bloc
        for (int section = reloadedChunk.getMinSection(); section < reloadedChunk.getMaxSection(); section++) {
            // Appelle une méthode
            Section originalSection = originalChunk.getSection(section);
            // Appelle une méthode
            Section reloadedSection = reloadedChunk.getSection(section);

            // Appelle une méthode
            NetworkBuffer.Type<Palette> biomeSerializer = Palette.biomeSerializer(MinecraftServer.getBiomeRegistry().size());
            // easiest equality check to write is a memory compare on written output
            // Affecte une valeur
            var original = NetworkBuffer.makeArray(buffer -> {
                // Appelle une méthode
                buffer.write(SHORT, (short) originalSection.blockPalette().count());
                // Appelle une méthode
                buffer.write(Palette.BLOCK_SERIALIZER, originalSection.blockPalette());
                // Appelle une méthode
                buffer.write(biomeSerializer, originalSection.biomePalette());
            // Fin d'un bloc/d'une expression
            });
            // Affecte une valeur
            var reloaded = NetworkBuffer.makeArray(buffer -> {
                // Appelle une méthode
                buffer.write(SHORT, (short) reloadedSection.blockPalette().count());
                // Appelle une méthode
                buffer.write(Palette.BLOCK_SERIALIZER, reloadedSection.blockPalette());
                // Appelle une méthode
                buffer.write(biomeSerializer, reloadedSection.biomePalette());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            Assertions.assertArrayEquals(original, reloaded);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadAndSaveBlockNBT(Env env) throws IOException {
        // Appelle une méthode
        var worldFolder = extractWorld("anvil_loader");
        // Appelle une méthode
        Instance instance = env.createFlatInstance(new AnvilLoader(worldFolder));
        // Appelle une méthode
        Chunk originalChunk = instance.loadChunk(0, 0).join();

        // Affecte une valeur
        var nbt = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("hello", "world")
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var block = Block.STONE.withNbt(nbt);
        // Appelle une méthode
        instance.setBlock(BlockVec.ZERO, block);

        // Appelle une méthode
        instance.saveChunkToStorage(originalChunk).join();
        // Appelle une méthode
        instance.unloadChunk(originalChunk);
        // Appelle une méthode
        assertNull(instance.getChunk(0, 0));

        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(block, instance.getBlock(BlockVec.ZERO));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Collection<BlockVec> provideLocationsForLoadAndSaveBlockHandler() {
        // Renvoie une valeur à l'appelant
        return List.of(BlockVec.ZERO,
                // Crée un nouvel objet
                new BlockVec(0, 15, 0),
                // Crée un nouvel objet
                new BlockVec(0, 16, 0),
                // Crée un nouvel objet
                new BlockVec(0, -15, 0),
                // Crée un nouvel objet
                new BlockVec(0, -16, 0),
                // Crée un nouvel objet
                new BlockVec(0, 64, 0),
                // Crée un nouvel objet
                new BlockVec(15, 0, 15),
                // Crée un nouvel objet
                new BlockVec(16, 0, 16),
                // Crée un nouvel objet
                new BlockVec(-15, 0, -15),
                // Crée un nouvel objet
                new BlockVec(-16, 0, -16)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("provideLocationsForLoadAndSaveBlockHandler")
    // Début d'une méthode/d'un bloc
    public void loadAndSaveBlockHandler(Point point, Env env) throws IOException {
        // Appelle une méthode
        var worldFolder = extractWorld("anvil_loader");
        // Appelle une méthode
        Instance instance = env.createFlatInstance(new AnvilLoader(worldFolder));
        // Appelle une méthode
        Chunk originalChunk = instance.loadChunk(point).join();

        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key getKey() {
                // Renvoie une valeur à l'appelant
                return Key.key("test");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        env.process().block().registerHandler(Block.STONE.key(), () -> handler);

        // Affecte une valeur
        var nbt = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("hello", "world")
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var block = Block.STONE.withNbt(nbt);
        // Appelle une méthode
        instance.setBlock(point, block);

        // Appelle une méthode
        instance.saveChunkToStorage(originalChunk).join();
        // Appelle une méthode
        instance.unloadChunk(originalChunk);
        // Appelle une méthode
        assertNull(instance.getChunkAt(point));

        // Appelle une méthode
        instance.loadChunk(point).join();
        // Appelle une méthode
        assertEquals(block, instance.getBlock(point));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadAndSaveBlockHandlerWithPlacement(Env env) throws IOException {
        // Appelle une méthode
        final Point point = new BlockVec(100_000, 16, 100_000);
        // Appelle une méthode
        var worldFolder = extractWorld("anvil_loader");
        // Appelle une méthode
        Instance instance = env.createFlatInstance(new AnvilLoader(worldFolder));
        // Appelle une méthode
        Chunk originalChunk = instance.loadChunk(point).join();

        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key getKey() {
                // Renvoie une valeur à l'appelant
                return Block.DIAMOND_BLOCK.key();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void onPlace(Placement placement) {
                // Appelle une méthode
                assertEquals(point.x(), placement.getBlockPosition().x());
                // Appelle une méthode
                assertEquals(point.y(), placement.getBlockPosition().y());
                // Appelle une méthode
                assertEquals(point.z(), placement.getBlockPosition().z());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        env.process().block().registerHandler(Block.DIAMOND_BLOCK.key(), () -> handler);

        // Appelle une méthode
        final Block block = Block.DIAMOND_BLOCK.withHandler(handler);
        // Appelle une méthode
        instance.setBlock(point, block);

        // Appelle une méthode
        instance.saveChunkToStorage(originalChunk).join();
        // Appelle une méthode
        instance.unloadChunk(originalChunk);
        // Appelle une méthode
        assertNull(instance.getChunkAt(point));

        // Appelle une méthode
        instance.loadChunk(point).join();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void saveChunks(Env env) throws IOException {
        // load a full vanilla region, not checking any content just making sure it loads without issues.
        // Appelle une méthode
        var worldFolder = Files.createTempDirectory("minestom-test-world-save-chunks");
        // Affecte une valeur
        AnvilLoader chunkLoader = new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelLoading() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelSaving() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        Instance instance = env.createFlatInstance(chunkLoader);

        // Boucle : répète un bloc
        for (int chunkX = 0; chunkX < 16; chunkX++) {
            // Boucle : répète un bloc
            for (int chunkZ = 0; chunkZ < 16; chunkZ++) {
                // Appelle une méthode
                Chunk chunk = instance.loadChunk(chunkX, chunkZ).join();
                // Appelle une méthode
                instance.saveChunkToStorage(chunk).join();
                // Appelle une méthode
                instance.unloadChunk(chunk);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        final AnvilLoader secondChunkLoader = new AnvilLoader(worldFolder) {
            // Force loads inside current thread
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelLoading() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelSaving() {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        final var secondInstance = env.createEmptyInstance(secondChunkLoader);
        // Boucle : répète un bloc
        for (int chunkX = 0; chunkX < 16; chunkX++) {
            // Boucle : répète un bloc
            for (int chunkZ = 0; chunkZ < 16; chunkZ++) {
                // Appelle une méthode
                final Chunk originalChunk = instance.loadChunk(chunkX, chunkZ).join();
                // Appelle une méthode
                final Chunk chunk = secondInstance.loadChunk(chunkX, chunkZ).join();
                // Boucle : répète un bloc
                for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                    // Boucle : répète un bloc
                    for (int y = secondInstance.getCachedDimensionType().minY(); y < secondInstance.getCachedDimensionType().maxY(); y++) {
                        // Boucle : répète un bloc
                        for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                            // Appelle une méthode
                            final Block originalBlock = instance.getBlock(x, y, z);
                            // Appelle une méthode
                            final Block block = secondInstance.getBlock(x, y, z);
                            // Appelle une méthode
                            assertEquals(originalBlock, block);
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

    // Début d'une méthode/d'un bloc
    private static Path extractWorld(String resourceName) throws IOException {
        // Appelle une méthode
        final Path worldFolder = Files.createTempDirectory("minestom-test-world-" + resourceName);

        // https://stackoverflow.com/a/60621544
        // Début d'une méthode/d'un bloc
        Files.walkFileTree(WORLD_RESOURCES.resolve(resourceName), new SimpleFileVisitor<>() {
            // Annotation pour l'élément suivant
            @Override
            // Instruction de code
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    // Début d'une méthode/d'un bloc
                    throws IOException {
                // Appelle une méthode
                Files.createDirectories(worldFolder.resolve(WORLD_RESOURCES.relativize(dir)));
                // Renvoie une valeur à l'appelant
                return FileVisitResult.CONTINUE;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Instruction de code
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    // Début d'une méthode/d'un bloc
                    throws IOException {
                // Appelle une méthode
                Files.copy(file, worldFolder.resolve(WORLD_RESOURCES.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                // Renvoie une valeur à l'appelant
                return FileVisitResult.CONTINUE;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Renvoie une valeur à l'appelant
        return worldFolder.resolve(resourceName);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
