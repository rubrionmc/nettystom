// Déclaration du paquet de ce fichier
package net.minestom.server.entity.player;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.ChunkRange;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.MainHand;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerMoveEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatMessageType;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientSettingsPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerPositionPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientTeleportConfirmPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.EntityPositionPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.PlayerPositionAndLookPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.UnloadChunkPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ClientSettings;
// Import d'une classe nécessaire
import net.minestom.testing.Collector;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.testing.TestConnection;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerMovementIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void teleportConfirm(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 40, 0));
        // No confirmation
        // Appelle une méthode
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.2, 40, 0), true, false));
        // Appelle une méthode
        p1.interpretPacketQueue();
        // Appelle une méthode
        assertEquals(new Pos(0, 40, 0), p1.getPosition());
        // Confirmation
        // Appelle une méthode
        p1.addPacketToQueue(new ClientTeleportConfirmPacket(p1.getLastSentTeleportId()));
        // Appelle une méthode
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.2, 40, 0), true, false));
        // Appelle une méthode
        p1.interpretPacketQueue();
        // Appelle une méthode
        assertEquals(new Pos(0.2, 40, 0), p1.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // FIXME
    //@Test
    // Début d'une méthode/d'un bloc
    public void singleTickMovementUpdate(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        p1.addPacketToQueue(new ClientTeleportConfirmPacket(p1.getLastSentTeleportId()));
        // Appelle une méthode
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.2, 40, 0), true, false));
        // Appelle une méthode
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.4, 40, 0), true, false));
        // Appelle une méthode
        var tracker = connection.trackIncoming(EntityPositionPacket.class);
        // Appelle une méthode
        p1.interpretPacketQueue();

        // Position update should only be sent once per tick independently of the number of packets
        // Appelle une méthode
        tracker.assertSingle();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkUpdateDebounceTest(Env env) {
        // Appelle une méthode
        final Instance flatInstance = env.createFlatInstance();
        // Affecte une valeur
        final int viewDiameter = (ServerFlag.CHUNK_VIEW_DISTANCE + 1) * 2 + 1;
        // Preload all possible chunks to avoid issues due to async loading
        // Affecte une valeur
        Set<CompletableFuture<Chunk>> chunks = new HashSet<>();
        // Appelle une méthode
        ChunkRange.chunksInRange(0, 0, viewDiameter + 2, (x, z) -> chunks.add(flatInstance.loadChunk(x, z)));
        // Appelle une méthode
        CompletableFuture.allOf(chunks.toArray(CompletableFuture[]::new)).join();
        // Appelle une méthode
        final TestConnection connection = env.createConnection();
        // Appelle une méthode
        Collector<ChunkDataPacket> chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        final Player player = connection.connect(flatInstance, new Pos(0.5, 40, 0.5));
        // Initial join
        // Appelle une méthode
        chunkDataPacketCollector.assertCount(ChunkRange.chunksCount(player.effectiveViewDistance()));
        // Appelle une méthode
        player.addPacketToQueue(new ClientTeleportConfirmPacket(player.getLastSentTeleportId()));

        // Move to next chunk
        // Appelle une méthode
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(-0.5, 40, 0.5), true, false));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        chunkDataPacketCollector.assertCount(viewDiameter);

        // Move to next chunk
        // Appelle une méthode
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(-0.5, 40, -0.5), true, false));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        chunkDataPacketCollector.assertCount(viewDiameter);

        // Move to next chunk
        // Appelle une méthode
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(0.5, 40, -0.5), true, false));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        chunkDataPacketCollector.assertCount(viewDiameter);

        // Move to next chunk
        // Appelle une méthode
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(0.5, 40, 0.5), true, false));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        chunkDataPacketCollector.assertEmpty();

        // Move to next chunk
        // Appelle une méthode
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(0.5, 40, -0.5), true, false));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        chunkDataPacketCollector.assertEmpty();

        // Move to next chunk
        // Appelle une méthode
        chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Abuse the fact that there is no delta check
        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(16.5, 40, -16.5), true, false));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        chunkDataPacketCollector.assertCount(viewDiameter * 2 - 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testClientViewDistanceSettings(Env env) {
        // Affecte une valeur
        int viewDistance = 4;
        // Appelle une méthode
        final Instance flatInstance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        Player player = connection.connect(flatInstance, new Pos(0.5, 40, 0.5));
        // Preload all possible chunks to avoid issues due to async loading
        // Affecte une valeur
        Set<CompletableFuture<Chunk>> chunks = new HashSet<>();
        // Appelle une méthode
        ChunkRange.chunksInRange(10, 10, viewDistance + 3, (x, z) -> chunks.add(flatInstance.loadChunk(x, z)));
        // Appelle une méthode
        CompletableFuture.allOf(chunks.toArray(CompletableFuture[]::new)).join();
        // Instruction de code
        player.refreshSettings(new ClientSettings(
                // Instruction de code
                Locale.US, (byte) viewDistance,
                // Instruction de code
                ChatMessageType.FULL, true,
                // Instruction de code
                (byte) 0, MainHand.RIGHT,
                // Instruction de code
                false, true,
                // Instruction de code
                ClientSettings.ParticleSetting.ALL
        // Instruction de code
        ));

        // Appelle une méthode
        Collector<ChunkDataPacket> chunkDataPacketCollector = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientTeleportConfirmPacket(player.getLastSentTeleportId()));
        // Appelle une méthode
        player.teleport(new Pos(176, 40, 176));
        // Appelle une méthode
        player.addPacketToQueue(new ClientTeleportConfirmPacket(player.getLastSentTeleportId()));
        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerPositionPacket(new Vec(176.5, 40, 176.5), true, false));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        chunkDataPacketCollector.assertCount(ChunkRange.chunksCount(player.effectiveViewDistance()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSettingsViewDistanceExpansionAndShrink(Env env) {
        // Affecte une valeur
        int startingViewDistance = 8;
        // Affecte une valeur
        byte endViewDistance = 12;
        // Affecte une valeur
        byte finalViewDistance = 10;
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        Pos startingPlayerPos = new Pos(0, 42, 0);
        // Appelle une méthode
        var player = connection.connect(instance, startingPlayerPos);

        // Appelle une méthode
        int chunkDifference = ChunkRange.chunksCount(endViewDistance) - ChunkRange.chunksCount(startingViewDistance);

        // Preload chunks, otherwise our first tracker.assertCount call will fail randomly due to chunks being loaded off the main thread
        // Appelle une méthode
        ChunkRange.chunksInRange(0, 0, endViewDistance, (chunkX, chunkZ) -> instance.loadChunk(chunkX, chunkZ).join());

        // Appelle une méthode
        var tracker = connection.trackIncoming(ChunkDataPacket.class);
        // Instruction de code
        player.addPacketToQueue(new ClientSettingsPacket(new ClientSettings(Locale.US, endViewDistance,
                // Instruction de code
                ChatMessageType.FULL, false, (byte) 0, MainHand.RIGHT,
                // Instruction de code
                false, true, ClientSettings.ParticleSetting.ALL)));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        tracker.assertCount(chunkDifference);

        // Appelle une méthode
        var tracker1 = connection.trackIncoming(UnloadChunkPacket.class);
        // Instruction de code
        player.addPacketToQueue(new ClientSettingsPacket(new ClientSettings(Locale.US, finalViewDistance,
                // Instruction de code
                ChatMessageType.FULL, false, (byte) 0, MainHand.RIGHT,
                // Instruction de code
                false, true, ClientSettings.ParticleSetting.ALL)));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        int chunkDifference1 = ChunkRange.chunksCount(endViewDistance) - ChunkRange.chunksCount(finalViewDistance);
        // Appelle une méthode
        tracker1.assertCount(chunkDifference1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCancelledMove(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var p1 = connection.connect(instance, new Pos(0, 40, 0));
        // Instruction de code
        p1.refreshReceivedTeleportId(p1.getLastSentTeleportId()); // Don't care about teleport confirm from spawn

        // Appelle une méthode
        instance.eventNode().addListener(PlayerMoveEvent.class, event -> event.setCancelled(true));
        // Appelle une méthode
        var collector = connection.trackIncoming(PlayerPositionAndLookPacket.class);

        // Appelle une méthode
        p1.addPacketToQueue(new ClientPlayerPositionPacket(new Pos(0.2, 40, 0), true, false));
        // Appelle une méthode
        p1.interpretPacketQueue();

        // Appelle une méthode
        assertEquals(new Pos(0, 40, 0), p1.getPosition());
        // Début d'une méthode/d'un bloc
        collector.assertSingle(packet -> {
            // Appelle une méthode
            assertEquals(0, packet.flags());
            // Appelle une méthode
            assertEquals(new Vec(0, 40, 0), packet.position().asVec());
            // Must reset velocity or the player will keep moving and create a loop of teleport cancel teleport.
            // Appelle une méthode
            assertEquals(Vec.ZERO, packet.delta());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
