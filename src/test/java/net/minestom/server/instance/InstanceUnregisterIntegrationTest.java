// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.event.instance.InstanceTickEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerMoveEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerTickEvent;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.lang.ref.WeakReference;
// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.testing.TestUtils.waitUntilCleared;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class InstanceUnregisterIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sharedInstance(Env env) {
        // Ensure that unregistering a shared instance does not unload the container chunks
        // Calls a method
        var instanceManager = env.process().instance();
        // Calls a method
        var instance = instanceManager.createInstanceContainer();
        // Calls a method
        var shared1 = instanceManager.createSharedInstance(instance);
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(shared1, new Pos(0, 40, 0));

        // Calls a method
        var listener = env.listen(PlayerTickEvent.class);
        // Calls a method
        listener.followup();
        // Calls a method
        env.tick();

        // Calls a method
        var acquired = player.acquirable().lock();
        // Calls a method
        player.setInstance(instanceManager.createSharedInstance(instance)).join();
        // Calls a method
        acquired.unlock();
        // Calls a method
        listener.followup();
        // Calls a method
        env.tick();

        // Calls a method
        instanceManager.unregisterInstance(shared1);
        // Calls a method
        listener.followup();
        // Calls a method
        env.tick();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void instanceGC(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var ref = new WeakReference<>(instance);
        // Calls a method
        env.process().instance().unregisterInstance(instance);

        //noinspection UnusedAssignment
        // Assigns a value
        instance = null;
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void instanceNodeGC(Env env) {
        // Type declaration (class/interface/enum/record)
        final class Game {
            // Code statement
            final Instance instance;

            // Start of a method/block
            Game(Env env) {
                // Calls a method
                instance = env.process().instance().createInstanceContainer();
                // Calls a method
                instance.eventNode().addListener(PlayerMoveEvent.class, e -> System.out.println(instance));
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        var game = new Game(env);
        // Calls a method
        var ref = new WeakReference<>(game);
        // Calls a method
        env.process().instance().unregisterInstance(game.instance);

        //noinspection UnusedAssignment
        // Assigns a value
        game = null;
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkGC(Env env) {
        // Ensure that unregistering an instance does release its chunks
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var chunk = instance.loadChunk(0, 0).join();
        // Calls a method
        var ref = new WeakReference<>(chunk);
        // Calls a method
        instance.unloadChunk(chunk);
        // Calls a method
        env.process().instance().unregisterInstance(instance);
        // Code statement
        env.tick(); // Required to remove the chunk from the thread dispatcher

        //noinspection UnusedAssignment
        // Assigns a value
        chunk = null;
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testGCWithEventsLambda(Env env) {
        // Calls a method
        var ref = new WeakReference<>(new InstanceContainer(UUID.randomUUID(), DimensionType.OVERWORLD));
        // Calls a method
        env.process().instance().registerInstance(ref.get());

        // Calls a method
        tmp(ref.get());

        // Calls a method
        ref.get().tick(0);
        // Calls a method
        env.process().instance().unregisterInstance(ref.get());

        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

    // Start of a method/block
    private void tmp(InstanceContainer instanceContainer) {
        // Start of a method/block
        instanceContainer.eventNode().addListener(InstanceTickEvent.class, (e) -> {
            // Calls a method
            var uuid = instanceContainer.getUuid();
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
