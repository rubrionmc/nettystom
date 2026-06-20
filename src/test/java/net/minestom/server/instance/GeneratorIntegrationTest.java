// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.generator.Generator;
// Import of a required class
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.ValueSource;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.CompletionException;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static org.junit.jupiter.api.Assumptions.assumeTrue;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class GeneratorIntegrationTest {

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void loader(boolean data, Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var block = data ? Block.STONE.withNbt(CompoundBinaryTag.builder().putString("key", "value").build()) : Block.STONE;
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Calls a method
        instance.setGenerator(unit -> unit.modifier().fill(block));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(block, instance.getBlock(0, 0, 0));
        // Calls a method
        assertEquals(block, instance.getBlock(15, 0, 0));
        // Calls a method
        assertEquals(block, instance.getBlock(0, 15, 0));
        // Calls a method
        assertEquals(block, instance.getBlock(0, 0, 15));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void exceptionCatch(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();

        // Calls a method
        var ref = new AtomicReference<Throwable>();
        // Calls a method
        env.process().exception().setExceptionHandler(ref::set);

        // Calls a method
        var exception = new RuntimeException();
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            unit.modifier().fill(Block.STONE);
            // Throws an exception
            throw exception;
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();

        // Calls a method
        assertSame(exception, ref.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fillHeightNegative(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Calls a method
        instance.setGenerator(unit -> unit.modifier().fillHeight(-64, -60, Block.STONE));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Loop: repeats a block
        for (int y = -64; y < -60; y++) {
            // Calls a method
            assertEquals(Block.STONE, instance.getBlock(0, y, 0), "y=" + y);
        // End of a block/expression
        }
        // Loop: repeats a block
        for (int y = -60; y < 100; y++) {
            // Calls a method
            assertEquals(Block.AIR, instance.getBlock(0, y, 0), "y=" + y);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fillHeightSingleSectionFull(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Calls a method
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 16, Block.GRASS_BLOCK));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Loop: repeats a block
        for (int y = 0; y < 16; y++) {
            // Calls a method
            assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, y, 0), "y=" + y);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fillHeightSingleSection(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Calls a method
        instance.setGenerator(unit -> unit.modifier().fillHeight(4, 5, Block.GRASS_BLOCK));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Loop: repeats a block
        for (int y = 0; y < 5; y++) {
            // Calls a method
            assertEquals(y == 4 ? Block.GRASS_BLOCK : Block.AIR, instance.getBlock(0, y, 0), "y=" + y);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fillHeightOverride(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            unit.modifier().fillHeight(0, 39, Block.GRASS_BLOCK);
            // Calls a method
            unit.modifier().fillHeight(39, 40, Block.STONE);
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Loop: repeats a block
        for (int y = 0; y < 40; y++) {
            // Calls a method
            assertEquals(y == 39 ? Block.STONE : Block.GRASS_BLOCK, instance.getBlock(0, y, 0), "y=" + y);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void explicitChunkGenerate(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Assigns a value
        Generator generator = unit -> {
            // Calls a method
            assertTrue(Thread.currentThread().isVirtual());
            // Calls a method
            unit.modifier().fill(Block.GRASS_BLOCK);
        // End of a block/expression
        };
        // Calls a method
        instance.generateChunk(0, 0, generator).join();
        // Calls a method
        assertNotNull(instance.getChunk(0, 0));
        // Calls a method
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 0, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void explicitChunkGenerateOverride(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        instance.setGenerator(unit -> unit.modifier().fill(Block.STONE));
        // Calls a method
        Generator generator = unit -> unit.modifier().fill(Block.GRASS_BLOCK);
        // Calls a method
        instance.generateChunk(0, 0, generator).join();
        // Calls a method
        assertNotNull(instance.getChunk(0, 0));
        // Calls a method
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 0, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void explicitChunkGenerateLock(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        DynamicChunk chunk = (DynamicChunk) instance.loadChunk(0, 0).join();
        // Assigns a value
        Generator generator = unit -> {
            // Calls a method
            chunk.assertWriteLock();
            // Calls a method
            unit.modifier().fill(Block.GRASS_BLOCK);
        // End of a block/expression
        };
        // Calls a method
        instance.generateChunk(0, 0, generator).join();
        // Calls a method
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 0, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkReadLockCannotUpgradeToWriteLock(Env env) {
        // Calls a method
        assumeTrue(Chunk.class.desiredAssertionStatus(), "Chunk lock contract checks require assertions");
        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        var chunk = instance.loadChunk(0, 0).join();
        // Calls a method
        chunk.lockReadLock();
        // Exception handling
        try {
            // Calls a method
            assertThrows(AssertionError.class, chunk::lockWriteLock);
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockReadLock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void loaderExceptionCompletesChunkFuture(boolean parallel, Env env) {
        // Calls a method
        var exception = new RuntimeException("loader failure");
        // Start of a method/block
        env.process().exception().setExceptionHandler(throwable -> {
        // End of a block/expression
        });
        // Assigns a value
        ChunkLoader chunkLoader = new ChunkLoader() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Chunk loadChunk(Instance instance, int chunkX, int chunkZ) {
                // Throws an exception
                throw exception;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void saveChunk(Chunk chunk) {
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean supportsParallelLoading() {
                // Returns a value to the caller
                return parallel;
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        var instance = env.createEmptyInstance(chunkLoader);

        // Calls a method
        var thrown = assertThrows(CompletionException.class, () -> instance.loadChunk(0, 0).join());
        // Calls a method
        assertSame(exception, thrown.getCause());
        // Calls a method
        assertNull(instance.getChunk(0, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void concurrentChunkLoadsComplete(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Assigns a value
        CompletableFuture<?>[] futures = new CompletableFuture<?>[64];
        // Loop: repeats a block
        for (int i = 0; i < futures.length; i++) {
            // Calls a method
            futures[i] = instance.loadChunk(i & 7, i >> 3);
        // End of a block/expression
        }

        // Calls a method
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> CompletableFuture.allOf(futures).join());
        // Calls a method
        assertEquals(futures.length, instance.getChunks().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void explicitChunkGeneratePacket(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        connection.connect(instance, Pos.ZERO);
        // Calls a method
        Generator generator = unit -> unit.modifier().fill(Block.GRASS_BLOCK);
        // Calls a method
        var tracker = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        instance.generateChunk(0, 0, generator).join();
        // Calls a method
        tracker.assertAny();
    // End of a block/expression
    }
// End of a block/expression
}
