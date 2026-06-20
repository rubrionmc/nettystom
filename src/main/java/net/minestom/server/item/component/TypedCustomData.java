// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
public record TypedCustomData<T>(T type, CompoundBinaryTag nbt) implements TagReadable {

    // Start of a method/block
    public static <T> Codec<TypedCustomData<T>> codec(Codec<T> typeCodec) {
        // Returns a value to the caller
        return StructCodec.struct(
                // Code statement
                "id", typeCodec, TypedCustomData::type,
                // Code statement
                StructCodec.INLINE, Codec.NBT_COMPOUND, TypedCustomData::nbt,
                // Code statement
                TypedCustomData::new
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    public static <T> NetworkBuffer.Type<TypedCustomData<T>> networkType(NetworkBuffer.Type<T> typeNetwork) {
        // Returns a value to the caller
        return NetworkBufferTemplate.template(
                // Code statement
                typeNetwork, TypedCustomData::type,
                // Code statement
                NetworkBuffer.NBT_COMPOUND, TypedCustomData::nbt,
                // Code statement
                TypedCustomData::new
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    public TypedCustomData(T type, CompoundBinaryTag nbt) {
        // Access to the current/parent object
        this.type = type;
        // Access to the current/parent object
        this.nbt = nbt.remove("id");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <TT> @UnknownNullability TT getTag(Tag<TT> tag) {
        // Calls a method
        final TagHandler tagHandler = TagHandler.fromCompound(nbt);
        // Returns a value to the caller
        return tagHandler.getTag(tag);
    // End of a block/expression
    }

    // Start of a method/block
    public <TT> TypedCustomData<T> withTag(Tag<TT> tag, TT value) {
        // Calls a method
        TagHandler tagHandler = TagHandler.fromCompound(nbt);
        // Calls a method
        tagHandler.setTag(tag, value);
        // Returns a value to the caller
        return new TypedCustomData<>(type, tagHandler.asCompound());
    // End of a block/expression
    }
// End of a block/expression
}
