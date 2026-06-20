// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import org.openjdk.jcstress.annotations.*;
// Import of a required class
import org.openjdk.jcstress.infra.results.L_Result;

// Static import of a member
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation for the following element
@JCStressTest
// Annotation for the following element
@Outcome(id = "1", expect = ACCEPTABLE)
// Annotation for the following element
@Outcome(id = "2", expect = ACCEPTABLE)
// Annotation for the following element
@State
// Type declaration (class/interface/enum/record)
public class ObjectPoolTest {
    // Assigns a value
    private final ObjectPool<NetworkBuffer> pool = PacketVanilla.PACKET_POOL;

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor1() {
        // Calls a method
        var buffer = pool.get();
        // Calls a method
        pool.add(buffer);
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor2() {
        // Calls a method
        var buffer = pool.get();
        // Calls a method
        pool.add(buffer);
    // End of a block/expression
    }

    // Annotation for the following element
    @Arbiter
    // Start of a method/block
    public void arbiter(L_Result r) {
        // Calls a method
        r.r1 = pool.count();
    // End of a block/expression
    }
// End of a block/expression
}
