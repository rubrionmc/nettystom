// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
public record CustomData(CompoundBinaryTag nbt) implements TagReadable {
    // Calls a method
    public static final CustomData EMPTY = new CustomData(CompoundBinaryTag.empty());

    // Assigns a value
    public static final NetworkBuffer.Type<CustomData> NETWORK_TYPE = NetworkBuffer.NBT_COMPOUND
            // Calls a method
            .transform(CustomData::new, CustomData::nbt);

    // Assigns a value
    public static final Codec<CustomData> CODEC = Codec.NBT_COMPOUND
            // Calls a method
            .transform(CustomData::new, CustomData::nbt);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Calls a method
        final TagHandler tagHandler = TagHandler.fromCompound(nbt);
        // Returns a value to the caller
        return tagHandler.getTag(tag);
    // End of a block/expression
    }

    // Start of a method/block
    public <T> CustomData withTag(Tag<T> tag, @Nullable T value) {
        // Calls a method
        TagHandler tagHandler = TagHandler.fromCompound(nbt);
        // Calls a method
        tagHandler.setTag(tag, value);
        // Returns a value to the caller
        return new CustomData(tagHandler.asCompound());
    // End of a block/expression
    }
// End of a block/expression
}
