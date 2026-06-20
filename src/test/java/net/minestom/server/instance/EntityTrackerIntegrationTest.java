// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertSame;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityTrackerIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void maxDistance(Env env) {
        // Calls a method
        final Instance instance = env.createFlatInstance();
        // Calls a method
        final Pos spawnPos = new Pos(0, 41, 0);
        // Assigns a value
        final int viewDistanceInChunks = ServerFlag.ENTITY_VIEW_DISTANCE;

        // Calls a method
        final Player viewer = env.createPlayer(instance, spawnPos);
        // Calls a method
        final AtomicInteger viewersCount = new AtomicInteger();
        // Assigns a value
        final Entity entity = new Entity(EntityType.ZOMBIE) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void updateNewViewer(Player player) {
                // Calls a method
                viewersCount.incrementAndGet();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void updateOldViewer(Player player) {
                // Calls a method
                viewersCount.decrementAndGet();
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        entity.setInstance(instance, spawnPos).join();
        // Calls a method
        assertEquals(1, viewersCount.get());
        // Code statement
        viewer.teleport(new Pos(viewDistanceInChunks * 16 + 15, 41, 0)).join(); // viewer at max chunk range
        // Calls a method
        assertEquals(1, viewersCount.get());
        // Code statement
        viewer.teleport(new Pos(viewDistanceInChunks * 16 + 16, 41, 0)).join(); // viewer outside of chunk range
        // Calls a method
        assertEquals(0, viewersCount.get());
        // Code statement
        viewer.teleport(new Pos(viewDistanceInChunks * 16 + 15, 41, 0)).join(); // viewer back to max chunk range
        // Calls a method
        assertEquals(1, viewersCount.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cornerInstanceSwap(Env env) {
        // Calls a method
        final Instance instance = env.createFlatInstance();
        // Calls a method
        final Instance anotherInstance = env.createFlatInstance();
        // Calls a method
        final Pos spawnPos = new Pos(0, 41, 0);
        // Assigns a value
        final int viewDistanceInChunks = ServerFlag.ENTITY_VIEW_DISTANCE;

        // Calls a method
        final Player viewer = env.createPlayer(instance, spawnPos);
        // Calls a method
        final AtomicInteger viewersCount = new AtomicInteger();
        // Assigns a value
        final Entity entity = new Entity(EntityType.ZOMBIE) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void updateNewViewer(Player player) {
                // Calls a method
                viewersCount.incrementAndGet();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void updateOldViewer(Player player) {
                // Calls a method
                viewersCount.decrementAndGet();
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        entity.setInstance(instance, spawnPos).join();
        // Calls a method
        assertEquals(1, viewersCount.get());
        // Code statement
        viewer.teleport(new Pos(viewDistanceInChunks * 16 + 15, 41, 0)).join(); // viewer at max chunk range
        // Calls a method
        assertEquals(1, viewersCount.get());
        // Code statement
        viewer.setInstance(anotherInstance, spawnPos).join(); // viewer swapped instance
        // Calls a method
        assertEquals(0, viewersCount.get());
        // Code statement
        viewer.setInstance(instance, spawnPos).join(); // viewer back to spawn
        // Calls a method
        assertEquals(1, viewersCount.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewable(Env env) {
        // Calls a method
        final Instance instance = env.createFlatInstance();
        // Calls a method
        final Pos spawnPos = new Pos(0, 41, 0);
        // Calls a method
        var viewable = instance.getEntityTracker().viewable(spawnPos.chunkX(), spawnPos.chunkZ());
        // Calls a method
        assertEquals(0, viewable.getViewers().size());

        // Calls a method
        final Player player = env.createPlayer(instance, spawnPos);
        // Calls a method
        assertEquals(1, viewable.getViewers().size());
        // Calls a method
        assertSame(viewable, instance.getEntityTracker().viewable(spawnPos.chunkX(), spawnPos.chunkZ()));

        // Calls a method
        player.teleport(new Pos(10_000, 41, 0)).join();
        // Calls a method
        assertEquals(0, viewable.getViewers().size());

        // Calls a method
        player.teleport(spawnPos).join();
        // Calls a method
        assertEquals(1, viewable.getViewers().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewableShared(Env env) {
        // Calls a method
        final InstanceContainer instance = (InstanceContainer) env.createFlatInstance();
        // Calls a method
        var shared = env.process().instance().createSharedInstance(instance);
        // Calls a method
        var sharedList = instance.getSharedInstances();

        // Calls a method
        final Pos spawnPos = new Pos(0, 41, 0);
        // Calls a method
        var viewable = instance.getEntityTracker().viewable(sharedList, spawnPos.chunkX(), spawnPos.chunkZ());
        // Calls a method
        assertEquals(0, viewable.getViewers().size());

        // Calls a method
        final Player player = env.createPlayer(instance, spawnPos);
        // Calls a method
        assertEquals(1, viewable.getViewers().size());
        // Calls a method
        assertSame(viewable, instance.getEntityTracker().viewable(sharedList, spawnPos.chunkX(), spawnPos.chunkZ()));

        // Calls a method
        player.setInstance(shared).join();
        // Calls a method
        assertEquals(1, viewable.getViewers().size());

        // Calls a method
        player.teleport(new Pos(10_000, 41, 0)).join();
        // Calls a method
        assertEquals(0, viewable.getViewers().size());

        // Calls a method
        var shared2 = env.process().instance().createSharedInstance(instance);
        // Calls a method
        player.setInstance(shared2, spawnPos).join();
        // Calls a method
        assertEquals(1, viewable.getViewers().size());
    // End of a block/expression
    }
// End of a block/expression
}
