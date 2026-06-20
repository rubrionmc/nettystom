// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Objects;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class InstanceTimeIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void overworldTicking(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        var clock = instance.defaultClock();
        // Calls a method
        assertNotNull(clock);

        // Calls a method
        assertEquals(0, clock.time());

        // Loop: repeats a block
        for (int i = 0; i < 100; i++) {
            // Calls a method
            env.tick();
            // Calls a method
            assertEquals(i + 1, clock.time());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void pausing(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        var clock = instance.defaultClock();
        // Calls a method
        assertNotNull(clock);

        // Calls a method
        assertEquals(0, clock.time());
        // Loop: repeats a block
        for (int i = 0; i < 5; i++) env.tick();
        // Calls a method
        assertEquals(5, clock.time());

        // Calls a method
        clock.pause();
        // Calls a method
        assertTrue(clock.paused());

        // Loop: repeats a block
        for (int i = 0; i < 5; i++) env.tick();
        // Calls a method
        assertEquals(5, clock.time());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void partialTickRate(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        var clock = instance.defaultClock();
        // Calls a method
        assertNotNull(clock);

        // Calls a method
        clock.rate(0.2f);
        // Loop: repeats a block
        for (int i = 0; i < 10; i++) env.tick();
        // Calls a method
        assertEquals(2, clock.time());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void multipleClocks(Env env) {
        // Calls a method
        var myOtherClock = env.process().worldClock().register(Key.key("minestom:clock"), WorldClock.create());

        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        var defaultClock = Objects.requireNonNull(instance.defaultClock());
        // Calls a method
        var otherClock = instance.clock(myOtherClock);

        // Loop: repeats a block
        for (int i = 0; i < 5; i++) env.tick();
        // Calls a method
        defaultClock.pause();
        // Loop: repeats a block
        for (int i = 0; i < 5; i++) env.tick();

        // Calls a method
        assertEquals(5, defaultClock.time());
        // Calls a method
        assertEquals(10, otherClock.time());
    // End of a block/expression
    }
// End of a block/expression
}
