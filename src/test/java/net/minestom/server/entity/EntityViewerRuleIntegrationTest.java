// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SpawnEntityPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityViewerRuleIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewableRule(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        p1.updateViewableRule(p -> p.getEntityId() == p1.getEntityId() + 1);

        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        p1.updateViewableRule(player -> false);

        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewableRuleUpdate(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        AtomicBoolean enabled = new AtomicBoolean(false);
        // Appelle une méthode
        p1.updateViewableRule(p -> enabled.get());

        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        enabled.set(true);
        // Appelle une méthode
        p1.updateViewableRule();
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewableRuleDouble(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        AtomicBoolean enabled1 = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean enabled2 = new AtomicBoolean(false);

        // Appelle une méthode
        p1.updateViewableRule(p -> enabled1.get());
        // Appelle une méthode
        p2.updateViewableRule(p -> enabled2.get());
        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());

        // Appelle une méthode
        enabled1.set(true);
        // Appelle une méthode
        p1.updateViewableRule();
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());

        // Appelle une méthode
        enabled2.set(true);
        // Appelle une méthode
        p2.updateViewableRule();
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        enabled1.set(false);
        // Appelle une méthode
        p1.updateViewableRule();
        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewerRule(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        p1.updateViewerRule(e -> e.getEntityId() == p1.getEntityId() + 1);

        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        p1.updateViewerRule(player -> false);

        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewerRuleUpdate(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        AtomicBoolean enabled = new AtomicBoolean(false);
        // Appelle une méthode
        p1.updateViewerRule(e -> enabled.get());

        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());

        // Appelle une méthode
        enabled.set(true);
        // Appelle une méthode
        p1.updateViewerRule();
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewerRuleDouble(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        AtomicBoolean enabled1 = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean enabled2 = new AtomicBoolean(false);

        // Appelle une méthode
        p1.updateViewerRule(e -> enabled1.get());
        // Appelle une méthode
        p2.updateViewerRule(e -> enabled2.get());
        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());

        // Appelle une méthode
        enabled1.set(true);
        // Appelle une méthode
        p1.updateViewerRule();
        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        enabled2.set(true);
        // Appelle une méthode
        p2.updateViewerRule();
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        enabled1.set(false);
        // Appelle une méthode
        p1.updateViewerRule();
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void passengerRespectsViewableRuleOnJoin(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var spawnTracker = connection.trackIncoming(SpawnEntityPacket.class);

        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        var passenger = new Entity(EntityType.PIG);
        // Appelle une méthode
        passenger.updateViewableRule(p -> false);
        // Appelle une méthode
        vehicle.addPassenger(passenger);

        // Appelle une méthode
        var testPlayer = connection.connect(instance, new Pos(0, 40, 0));

        // Affecte une valeur
        var spawns = spawnTracker.collect().stream()
                // Instruction de code
                .filter(p -> p.entityId() != testPlayer.getEntityId())
                // Appelle une méthode
                .toList();
        // Appelle une méthode
        assertEquals(1, spawns.size());
        // Appelle une méthode
        assertEquals(vehicle.getEntityId(), spawns.getFirst().entityId());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void passengerRespectsViewableRuleChange(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var spawnTracker = connection.trackIncoming(SpawnEntityPacket.class);

        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        var passenger = new Entity(EntityType.PIG);
        // Appelle une méthode
        vehicle.addPassenger(passenger);

        // Appelle une méthode
        var testPlayer = connection.connect(instance, new Pos(0, 40, 0));

        // Affecte une valeur
        var spawns = spawnTracker.collect().stream()
                // Instruction de code
                .filter(p -> p.entityId() != testPlayer.getEntityId())
                // Appelle une méthode
                .toList();
        // Appelle une méthode
        assertEquals(2, spawns.size());

        // Appelle une méthode
        passenger.updateViewableRule(p -> false);

        // Appelle une méthode
        assertTrue(vehicle.getViewers().contains(testPlayer));
        // Appelle une méthode
        assertFalse(passenger.getViewers().contains(testPlayer));
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void vehicleViewableRuleChange(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var spawnTracker = connection.trackIncoming(SpawnEntityPacket.class);

        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        var passenger = new Entity(EntityType.PIG);
        // Appelle une méthode
        vehicle.addPassenger(passenger);

        // Appelle une méthode
        var testPlayer = connection.connect(instance, new Pos(0, 40, 0));

        // Affecte une valeur
        var spawns = spawnTracker.collect().stream()
                // Instruction de code
                .filter(p -> p.entityId() != testPlayer.getEntityId())
                // Appelle une méthode
                .toList();
        // Appelle une méthode
        assertEquals(2, spawns.size());

        // Appelle une méthode
        vehicle.updateViewableRule(p -> false);

        // Appelle une méthode
        assertFalse(vehicle.getViewers().contains(testPlayer));
        // Appelle une méthode
        assertFalse(passenger.getViewers().contains(testPlayer));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void manualViewerOnlySeesVehicle(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var spawnTracker1 = connection.trackIncoming(SpawnEntityPacket.class);
        // Appelle une méthode
        var spawnTracker2 = connection.trackIncoming(SpawnEntityPacket.class);

        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var passenger = new Entity(EntityType.PIG);
        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();

        // Appelle une méthode
        vehicle.setAutoViewable(false);
        // Appelle une méthode
        passenger.setAutoViewable(false);
        // Appelle une méthode
        vehicle.addPassenger(passenger);

        // Appelle une méthode
        var testPlayer = connection.connect(instance, new Pos(0, 40, 5000));
        // Appelle une méthode
        spawnTracker1.assertCount(0);

        // Appelle une méthode
        vehicle.addViewer(testPlayer);

        // Appelle une méthode
        spawnTracker2.assertCount(1);
        // Appelle une méthode
        assertTrue(vehicle.isViewer(testPlayer));
        // Appelle une méthode
        assertFalse(passenger.isViewer(testPlayer));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void manualViewerRespectsPassengerRule(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var spawnTracker1 = connection.trackIncoming(SpawnEntityPacket.class);
        // Appelle une méthode
        var spawnTracker2 = connection.trackIncoming(SpawnEntityPacket.class);

        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var passenger = new Entity(EntityType.PIG);
        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();

        // Appelle une méthode
        vehicle.setAutoViewable(false);
        // Appelle une méthode
        passenger.updateViewableRule(p -> false);
        // Appelle une méthode
        vehicle.addPassenger(passenger);

        // Appelle une méthode
        var testPlayer = connection.connect(instance, new Pos(0, 40, 5000));
        // Appelle une méthode
        spawnTracker1.assertCount(0);

        // Appelle une méthode
        vehicle.addViewer(testPlayer);

        // Appelle une méthode
        spawnTracker2.assertCount(1);
        // Appelle une méthode
        assertEquals(vehicle.getEntityId(), spawnTracker2.collect().getFirst().entityId());
        // Appelle une méthode
        assertTrue(vehicle.isViewer(testPlayer));
        // Appelle une méthode
        assertFalse(passenger.isViewer(testPlayer));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}