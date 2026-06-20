// Package declaration for this file
package net.minestom.testing;

// Import of a required class
import net.minestom.server.ServerProcess;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.Event;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.instance.ChunkLoader;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.function.BooleanSupplier;

// Type declaration (class/interface/enum/record)
public interface Env {
    // Calls a method
    ServerProcess process();

    // Calls a method
    TestConnection createConnection(GameProfile gameProfile);

    // Start of a method/block
    default TestConnection createConnection() {
        // Returns a value to the caller
        return createConnection(new GameProfile(UUID.randomUUID(), "RandName"));
    // End of a block/expression
    }

    // Calls a method
    <E extends Event, H> Collector<E> trackEvent(Class<E> eventType, EventFilter<? super E, H> filter, H actor);

    // Calls a method
    <E extends Event> FlexibleListener<E> listen(Class<E> eventType);

    // Start of a method/block
    default void tick() {
        // Calls a method
        process().ticker().tick(System.nanoTime());
    // End of a block/expression
    }

    // Start of a method/block
    default boolean tickWhile(BooleanSupplier condition, @Nullable Duration timeout) {
        // Calls a method
        var ticker = process().ticker();
        // Calls a method
        final long start = System.nanoTime();
        // Loop: repeats a block
        while (condition.getAsBoolean()) {
            // Calls a method
            final long tick = System.nanoTime();
            // Calls a method
            ticker.tick(tick);
            // Branch: checks a condition
            if (timeout != null && System.nanoTime() - start > timeout.toNanos()) {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Start of a method/block
    default Player createPlayer(Instance instance, Pos pos) {
        // Returns a value to the caller
        return createConnection().connect(instance, pos);
    // End of a block/expression
    }

    // Start of a method/block
    default Instance createFlatInstance() {
        // Returns a value to the caller
        return createFlatInstance(null);
    // End of a block/expression
    }

    // Start of a method/block
    default Instance createFlatInstance(@Nullable ChunkLoader chunkLoader) {
        // Calls a method
        var instance = process().instance().createInstanceContainer(chunkLoader);
        // Calls a method
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.STONE));
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    // Start of a method/block
    default Instance createEmptyInstance() {
        // Returns a value to the caller
        return process().instance().createInstanceContainer();
    // End of a block/expression
    }

    // Start of a method/block
    default Instance createEmptyInstance(ChunkLoader chunkLoader) {
        // Returns a value to the caller
        return process().instance().createInstanceContainer(chunkLoader);
    // End of a block/expression
    }

    // Start of a method/block
    default void destroyInstance(Instance instance) {
        // Calls a method
        process().instance().unregisterInstance(instance);
    // End of a block/expression
    }
// End of a block/expression
}
