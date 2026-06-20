// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

/**
 * Represents an element which can write {@link Tag tags}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface TagWritable extends TagReadable {

    /**
     * Writes the specified type.
     *
     * @param tag   the tag to write
     * @param value the tag value, null to remove
     * @param <T>   the tag type
     */
    // Appelle une méthode
    <T> void setTag(Tag<T> tag, @Nullable T value);

    // Début d'une méthode/d'un bloc
    default void removeTag(Tag<?> tag) {
        // Appelle une méthode
        setTag(tag, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Reads the current value, and then write the new one.
     *
     * @param tag   the tag to write
     * @param value the tag value, null to remove
     * @param <T>   the tag type
     * @return the previous tag value, null if not present
     */
    // Appelle une méthode
    <T> @Nullable T getAndSetTag(Tag<T> tag, @Nullable T value);

    // Instruction de code
    <T> void updateTag(Tag<T> tag,
                       // Instruction de code
                       UnaryOperator<@UnknownNullability T> value);

    // Instruction de code
    <T> @UnknownNullability T updateAndGetTag(Tag<T> tag,
                                              // Instruction de code
                                              UnaryOperator<@UnknownNullability T> value);

    // Instruction de code
    <T> @UnknownNullability T getAndUpdateTag(Tag<T> tag,
                                              // Instruction de code
                                              UnaryOperator<@UnknownNullability T> value);
// Fin d'un bloc/d'une expression
}
