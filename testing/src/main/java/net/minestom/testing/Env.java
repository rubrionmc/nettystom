// Déclaration du paquet de ce fichier
package net.minestom.testing;

// Import d'une classe nécessaire
import net.minestom.server.ServerProcess;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.instance.ChunkLoader;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.BooleanSupplier;

// Déclaration de type (classe/interface/enum/record)
public interface Env {
    // Appelle une méthode
    ServerProcess process();

    // Appelle une méthode
    TestConnection createConnection(GameProfile gameProfile);

    // Début d'une méthode/d'un bloc
    default TestConnection createConnection() {
        // Renvoie une valeur à l'appelant
        return createConnection(new GameProfile(UUID.randomUUID(), "RandName"));
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    <E extends Event, H> Collector<E> trackEvent(Class<E> eventType, EventFilter<? super E, H> filter, H actor);

    // Appelle une méthode
    <E extends Event> FlexibleListener<E> listen(Class<E> eventType);

    // Début d'une méthode/d'un bloc
    default void tick() {
        // Appelle une méthode
        process().ticker().tick(System.nanoTime());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean tickWhile(BooleanSupplier condition, @Nullable Duration timeout) {
        // Appelle une méthode
        var ticker = process().ticker();
        // Appelle une méthode
        final long start = System.nanoTime();
        // Boucle : répète un bloc
        while (condition.getAsBoolean()) {
            // Appelle une méthode
            final long tick = System.nanoTime();
            // Appelle une méthode
            ticker.tick(tick);
            // Embranchement : vérifie une condition
            if (timeout != null && System.nanoTime() - start > timeout.toNanos()) {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Player createPlayer(Instance instance, Pos pos) {
        // Renvoie une valeur à l'appelant
        return createConnection().connect(instance, pos);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Instance createFlatInstance() {
        // Renvoie une valeur à l'appelant
        return createFlatInstance(null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Instance createFlatInstance(@Nullable ChunkLoader chunkLoader) {
        // Appelle une méthode
        var instance = process().instance().createInstanceContainer(chunkLoader);
        // Appelle une méthode
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.STONE));
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Instance createEmptyInstance() {
        // Renvoie une valeur à l'appelant
        return process().instance().createInstanceContainer();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Instance createEmptyInstance(ChunkLoader chunkLoader) {
        // Renvoie une valeur à l'appelant
        return process().instance().createInstanceContainer(chunkLoader);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void destroyInstance(Instance instance) {
        // Appelle une méthode
        process().instance().unregisterInstance(instance);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
