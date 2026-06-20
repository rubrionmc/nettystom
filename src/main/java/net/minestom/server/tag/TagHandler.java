// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;

/**
 * Represents an element which can read and write {@link Tag tags}.
 */
// Type declaration (class/interface/enum/record)
public interface TagHandler extends TagReadable, TagWritable {

    /**
     * Creates a readable copy of this handler.
     * <p>
     * Similar to {@link #asCompound()} with the advantage that cached objects
     * and adaptive optimizations may be reused.
     *
     * @return a copy of this handler
     */
    // Calls a method
    TagReadable readableCopy();

    /**
     * Creates a copy of this handler.
     * <p>
     * Similar to {@link #fromCompound(CompoundBinaryTag)} using {@link #asCompound()}
     * with the advantage that cached objects and adaptive optimizations may be reused.
     *
     * @return a copy of this handler
     */
    // Calls a method
    TagHandler copy();

    /**
     * Updates the content of this handler.
     * <p>
     * Can be used as a clearing method with {@link CompoundBinaryTag#empty()}.
     *
     * @param compound the new content of this handler
     */
    // Calls a method
    void updateContent(CompoundBinaryTag compound);

    /**
     * Converts the content of this handler into a {@link CompoundBinaryTag}.
     *
     * @return a nbt compound representation of this handler
     */
    // Calls a method
    CompoundBinaryTag asCompound();

    // Start of a method/block
    static TagHandler newHandler() {
        // Returns a value to the caller
        return new TagHandlerImpl();
    // End of a block/expression
    }

    /**
     * Copy the content of the given {@link CompoundBinaryTag} into a new {@link TagHandler}.
     *
     * @param compound the compound to read tags from
     * @return a new tag handler with the content of the given compound
     */
    // Start of a method/block
    static TagHandler fromCompound(CompoundBinaryTag compound) {
        // Returns a value to the caller
        return TagHandlerImpl.fromCompound(compound);
    // End of a block/expression
    }
// End of a block/expression
}
