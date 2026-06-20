// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

/**
 * Represents an element which can read {@link Tag tags}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface TagReadable {

    /**
     * Reads the specified tag.
     *
     * @param tag the tag to read
     * @param <T> the tag type
     * @return the read tag, null if not present
     */
    // Appelle une méthode
    <T> @UnknownNullability T getTag(Tag<T> tag);

    /**
     * Returns if a tag is present.
     *
     * @param tag the tag to check
     * @return true if the tag is present, false otherwise
     */
    // Début d'une méthode/d'un bloc
    default boolean hasTag(Tag<?> tag) {
        // Renvoie une valeur à l'appelant
        return getTag(tag) != null;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
