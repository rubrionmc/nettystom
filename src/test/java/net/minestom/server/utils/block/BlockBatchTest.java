// Package declaration for this file
package net.minestom.server.utils.block;

// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.batch.AbsoluteBlockBatch;
// Import of a required class
import net.minestom.server.instance.batch.BatchOption;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.concurrent.CountDownLatch;
// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class BlockBatchTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inverseConsumerNotNull(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();

        // Calls a method
        AbsoluteBlockBatch blockBatch = new AbsoluteBlockBatch(new BatchOption().setCalculateInverse(true));
        // Calls a method
        blockBatch.setBlock(0, 0, 0, Block.SNOW);

        // Calls a method
        CountDownLatch latch = new CountDownLatch(1);
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Start of a method/block
        blockBatch.apply(instance, (inverse) -> {
            // Calls a method
            Assertions.assertNotNull(inverse);
            // Calls a method
            latch.countDown();
        // End of a block/expression
        });
        // Calls a method
        env.tickWhile(() -> latch.getCount() > 0, Duration.ofSeconds(1));
        // Calls a method
        Assertions.assertDoesNotThrow(()->Assertions.assertTrue(latch.await(1, TimeUnit.SECONDS)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inverseConsumerNull(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();

        // Calls a method
        AbsoluteBlockBatch blockBatch = new AbsoluteBlockBatch(new BatchOption().setCalculateInverse(false));
        // Calls a method
        blockBatch.setBlock(0, 0, 0, Block.SNOW);

        // Calls a method
        CountDownLatch latch = new CountDownLatch(1);
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Start of a method/block
        blockBatch.apply(instance, (inverse) -> {
            // Calls a method
            Assertions.assertNull(inverse);
            // Calls a method
            latch.countDown();
        // End of a block/expression
        });
        // Calls a method
        env.tickWhile(() -> latch.getCount() > 0, Duration.ofSeconds(1));
        // Calls a method
        Assertions.assertDoesNotThrow(()->Assertions.assertTrue(latch.await(1, TimeUnit.SECONDS)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inverseConsumerNotNullUnsafe(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();

        // Calls a method
        AbsoluteBlockBatch blockBatch = new AbsoluteBlockBatch(new BatchOption().setCalculateInverse(true));
        // Calls a method
        blockBatch.setBlock(0, 0, 0, Block.SNOW);

        // Calls a method
        CountDownLatch latch = new CountDownLatch(1);
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Start of a method/block
        blockBatch.unsafeApply(instance, (inverse) -> {
            // Calls a method
            Assertions.assertNotNull(inverse);
            // Calls a method
            latch.countDown();
        // End of a block/expression
        });
        // Calls a method
        env.tickWhile(() -> latch.getCount() > 0, Duration.ofSeconds(1));
        // Calls a method
        Assertions.assertDoesNotThrow(()->Assertions.assertTrue(latch.await(1, TimeUnit.SECONDS)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inverseConsumerNullUnsafe(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();

        // Calls a method
        AbsoluteBlockBatch blockBatch = new AbsoluteBlockBatch(new BatchOption().setCalculateInverse(false));
        // Calls a method
        blockBatch.setBlock(0, 0, 0, Block.SNOW);

        // Calls a method
        CountDownLatch latch = new CountDownLatch(1);
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Start of a method/block
        blockBatch.unsafeApply(instance, (inverse) -> {
            // Calls a method
            Assertions.assertNull(inverse);
            // Calls a method
            latch.countDown();
        // End of a block/expression
        });
        // Calls a method
        env.tickWhile(() -> latch.getCount() > 0, Duration.ofSeconds(1));
        // Calls a method
        Assertions.assertDoesNotThrow(()->Assertions.assertTrue(latch.await(1, TimeUnit.SECONDS)));
    // End of a block/expression
    }
// End of a block/expression
}
