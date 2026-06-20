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

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityViewIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void emptyEntity(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 42)).join();
        // Appelle une méthode
        assertEquals(0, entity.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void emptyPlayer(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(0, player.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void multiPlayers(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 42));
        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 42));

        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        p1.getViewers().forEach(p -> assertEquals(p2, p));

        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());
        // Appelle une méthode
        p2.getViewers().forEach(p -> assertEquals(p1, p));

        // Appelle une méthode
        p2.remove();
        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());

        // Appelle une méthode
        var p3 = env.createPlayer(instance, new Pos(0, 42, 42));
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        p1.getViewers().forEach(p -> assertEquals(p3, p));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void manualViewers(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 5_000));

        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());
        // Appelle une méthode
        p1.addViewer(p2);
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());

        // Appelle une méthode
        p2.teleport(new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void movements(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 96));

        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, p2.getViewers().size());

        // Instruction de code
        p2.teleport(new Pos(0, 42, 95)).join(); // Teleport in range (6 chunks)
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void autoViewable(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertTrue(p1.isAutoViewable());
        // Appelle une méthode
        p1.setAutoViewable(false);

        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        assertEquals(0, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());

        // Appelle une méthode
        p1.setAutoViewable(true);
        // Appelle une méthode
        assertEquals(1, p1.getViewers().size());
        // Appelle une méthode
        assertEquals(1, p2.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void predictableViewers(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertTrue(p.hasPredictableViewers());
        // Appelle une méthode
        p.setAutoViewable(false);
        // Appelle une méthode
        assertFalse(p.hasPredictableViewers());
        // Appelle une méthode
        p.setAutoViewable(true);
        // Appelle une méthode
        assertTrue(p.hasPredictableViewers());
        // MANUAL VIEWERS
        // Début d'un bloc
        {
            // Appelle une méthode
            var tmpPlayer = env.createPlayer(instance, new Pos(0, 42, 0));
            // Appelle une méthode
            p.addViewer(tmpPlayer);
            // Appelle une méthode
            assertFalse(p.hasPredictableViewers());
            // Appelle une méthode
            p.removeViewer(tmpPlayer);
            // Appelle une méthode
            tmpPlayer.remove();
            // Appelle une méthode
            assertTrue(p.hasPredictableViewers());
        // Fin d'un bloc/d'une expression
        }
        // CHANGE RULE
        // Début d'un bloc
        {
            // Appelle une méthode
            p.updateViewableRule(player -> false);
            // Appelle une méthode
            assertFalse(p.hasPredictableViewers());
            // Appelle une méthode
            p.updateViewableRule(null);
            // Appelle une méthode
            assertTrue(p.hasPredictableViewers());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void livingVehicle(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var passenger = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        var tracker = connection.trackIncoming(SpawnEntityPacket.class);

        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        vehicle.addPassenger(passenger);
        // Verify packets
        // Début d'un bloc
        {
            // Appelle une méthode
            var results = tracker.collect();
            // Appelle une méthode
            assertEquals(2, results.size());
            // Appelle une méthode
            assertEquals(vehicle.getEntityId(), results.get(0).entityId());
            // Appelle une méthode
            assertEquals(passenger.getEntityId(), results.get(1).entityId());
        // Fin d'un bloc/d'une expression
        }
        // Verify viewers
        // Début d'un bloc
        {
            // Appelle une méthode
            assertEquals(0, player.getViewers().size());
            // Appelle une méthode
            assertEquals(1, vehicle.getViewers().size());
            // Appelle une méthode
            assertTrue(vehicle.isViewer(player));
            // Appelle une méthode
            assertEquals(1, passenger.getViewers().size());
            // Appelle une méthode
            assertTrue(passenger.isViewer(player));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sendsSpawnPacketsToExistingViewers(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var passenger = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        vehicle.addPassenger(passenger);

        // Appelle une méthode
        var tracker = connection.trackIncoming(SpawnEntityPacket.class);
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Affecte une valeur
        var spawns = tracker.collect().stream()
                // Appelle une méthode
                .filter(p -> p.entityId() != player.getEntityId()).toList();
        // Appelle une méthode
        assertEquals(2, spawns.size());

        // Appelle une méthode
        assertEquals(1, vehicle.getViewers().size());
        // Appelle une méthode
        assertTrue(vehicle.isViewer(player));
        // Appelle une méthode
        assertEquals(1, passenger.getViewers().size());
        // Appelle une méthode
        assertTrue(passenger.isViewer(player));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void vehicleInheritance(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var p1 = env.createPlayer(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        var p2 = env.createPlayer(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var vehicle1 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        vehicle1.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        vehicle1.addPassenger(p1);

        // Appelle une méthode
        var vehicle2 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        vehicle2.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        vehicle2.addPassenger(p2);

        // Appelle une méthode
        assertEquals(2, vehicle1.getViewers().size());
        // Appelle une méthode
        assertTrue(vehicle1.getViewers().contains(p2));

        // Appelle une méthode
        assertEquals(2, vehicle2.getViewers().size());
        // Appelle une méthode
        assertTrue(vehicle2.getViewers().contains(p1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sizeMatchesIteratorIncludingNullPlayers(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        var set = entity.getViewers();

        // Appelle une méthode
        env.createPlayer(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        assertEquals(1, set.size());

        // Appelle une méthode
        entity.viewEngine.viewableOption.bitSet.add(-1);

        // Appelle une méthode
        assertEquals(1, set.size());

        // Affecte une valeur
        long iteratorCount = 0;
        // Boucle : répète un bloc
        for (var _ : set) iteratorCount++;
        // Appelle une méthode
        assertEquals(set.size(), iteratorCount);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
