// Package declaration for this file
package net.minestom.server.entity.player;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.coordinate.ChunkRange;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.MainHand;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.player.PlayerMoveEvent;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.message.ChatMessageType;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientSettingsPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerPositionPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientTeleportConfirmPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityPositionPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.PlayerPositionAndLookPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.UnloadChunkPacket;
// Import of a required class
import net.minestom.server.network.player.ClientSettings;
// Import of a required class
import net.minestom.testing.Collector;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import net.minestom.testing.TestConnection;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.CompletableFuture;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerMovementIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void teleportConfirm(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 40, 0));
        // No confirmation
        // Calls a method
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.2, 40, 0), true, false));
        // Calls a method
        p1.interpretPacketQueue();
        // Calls a method
        assertEquals(new Pos(0, 40, 0), p1.getPosition());
        // Confirmation
        // Calls a method
        p1.addPacketToQueue(new ClientTeleportConfirmPacket(p1.getLastSentTeleportId()));
        // Calls a method
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.2, 40, 0), true, false));
        // Calls a method
        p1.interpretPacketQueue();
        // Calls a method
        assertEquals(new Pos(0.2, 40, 0), p1.getPosition());
    // End of a block/expression
    }

    // FIXME
    //@Test
    // Start of a method/block
    public void singleTickMovementUpdate(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 40, 0));
        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        p1.addPacketToQueue(new ClientTeleportConfirmPacket(p1.getLastSentTeleportId()));
        // Calls a method
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.2, 40, 0), true, false));
        // Calls a method
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.4, 40, 0), true, false));
        // Calls a method
        var tracker = connection.trackIncoming(EntityPositionPacket.class);
        // Calls a method
        p1.interpretPacketQueue();

        // Position update should only be sent once per tick independently of the number of packets
        // Calls a method
        tracker.assertSingle();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkUpdateDebounceTest(Env env) {
        // Calls a method
        final Instance flatInstance = env.createFlatInstance();
        // Calls a method
        final int viewDiameter = (ServerFlag.CHUNK_VIEW_DISTANCE + 1) * 2 + 1;
        // Preload all possible chunks to avoid issues due to async loading
        // Calls a method
        Set<CompletableFuture<Chunk>> chunks = new HashSet<>();
        // Calls a method
        ChunkRange.chunksInRange(0, 0, viewDiameter + 2, (x, z) -> chunks.add(flatInstance.loadChunk(x, z)));
        // Calls a method
        CompletableFuture.allOf(chunks.toArray(CompletableFuture[]::new)).join();
        // Calls a method
        final TestConnection connection = env.createConnection();
        // Calls a method
        Collector<ChunkDataPacket> chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        final Player player = connection.connect(flatInstance, new Pos(0.5, 40, 0.5));
        // Initial join
        // Calls a method
        chunkDataPacketCollector.assertCount(ChunkRange.chunksCount(player.effectiveViewDistance()));
        // Calls a method
        player.addPacketToQueue(new ClientTeleportConfirmPacket(player.getLastSentTeleportId()));

        // Move to next chunk
        // Calls a method
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(-0.5, 40, 0.5), true, false));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        chunkDataPacketCollector.assertCount(viewDiameter);

        // Move to next chunk
        // Calls a method
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(-0.5, 40, -0.5), true, false));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        chunkDataPacketCollector.assertCount(viewDiameter);

        // Move to next chunk
        // Calls a method
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(0.5, 40, -0.5), true, false));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        chunkDataPacketCollector.assertCount(viewDiameter);

        // Move to next chunk
        // Calls a method
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(0.5, 40, 0.5), true, false));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        chunkDataPacketCollector.assertEmpty();

        // Move to next chunk
        // Calls a method
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(0.5, 40, -0.5), true, false));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        chunkDataPacketCollector.assertEmpty();

        // Move to next chunk
        // Calls a method
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Abuse the fact that there is no delta check
        // Calls a method
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(16.5, 40, -16.5), true, false));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        chunkDataPacketCollector.assertCount(viewDiameter * 2 - 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testClientViewDistanceSettings(Env env) {
        // Assigns a value
        int viewDistance = 4;
        // Calls a method
        final Instance flatInstance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        Player player = connection.connect(flatInstance, new Pos(0.5, 40, 0.5));
        // Preload all possible chunks to avoid issues due to async loading
        // Calls a method
        Set<CompletableFuture<Chunk>> chunks = new HashSet<>();
        // Calls a method
        ChunkRange.chunksInRange(10, 10, viewDistance + 3, (x, z) -> chunks.add(flatInstance.loadChunk(x, z)));
        // Calls a method
        CompletableFuture.allOf(chunks.toArray(CompletableFuture[]::new)).join();
        // Code statement
        player.refreshSettings(new ClientSettings(
                // Code statement
                Locale.US, (byte) viewDistance,
                // Code statement
                ChatMessageType.FULL, true,
                // Code statement
                (byte) 0, MainHand.RIGHT,
                // Code statement
                false, true,
                // Code statement
                ClientSettings.ParticleSetting.ALL
        // Code statement
        ));

        // Calls a method
        Collector<ChunkDataPacket> chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientTeleportConfirmPacket(player.getLastSentTeleportId()));
        // Calls a method
        player.teleport(new Pos(176, 40, 176));
        // Calls a method
        player.addPacketToQueue(new ClientTeleportConfirmPacket(player.getLastSentTeleportId()));
        // Calls a method
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(176.5, 40, 176.5), true, false));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        chunkDataPacketCollector.assertCount(ChunkRange.chunksCount(player.effectiveViewDistance()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSettingsViewDistanceExpansionAndShrink(Env env) {
        // Assigns a value
        int startingViewDistance = 8;
        // Assigns a value
        byte endViewDistance = 12;
        // Assigns a value
        byte finalViewDistance = 10;
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        Pos startingPlayerPos = new Pos(0, 42, 0);
        // Calls a method
        var player = connection.connect(instance, startingPlayerPos);

        // Calls a method
        int chunkDifference = ChunkRange.chunksCount(endViewDistance) - ChunkRange.chunksCount(startingViewDistance);

        // Preload chunks, otherwise our first tracker.assertCount call will fail randomly due to chunks being loaded off the main thread
        // Calls a method
        ChunkRange.chunksInRange(0, 0, endViewDistance, (chunkX, chunkZ) -> instance.loadChunk(chunkX, chunkZ).join());

        // Calls a method
        var tracker = connection.trackIncoming(ChunkDataPacket.class);
        // Code statement
        player.addPacketToQueue(new ClientSettingsPacket(new ClientSettings(Locale.US, endViewDistance,
                // Code statement
                ChatMessageType.FULL, false, (byte) 0, MainHand.RIGHT,
                // Code statement
                false, true, ClientSettings.ParticleSetting.ALL)));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        tracker.assertCount(chunkDifference);

        // Calls a method
        var tracker1 = connection.trackIncoming(UnloadChunkPacket.class);
        // Code statement
        player.addPacketToQueue(new ClientSettingsPacket(new ClientSettings(Locale.US, finalViewDistance,
                // Code statement
                ChatMessageType.FULL, false, (byte) 0, MainHand.RIGHT,
                // Code statement
                false, true, ClientSettings.ParticleSetting.ALL)));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        int chunkDifference1 = ChunkRange.chunksCount(endViewDistance) - ChunkRange.chunksCount(finalViewDistance);
        // Calls a method
        tracker1.assertCount(chunkDifference1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCancelledMove(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var p1 = connection.connect(instance, new Pos(0, 40, 0));
        // Code statement
        p1.refreshReceivedTeleportId(p1.getLastSentTeleportId()); // Don't care about teleport confirm from spawn

        // Calls a method
        instance.eventNode().addListener(PlayerMoveEvent.class, event -> event.setCancelled(true));
        // Calls a method
        var collector = connection.trackIncoming(PlayerPositionAndLookPacket.class);

        // Calls a method
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.2, 40, 0), true, false));
        // Calls a method
        p1.interpretPacketQueue();

        // Calls a method
        assertEquals(new Pos(0, 40, 0), p1.getPosition());
        // Start of a method/block
        collector.assertSingle(packet -> {
            // Calls a method
            assertEquals(0, packet.flags());
            // Calls a method
            assertEquals(new Vec(0, 40, 0), packet.position().asVec());
            // Must reset velocity or the player will keep moving and create a loop of teleport cancel teleport.
            // Calls a method
            assertEquals(Vec.ZERO, packet.delta());
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
