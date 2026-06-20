// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityTickEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DestroyEntitiesPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.lang.ref.WeakReference;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityRemovalIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void destructionPacket(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();

        // Appelle une méthode
        var tracker = connection.trackIncoming(DestroyEntitiesPacket.class);
        // Appelle une méthode
        entity.remove();
        // Appelle une méthode
        tracker.assertSingle(packet -> assertEquals(List.of(entity.getEntityId()), packet.entityIds()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void instanceRemoval(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        assertFalse(entity.isRemoved());

        // Appelle une méthode
        entity.remove();
        // Appelle une méthode
        assertTrue(entity.isRemoved());
        // Appelle une méthode
        assertFalse(instance.getEntities().contains(entity), "Entity must not be in the instance anymore");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void tickTimedRemoval(Env env) throws InterruptedException {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new TestEntity(2, TimeUnit.SERVER_TICK);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();

        // Appelle une méthode
        assertFalse(entity.isRemoved());
        // Appelle une méthode
        assertEquals(0, entity.getAliveTicks());

        // Instruction de code
        Thread.sleep(150); // Ensure that time is not used for tick scheduling

        // Appelle une méthode
        env.tick();
        // Appelle une méthode
        assertFalse(entity.isRemoved());
        // Appelle une méthode
        assertEquals(1, entity.getAliveTicks());

        // Appelle une méthode
        env.tick();
        // Appelle une méthode
        assertTrue(entity.isRemoved());
        // Appelle une méthode
        assertEquals(1, entity.getAliveTicks());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityGC(Env env) {
        // Ensure that entities do not stay in memory after they are removed
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        entity.remove();

        // Appelle une méthode
        var ref = new WeakReference<>(entity);
        //noinspection UnusedAssignment
        // Affecte une valeur
        entity = null;
        // Instruction de code
        env.tick(); // Required to remove the entity from the thread dispatcher
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityNodeGC(Env env) {
        // Ensure that the entities GCed when a local listener is present
        // Appelle une méthode
        var node = env.process().eventHandler();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Début d'une méthode/d'un bloc
        entity.eventNode().addListener(EntityTickEvent.class, event -> {
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        node.call(new EntityTickEvent(entity));

        // Appelle une méthode
        var ref = new WeakReference<>(entity);
        // Appelle une méthode
        entity.remove();
        //noinspection UnusedAssignment
        // Affecte une valeur
        entity = null;
        // Appelle une méthode
        env.tick();
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class TestEntity extends Entity {
        // Début d'une méthode/d'un bloc
        public TestEntity(long delay, TemporalUnit unit) {
            // Accès à l'objet courant/parent
            super(EntityType.ZOMBIE);
            // Appelle une méthode
            scheduleRemove(delay, unit);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
