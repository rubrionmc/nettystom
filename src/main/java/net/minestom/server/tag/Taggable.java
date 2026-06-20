// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public interface Taggable extends TagReadable, TagWritable {

    // Calls a method
    TagHandler tagHandler();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Returns a value to the caller
        return tagHandler().getTag(tag);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default boolean hasTag(Tag<?> tag) {
        // Returns a value to the caller
        return tagHandler().hasTag(tag);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default <T> void setTag(Tag<T> tag, @Nullable T value) {
        // Calls a method
        tagHandler().setTag(tag, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void removeTag(Tag<?> tag) {
        // Calls a method
        tagHandler().removeTag(tag);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default <T> @Nullable T getAndSetTag(Tag<T> tag, @Nullable T value) {
        // Returns a value to the caller
        return tagHandler().getAndSetTag(tag, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default <T> void updateTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Calls a method
        tagHandler().updateTag(tag, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default <T> @UnknownNullability T updateAndGetTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Returns a value to the caller
        return tagHandler().updateAndGetTag(tag, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default <T> @UnknownNullability T getAndUpdateTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Returns a value to the caller
        return tagHandler().getAndUpdateTag(tag, value);
    // End of a block/expression
    }
// End of a block/expression
}
