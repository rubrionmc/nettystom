// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * An object that is a source of {@link Acquirable} objects, and can be synchronized within a {@link ThreadDispatcher}.
 *
 * @param <T> the type of the acquired object
 */
// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public interface AcquirableSource<T> {
    /**
     * Obtains an {@link Acquirable}. To safely perform operations on this object, the user must call
     * {@link Acquirable#sync(Consumer)}, {@link Acquirable#applySync(Function)}, or {@link Acquirable#lock()} (followed by
     * a subsequent unlock) on the Acquirable instance.
     *
     * @return an Acquirable which can be used to synchronize access to this object
     */
    // Appelle une méthode
    Acquirable<? extends T> acquirable();
// Fin d'un bloc/d'une expression
}
