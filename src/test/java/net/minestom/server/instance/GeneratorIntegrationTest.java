// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.Generator;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSource;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.CompletionException;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static org.junit.jupiter.api.Assumptions.assumeTrue;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class GeneratorIntegrationTest {

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void loader(boolean data, Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var block = data ? Block.STONE.withNbt(CompoundBinaryTag.builder().putString("key", "value").build()) : Block.STONE;
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Appelle une méthode
        instance.setGenerator(unit -> unit.modifier().fill(block));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(block, instance.getBlock(0, 0, 0));
        // Appelle une méthode
        assertEquals(block, instance.getBlock(15, 0, 0));
        // Appelle une méthode
        assertEquals(block, instance.getBlock(0, 15, 0));
        // Appelle une méthode
        assertEquals(block, instance.getBlock(0, 0, 15));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void exceptionCatch(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();

        // Appelle une méthode
        var ref = new AtomicReference<Throwable>();
        // Appelle une méthode
        env.process().exception().setExceptionHandler(ref::set);

        // Appelle une méthode
        var exception = new RuntimeException();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            unit.modifier().fill(Block.STONE);
            // Lève une exception
            throw exception;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Appelle une méthode
        assertSame(exception, ref.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fillHeightNegative(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Appelle une méthode
        instance.setGenerator(unit -> unit.modifier().fillHeight(-64, -60, Block.STONE));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Boucle : répète un bloc
        for (int y = -64; y < -60; y++) {
            // Appelle une méthode
            assertEquals(Block.STONE, instance.getBlock(0, y, 0), "y=" + y);
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (int y = -60; y < 100; y++) {
            // Appelle une méthode
            assertEquals(Block.AIR, instance.getBlock(0, y, 0), "y=" + y);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fillHeightSingleSectionFull(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Appelle une méthode
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 16, Block.GRASS_BLOCK));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Boucle : répète un bloc
        for (int y = 0; y < 16; y++) {
            // Appelle une méthode
            assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, y, 0), "y=" + y);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fillHeightSingleSection(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Appelle une méthode
        instance.setGenerator(unit -> unit.modifier().fillHeight(4, 5, Block.GRASS_BLOCK));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Boucle : répète un bloc
        for (int y = 0; y < 5; y++) {
            // Appelle une méthode
            assertEquals(y == 4 ? Block.GRASS_BLOCK : Block.AIR, instance.getBlock(0, y, 0), "y=" + y);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fillHeightOverride(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            unit.modifier().fillHeight(0, 39, Block.GRASS_BLOCK);
            // Appelle une méthode
            unit.modifier().fillHeight(39, 40, Block.STONE);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Boucle : répète un bloc
        for (int y = 0; y < 40; y++) {
            // Appelle une méthode
            assertEquals(y == 39 ? Block.STONE : Block.GRASS_BLOCK, instance.getBlock(0, y, 0), "y=" + y);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void explicitChunkGenerate(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Affecte une valeur
        Generator generator = unit -> {
            // Appelle une méthode
            assertTrue(Thread.currentThread().isVirtual());
            // Appelle une méthode
            unit.modifier().fill(Block.GRASS_BLOCK);
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        instance.generateChunk(0, 0, generator).join();
        // Appelle une méthode
        assertNotNull(instance.getChunk(0, 0));
        // Appelle une méthode
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 0, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void explicitChunkGenerateOverride(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        instance.setGenerator(unit -> unit.modifier().fill(Block.STONE));
        // Appelle une méthode
        Generator generator = unit -> unit.modifier().fill(Block.GRASS_BLOCK);
        // Appelle une méthode
        instance.generateChunk(0, 0, generator).join();
        // Appelle une méthode
        assertNotNull(instance.getChunk(0, 0));
        // Appelle une méthode
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 0, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void explicitChunkGenerateLock(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        DynamicChunk chunk = (DynamicChunk) instance.loadChunk(0, 0).join();
        // Affecte une valeur
        Generator generator = unit -> {
            // Appelle une méthode
            chunk.assertWriteLock();
            // Appelle une méthode
            unit.modifier().fill(Block.GRASS_BLOCK);
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        instance.generateChunk(0, 0, generator).join();
        // Appelle une méthode
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 0, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkReadLockCannotUpgradeToWriteLock(Env env) {
        // Appelle une méthode
        assumeTrue(Chunk.class.desiredAssertionStatus(), "Chunk lock contract checks require assertions");
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        var chunk = instance.loadChunk(0, 0).join();
        // Appelle une méthode
        chunk.lockReadLock();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            assertThrows(AssertionError.class, chunk::lockWriteLock);
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            chunk.unlockReadLock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void loaderExceptionCompletesChunkFuture(boolean parallel, Env env) {
        // Appelle une méthode
        var exception = new RuntimeException("loader failure");
        // Début d'une méthode/d'un bloc
        env.process().exception().setExceptionHandler(throwable -> {
        // Fin d'un bloc/d'une expression
        });
        // Affecte une valeur
        ChunkLoader chunkLoader = new ChunkLoader() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Chunk loadChunk(Instance instance, int chunkX, int chunkZ) {
                // Lève une exception
                throw exception;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void saveChunk(Chunk chunk) {
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean supportsParallelLoading() {
                // Renvoie une valeur à l'appelant
                return parallel;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        var instance = env.createEmptyInstance(chunkLoader);

        // Appelle une méthode
        var thrown = assertThrows(CompletionException.class, () -> instance.loadChunk(0, 0).join());
        // Appelle une méthode
        assertSame(exception, thrown.getCause());
        // Appelle une méthode
        assertNull(instance.getChunk(0, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void concurrentChunkLoadsComplete(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Affecte une valeur
        CompletableFuture<?>[] futures = new CompletableFuture<?>[64];
        // Boucle : répète un bloc
        for (int i = 0; i < futures.length; i++) {
            // Appelle une méthode
            futures[i] = instance.loadChunk(i & 7, i >> 3);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> CompletableFuture.allOf(futures).join());
        // Appelle une méthode
        assertEquals(futures.length, instance.getChunks().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void explicitChunkGeneratePacket(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        connection.connect(instance, Pos.ZERO);
        // Appelle une méthode
        Generator generator = unit -> unit.modifier().fill(Block.GRASS_BLOCK);
        // Appelle une méthode
        var tracker = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        instance.generateChunk(0, 0, generator).join();
        // Appelle une méthode
        tracker.assertAny();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
