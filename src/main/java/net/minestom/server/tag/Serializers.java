// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.*;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.utils.UUIDUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * Basic serializers for {@link Tag tags}.
 */
// Déclaration de type (classe/interface/enum/record)
final class Serializers {
    // Affecte une valeur
    static final Entry<Byte, ByteBinaryTag> BYTE = new Entry<>(BinaryTagTypes.BYTE, ByteBinaryTag::value, ByteBinaryTag::byteBinaryTag);
    // Appelle une méthode
    static final Entry<Boolean, ByteBinaryTag> BOOLEAN = new Entry<>(BinaryTagTypes.BYTE, b -> b.value() != 0, b -> b ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO);
    // Affecte une valeur
    static final Entry<Short, ShortBinaryTag> SHORT = new Entry<>(BinaryTagTypes.SHORT, ShortBinaryTag::value, ShortBinaryTag::shortBinaryTag);
    // Affecte une valeur
    static final Entry<Integer, IntBinaryTag> INT = new Entry<>(BinaryTagTypes.INT, IntBinaryTag::value, IntBinaryTag::intBinaryTag);
    // Affecte une valeur
    static final Entry<Long, LongBinaryTag> LONG = new Entry<>(BinaryTagTypes.LONG, LongBinaryTag::value, LongBinaryTag::longBinaryTag);
    // Affecte une valeur
    static final Entry<Float, FloatBinaryTag> FLOAT = new Entry<>(BinaryTagTypes.FLOAT, FloatBinaryTag::value, FloatBinaryTag::floatBinaryTag);
    // Affecte une valeur
    static final Entry<Double, DoubleBinaryTag> DOUBLE = new Entry<>(BinaryTagTypes.DOUBLE, DoubleBinaryTag::value, DoubleBinaryTag::doubleBinaryTag);
    // Affecte une valeur
    static final Entry<String, StringBinaryTag> STRING = new Entry<>(BinaryTagTypes.STRING, StringBinaryTag::value, StringBinaryTag::stringBinaryTag);
    // Appelle une méthode
    static final Entry<BinaryTag, BinaryTag> NBT_ENTRY = new Entry<>(null, Function.identity(), Function.identity());

    // Affecte une valeur
    static final Entry<java.util.UUID, IntArrayBinaryTag> UUID = new Entry<>(BinaryTagTypes.INT_ARRAY, UUIDUtils::fromNbt, UUIDUtils::toNbt);
    // Affecte une valeur
    static final Entry<ItemStack, CompoundBinaryTag> ITEM = new Entry<>(BinaryTagTypes.COMPOUND, ItemStack::fromItemNBT, ItemStack::toItemNBT);
    // Affecte une valeur
    static final Entry<Component, BinaryTag> COMPONENT = new Entry<>(null,
            // Instruction de code
            input -> Codec.COMPONENT.decode(new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process()), input).orElse(null),
            // Instruction de code
            component -> Codec.COMPONENT.encode(new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process()), component).orElse(null)
    // Fin d'un bloc/d'une expression
    );

    // Affecte une valeur
    static final Entry<Object, ByteBinaryTag> EMPTY = new Entry<>(BinaryTagTypes.BYTE, _ -> null, _ -> null);

    // Début d'une méthode/d'un bloc
    static <T> Entry<T, CompoundBinaryTag> fromTagSerializer(TagSerializer<T> serializer) {
        // Renvoie une valeur à l'appelant
        return new Serializers.Entry<>(BinaryTagTypes.COMPOUND,
                // Début d'une méthode/d'un bloc
                (CompoundBinaryTag compound) -> {
                    // Embranchement : vérifie une condition
                    if ((!ServerFlag.SERIALIZE_EMPTY_COMPOUND) && compound.isEmpty()) return null;
                    // Renvoie une valeur à l'appelant
                    return serializer.read(TagHandler.fromCompound(compound));
                // Instruction de code
                },
                // Début d'une méthode/d'un bloc
                (value) -> {
                    // Embranchement : vérifie une condition
                    if (value == null) return CompoundBinaryTag.empty();
                    // Appelle une méthode
                    TagHandler handler = TagHandler.newHandler();
                    // Appelle une méthode
                    serializer.write(handler, value);
                    // Renvoie une valeur à l'appelant
                    return handler.asCompound();
                // Fin d'un bloc/d'une expression
                });
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Entry<T, N extends BinaryTag>(@Nullable BinaryTagType<N> nbtType,
                                         // Instruction de code
                                         Function<N, @Nullable T> reader,
                                         // Instruction de code
                                         Function<T, @Nullable N> writer,
                                         // Début d'une méthode/d'un bloc
                                         boolean isPath) {
        // Début d'une méthode/d'un bloc
        Entry(@Nullable BinaryTagType<N> nbtType, Function<N, T> reader, Function<T, N> writer) {
            // Appelle une méthode
            this(nbtType, reader, writer, false);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Nullable T read(N nbt) {
            // Renvoie une valeur à l'appelant
            return reader.apply(nbt);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Nullable N write(T value) {
            // Renvoie une valeur à l'appelant
            return writer.apply(value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
