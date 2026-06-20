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

// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityViewerRuleIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewableRule(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        p1.updateViewableRule(p -> p.getEntityId() == p1.getEntityId() + 1);

        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));

        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        p1.updateViewableRule(player -> false);

        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewableRuleUpdate(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));

        // Calls a method
        AtomicBoolean enabled = new AtomicBoolean(false);
        // Calls a method
        p1.updateViewableRule(p -> enabled.get());

        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        enabled.set(true);
        // Calls a method
        p1.updateViewableRule();
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewableRuleDouble(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        AtomicBoolean enabled1 = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean enabled2 = new AtomicBoolean(false);

        // Calls a method
        p1.updateViewableRule(p -> enabled1.get());
        // Calls a method
        p2.updateViewableRule(p -> enabled2.get());
        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());

        // Calls a method
        enabled1.set(true);
        // Calls a method
        p1.updateViewableRule();
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());

        // Calls a method
        enabled2.set(true);
        // Calls a method
        p2.updateViewableRule();
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        enabled1.set(false);
        // Calls a method
        p1.updateViewableRule();
        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewerRule(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        p1.updateViewerRule(e -> e.getEntityId() == p1.getEntityId() + 1);

        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));

        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        p1.updateViewerRule(player -> false);

        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewerRuleUpdate(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        AtomicBoolean enabled = new AtomicBoolean(false);
        // Calls a method
        p1.updateViewerRule(e -> enabled.get());

        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());

        // Calls a method
        enabled.set(true);
        // Calls a method
        p1.updateViewerRule();
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewerRuleDouble(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        AtomicBoolean enabled1 = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean enabled2 = new AtomicBoolean(false);

        // Calls a method
        p1.updateViewerRule(e -> enabled1.get());
        // Calls a method
        p2.updateViewerRule(e -> enabled2.get());
        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());

        // Calls a method
        enabled1.set(true);
        // Calls a method
        p1.updateViewerRule();
        // Calls a method
        assertEquals(0, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        enabled2.set(true);
        // Calls a method
        p2.updateViewerRule();
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(1, p2.getViewers().size());

        // Calls a method
        enabled1.set(false);
        // Calls a method
        p1.updateViewerRule();
        // Calls a method
        assertEquals(1, p1.getViewers().size());
        // Calls a method
        assertEquals(0, p2.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void passengerRespectsViewableRuleOnJoin(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var spawnTracker = connection.trackIncoming(SpawnEntityPacket.class);

        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        var passenger = new Entity(EntityType.PIG);
        // Calls a method
        passenger.updateViewableRule(p -> false);
        // Calls a method
        vehicle.addPassenger(passenger);

        // Calls a method
        var testPlayer = connection.connect(instance, new Pos(0, 40, 0));

        // Assigns a value
        var spawns = spawnTracker.collect().stream()
                // Code statement
                .filter(p -> p.entityId() != testPlayer.getEntityId())
                // Calls a method
                .toList();
        // Calls a method
        assertEquals(1, spawns.size());
        // Calls a method
        assertEquals(vehicle.getEntityId(), spawns.getFirst().entityId());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void passengerRespectsViewableRuleChange(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var spawnTracker = connection.trackIncoming(SpawnEntityPacket.class);

        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        var passenger = new Entity(EntityType.PIG);
        // Calls a method
        vehicle.addPassenger(passenger);

        // Calls a method
        var testPlayer = connection.connect(instance, new Pos(0, 40, 0));

        // Assigns a value
        var spawns = spawnTracker.collect().stream()
                // Code statement
                .filter(p -> p.entityId() != testPlayer.getEntityId())
                // Calls a method
                .toList();
        // Calls a method
        assertEquals(2, spawns.size());

        // Calls a method
        passenger.updateViewableRule(p -> false);

        // Calls a method
        assertTrue(vehicle.getViewers().contains(testPlayer));
        // Calls a method
        assertFalse(passenger.getViewers().contains(testPlayer));
    // End of a block/expression
    }


    // Annotation for the following element
    @Test
    // Start of a method/block
    public void vehicleViewableRuleChange(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var spawnTracker = connection.trackIncoming(SpawnEntityPacket.class);

        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        var passenger = new Entity(EntityType.PIG);
        // Calls a method
        vehicle.addPassenger(passenger);

        // Calls a method
        var testPlayer = connection.connect(instance, new Pos(0, 40, 0));

        // Assigns a value
        var spawns = spawnTracker.collect().stream()
                // Code statement
                .filter(p -> p.entityId() != testPlayer.getEntityId())
                // Calls a method
                .toList();
        // Calls a method
        assertEquals(2, spawns.size());

        // Calls a method
        vehicle.updateViewableRule(p -> false);

        // Calls a method
        assertFalse(vehicle.getViewers().contains(testPlayer));
        // Calls a method
        assertFalse(passenger.getViewers().contains(testPlayer));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void manualViewerOnlySeesVehicle(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var spawnTracker1 = connection.trackIncoming(SpawnEntityPacket.class);
        // Calls a method
        var spawnTracker2 = connection.trackIncoming(SpawnEntityPacket.class);

        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var passenger = new Entity(EntityType.PIG);
        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();

        // Calls a method
        vehicle.setAutoViewable(false);
        // Calls a method
        passenger.setAutoViewable(false);
        // Calls a method
        vehicle.addPassenger(passenger);

        // Calls a method
        var testPlayer = connection.connect(instance, new Pos(0, 40, 5000));
        // Calls a method
        spawnTracker1.assertCount(0);

        // Calls a method
        vehicle.addViewer(testPlayer);

        // Calls a method
        spawnTracker2.assertCount(1);
        // Calls a method
        assertTrue(vehicle.isViewer(testPlayer));
        // Calls a method
        assertFalse(passenger.isViewer(testPlayer));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void manualViewerRespectsPassengerRule(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var spawnTracker1 = connection.trackIncoming(SpawnEntityPacket.class);
        // Calls a method
        var spawnTracker2 = connection.trackIncoming(SpawnEntityPacket.class);

        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var passenger = new Entity(EntityType.PIG);
        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();

        // Calls a method
        vehicle.setAutoViewable(false);
        // Calls a method
        passenger.updateViewableRule(p -> false);
        // Calls a method
        vehicle.addPassenger(passenger);

        // Calls a method
        var testPlayer = connection.connect(instance, new Pos(0, 40, 5000));
        // Calls a method
        spawnTracker1.assertCount(0);

        // Calls a method
        vehicle.addViewer(testPlayer);

        // Calls a method
        spawnTracker2.assertCount(1);
        // Calls a method
        assertEquals(vehicle.getEntityId(), spawnTracker2.collect().getFirst().entityId());
        // Calls a method
        assertTrue(vehicle.isViewer(testPlayer));
        // Calls a method
        assertFalse(passenger.isViewer(testPlayer));
    // End of a block/expression
    }
// End of a block/expression
}