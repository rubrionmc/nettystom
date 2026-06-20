// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.nbt.BinaryTagUtil;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static java.util.Map.entry;

/**
 * Handles conversion of {@link BinaryTag} subtypes into one or multiple primitive {@link Tag tags}.
 */
// Déclaration de type (classe/interface/enum/record)
final class TagNbtSeparator {
    // Affecte une valeur
    static final Map<BinaryTagType<?>, Function<String, Tag<?>>> SUPPORTED_TYPES = Map.ofEntries(
            // Instruction de code
            entry(BinaryTagTypes.BYTE, Tag::Byte),
            // Instruction de code
            entry(BinaryTagTypes.SHORT, Tag::Short),
            // Instruction de code
            entry(BinaryTagTypes.INT, Tag::Integer),
            // Instruction de code
            entry(BinaryTagTypes.LONG, Tag::Long),
            // Instruction de code
            entry(BinaryTagTypes.FLOAT, Tag::Float),
            // Instruction de code
            entry(BinaryTagTypes.DOUBLE, Tag::Double),
            // Appelle une méthode
            entry(BinaryTagTypes.STRING, Tag::String));

    // Début d'une méthode/d'un bloc
    static void separate(CompoundBinaryTag nbtCompound, Consumer<Entry> consumer) {
        // Boucle : répète un bloc
        for (var ent : nbtCompound) {
            // Appelle une méthode
            convert(new ArrayList<>(), ent.getKey(), ent.getValue(), consumer);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static void separate(String key, BinaryTag nbt, Consumer<Entry> consumer) {
        // Appelle une méthode
        convert(new ArrayList<>(), key, nbt, consumer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Entry separateSingle(String key, BinaryTag nbt) {
        // Appelle une méthode
        assert !(nbt instanceof CompoundBinaryTag);
        // Appelle une méthode
        AtomicReference<Entry<?>> entryRef = new AtomicReference<>();
        // Début d'une méthode/d'un bloc
        convert(new ArrayList<>(), key, nbt, entry -> {
            // Appelle une méthode
            assert entryRef.getPlain() == null : "Multiple entries found for nbt tag: " + key + " -> " + nbt;
            // Appelle une méthode
            entryRef.setPlain(entry);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        var entry = entryRef.getPlain();
        // Instruction de code
        assert entry != null;
        // Renvoie une valeur à l'appelant
        return entry;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void convert(List<String> path, String key, BinaryTag nbt, Consumer<Entry> consumer) {
        // Appelle une méthode
        var tagFunction = SUPPORTED_TYPES.get(nbt.type());
        // Embranchement : vérifie une condition
        if (tagFunction != null) {
            // Appelle une méthode
            Tag<?> tag = tagFunction.apply(key);
            // Appelle une méthode
            consumer.accept(makeEntry(path, (Tag<Object>) tag, BinaryTagUtil.nbtValueFromTag(nbt)));
        // Embranchement : vérifie une condition
        } else if (nbt instanceof CompoundBinaryTag nbtCompound) {
            // Embranchement : vérifie une condition
            if (nbtCompound.isEmpty()) {
                // Embranchement : vérifie une condition
                if (ServerFlag.SERIALIZE_EMPTY_COMPOUND || path.isEmpty()) {
                    // Appelle une méthode
                    consumer.accept(makeEntry(path, Tag.NBT(key), nbt));
                // Fin d'un bloc/d'une expression
                }
            // Branche alternative de la condition
            } else {
                // Boucle : répète un bloc
                for (var ent : nbtCompound) {
                    // Appelle une méthode
                    var newPath = new ArrayList<>(path);
                    // Appelle une méthode
                    newPath.add(key);
                    // Appelle une méthode
                    convert(newPath, ent.getKey(), ent.getValue(), consumer);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Embranchement : vérifie une condition
        } else if (nbt instanceof ListBinaryTag nbtList) {
            // Appelle une méthode
            tagFunction = SUPPORTED_TYPES.get(nbtList.elementType());
            // Embranchement : vérifie une condition
            if (tagFunction == null) {
                // Invalid list subtype, fallback to nbt
                // Appelle une méthode
                consumer.accept(makeEntry(path, Tag.NBT(key), nbt));
            // Branche alternative de la condition
            } else {
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    var tag = tagFunction.apply(key).list();
                    // Appelle une méthode
                    Object[] values = new Object[nbtList.size()];
                    // Boucle : répète un bloc
                    for (int i = 0; i < values.length; i++) {
                        // Appelle une méthode
                        values[i] = BinaryTagUtil.nbtValueFromTag(nbtList.get(i));
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    consumer.accept(makeEntry(path, (Tag<? super List<Object>>) tag, List.of(values)));
                // Début d'une méthode/d'un bloc
                } catch (Exception e) {
                    // Appelle une méthode
                    e.printStackTrace();
                    // Appelle une méthode
                    consumer.accept(makeEntry(path, Tag.NBT(key), nbt));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // TODO array support
            // Appelle une méthode
            consumer.accept(makeEntry(path, Tag.NBT(key), nbt));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> Entry<?> makeEntry(List<String> path, Tag<T> tag, T value) {
        // Renvoie une valeur à l'appelant
        return new Entry<>(tag.path(path.toArray(String[]::new)), value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Entry<T>(TagImpl<T> tag, T value) {
        // Début d'une méthode/d'un bloc
        public Entry(Tag<T> tag, T value) {
            // Appelle une méthode
            this((TagImpl<T>) tag, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
