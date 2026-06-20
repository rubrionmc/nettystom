// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.*;
// Import of a required class
import net.minestom.server.utils.collection.AutoIncrementMap;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public record TagImpl<T>(int index, String key,
                         // Code statement
                         Function<?, ?> readComparator,
                         // Code statement
                         Serializers.Entry<T, BinaryTag> entry,
                         // Optional properties
                         // Annotation for the following element
                         @Nullable Supplier<@Nullable T> defaultValue,
                         // Code statement
                         PathEntry @Nullable [] path,
                         // Annotation for the following element
                         @Nullable UnaryOperator<T> copy, int listScope) implements Tag<T> {
    // Calls a method
    private static final AutoIncrementMap<String> INDEX_MAP = new AutoIncrementMap<>();

    // Start of a method/block
    public TagImpl {
        // Calls a method
        assert index == INDEX_MAP.get(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    static <T, N extends BinaryTag> TagImpl<T> tag(String key, Serializers.Entry<T, N> entry) {
        // Returns a value to the caller
        return new TagImpl<>(INDEX_MAP.get(key), key, entry.reader(), (Serializers.Entry<T, BinaryTag>) entry,
                // Code statement
                null, null, null, 0);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> TagImpl<T> fromSerializer(String key, TagSerializer<T> serializer) {
        // Branch: checks a condition
        if (serializer instanceof TagRecord.Serializer<?> recordSerializer) {
            // Allow fast retrieval
            //noinspection unchecked
            // Returns a value to the caller
            return (TagImpl<T>) tag(key, recordSerializer.serializerEntry);
        // End of a block/expression
        }
        // Returns a value to the caller
        return tag(key, Serializers.fromTagSerializer(serializer));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String getKey() {
        // Returns a value to the caller
        return key;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String key() {
        // Returns a value to the caller
        return key;
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Tag<T> defaultValue(Supplier<T> defaultValue) {
        // Returns a value to the caller
        return new TagImpl<>(index, key, readComparator, entry, defaultValue, path, copy, listScope);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Tag<T> defaultValue(T defaultValue) {
        // Returns a value to the caller
        return defaultValue(() -> defaultValue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_, _ -> new", pure = true)
    // Annotation for the following element
    @Override
    // Code statement
    public <R extends @UnknownNullability Object> Tag<R> map(Function<T, R> readMap,
                          // Start of a method/block
                          Function<R, T> writeMap) {
        // Assigns a value
        var entry = this.entry;
        // Assigns a value
        final Function<BinaryTag, R> readFunction = entry.reader().andThen(t -> {
            // Branch: checks a condition
            if (t == null) return null;
            // Returns a value to the caller
            return readMap.apply(t);
        // End of a block/expression
        });
        // Calls a method
        final Function<R, BinaryTag> writeFunction = writeMap.andThen(entry.writer());
        // Returns a value to the caller
        return new TagImpl<>(index, key, readMap,
                // Creates a new object
                new Serializers.Entry<>(entry.nbtType(), readFunction, writeFunction),
                // Default value
                // Start of a method/block
                () -> {
                    // Calls a method
                    T defaultValue = createDefault();
                    // Branch: checks a condition
                    if (defaultValue == null) return null;
                    // Returns a value to the caller
                    return readMap.apply(defaultValue);
                // Code statement
                },
                // Code statement
                path, null, listScope);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "-> new", pure = true)
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Tag<List<T>> list() {
        // Assigns a value
        var entry = this.entry;
        // Calls a method
        var readFunction = entry.reader();
        // Calls a method
        var writeFunction = entry.writer();
        // Assigns a value
        var listEntry = new Serializers.Entry<List<T>, ListBinaryTag>(
                // Code statement
                BinaryTagTypes.LIST,
                // Start of a method/block
                read -> {
                    // Branch: checks a condition
                    if (read.isEmpty()) return List.of();
                    // Returns a value to the caller
                    return read.stream().map(readFunction).toList();
                // Code statement
                },
                // Start of a method/block
                write -> {
                    // Branch: checks a condition
                    if (write.isEmpty())
                        // Returns a value to the caller
                        return ListBinaryTag.empty();
                    // Calls a method
                    final List<BinaryTag> list = write.stream().map(writeFunction).toList();
                    // Calls a method
                    final BinaryTagType<?> type = list.getFirst().type();
                    // Returns a value to the caller
                    return ListBinaryTag.listBinaryTag(type, list);
                // End of a block/expression
                });
        // Assigns a value
        UnaryOperator<List<T>> co = this.copy != null ? ts -> {
            // Calls a method
            final int size = ts.size();
            // Calls a method
            T[] array = (T[]) new Object[size];
            // Assigns a value
            boolean shallowCopy = true;
            // Loop: repeats a block
            for (int i = 0; i < size; i++) {
                // Calls a method
                final T t = ts.get(i);
                // Calls a method
                final T copy = this.copy.apply(t);
                // Branch: checks a condition
                if (shallowCopy && copy != t) shallowCopy = false;
                // Assigns a value
                array[i] = copy;
            // End of a block/expression
            }
            // Returns a value to the caller
            return shallowCopy ? List.copyOf(ts) : List.of(array);
        // Code statement
        } : List::copyOf;
        // Returns a value to the caller
        return new TagImpl<>(index, key, readComparator, (Serializers.Entry) listEntry,
                // Code statement
                null, path, co, listScope + 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Tag<T> path(String @Nullable ... path) {
        // Branch: checks a condition
        if (path == null || path.length == 0) {
            // Returns a value to the caller
            return new TagImpl<>(index, key, readComparator, entry, defaultValue, null, copy, listScope);
        // End of a block/expression
        }
        // Assigns a value
        PathEntry[] pathEntries = new PathEntry[path.length];
        // Loop: repeats a block
        for (int i = 0; i < path.length; i++) {
            // Assigns a value
            final String name = path[i];
            // Branch: checks a condition
            if (name == null || name.isEmpty()) throw new IllegalArgumentException("Path must not be empty: " + Arrays.toString(path));
            // Calls a method
            pathEntries[i] = new PathEntry(name, INDEX_MAP.get(name));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new TagImpl<>(index, key, readComparator, entry, defaultValue, pathEntries, copy, listScope);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable T read(CompoundBinaryTag nbt) {
        // Calls a method
        final BinaryTag readable = isView() ? nbt : nbt.get(key);
        // Code statement
        final T result;
        // Exception handling
        try {
            // Branch: checks a condition
            if (readable == null || (result = entry.read(readable)) == null)
                // Returns a value to the caller
                return createDefault();
            // Returns a value to the caller
            return result;
        // Start of a method/block
        } catch (ClassCastException e) {
            // Returns a value to the caller
            return createDefault();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void write(CompoundBinaryTag.Builder nbtCompound, @Nullable T value) {
        // Branch: checks a condition
        if (value != null) {
            // Calls a method
            final BinaryTag nbt = entry.write(value);
            // Branch: checks a condition
            if (isView()) nbtCompound.put((CompoundBinaryTag) nbt);
            // Alternative branch of the condition
            else nbtCompound.put(key, nbt);
        // Alternative branch of the condition
        } else {
            // Branch: checks a condition
            if (isView()) {
                // Adventure compound builder doesn't currently have a clear method.
                // Calls a method
                nbtCompound.build().keySet().forEach(nbtCompound::remove);
            // Alternative branch of the condition
            } else nbtCompound.remove(key);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void writeUnsafe(CompoundBinaryTag.Builder nbtCompound, @Nullable Object value) {
        //noinspection unchecked
        // Calls a method
        write(nbtCompound, (T) value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isView() {
        // Returns a value to the caller
        return key.isEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shareValue(Tag<?> other) {
        // Branch: checks a condition
        if (this == other) return true;
        // Branch: checks a condition
        if (!(other instanceof TagImpl<?> otherImpl)) return false;
        // Tags are not strictly the same, compare readers
        // Branch: checks a condition
        if (this.listScope != otherImpl.listScope) return false;
        // Returns a value to the caller
        return this.readComparator == otherImpl.readComparator;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable T createDefault() {
        // Assigns a value
        final Supplier<T> supplier = defaultValue;
        // Returns a value to the caller
        return supplier != null ? supplier.get() : null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T copyValue(T value) {
        // Assigns a value
        final UnaryOperator<T> copier = copy;
        // Returns a value to the caller
        return copier != null ? copier.apply(value) : value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (this == o) return true;
        // Branch: checks a condition
        if (!(o instanceof TagImpl<?> tag)) return false;
        // Returns a value to the caller
        return index == tag.index &&
                // Code statement
                listScope == tag.listScope &&
                // Code statement
                readComparator.equals(tag.readComparator) &&
                // Code statement
                Objects.equals(defaultValue, tag.defaultValue) &&
                // Calls a method
                Arrays.equals(path, tag.path) && Objects.equals(copy, tag.copy);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = Objects.hash(index, readComparator, defaultValue, copy, listScope);
        // Calls a method
        result = 31 * result + Arrays.hashCode(path);
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record PathEntry(String name, int index) {
    // End of a block/expression
    }
// End of a block/expression
}
