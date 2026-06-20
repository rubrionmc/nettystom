// Déclaration du paquet de ce fichier
package net.minestom.server.utils.callback;

// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkCallback;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Convenient class to execute callbacks which can be null.
 */
// Déclaration de type (classe/interface/enum/record)
public class OptionalCallback {

    /**
     * Executes an optional {@link Runnable}.
     *
     * @param callback the optional runnable, can be null
     */
    // Début d'une méthode/d'un bloc
    public static void execute(@Nullable Runnable callback) {
        // Embranchement : vérifie une condition
        if (callback != null) {
            // Appelle une méthode
            callback.run();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Executes an optional {@link ChunkCallback}.
     *
     * @param callback the optional chunk callback, can be null
     * @param chunk    the chunk to forward to the callback
     */
    // Début d'une méthode/d'un bloc
    public static void execute(@Nullable ChunkCallback callback, @Nullable Chunk chunk) {
        // Embranchement : vérifie une condition
        if (callback != null) {
            // Appelle une méthode
            callback.accept(chunk);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
