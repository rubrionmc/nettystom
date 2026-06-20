// Package declaration for this file
package net.minestom.server.utils.time;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.ChronoUnit;
// Import of a required class
import java.util.concurrent.TimeUnit;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class CooldownTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    void testReadySinceBeginning() {
        // Calls a method
        var cooldown = new Cooldown(Duration.ofSeconds(1));
        // Calls a method
        assertTrue(cooldown.isReady(0));
        // Calls a method
        assertTrue(cooldown.isReady(Long.MIN_VALUE));
        // Calls a method
        assertTrue(cooldown.isReady(Long.MAX_VALUE));
    // End of a block/expression
    }
    // Annotation for the following element
    @Test
    // Start of a method/block
    void testConstructorAndIsReady() {
        // Calls a method
        var beforeNanos = System.nanoTime() - 1;
        // Calls a method
        var cooldown = new Cooldown(Duration.ofSeconds(1), ChronoUnit.NANOS);
        // Calls a method
        cooldown.refreshLastUpdate(System.nanoTime());
        // Calls a method
        var afterNanos = System.nanoTime() + 1;
        // Calls a method
        assertFalse(cooldown.isReady(beforeNanos + TimeUnit.SECONDS.toNanos(1)));
        // Calls a method
        assertTrue(cooldown.isReady(afterNanos + TimeUnit.SECONDS.toNanos(1)));
        // Calls a method
        assertEquals(cooldown.getDuration(), Duration.ofSeconds(1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testHasCooldown() {
        // Calls a method
        var nanoTime = System.nanoTime();
        // Calls a method
        assertTrue(Cooldown.hasCooldown(ChronoUnit.NANOS, nanoTime, nanoTime - TimeUnit.SECONDS.toNanos(1) + 1, ChronoUnit.SECONDS, 1));
        // Calls a method
        assertFalse(Cooldown.hasCooldown(ChronoUnit.NANOS, nanoTime, nanoTime - TimeUnit.SECONDS.toNanos(1), ChronoUnit.SECONDS, 1));

        // we assume this test does not take longer than 1 hour
        // Calls a method
        assertTrue(Cooldown.hasCooldown(nanoTime, ChronoUnit.HOURS, 1));

        // Calls a method
        assertFalse(Cooldown.hasCooldown(nanoTime - TimeUnit.HOURS.toNanos(1), ChronoUnit.HOURS, 1));
    // End of a block/expression
    }
// End of a block/expression
}
