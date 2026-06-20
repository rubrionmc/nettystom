// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.ToIntFunction;
// Import d'une classe nécessaire
import java.util.function.ToLongFunction;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

/**
 * Represents the context of a snapshot build.
 * Used in {@link Snapshotable#updateSnapshot(SnapshotUpdater)} to create snapshot references and avoid circular dependencies.
 * Updaters must never leave scope, as its data may be state related (change according to the currently processed snapshot).
 * <p>
 * Implementations do not need to be thread-safe and cannot be re-used.
 */
// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
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
    // Début d'une méthode/d'un bloc
    static <T extends Snapshot> T update(Snapshotable snapshotable) {
        // Renvoie une valeur à l'appelant
        return SnapshotUpdaterImpl.update(snapshotable);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    <T extends Snapshot> AtomicReference<T> reference(Snapshotable snapshotable);

    // Annotation pour l'élément suivant
    @Contract("!null -> !null")
    // Début d'une méthode/d'un bloc
    default <T extends Snapshot> AtomicReference<T> optionalReference(Snapshotable snapshotable) {
        // Renvoie une valeur à l'appelant
        return snapshotable != null ? reference(snapshotable) : null;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    default <T extends Snapshot, S extends Snapshotable, K> Map<K, AtomicReference<T>> referencesMap(Collection<S> snapshotables,
                                                                                                              // Début d'une méthode/d'un bloc
                                                                                                              Function<S, K> mappingFunction) {
        // Renvoie une valeur à l'appelant
        return snapshotables.stream().collect(Collectors.toUnmodifiableMap(mappingFunction, this::reference));
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    default <T extends Snapshot, S extends Snapshotable> Map<Long, AtomicReference<T>> referencesMapLong(Collection<S> snapshotables,
                                                                                                                  // Début d'une méthode/d'un bloc
                                                                                                                  ToLongFunction<S> mappingFunction) {
        // Appelle une méthode
        Long2ObjectOpenHashMap<AtomicReference<T>> map = new Long2ObjectOpenHashMap<>(snapshotables.size());
        // Boucle : répète un bloc
        for (S snapshotable : snapshotables) {
            // Appelle une méthode
            map.put(mappingFunction.applyAsLong(snapshotable), reference(snapshotable));
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        map.trim();
        // Renvoie une valeur à l'appelant
        return map;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    default <T extends Snapshot, S extends Snapshotable> Map<Integer, AtomicReference<T>> referencesMapInt(Collection<S> snapshotables,
                                                                                                                    // Début d'une méthode/d'un bloc
                                                                                                                    ToIntFunction<S> mappingFunction) {
        // Appelle une méthode
        Int2ObjectOpenHashMap<AtomicReference<T>> map = new Int2ObjectOpenHashMap<>(snapshotables.size());
        // Boucle : répète un bloc
        for (S snapshotable : snapshotables) {
            // Appelle une méthode
            map.put(mappingFunction.applyAsInt(snapshotable), reference(snapshotable));
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        map.trim();
        // Renvoie une valeur à l'appelant
        return map;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
