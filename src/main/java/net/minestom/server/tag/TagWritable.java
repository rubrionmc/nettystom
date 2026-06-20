// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.function.UnaryOperator;

/**
 * Represents an element which can write {@link Tag tags}.
 */
// Type declaration (class/interface/enum/record)
public interface TagWritable extends TagReadable {

    /**
     * Writes the specified type.
     *
     * @param tag   the tag to write
     * @param value the tag value, null to remove
     * @param <T>   the tag type
     */
    // Calls a method
    <T> void setTag(Tag<T> tag, @Nullable T value);

    // Start of a method/block
    default void removeTag(Tag<?> tag) {
        // Calls a method
        setTag(tag, null);
    // End of a block/expression
    }

    /**
     * Reads the current value, and then write the new one.
     *
     * @param tag   the tag to write
     * @param value the tag value, null to remove
     * @param <T>   the tag type
     * @return the previous tag value, null if not present
     */
    // Calls a method
    <T> @Nullable T getAndSetTag(Tag<T> tag, @Nullable T value);

    // Code statement
    <T> void updateTag(Tag<T> tag,
                       // Code statement
                       UnaryOperator<@UnknownNullability T> value);

    // Code statement
    <T> @UnknownNullability T updateAndGetTag(Tag<T> tag,
                                              // Code statement
                                              UnaryOperator<@UnknownNullability T> value);

    // Code statement
    <T> @UnknownNullability T getAndUpdateTag(Tag<T> tag,
                                              // Code statement
                                              UnaryOperator<@UnknownNullability T> value);
// End of a block/expression
}
