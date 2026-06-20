// Package declaration for this file
package net.minestom.server.entity.player;

// Import of a required class
import net.minestom.server.coordinate.ChunkRange;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientStatusPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.UnloadChunkPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;


// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerRespawnChunkIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testChunkUnloadsOnRespawn(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        Player player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        player.teleport(new Pos(32, 40, 32)).join();

        // Calls a method
        var unloadChunkTracker = connection.trackIncoming(UnloadChunkPacket.class);
        // Calls a method
        player.setHealth(0);
        // Calls a method
        player.respawn();
        // Since client unloads the chunks, we shouldn't receive any unload packets
        // Calls a method
        unloadChunkTracker.assertCount(0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testChunkReloadCount(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        Player player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var loadChunkTracker = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        player.setHealth(0);
        // Calls a method
        player.respawn();
        // Player should have all their chunks reloaded
        // Calls a method
        int chunkLoads = ChunkRange.chunksCount(player.effectiveViewDistance());
        // Calls a method
        loadChunkTracker.assertCount(chunkLoads);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPlayerTryRespawn(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        Player player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var loadChunkTracker = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        player.setHealth(0);
        // Calls a method
        player.addPacketToQueue(new ClientStatusPacket(ClientStatusPacket.Action.PERFORM_RESPAWN));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        List<ChunkDataPacket> dataPacketList = loadChunkTracker.collect();
        // Calls a method
        Set<ChunkDataPacket> duplicateCheck = new HashSet<>();
        // Calls a method
        int chunkLoads = ChunkRange.chunksCount(player.effectiveViewDistance());
        // Calls a method
        loadChunkTracker.assertCount(chunkLoads);
        // Loop: repeats a block
        for (ChunkDataPacket packet : dataPacketList) {
            // Calls a method
            assertFalse(duplicateCheck.contains(packet));
            // Calls a method
            duplicateCheck.add(packet);
            // Calls a method
            assertTrue(Math.abs(packet.chunkX()) <= player.effectiveViewDistance() && Math.abs(packet.chunkZ()) <= player.effectiveViewDistance());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
