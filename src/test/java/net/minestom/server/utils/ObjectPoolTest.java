// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Collections;
// Import of a required class
import java.util.IdentityHashMap;
// Import of a required class
import java.util.Set;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ObjectPoolTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void pool() {
        // Assigns a value
        var pool = PacketVanilla.PACKET_POOL;
        // Calls a method
        Set<NetworkBuffer> pooledBuffers = Collections.newSetFromMap(new IdentityHashMap<>());
        // Calls a method
        pool.clear();

        // Calls a method
        assertEquals(0, pool.count());
        // Calls a method
        var buffer = pool.get();
        // Calls a method
        pooledBuffers.add(buffer);

        // Calls a method
        buffer = pool.get();
        // Calls a method
        assertTrue(pooledBuffers.add(buffer));

        // Calls a method
        pool.add(buffer);
        // Calls a method
        assertEquals(1, pool.count());
        // Calls a method
        buffer = pool.get();
        // Calls a method
        assertEquals(0, pool.count());
        // Calls a method
        assertFalse(pooledBuffers.add(buffer));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void autoClose() {
        // Assigns a value
        var pool = PacketVanilla.PACKET_POOL;
        // Calls a method
        assertEquals(0, pool.count());
        // Exception handling
        try (var ignored = pool.hold()) {
            // Calls a method
            assertEquals(0, pool.count());
        // End of a block/expression
        }
        // Calls a method
        assertEquals(1, pool.count());

        // Exception handling
        try (var ignored = pool.hold()) {
            // Calls a method
            assertEquals(0, pool.count());
        // End of a block/expression
        }
        // Calls a method
        assertEquals(1, pool.count());
    // End of a block/expression
    }
// End of a block/expression
}
