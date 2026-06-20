// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.AutoIncrementMap;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Déclaration de type (classe/interface/enum/record)
public record TagImpl<T>(int index, String key,
                         // Instruction de code
                         Function<?, ?> readComparator,
                         // Instruction de code
                         Serializers.Entry<T, BinaryTag> entry,
                         // Optional properties
                         // Annotation pour l'élément suivant
                         @Nullable Supplier<@Nullable T> defaultValue,
                         // Instruction de code
                         PathEntry @Nullable [] path,
                         // Annotation pour l'élément suivant
                         @Nullable UnaryOperator<T> copy, int listScope) implements Tag<T> {
    // Appelle une méthode
    private static final AutoIncrementMap<String> INDEX_MAP = new AutoIncrementMap<>();

    // Début d'une méthode/d'un bloc
    public TagImpl {
        // Appelle une méthode
        assert index == INDEX_MAP.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    static <T, N extends BinaryTag> TagImpl<T> tag(String key, Serializers.Entry<T, N> entry) {
        // Renvoie une valeur à l'appelant
        return new TagImpl<>(INDEX_MAP.get(key), key, entry.reader(), (Serializers.Entry<T, BinaryTag>) entry,
                // Instruction de code
                null, null, null, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> TagImpl<T> fromSerializer(String key, TagSerializer<T> serializer) {
        // Embranchement : vérifie une condition
        if (serializer instanceof TagRecord.Serializer<?> recordSerializer) {
            // Allow fast retrieval
            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return (TagImpl<T>) tag(key, recordSerializer.serializerEntry);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return tag(key, Serializers.fromTagSerializer(serializer));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String getKey() {
        // Renvoie une valeur à l'appelant
        return key;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String key() {
        // Renvoie une valeur à l'appelant
        return key;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Tag<T> defaultValue(Supplier<T> defaultValue) {
        // Renvoie une valeur à l'appelant
        return new TagImpl<>(index, key, readComparator, entry, defaultValue, path, copy, listScope);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Tag<T> defaultValue(T defaultValue) {
        // Renvoie une valeur à l'appelant
        return defaultValue(() -> defaultValue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_, _ -> new", pure = true)
    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public <R extends @UnknownNullability Object> Tag<R> map(Function<T, R> readMap,
                          // Début d'une méthode/d'un bloc
                          Function<R, T> writeMap) {
        // Affecte une valeur
        var entry = this.entry;
        // Affecte une valeur
        final Function<BinaryTag, R> readFunction = entry.reader().andThen(t -> {
            // Embranchement : vérifie une condition
            if (t == null) return null;
            // Renvoie une valeur à l'appelant
            return readMap.apply(t);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        final Function<R, BinaryTag> writeFunction = writeMap.andThen(entry.writer());
        // Renvoie une valeur à l'appelant
        return new TagImpl<>(index, key, readMap,
                // Crée un nouvel objet
                new Serializers.Entry<>(entry.nbtType(), readFunction, writeFunction),
                // Default value
                // Début d'une méthode/d'un bloc
                () -> {
                    // Appelle une méthode
                    T defaultValue = createDefault();
                    // Embranchement : vérifie une condition
                    if (defaultValue == null) return null;
                    // Renvoie une valeur à l'appelant
                    return readMap.apply(defaultValue);
                // Instruction de code
                },
                // Instruction de code
                path, null, listScope);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "-> new", pure = true)
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Tag<List<T>> list() {
        // Affecte une valeur
        var entry = this.entry;
        // Appelle une méthode
        var readFunction = entry.reader();
        // Appelle une méthode
        var writeFunction = entry.writer();
        // Affecte une valeur
        var listEntry = new Serializers.Entry<List<T>, ListBinaryTag>(
                // Instruction de code
                BinaryTagTypes.LIST,
                // Début d'une méthode/d'un bloc
                read -> {
                    // Embranchement : vérifie une condition
                    if (read.isEmpty()) return List.of();
                    // Renvoie une valeur à l'appelant
                    return read.stream().map(readFunction).toList();
                // Instruction de code
                },
                // Début d'une méthode/d'un bloc
                write -> {
                    // Embranchement : vérifie une condition
                    if (write.isEmpty())
                        // Renvoie une valeur à l'appelant
                        return ListBinaryTag.empty();
                    // Appelle une méthode
                    final List<BinaryTag> list = write.stream().map(writeFunction).toList();
                    // Appelle une méthode
                    final BinaryTagType<?> type = list.getFirst().type();
                    // Renvoie une valeur à l'appelant
                    return ListBinaryTag.listBinaryTag(type, list);
                // Fin d'un bloc/d'une expression
                });
        // Affecte une valeur
        UnaryOperator<List<T>> co = this.copy != null ? ts -> {
            // Appelle une méthode
            final int size = ts.size();
            // Appelle une méthode
            T[] array = (T[]) new Object[size];
            // Affecte une valeur
            boolean shallowCopy = true;
            // Boucle : répète un bloc
            for (int i = 0; i < size; i++) {
                // Appelle une méthode
                final T t = ts.get(i);
                // Appelle une méthode
                final T copy = this.copy.apply(t);
                // Embranchement : vérifie une condition
                if (shallowCopy && copy != t) shallowCopy = false;
                // Affecte une valeur
                array[i] = copy;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return shallowCopy ? List.copyOf(ts) : List.of(array);
        // Instruction de code
        } : List::copyOf;
        // Renvoie une valeur à l'appelant
        return new TagImpl<>(index, key, readComparator, (Serializers.Entry) listEntry,
                // Instruction de code
                null, path, co, listScope + 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Tag<T> path(String @Nullable ... path) {
        // Embranchement : vérifie une condition
        if (path == null || path.length == 0) {
            // Renvoie une valeur à l'appelant
            return new TagImpl<>(index, key, readComparator, entry, defaultValue, null, copy, listScope);
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        PathEntry[] pathEntries = new PathEntry[path.length];
        // Boucle : répète un bloc
        for (int i = 0; i < path.length; i++) {
            // Affecte une valeur
            final String name = path[i];
            // Embranchement : vérifie une condition
            if (name == null || name.isEmpty()) throw new IllegalArgumentException("Path must not be empty: " + Arrays.toString(path));
            // Appelle une méthode
            pathEntries[i] = new PathEntry(name, INDEX_MAP.get(name));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new TagImpl<>(index, key, readComparator, entry, defaultValue, pathEntries, copy, listScope);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable T read(CompoundBinaryTag nbt) {
        // Appelle une méthode
        final BinaryTag readable = isView() ? nbt : nbt.get(key);
        // Instruction de code
        final T result;
        // Gestion des exceptions
        try {
            // Embranchement : vérifie une condition
            if (readable == null || (result = entry.read(readable)) == null)
                // Renvoie une valeur à l'appelant
                return createDefault();
            // Renvoie une valeur à l'appelant
            return result;
        // Début d'une méthode/d'un bloc
        } catch (ClassCastException e) {
            // Renvoie une valeur à l'appelant
            return createDefault();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void write(CompoundBinaryTag.Builder nbtCompound, @Nullable T value) {
        // Embranchement : vérifie une condition
        if (value != null) {
            // Appelle une méthode
            final BinaryTag nbt = entry.write(value);
            // Embranchement : vérifie une condition
            if (isView()) nbtCompound.put((CompoundBinaryTag) nbt);
            // Branche alternative de la condition
            else nbtCompound.put(key, nbt);
        // Branche alternative de la condition
        } else {
            // Embranchement : vérifie une condition
            if (isView()) {
                // Adventure compound builder doesn't currently have a clear method.
                // Appelle une méthode
                nbtCompound.build().keySet().forEach(nbtCompound::remove);
            // Branche alternative de la condition
            } else nbtCompound.remove(key);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void writeUnsafe(CompoundBinaryTag.Builder nbtCompound, @Nullable Object value) {
        //noinspection unchecked
        // Appelle une méthode
        write(nbtCompound, (T) value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isView() {
        // Renvoie une valeur à l'appelant
        return key.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shareValue(Tag<?> other) {
        // Embranchement : vérifie une condition
        if (this == other) return true;
        // Embranchement : vérifie une condition
        if (!(other instanceof TagImpl<?> otherImpl)) return false;
        // Tags are not strictly the same, compare readers
        // Embranchement : vérifie une condition
        if (this.listScope != otherImpl.listScope) return false;
        // Renvoie une valeur à l'appelant
        return this.readComparator == otherImpl.readComparator;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable T createDefault() {
        // Affecte une valeur
        final Supplier<T> supplier = defaultValue;
        // Renvoie une valeur à l'appelant
        return supplier != null ? supplier.get() : null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T copyValue(T value) {
        // Affecte une valeur
        final UnaryOperator<T> copier = copy;
        // Renvoie une valeur à l'appelant
        return copier != null ? copier.apply(value) : value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (this == o) return true;
        // Embranchement : vérifie une condition
        if (!(o instanceof TagImpl<?> tag)) return false;
        // Renvoie une valeur à l'appelant
        return index == tag.index &&
                // Instruction de code
                listScope == tag.listScope &&
                // Instruction de code
                readComparator.equals(tag.readComparator) &&
                // Instruction de code
                Objects.equals(defaultValue, tag.defaultValue) &&
                // Appelle une méthode
                Arrays.equals(path, tag.path) && Objects.equals(copy, tag.copy);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = Objects.hash(index, readComparator, defaultValue, copy, listScope);
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(path);
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record PathEntry(String name, int index) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
