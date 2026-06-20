// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Annotation pour l'élément suivant
@FunctionalInterface
// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public interface ThreadProvider<T> {
    // Début d'une méthode/d'un bloc
    static <T> ThreadProvider<T> counter() {
        // Renvoie une valeur à l'appelant
        return new ThreadProvider<>() {
            // Appelle une méthode
            private final AtomicInteger counter = new AtomicInteger();

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public int findThread(T partition) {
                // Renvoie une valeur à l'appelant
                return counter.getAndIncrement();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Performs a server tick for all chunks based on their linked thread.
     *
     * @param partition the partition
     */
    // Appelle une méthode
    int findThread(T partition);

    /**
     * Defines how often chunks thread should be updated.
     *
     * @return the refresh type
     */
    // Début d'une méthode/d'un bloc
    default RefreshType refreshType() {
        // Renvoie une valeur à l'appelant
        return RefreshType.NEVER;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Defines how often chunks thread should be refreshed.
     */
    // Déclaration de type (classe/interface/enum/record)
    enum RefreshType {
        /**
         * Thread never change after being defined once.
         * <p>
         * Means that {@link #findThread(Object)} will only be called once for each partition.
         */
        // Instruction de code
        NEVER,
        /**
         * Thread is updated as often as possible.
         * <p>
         * Means that {@link #findThread(Object)} may be called multiple time for each partition.
         */
        // Instruction de code
        ALWAYS
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
