// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;

/**
 * Represents an element which can read and write {@link Tag tags}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface TagHandler extends TagReadable, TagWritable {

    /**
     * Creates a readable copy of this handler.
     * <p>
     * Similar to {@link #asCompound()} with the advantage that cached objects
     * and adaptive optimizations may be reused.
     *
     * @return a copy of this handler
     */
    // Appelle une méthode
    TagReadable readableCopy();

    /**
     * Creates a copy of this handler.
     * <p>
     * Similar to {@link #fromCompound(CompoundBinaryTag)} using {@link #asCompound()}
     * with the advantage that cached objects and adaptive optimizations may be reused.
     *
     * @return a copy of this handler
     */
    // Appelle une méthode
    TagHandler copy();

    /**
     * Updates the content of this handler.
     * <p>
     * Can be used as a clearing method with {@link CompoundBinaryTag#empty()}.
     *
     * @param compound the new content of this handler
     */
    // Appelle une méthode
    void updateContent(CompoundBinaryTag compound);

    /**
     * Converts the content of this handler into a {@link CompoundBinaryTag}.
     *
     * @return a nbt compound representation of this handler
     */
    // Appelle une méthode
    CompoundBinaryTag asCompound();

    // Début d'une méthode/d'un bloc
    static TagHandler newHandler() {
        // Renvoie une valeur à l'appelant
        return new TagHandlerImpl();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Copy the content of the given {@link CompoundBinaryTag} into a new {@link TagHandler}.
     *
     * @param compound the compound to read tags from
     * @return a new tag handler with the content of the given compound
     */
    // Début d'une méthode/d'un bloc
    static TagHandler fromCompound(CompoundBinaryTag compound) {
        // Renvoie une valeur à l'appelant
        return TagHandlerImpl.fromCompound(compound);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
