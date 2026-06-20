// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class EntityTrackerTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void register() {
        // Appelle une méthode
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Affecte une valeur
        var updater = new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Appelle une méthode
                assertNotSame(ent1, entity);
                // Appelle une méthode
                fail("No other entity should be registered yet");
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Appelle une méthode
                assertNotSame(ent1, entity);
                // Appelle une méthode
                fail("No other entity should be registered yet");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        EntityTracker tracker = EntityTracker.newTracker();
        // Appelle une méthode
        var chunkEntities = tracker.chunkEntities(Vec.ZERO, EntityTracker.Target.ENTITIES);
        // Appelle une méthode
        assertTrue(chunkEntities.isEmpty());

        // Appelle une méthode
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        assertEquals(1, chunkEntities.size());

        // Appelle une méthode
        tracker.unregister(ent1, EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        assertEquals(0, chunkEntities.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void move() {
        // Appelle une méthode
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Affecte une valeur
        var updater = new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Appelle une méthode
                fail("No other entity should be registered yet");
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Appelle une méthode
                fail("No other entity should be registered yet");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        EntityTracker tracker = EntityTracker.newTracker();

        // Appelle une méthode
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        assertEquals(1, tracker.chunkEntities(Vec.ZERO, EntityTracker.Target.ENTITIES).size());

        // Appelle une méthode
        tracker.move(ent1, new Vec(32, 0, 32), EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        assertEquals(0, tracker.chunkEntities(Vec.ZERO, EntityTracker.Target.ENTITIES).size());
        // Appelle une méthode
        assertEquals(1, tracker.chunkEntities(new Vec(32, 0, 32), EntityTracker.Target.ENTITIES).size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void tracking() {
        // Appelle une méthode
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var ent2 = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        EntityTracker tracker = EntityTracker.newTracker();
        // Début d'une méthode/d'un bloc
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Appelle une méthode
                fail("No other entity should be registered yet");
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Appelle une méthode
                fail("No other entity should be registered yet");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Début d'une méthode/d'un bloc
        tracker.register(ent2, Vec.ZERO, EntityTracker.Target.ENTITIES, new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Appelle une méthode
                assertNotSame(ent2, entity);
                // Appelle une méthode
                assertSame(ent1, entity);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Appelle une méthode
                fail("No other entity should be removed yet");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Début d'une méthode/d'un bloc
        tracker.move(ent1, new Vec(Integer.MAX_VALUE, 0, 0), EntityTracker.Target.ENTITIES, new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Appelle une méthode
                assertNotSame(ent1, entity);
                // Appelle une méthode
                fail("No other entity should be added");
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Appelle une méthode
                assertNotSame(ent1, entity);
                // Appelle une méthode
                assertSame(ent2, entity);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Début d'une méthode/d'un bloc
        tracker.move(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Appelle une méthode
                assertNotSame(ent1, entity);
                // Appelle une méthode
                assertSame(ent2, entity);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Appelle une méthode
                fail("no entity to remove");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nearby() {
        // Appelle une méthode
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var ent2 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var ent3 = new Entity(EntityType.ZOMBIE);
        // Affecte une valeur
        var updater = new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Empty
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Empty
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        EntityTracker tracker = EntityTracker.newTracker();
        // Appelle une méthode
        tracker.register(ent2, new Vec(5, 0, 0), EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        tracker.register(ent3, new Vec(50, 0, 0), EntityTracker.Target.ENTITIES, updater);

        // Appelle une méthode
        tracker.nearbyEntities(Vec.ZERO, 4, EntityTracker.Target.ENTITIES, entity -> fail("No entity should be nearby"));

        // Appelle une méthode
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, updater);

        // Affecte une valeur
        Set<Entity> entities = new HashSet<>();

        // Appelle une méthode
        entities.add(ent1);
        // Appelle une méthode
        tracker.nearbyEntities(Vec.ZERO, 4, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Appelle une méthode
        assertEquals(0, entities.size());

        // Appelle une méthode
        entities.add(ent1);
        // Appelle une méthode
        tracker.nearbyEntities(Vec.ZERO, 4.99, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Appelle une méthode
        assertEquals(0, entities.size());

        // Appelle une méthode
        entities.add(ent1);
        // Appelle une méthode
        entities.add(ent2);
        // Appelle une méthode
        tracker.nearbyEntities(Vec.ZERO, 5, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Appelle une méthode
        assertEquals(0, entities.size());

        // Appelle une méthode
        entities.add(ent1);
        // Appelle une méthode
        entities.add(ent2);
        // Appelle une méthode
        entities.add(ent3);
        // Appelle une méthode
        tracker.nearbyEntities(Vec.ZERO, 50, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Appelle une méthode
        assertEquals(0, entities.size());

        // Chunk border
        // Appelle une méthode
        tracker.move(ent1, new Vec(16, 0, 0), EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        entities.add(ent1);
        // Appelle une méthode
        tracker.nearbyEntities(new Vec(15, 0, 0), 2, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Appelle une méthode
        assertEquals(0, entities.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nearbySingleChunk() {
        // Appelle une méthode
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var ent2 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var ent3 = new Entity(EntityType.ZOMBIE);
        // Affecte une valeur
        var updater = new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Empty
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Empty
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        EntityTracker tracker = EntityTracker.newTracker();
        // Appelle une méthode
        tracker.register(ent1, new Vec(5, 0, 5), EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        tracker.register(ent2, new Vec(8, 0, 8), EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        tracker.register(ent3, new Vec(17, 0, 17), EntityTracker.Target.ENTITIES, updater);

        // Affecte une valeur
        Set<Entity> entities = new HashSet<>();

        // Appelle une méthode
        entities.add(ent1);
        // Appelle une méthode
        entities.add(ent2);
        // Appelle une méthode
        tracker.nearbyEntities(Vec.ZERO, 16, EntityTracker.Target.ENTITIES, entities::add);
        // Appelle une méthode
        assertEquals(Set.of(ent1, ent2), entities);
        // Appelle une méthode
        entities.clear();

        // Appelle une méthode
        entities.add(ent1);
        // Appelle une méthode
        entities.add(ent2);
        // Appelle une méthode
        tracker.nearbyEntities(new Vec(8, 0, 8), 5, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Appelle une méthode
        assertEquals(0, entities.size());

        // Appelle une méthode
        entities.add(ent2);
        // Appelle une méthode
        tracker.nearbyEntities(new Vec(8, 0, 8), 1, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Appelle une méthode
        assertEquals(0, entities.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void collectionView() {
        // Appelle une méthode
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Affecte une valeur
        var updater = new EntityTracker.Update<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void add(Entity entity) {
                // Appelle une méthode
                assertNotSame(ent1, entity);
                // Appelle une méthode
                fail("No other entity should be registered yet");
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void remove(Entity entity) {
                // Appelle une méthode
                assertNotSame(ent1, entity);
                // Appelle une méthode
                fail("No other entity should be registered yet");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        EntityTracker tracker = EntityTracker.newTracker();
        // Appelle une méthode
        var entities = tracker.entities();
        // Appelle une méthode
        var chunkEntities = tracker.chunkEntities(Vec.ZERO, EntityTracker.Target.ENTITIES);

        // Appelle une méthode
        assertTrue(entities.isEmpty());
        // Appelle une méthode
        assertTrue(chunkEntities.isEmpty());
        // Appelle une méthode
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, updater);
        // Appelle une méthode
        assertEquals(1, entities.size());
        // Appelle une méthode
        assertEquals(1, chunkEntities.size());

        // Appelle une méthode
        assertThrows(Exception.class, () -> entities.add(new Entity(EntityType.ZOMBIE)));
        // Appelle une méthode
        assertThrows(Exception.class, () -> chunkEntities.add(new Entity(EntityType.ZOMBIE)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
