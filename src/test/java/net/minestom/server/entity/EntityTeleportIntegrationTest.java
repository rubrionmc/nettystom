// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.EntityPositionSyncPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.PlayerPositionAndLookPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.ExecutionException;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.concurrent.TimeoutException;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityTeleportIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityChunkTeleport(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());
        // Appelle une méthode
        assertEquals(new Pos(0, 42, 0), entity.getPosition());

        // Appelle une méthode
        entity.teleport(new Pos(1, 42, 1)).join();
        // Appelle une méthode
        assertEquals(new Pos(1, 42, 1), entity.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityTeleport(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());
        // Appelle une méthode
        assertEquals(new Pos(0, 42, 0), entity.getPosition());

        // Appelle une méthode
        entity.teleport(new Pos(52, 42, 52)).join();
        // Appelle une méthode
        assertEquals(new Pos(52, 42, 52), entity.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void playerChunkTeleport(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());
        // Appelle une méthode
        assertEquals(new Pos(0, 40, 0), player.getPosition());

        // Appelle une méthode
        var viewerConnection = env.createConnection();
        // Appelle une méthode
        viewerConnection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var tracker = connection.trackIncoming(ServerPacket.class);
        // Appelle une méthode
        var viewerTracker = viewerConnection.trackIncoming(ServerPacket.class);
        // Appelle une méthode
        var teleportPosition = new Pos(1, 42, 1).withYaw(5);
        // Appelle une méthode
        player.teleport(teleportPosition).join();
        // Appelle une méthode
        assertEquals(teleportPosition, player.getPosition());

        // Verify received packet(s)
        // Instruction de code
        tracker.assertSingle(PlayerPositionAndLookPacket.class,
                // Appelle une méthode
                packet -> assertEquals(teleportPosition, packet.position()));
        // Verify broadcast packet(s)

        // Appelle une méthode
        viewerTracker.assertCount(1);
        // Début d'une méthode/d'un bloc
        viewerTracker.assertSingle(EntityPositionSyncPacket.class, packet -> {
            // Appelle une méthode
            assertEquals(player.getEntityId(), packet.entityId());
            // Appelle une méthode
            assertEquals(teleportPosition, packet.position());
            // Appelle une méthode
            assertEquals(teleportPosition.yaw(), packet.yaw());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void playerTeleport(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());
        // Appelle une méthode
        assertEquals(new Pos(0, 40, 0), player.getPosition());

        // Appelle une méthode
        var viewerConnection = env.createConnection();
        // Appelle une méthode
        viewerConnection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var teleportPosition = new Pos(4999, 42, 4999);
        // Appelle une méthode
        player.teleport(teleportPosition).join();
        // Appelle une méthode
        assertEquals(teleportPosition, player.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void playerTeleportWithFlagsTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 0, 0));

        // Appelle une méthode
        player.teleport(new Pos(10, 10, 10, 90, 0)).join();
        // Appelle une méthode
        assertEquals(new Pos(10, 10, 10, 90, 0), player.getPosition());

        // Appelle une méthode
        player.teleport(new Pos(0, 0, 0, 0, 0), null, RelativeFlags.ALL).join();
        // Appelle une méthode
        assertEquals(new Pos(10, 10, 10, 90, 0), player.getPosition());

        // Appelle une méthode
        player.teleport(new Pos(5, 10, 2, 5, 5), null, RelativeFlags.VIEW).join();
        // Appelle une méthode
        assertEquals(new Pos(5, 10, 2, 95, 5), player.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityTeleportToInfinity(Env env) throws ExecutionException, InterruptedException, TimeoutException {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());
        // Appelle une méthode
        assertEquals(new Pos(0, 42, 0), entity.getPosition());

        // Appelle une méthode
        entity.teleport(new Pos(Double.POSITIVE_INFINITY, 42, 52)).join();
        // Instruction de code
        CompletableFuture.runAsync(() -> entity.tick(0 /* 0 is fine here, it's just a delta*/))
                // Appelle une méthode
                .get(10, TimeUnit.SECONDS);
        // This should not hang forever

        // The position should have been capped at 2 billion.
        // Appelle une méthode
        assertEquals(new Pos(Entity.MAX_COORDINATE, 42, 52), entity.getPosition());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
