// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntitySnapshotIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var ent = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        ent.setInstance(instance).join();
        // Appelle une méthode
        var snapshot = ServerSnapshot.update();

        // Appelle une méthode
        var inst = snapshot.instances().iterator().next();
        // Appelle une méthode
        var entities = inst.entities();
        // Appelle une méthode
        assertEquals(1, entities.size());

        // Appelle une méthode
        var entity = entities.iterator().next();
        // Appelle une méthode
        assertEquals(EntityType.ZOMBIE, entity.type());
        // Appelle une méthode
        assertEquals(ent.getUuid(), entity.uuid());
        // Appelle une méthode
        assertEquals(ent.getEntityId(), entity.id());
        // Appelle une méthode
        assertEquals(ent.getPosition(), entity.position());
        // Appelle une méthode
        assertEquals(ent.getVelocity(), entity.velocity());
        // Appelle une méthode
        assertEquals(inst, entity.instance());
        // Appelle une méthode
        assertEquals(inst.chunkAt(entity.position()), entity.chunk());
        // Appelle une méthode
        assertEquals(ent.getViewers().size(), entity.viewers().size());
        // Appelle une méthode
        assertEquals(ent.getPassengers().size(), entity.passengers().size());
        // Appelle une méthode
        assertNull(ent.getVehicle());
        // Appelle une méthode
        assertNull(entity.vehicle());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
