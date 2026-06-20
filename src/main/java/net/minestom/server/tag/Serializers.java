// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.*;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.server.utils.UUIDUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.function.Function;

/**
 * Basic serializers for {@link Tag tags}.
 */
// Type declaration (class/interface/enum/record)
final class Serializers {
    // Calls a method
    static final Entry<Byte, ByteBinaryTag> BYTE = new Entry<>(BinaryTagTypes.BYTE, ByteBinaryTag::value, ByteBinaryTag::byteBinaryTag);
    // Calls a method
    static final Entry<Boolean, ByteBinaryTag> BOOLEAN = new Entry<>(BinaryTagTypes.BYTE, b -> b.value() != 0, b -> b ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO);
    // Calls a method
    static final Entry<Short, ShortBinaryTag> SHORT = new Entry<>(BinaryTagTypes.SHORT, ShortBinaryTag::value, ShortBinaryTag::shortBinaryTag);
    // Calls a method
    static final Entry<Integer, IntBinaryTag> INT = new Entry<>(BinaryTagTypes.INT, IntBinaryTag::value, IntBinaryTag::intBinaryTag);
    // Calls a method
    static final Entry<Long, LongBinaryTag> LONG = new Entry<>(BinaryTagTypes.LONG, LongBinaryTag::value, LongBinaryTag::longBinaryTag);
    // Calls a method
    static final Entry<Float, FloatBinaryTag> FLOAT = new Entry<>(BinaryTagTypes.FLOAT, FloatBinaryTag::value, FloatBinaryTag::floatBinaryTag);
    // Calls a method
    static final Entry<Double, DoubleBinaryTag> DOUBLE = new Entry<>(BinaryTagTypes.DOUBLE, DoubleBinaryTag::value, DoubleBinaryTag::doubleBinaryTag);
    // Calls a method
    static final Entry<String, StringBinaryTag> STRING = new Entry<>(BinaryTagTypes.STRING, StringBinaryTag::value, StringBinaryTag::stringBinaryTag);
    // Calls a method
    static final Entry<BinaryTag, BinaryTag> NBT_ENTRY = new Entry<>(null, Function.identity(), Function.identity());

    // Calls a method
    static final Entry<java.util.UUID, IntArrayBinaryTag> UUID = new Entry<>(BinaryTagTypes.INT_ARRAY, UUIDUtils::fromNbt, UUIDUtils::toNbt);
    // Calls a method
    static final Entry<ItemStack, CompoundBinaryTag> ITEM = new Entry<>(BinaryTagTypes.COMPOUND, ItemStack::fromItemNBT, ItemStack::toItemNBT);
    // Assigns a value
    static final Entry<Component, BinaryTag> COMPONENT = new Entry<>(null,
            // Code statement
            input -> Codec.COMPONENT.decode(new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process()), input).orElse(null),
            // Code statement
            component -> Codec.COMPONENT.encode(new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process()), component).orElse(null)
    // End of a block/expression
    );

    // Calls a method
    static final Entry<Object, ByteBinaryTag> EMPTY = new Entry<>(BinaryTagTypes.BYTE, _ -> null, _ -> null);

    // Start of a method/block
    static <T> Entry<T, CompoundBinaryTag> fromTagSerializer(TagSerializer<T> serializer) {
        // Returns a value to the caller
        return new Serializers.Entry<>(BinaryTagTypes.COMPOUND,
                // Start of a method/block
                (CompoundBinaryTag compound) -> {
                    // Branch: checks a condition
                    if ((!ServerFlag.SERIALIZE_EMPTY_COMPOUND) && compound.isEmpty()) return null;
                    // Returns a value to the caller
                    return serializer.read(TagHandler.fromCompound(compound));
                // Code statement
                },
                // Start of a method/block
                (value) -> {
                    // Branch: checks a condition
                    if (value == null) return CompoundBinaryTag.empty();
                    // Calls a method
                    TagHandler handler = TagHandler.newHandler();
                    // Calls a method
                    serializer.write(handler, value);
                    // Returns a value to the caller
                    return handler.asCompound();
                // End of a block/expression
                });
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Entry<T, N extends BinaryTag>(@Nullable BinaryTagType<N> nbtType,
                                         // Code statement
                                         Function<N, @Nullable T> reader,
                                         // Code statement
                                         Function<T, @Nullable N> writer,
                                         // Start of a method/block
                                         boolean isPath) {
        // Start of a method/block
        Entry(@Nullable BinaryTagType<N> nbtType, Function<N, T> reader, Function<T, N> writer) {
            // Calls a method
            this(nbtType, reader, writer, false);
        // End of a block/expression
        }

        // Annotation for the following element
        @Nullable T read(N nbt) {
            // Returns a value to the caller
            return reader.apply(nbt);
        // End of a block/expression
        }

        // Annotation for the following element
        @Nullable N write(T value) {
            // Returns a value to the caller
            return writer.apply(value);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
