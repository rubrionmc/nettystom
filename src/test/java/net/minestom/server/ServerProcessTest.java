// Package declaration for this file
package net.minestom.server;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.net.InetSocketAddress;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertThrows;
// Static import of a member
import static org.junit.jupiter.api.Assumptions.assumeTrue;

// Type declaration (class/interface/enum/record)
public class ServerProcessTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void init() {
        // These like to fail on github actions
        // Calls a method
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Calls a method
        AtomicReference<ServerProcess> process = new AtomicReference<>();
        // Calls a method
        assertDoesNotThrow(() -> process.set(MinecraftServer.updateProcess()));
        // Calls a method
        assertDoesNotThrow(() -> process.get().start(new InetSocketAddress("localhost", 25565)));
        // Calls a method
        assertThrows(Exception.class, () -> process.get().start(new InetSocketAddress("localhost", 25566)));
        // Calls a method
        assertDoesNotThrow(() -> process.get().stop());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void tick() {
        // These like to fail on github actions
        // Calls a method
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Calls a method
        var process = MinecraftServer.updateProcess();
        // Calls a method
        process.start(new InetSocketAddress("localhost", 25565));
        // Calls a method
        var ticker = process.ticker();
        // Calls a method
        assertDoesNotThrow(() -> ticker.tick(System.nanoTime()));
        // Calls a method
        assertDoesNotThrow(process::stop);
    // End of a block/expression
    }
// End of a block/expression
}
