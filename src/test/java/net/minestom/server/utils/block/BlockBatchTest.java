// Déclaration du paquet de ce fichier
package net.minestom.server.utils.block;

// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.batch.AbsoluteBlockBatch;
// Import d'une classe nécessaire
import net.minestom.server.instance.batch.BatchOption;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.concurrent.CountDownLatch;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class BlockBatchTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inverseConsumerNotNull(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();

        // Appelle une méthode
        AbsoluteBlockBatch blockBatch = new AbsoluteBlockBatch(new BatchOption().setCalculateInverse(true));
        // Appelle une méthode
        blockBatch.setBlock(0, 0, 0, Block.SNOW);

        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(1);
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Début d'une méthode/d'un bloc
        blockBatch.apply(instance, (inverse) -> {
            // Appelle une méthode
            Assertions.assertNotNull(inverse);
            // Appelle une méthode
            latch.countDown();
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        env.tickWhile(() -> latch.getCount() > 0, Duration.ofSeconds(1));
        // Appelle une méthode
        Assertions.assertDoesNotThrow(()->Assertions.assertTrue(latch.await(1, TimeUnit.SECONDS)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inverseConsumerNull(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();

        // Appelle une méthode
        AbsoluteBlockBatch blockBatch = new AbsoluteBlockBatch(new BatchOption().setCalculateInverse(false));
        // Appelle une méthode
        blockBatch.setBlock(0, 0, 0, Block.SNOW);

        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(1);
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Début d'une méthode/d'un bloc
        blockBatch.apply(instance, (inverse) -> {
            // Appelle une méthode
            Assertions.assertNull(inverse);
            // Appelle une méthode
            latch.countDown();
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        env.tickWhile(() -> latch.getCount() > 0, Duration.ofSeconds(1));
        // Appelle une méthode
        Assertions.assertDoesNotThrow(()->Assertions.assertTrue(latch.await(1, TimeUnit.SECONDS)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inverseConsumerNotNullUnsafe(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();

        // Appelle une méthode
        AbsoluteBlockBatch blockBatch = new AbsoluteBlockBatch(new BatchOption().setCalculateInverse(true));
        // Appelle une méthode
        blockBatch.setBlock(0, 0, 0, Block.SNOW);

        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(1);
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Début d'une méthode/d'un bloc
        blockBatch.unsafeApply(instance, (inverse) -> {
            // Appelle une méthode
            Assertions.assertNotNull(inverse);
            // Appelle une méthode
            latch.countDown();
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        env.tickWhile(() -> latch.getCount() > 0, Duration.ofSeconds(1));
        // Appelle une méthode
        Assertions.assertDoesNotThrow(()->Assertions.assertTrue(latch.await(1, TimeUnit.SECONDS)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inverseConsumerNullUnsafe(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();

        // Appelle une méthode
        AbsoluteBlockBatch blockBatch = new AbsoluteBlockBatch(new BatchOption().setCalculateInverse(false));
        // Appelle une méthode
        blockBatch.setBlock(0, 0, 0, Block.SNOW);

        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(1);
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Début d'une méthode/d'un bloc
        blockBatch.unsafeApply(instance, (inverse) -> {
            // Appelle une méthode
            Assertions.assertNull(inverse);
            // Appelle une méthode
            latch.countDown();
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        env.tickWhile(() -> latch.getCount() > 0, Duration.ofSeconds(1));
        // Appelle une méthode
        Assertions.assertDoesNotThrow(()->Assertions.assertTrue(latch.await(1, TimeUnit.SECONDS)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
