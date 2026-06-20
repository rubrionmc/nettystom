// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.ToIntFunction;
// Import of a required class
import java.util.function.ToLongFunction;
// Import of a required class
import java.util.stream.Collectors;

/**
 * Represents the context of a snapshot build.
 * Used in {@link Snapshotable#updateSnapshot(SnapshotUpdater)} to create snapshot references and avoid circular dependencies.
 * Updaters must never leave scope, as its data may be state related (change according to the currently processed snapshot).
 * <p>
 * Implementations do not need to be thread-safe and cannot be re-used.
 */
// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public sealed interface SnapshotUpdater permits SnapshotUpdaterImpl {
    /**
     * Updates the snapshot of the given snapshotable.
     * <p>
     * Method must be called during a safe-point (when the server state is stable).
     *
     * @param snapshotable the snapshot container
     * @param <T>          the snapshot type
     * @return the new updated snapshot
     */
    // Start of a method/block
    static <T extends Snapshot> T update(Snapshotable snapshotable) {
        // Returns a value to the caller
        return SnapshotUpdaterImpl.update(snapshotable);
    // End of a block/expression
    }

    // Calls a method
    <T extends Snapshot> AtomicReference<T> reference(Snapshotable snapshotable);

    // Annotation for the following element
    @Contract("!null -> !null")
    // Start of a method/block
    default <T extends Snapshot> AtomicReference<T> optionalReference(Snapshotable snapshotable) {
        // Returns a value to the caller
        return snapshotable != null ? reference(snapshotable) : null;
    // End of a block/expression
    }

    // Code statement
    default <T extends Snapshot, S extends Snapshotable, K> Map<K, AtomicReference<T>> referencesMap(Collection<S> snapshotables,
                                                                                                              // Start of a method/block
                                                                                                              Function<S, K> mappingFunction) {
        // Returns a value to the caller
        return snapshotables.stream().collect(Collectors.toUnmodifiableMap(mappingFunction, this::reference));
    // End of a block/expression
    }

    // Code statement
    default <T extends Snapshot, S extends Snapshotable> Map<Long, AtomicReference<T>> referencesMapLong(Collection<S> snapshotables,
                                                                                                                  // Start of a method/block
                                                                                                                  ToLongFunction<S> mappingFunction) {
        // Calls a method
        Long2ObjectOpenHashMap<AtomicReference<T>> map = new Long2ObjectOpenHashMap<>(snapshotables.size());
        // Loop: repeats a block
        for (S snapshotable : snapshotables) {
            // Calls a method
            map.put(mappingFunction.applyAsLong(snapshotable), reference(snapshotable));
        // End of a block/expression
        }
        // Calls a method
        map.trim();
        // Returns a value to the caller
        return map;
    // End of a block/expression
    }

    // Code statement
    default <T extends Snapshot, S extends Snapshotable> Map<Integer, AtomicReference<T>> referencesMapInt(Collection<S> snapshotables,
                                                                                                                    // Start of a method/block
                                                                                                                    ToIntFunction<S> mappingFunction) {
        // Calls a method
        Int2ObjectOpenHashMap<AtomicReference<T>> map = new Int2ObjectOpenHashMap<>(snapshotables.size());
        // Loop: repeats a block
        for (S snapshotable : snapshotables) {
            // Calls a method
            map.put(mappingFunction.applyAsInt(snapshotable), reference(snapshotable));
        // End of a block/expression
        }
        // Calls a method
        map.trim();
        // Returns a value to the caller
        return map;
    // End of a block/expression
    }
// End of a block/expression
}
