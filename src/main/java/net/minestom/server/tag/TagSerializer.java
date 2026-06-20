// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * Interface used to create custom {@link Tag tags}.
 *
 * @param <T> the type to serialize
 */
// Déclaration de type (classe/interface/enum/record)
public interface TagSerializer<T> {

    /**
     * Reads the custom tag from a {@link TagReadable}.
     *
     * @param reader the reader
     * @return the deserialized value, null if invalid
     */
    // Annotation pour l'élément suivant
    @Nullable T read(TagReadable reader);

    /**
     * Writes the custom tag to a {@link TagWritable}.
     *
     * @param writer the writer
     * @param value  the value to serialize
     */
    // Appelle une méthode
    void write(TagWritable writer, T value);

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Affecte une valeur
    TagSerializer<CompoundBinaryTag> COMPOUND = TagSerializerImpl.COMPOUND;

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Instruction de code
    static <T> TagSerializer<T> fromCompound(Function<CompoundBinaryTag, T> reader,
                                             // Début d'une méthode/d'un bloc
                                             Function<T, CompoundBinaryTag> writer) {
        // Renvoie une valeur à l'appelant
        return TagSerializerImpl.fromCompound(reader, writer);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
