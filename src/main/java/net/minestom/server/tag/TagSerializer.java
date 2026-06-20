// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.function.Function;

/**
 * Interface used to create custom {@link Tag tags}.
 *
 * @param <T> the type to serialize
 */
// Type declaration (class/interface/enum/record)
public interface TagSerializer<T> {

    /**
     * Reads the custom tag from a {@link TagReadable}.
     *
     * @param reader the reader
     * @return the deserialized value, null if invalid
     */
    // Annotation for the following element
    @Nullable T read(TagReadable reader);

    /**
     * Writes the custom tag to a {@link TagWritable}.
     *
     * @param writer the writer
     * @param value  the value to serialize
     */
    // Calls a method
    void write(TagWritable writer, T value);

    // Annotation for the following element
    @ApiStatus.Experimental
    // Assigns a value
    TagSerializer<CompoundBinaryTag> COMPOUND = TagSerializerImpl.COMPOUND;

    // Annotation for the following element
    @ApiStatus.Experimental
    // Code statement
    static <T> TagSerializer<T> fromCompound(Function<CompoundBinaryTag, T> reader,
                                             // Start of a method/block
                                             Function<T, CompoundBinaryTag> writer) {
        // Returns a value to the caller
        return TagSerializerImpl.fromCompound(reader, writer);
    // End of a block/expression
    }
// End of a block/expression
}
