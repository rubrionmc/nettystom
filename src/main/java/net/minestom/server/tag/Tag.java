// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * Represents a key to retrieve or change a value.
 * <p>
 * All tags are serializable.
 *
 * @param <T> the tag type
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Tag<T extends @UnknownNullability Object> permits TagImpl {
    // Début d'une méthode/d'un bloc
    static Tag<Byte> Byte(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.BYTE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<Boolean> Boolean(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.BOOLEAN);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<Short> Short(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.SHORT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<Integer> Integer(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.INT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<Long> Long(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.LONG);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<Float> Float(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.FLOAT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<Double> Double(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.DOUBLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<String> String(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.STRING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<UUID> UUID(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.UUID);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<ItemStack> ItemStack(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.ITEM);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Tag<Component> Component(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.COMPONENT);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a flexible tag able to read and write any {@link BinaryTag} objects.
     * <p>
     * Specialized tags are recommended if the type is known as conversion will be required both way (read and write).
     */
    // Début d'une méthode/d'un bloc
    static Tag<BinaryTag> NBT(String key) {
        // Renvoie une valeur à l'appelant
        return TagImpl.tag(key, Serializers.NBT_ENTRY);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    static <T> Tag<T> Structure(String key, TagSerializer<T> serializer) {
        // Renvoie une valeur à l'appelant
        return TagImpl.fromSerializer(key, serializer);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Specialized Structure tag affecting the src of the handler (i.e. overwrite all its data).
     * <p>
     * Must be used with care.
     */
    // Début d'une méthode/d'un bloc
    static <T> Tag<T> View(TagSerializer<T> serializer) {
        // Renvoie une valeur à l'appelant
        return Structure("", serializer);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    static <T extends Record> Tag<T> Structure(String key, Class<T> type) {
        // Renvoie une valeur à l'appelant
        return Structure(key, TagRecord.serializer(type));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    static <T extends Record> Tag<T> View(Class<T> type) {
        // Renvoie une valeur à l'appelant
        return View(TagRecord.serializer(type));
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    static <T> Tag<T> Transient(String key) {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return (Tag<T>) TagImpl.tag(key, Serializers.EMPTY);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Use {@link #key()} instead
     * @return the key
     * @deprecated misleading non-record component, use {@link #key()} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Appelle une méthode
    String getKey();

    /**
     * Returns the key for the Tag
     * <br>
     * Same key specified during the creation.
     * @return the key to use
     */
    // Appelle une méthode
    String key();

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Appelle une méthode
    Tag<T> defaultValue(Supplier<T> defaultValue);

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Appelle une méthode
    Tag<T> defaultValue(T defaultValue);

    // Annotation pour l'élément suivant
    @Contract(value = "_, _ -> new", pure = true)
    // Instruction de code
    <R> Tag<R> map(Function<T, R> readMap,
                   // Instruction de code
                   Function<R, T> writeMap);

    // Annotation pour l'élément suivant
    @Contract(value = "-> new", pure = true)
    // Appelle une méthode
    Tag<List<T>> list();

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Appelle une méthode
    Tag<T> path(String @Nullable ... path);

    // Appelle une méthode
    T read(CompoundBinaryTag nbt);

    // Appelle une méthode
    void write(CompoundBinaryTag.Builder nbtCompound, T value);

    // Appelle une méthode
    void writeUnsafe(CompoundBinaryTag.Builder nbtCompound, @Nullable Object value);

    // Appelle une méthode
    boolean isView();

    // Appelle une méthode
    boolean shareValue(Tag<?> other);

    // Appelle une méthode
    T createDefault();

    // Appelle une méthode
    T copyValue(T value);
// Fin d'un bloc/d'une expression
}
