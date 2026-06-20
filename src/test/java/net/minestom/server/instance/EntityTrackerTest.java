// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Set;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class EntityTrackerTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void register() {
        // Calls a method
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Assigns a value
        var updater = new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Calls a method
                assertNotSame(ent1, entity);
                // Calls a method
                fail("No other entity should be registered yet");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Calls a method
                assertNotSame(ent1, entity);
                // Calls a method
                fail("No other entity should be registered yet");
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        EntityTracker tracker = EntityTracker.newTracker();
        // Calls a method
        var chunkEntities = tracker.chunkEntities(Vec.ZERO, EntityTracker.Target.ENTITIES);
        // Calls a method
        assertTrue(chunkEntities.isEmpty());

        // Calls a method
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        assertEquals(1, chunkEntities.size());

        // Calls a method
        tracker.unregister(ent1, EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        assertEquals(0, chunkEntities.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void move() {
        // Calls a method
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Assigns a value
        var updater = new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Calls a method
                fail("No other entity should be registered yet");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Calls a method
                fail("No other entity should be registered yet");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        EntityTracker tracker = EntityTracker.newTracker();

        // Calls a method
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        assertEquals(1, tracker.chunkEntities(Vec.ZERO, EntityTracker.Target.ENTITIES).size());

        // Calls a method
        tracker.move(ent1, new Vec(32, 0, 32), EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        assertEquals(0, tracker.chunkEntities(Vec.ZERO, EntityTracker.Target.ENTITIES).size());
        // Calls a method
        assertEquals(1, tracker.chunkEntities(new Vec(32, 0, 32), EntityTracker.Target.ENTITIES).size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void tracking() {
        // Calls a method
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var ent2 = new Entity(EntityType.ZOMBIE);

        // Calls a method
        EntityTracker tracker = EntityTracker.newTracker();
        // Start of a method/block
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Calls a method
                fail("No other entity should be registered yet");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Calls a method
                fail("No other entity should be registered yet");
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Start of a method/block
        tracker.register(ent2, Vec.ZERO, EntityTracker.Target.ENTITIES, new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Calls a method
                assertNotSame(ent2, entity);
                // Calls a method
                assertSame(ent1, entity);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Calls a method
                fail("No other entity should be removed yet");
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Start of a method/block
        tracker.move(ent1, new Vec(Integer.MAX_VALUE, 0, 0), EntityTracker.Target.ENTITIES, new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Calls a method
                assertNotSame(ent1, entity);
                // Calls a method
                fail("No other entity should be added");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Calls a method
                assertNotSame(ent1, entity);
                // Calls a method
                assertSame(ent2, entity);
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Start of a method/block
        tracker.move(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Calls a method
                assertNotSame(ent1, entity);
                // Calls a method
                assertSame(ent2, entity);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Calls a method
                fail("no entity to remove");
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nearby() {
        // Calls a method
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var ent2 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var ent3 = new Entity(EntityType.ZOMBIE);
        // Assigns a value
        var updater = new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Empty
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Empty
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        EntityTracker tracker = EntityTracker.newTracker();
        // Calls a method
        tracker.register(ent2, new Vec(5, 0, 0), EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        tracker.register(ent3, new Vec(50, 0, 0), EntityTracker.Target.ENTITIES, updater);

        // Calls a method
        tracker.nearbyEntities(Vec.ZERO, 4, EntityTracker.Target.ENTITIES, entity -> fail("No entity should be nearby"));

        // Calls a method
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, updater);

        // Calls a method
        Set<Entity> entities = new HashSet<>();

        // Calls a method
        entities.add(ent1);
        // Calls a method
        tracker.nearbyEntities(Vec.ZERO, 4, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Calls a method
        assertEquals(0, entities.size());

        // Calls a method
        entities.add(ent1);
        // Calls a method
        tracker.nearbyEntities(Vec.ZERO, 4.99, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Calls a method
        assertEquals(0, entities.size());

        // Calls a method
        entities.add(ent1);
        // Calls a method
        entities.add(ent2);
        // Calls a method
        tracker.nearbyEntities(Vec.ZERO, 5, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Calls a method
        assertEquals(0, entities.size());

        // Calls a method
        entities.add(ent1);
        // Calls a method
        entities.add(ent2);
        // Calls a method
        entities.add(ent3);
        // Calls a method
        tracker.nearbyEntities(Vec.ZERO, 50, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Calls a method
        assertEquals(0, entities.size());

        // Chunk border
        // Calls a method
        tracker.move(ent1, new Vec(16, 0, 0), EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        entities.add(ent1);
        // Calls a method
        tracker.nearbyEntities(new Vec(15, 0, 0), 2, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Calls a method
        assertEquals(0, entities.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nearbySingleChunk() {
        // Calls a method
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var ent2 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var ent3 = new Entity(EntityType.ZOMBIE);
        // Assigns a value
        var updater = new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Empty
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Empty
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        EntityTracker tracker = EntityTracker.newTracker();
        // Calls a method
        tracker.register(ent1, new Vec(5, 0, 5), EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        tracker.register(ent2, new Vec(8, 0, 8), EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        tracker.register(ent3, new Vec(17, 0, 17), EntityTracker.Target.ENTITIES, updater);

        // Calls a method
        Set<Entity> entities = new HashSet<>();

        // Calls a method
        entities.add(ent1);
        // Calls a method
        entities.add(ent2);
        // Calls a method
        tracker.nearbyEntities(Vec.ZERO, 16, EntityTracker.Target.ENTITIES, entities::add);
        // Calls a method
        assertEquals(Set.of(ent1, ent2), entities);
        // Calls a method
        entities.clear();

        // Calls a method
        entities.add(ent1);
        // Calls a method
        entities.add(ent2);
        // Calls a method
        tracker.nearbyEntities(new Vec(8, 0, 8), 5, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Calls a method
        assertEquals(0, entities.size());

        // Calls a method
        entities.add(ent2);
        // Calls a method
        tracker.nearbyEntities(new Vec(8, 0, 8), 1, EntityTracker.Target.ENTITIES, entity -> assertTrue(entities.remove(entity)));
        // Calls a method
        assertEquals(0, entities.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void collectionView() {
        // Calls a method
        var ent1 = new Entity(EntityType.ZOMBIE);
        // Assigns a value
        var updater = new EntityTracker.Update<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void add(Entity entity) {
                // Calls a method
                assertNotSame(ent1, entity);
                // Calls a method
                fail("No other entity should be registered yet");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void remove(Entity entity) {
                // Calls a method
                assertNotSame(ent1, entity);
                // Calls a method
                fail("No other entity should be registered yet");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        EntityTracker tracker = EntityTracker.newTracker();
        // Calls a method
        var entities = tracker.entities();
        // Calls a method
        var chunkEntities = tracker.chunkEntities(Vec.ZERO, EntityTracker.Target.ENTITIES);

        // Calls a method
        assertTrue(entities.isEmpty());
        // Calls a method
        assertTrue(chunkEntities.isEmpty());
        // Calls a method
        tracker.register(ent1, Vec.ZERO, EntityTracker.Target.ENTITIES, updater);
        // Calls a method
        assertEquals(1, entities.size());
        // Calls a method
        assertEquals(1, chunkEntities.size());

        // Calls a method
        assertThrows(Exception.class, () -> entities.add(new Entity(EntityType.ZOMBIE)));
        // Calls a method
        assertThrows(Exception.class, () -> chunkEntities.add(new Entity(EntityType.ZOMBIE)));
    // End of a block/expression
    }
// End of a block/expression
}
