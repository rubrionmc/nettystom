// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.coordinate.ChunkRange;
// Import of a required class
import net.minestom.server.coordinate.Pos;
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

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class ChunkViewerIntegrationTest {

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void basicJoin(boolean sharedInstance, Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Branch: checks a condition
        if (sharedInstance) {
            // Chunks get their viewers from the instance
            // Ensuring that the system works with shared instances is therefore important
            // Calls a method
            var manager = env.process().instance();
            // Calls a method
            instance = manager.createSharedInstance((InstanceContainer) instance);
        // End of a block/expression
        }

        // Calls a method
        var chunk = instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(0, chunk.getViewers().size());

        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Code statement
        assertEquals(1, chunk.getViewers().size(), sharedInstance ?
                // Code statement
                "Chunk viewer set must include players from shared instance" : "Instance should have 1 viewer");
        // Calls a method
        assertEquals(player, chunk.getViewers().iterator().next());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void renderDistance(Env env) {
        // Assigns a value
        final int viewRadius = ServerFlag.CHUNK_VIEW_DISTANCE;
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Check initial load
        // Start of a block
        {
            // Calls a method
            var tracker = connection.trackIncoming(ChunkDataPacket.class);
            // Calls a method
            var player = connection.connect(instance, new Pos(0, 40, 0));
            // Calls a method
            assertEquals(instance, player.getInstance());
            // Calls a method
            assertEquals(new Pos(0, 40, 0), player.getPosition());
            // Calls a method
            assertEquals(ChunkRange.chunksCount(player.effectiveViewDistance()), tracker.collect().size());
        // End of a block/expression
        }
        // Check chunk#sendChunk
        // Start of a block
        {
            // Calls a method
            var tracker = connection.trackIncoming(ChunkDataPacket.class);
            // Loop: repeats a block
            for (int x = -viewRadius; x <= viewRadius; x++) {
                // Loop: repeats a block
                for (int z = -viewRadius; z <= viewRadius; z++) {
                    // Calls a method
                    instance.getChunk(x, z).sendChunk();
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            assertEquals(ChunkRange.chunksCount(viewRadius), tracker.collect().size());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
