// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityPositionSyncPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.PlayerPositionAndLookPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.ExecutionException;
// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.concurrent.TimeoutException;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityTeleportIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityChunkTeleport(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());
        // Calls a method
        assertEquals(new Pos(0, 42, 0), entity.getPosition());

        // Calls a method
        entity.teleport(new Pos(1, 42, 1)).join();
        // Calls a method
        assertEquals(new Pos(1, 42, 1), entity.getPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityTeleport(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());
        // Calls a method
        assertEquals(new Pos(0, 42, 0), entity.getPosition());

        // Calls a method
        entity.teleport(new Pos(52, 42, 52)).join();
        // Calls a method
        assertEquals(new Pos(52, 42, 52), entity.getPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void playerChunkTeleport(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());
        // Calls a method
        assertEquals(new Pos(0, 40, 0), player.getPosition());

        // Calls a method
        var viewerConnection = env.createConnection();
        // Calls a method
        viewerConnection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var tracker = connection.trackIncoming(ServerPacket.class);
        // Calls a method
        var viewerTracker = viewerConnection.trackIncoming(ServerPacket.class);
        // Calls a method
        var teleportPosition = new Pos(1, 42, 1).withYaw(5);
        // Calls a method
        player.teleport(teleportPosition).join();
        // Calls a method
        assertEquals(teleportPosition, player.getPosition());

        // Verify received packet(s)
        // Code statement
        tracker.assertSingle(PlayerPositionAndLookPacket.class,
                // Calls a method
                packet -> assertEquals(teleportPosition, packet.position()));
        // Verify broadcast packet(s)

        // Calls a method
        viewerTracker.assertCount(1);
        // Start of a method/block
        viewerTracker.assertSingle(EntityPositionSyncPacket.class, packet -> {
            // Calls a method
            assertEquals(player.getEntityId(), packet.entityId());
            // Calls a method
            assertEquals(teleportPosition, packet.position());
            // Calls a method
            assertEquals(teleportPosition.yaw(), packet.yaw());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void playerTeleport(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());
        // Calls a method
        assertEquals(new Pos(0, 40, 0), player.getPosition());

        // Calls a method
        var viewerConnection = env.createConnection();
        // Calls a method
        viewerConnection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var teleportPosition = new Pos(4999, 42, 4999);
        // Calls a method
        player.teleport(teleportPosition).join();
        // Calls a method
        assertEquals(teleportPosition, player.getPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void playerTeleportWithFlagsTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 0, 0));

        // Calls a method
        player.teleport(new Pos(10, 10, 10, 90, 0)).join();
        // Calls a method
        assertEquals(new Pos(10, 10, 10, 90, 0), player.getPosition());

        // Calls a method
        player.teleport(new Pos(0, 0, 0, 0, 0), null, RelativeFlags.ALL).join();
        // Calls a method
        assertEquals(new Pos(10, 10, 10, 90, 0), player.getPosition());

        // Calls a method
        player.teleport(new Pos(5, 10, 2, 5, 5), null, RelativeFlags.VIEW).join();
        // Calls a method
        assertEquals(new Pos(5, 10, 2, 95, 5), player.getPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityTeleportToInfinity(Env env) throws ExecutionException, InterruptedException, TimeoutException {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());
        // Calls a method
        assertEquals(new Pos(0, 42, 0), entity.getPosition());

        // Calls a method
        entity.teleport(new Pos(Double.POSITIVE_INFINITY, 42, 52)).join();
        // Code statement
        CompletableFuture.runAsync(() -> entity.tick(0 /* 0 is fine here, it's just a delta*/))
                // Calls a method
                .get(10, TimeUnit.SECONDS);
        // This should not hang forever

        // The position should have been capped at 2 billion.
        // Calls a method
        assertEquals(new Pos(Entity.MAX_COORDINATE, 42, 52), entity.getPosition());
    // End of a block/expression
    }
// End of a block/expression
}
