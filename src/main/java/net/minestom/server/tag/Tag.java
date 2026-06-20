// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;

/**
 * Represents a key to retrieve or change a value.
 * <p>
 * All tags are serializable.
 *
 * @param <T> the tag type
 */
// Type declaration (class/interface/enum/record)
public sealed interface Tag<T extends @UnknownNullability Object> permits TagImpl {
    // Start of a method/block
    static Tag<Byte> Byte(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.BYTE);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<Boolean> Boolean(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.BOOLEAN);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<Short> Short(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.SHORT);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<Integer> Integer(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.INT);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<Long> Long(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.LONG);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<Float> Float(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.FLOAT);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<Double> Double(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.DOUBLE);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<String> String(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.STRING);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<UUID> UUID(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.UUID);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<ItemStack> ItemStack(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.ITEM);
    // End of a block/expression
    }

    // Start of a method/block
    static Tag<Component> Component(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.COMPONENT);
    // End of a block/expression
    }

    /**
     * Creates a flexible tag able to read and write any {@link BinaryTag} objects.
     * <p>
     * Specialized tags are recommended if the type is known as conversion will be required both way (read and write).
     */
    // Start of a method/block
    static Tag<BinaryTag> NBT(String key) {
        // Returns a value to the caller
        return TagImpl.tag(key, Serializers.NBT_ENTRY);
    // End of a block/expression
    }

    /**
     * Creates a tag containing multiple fields.
     * <p>
     * Those fields cannot be modified from an outside tag. (This is to prevent the backed object from becoming out of sync)
     *
     * @param key        the tag key
     * @param serializer the tag serializer
     * @param <T>        the tag type
     * @return the created tag
     */
    // Start of a method/block
    static <T> Tag<T> Structure(String key, TagSerializer<T> serializer) {
        // Returns a value to the caller
        return TagImpl.fromSerializer(key, serializer);
    // End of a block/expression
    }

    /**
     * Specialized Structure tag affecting the src of the handler (i.e. overwrite all its data).
     * <p>
     * Must be used with care.
     */
    // Start of a method/block
    static <T> Tag<T> View(TagSerializer<T> serializer) {
        // Returns a value to the caller
        return Structure("", serializer);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    static <T extends Record> Tag<T> Structure(String key, Class<T> type) {
        // Returns a value to the caller
        return Structure(key, TagRecord.serializer(type));
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    static <T extends Record> Tag<T> View(Class<T> type) {
        // Returns a value to the caller
        return View(TagRecord.serializer(type));
    // End of a block/expression
    }

    /**
     * Creates a transient tag with the specified key. This tag does not get serialized
     * to NBT (Named Binary Tag) format and is not sent to the client. Unlike other tags,
     * which are serialized, transient tags are used for temporary data
     * that only needs to exist on the server side.
     *
     * @param <T> The type of the tag's value.
     * @param key The key.
     * @return A transient tag with the key.
     */
    // Start of a method/block
    static <T> Tag<T> Transient(String key) {
        //noinspection unchecked
        // Returns a value to the caller
        return (Tag<T>) TagImpl.tag(key, Serializers.EMPTY);
    // End of a block/expression
    }

    /**
     * Use {@link #key()} instead
     * @return the key
     * @deprecated misleading non-record component, use {@link #key()} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Calls a method
    String getKey();

    /**
     * Returns the key for the Tag
     * <br>
     * Same key specified during the creation.
     * @return the key to use
     */
    // Calls a method
    String key();

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Calls a method
    Tag<T> defaultValue(Supplier<T> defaultValue);

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Calls a method
    Tag<T> defaultValue(T defaultValue);

    // Annotation for the following element
    @Contract(value = "_, _ -> new", pure = true)
    // Code statement
    <R> Tag<R> map(Function<T, R> readMap,
                   // Code statement
                   Function<R, T> writeMap);

    // Annotation for the following element
    @Contract(value = "-> new", pure = true)
    // Calls a method
    Tag<List<T>> list();

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Calls a method
    Tag<T> path(String @Nullable ... path);

    // Calls a method
    T read(CompoundBinaryTag nbt);

    // Calls a method
    void write(CompoundBinaryTag.Builder nbtCompound, T value);

    // Calls a method
    void writeUnsafe(CompoundBinaryTag.Builder nbtCompound, @Nullable Object value);

    // Calls a method
    boolean isView();

    // Calls a method
    boolean shareValue(Tag<?> other);

    // Calls a method
    T createDefault();

    // Calls a method
    T copyValue(T value);
// End of a block/expression
}
