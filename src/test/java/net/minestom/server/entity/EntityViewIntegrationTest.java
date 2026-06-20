// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.server.play.SpawnEntityPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityViewIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void emptyEntity(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 42)).join();
        // Calls a method
        assertEquals(0, entity.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void emptyPlayer(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(0, player.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void multiPlayers(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 42));
        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 42));

        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        p1.getViewers().forEach(p -> assertEquals(p2, p));

        // Calls a method
        assertEquals(1, p2.getViewers().size());
        // Calls a method
        p2.getViewers().forEach(p -> assertEquals(p1, p));

        // Calls a method
        p2.remove();
        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());

        // Calls a method
        var p3 = env.createPlayer(instance, new Pos(0, 42, 42));
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        p1.getViewers().forEach(p -> assertEquals(p3, p));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void manualViewers(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 5_000));

        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());
        // Calls a method
        p1.addViewer(p2);
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());

        // Calls a method
        p2.teleport(new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void movements(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 96));

        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());

        // Code statement
        p2.teleport(new Pos(0, 42, 95)).join(); // Teleport in range (6 chunks)
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void autoViewable(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertTrue(p1.isAutoViewable());
        // Calls a method
        p1.setAutoViewable(false);

        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));

        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        p1.setAutoViewable(true);
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void predictableViewers(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertTrue(p.hasPredictableViewers());
        // Calls a method
        p.setAutoViewable(false);
        // Calls a method
        assertFalse(p.hasPredictableViewers());
        // Calls a method
        p.setAutoViewable(true);
        // Calls a method
        assertTrue(p.hasPredictableViewers());
        // MANUAL VIEWERS
        // Start of a block
        {
            // Calls a method
            var tmpPlayer = env.createPlayer(instance, new Pos(0, 42, 0));
            // Calls a method
            p.addViewer(tmpPlayer);
            // Calls a method
            assertFalse(p.hasPredictableViewers());
            // Calls a method
            p.removeViewer(tmpPlayer);
            // Calls a method
            tmpPlayer.remove();
            // Calls a method
            assertTrue(p.hasPredictableViewers());
        // End of a block/expression
        }
        // CHANGE RULE
        // Start of a block
        {
            // Calls a method
            p.updateViewableRule(player -> false);
            // Calls a method
            assertFalse(p.hasPredictableViewers());
            // Calls a method
            p.updateViewableRule(null);
            // Calls a method
            assertTrue(p.hasPredictableViewers());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void livingVehicle(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var passenger = new Entity(EntityType.ZOMBIE);

        // Calls a method
        var tracker = connection.trackIncoming(SpawnEntityPacket.class);

        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        vehicle.addPassenger(passenger);
        // Verify packets
        // Start of a block
        {
            // Calls a method
            var results = tracker.collect();
            // Calls a method
            assertEquals(2, results.size());
            // Calls a method
            assertEquals(vehicle.getEntityId(), results.get(0).entityId());
            // Calls a method
            assertEquals(passenger.getEntityId(), results.get(1).entityId());
        // End of a block/expression
        }
        // Verify viewers
        // Start of a block
        {
            // Calls a method
            assertEquals(0, player.getViewers().size());
            // Calls a method
            assertEquals(1, vehicle.getViewers().size());
            // Calls a method
            assertTrue(vehicle.isViewer(player));
            // Calls a method
            assertEquals(1, passenger.getViewers().size());
            // Calls a method
            assertTrue(passenger.isViewer(player));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sendsSpawnPacketsToExistingViewers(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var passenger = new Entity(EntityType.ZOMBIE);

        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        vehicle.addPassenger(passenger);

        // Calls a method
        var tracker = connection.trackIncoming(SpawnEntityPacket.class);
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Assigns a value
        var spawns = tracker.collect().stream()
                // Calls a method
                .filter(p -> p.entityId() != player.getEntityId()).toList();
        // Calls a method
        assertEquals(2, spawns.size());

        // Calls a method
        assertEquals(1, vehicle.getViewers().size());
        // Calls a method
        assertTrue(vehicle.isViewer(player));
        // Calls a method
        assertEquals(1, passenger.getViewers().size());
        // Calls a method
        assertTrue(passenger.isViewer(player));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void vehicleInheritance(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 40, 0));
        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 40, 0));

        // Calls a method
        var vehicle1 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        vehicle1.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        vehicle1.addPassenger(p1);

        // Calls a method
        var vehicle2 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        vehicle2.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        vehicle2.addPassenger(p2);

        // Calls a method
        assertEquals(2, vehicle1.getViewers().size());
        // Calls a method
        assertTrue(vehicle1.getViewers().contains(p2));

        // Calls a method
        assertEquals(2, vehicle2.getViewers().size());
        // Calls a method
        assertTrue(vehicle2.getViewers().contains(p1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sizeMatchesIteratorIncludingNullPlayers(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        var set = entity.getViewers();

        // Calls a method
        env.createPlayer(instance, new Pos(0, 40, 0));
        // Calls a method
        assertEquals(1, set.size());

        // Calls a method
        entity.viewEngine.viewableOption.bitSet.add(-1);

        // Calls a method
        assertEquals(1, set.size());

        // Assigns a value
        long iteratorCount = 0;
        // Loop: repeats a block
        for (var _ : set) iteratorCount++;
        // Calls a method
        assertEquals(set.size(), iteratorCount);
    // End of a block/expression
    }
// End of a block/expression
}
