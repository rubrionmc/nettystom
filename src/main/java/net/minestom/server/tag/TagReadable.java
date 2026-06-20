// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

/**
 * Represents an element which can read {@link Tag tags}.
 */
// Type declaration (class/interface/enum/record)
public interface TagReadable {

    /**
     * Reads the specified tag.
     *
     * @param tag the tag to read
     * @param <T> the tag type
     * @return the read tag, null if not present
     */
    // Calls a method
    <T> @UnknownNullability T getTag(Tag<T> tag);

    /**
     * Returns if a tag is present.
     *
     * @param tag the tag to check
     * @return true if the tag is present, false otherwise
     */
    // Start of a method/block
    default boolean hasTag(Tag<?> tag) {
        // Returns a value to the caller
        return getTag(tag) != null;
    // End of a block/expression
    }
// End of a block/expression
}
