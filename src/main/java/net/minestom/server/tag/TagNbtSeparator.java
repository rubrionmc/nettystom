// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.*;
// Import of a required class
import net.minestom.server.utils.nbt.BinaryTagUtil;
// Import of a required class
import net.minestom.server.ServerFlag;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Function;

// Static import of a member
import static java.util.Map.entry;

/**
 * Handles conversion of {@link BinaryTag} subtypes into one or multiple primitive {@link Tag tags}.
 */
// Type declaration (class/interface/enum/record)
final class TagNbtSeparator {
    // Assigns a value
    static final Map<BinaryTagType<?>, Function<String, Tag<?>>> SUPPORTED_TYPES = Map.ofEntries(
            // Code statement
            entry(BinaryTagTypes.BYTE, Tag::Byte),
            // Code statement
            entry(BinaryTagTypes.SHORT, Tag::Short),
            // Code statement
            entry(BinaryTagTypes.INT, Tag::Integer),
            // Code statement
            entry(BinaryTagTypes.LONG, Tag::Long),
            // Code statement
            entry(BinaryTagTypes.FLOAT, Tag::Float),
            // Code statement
            entry(BinaryTagTypes.DOUBLE, Tag::Double),
            // Calls a method
            entry(BinaryTagTypes.STRING, Tag::String));

    // Start of a method/block
    static void separate(CompoundBinaryTag nbtCompound, Consumer<Entry> consumer) {
        // Loop: repeats a block
        for (var ent : nbtCompound) {
            // Calls a method
            convert(new ArrayList<>(), ent.getKey(), ent.getValue(), consumer);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    static void separate(String key, BinaryTag nbt, Consumer<Entry> consumer) {
        // Calls a method
        convert(new ArrayList<>(), key, nbt, consumer);
    // End of a block/expression
    }

    // Start of a method/block
    static Entry separateSingle(String key, BinaryTag nbt) {
        // Calls a method
        assert !(nbt instanceof CompoundBinaryTag);
        // Calls a method
        AtomicReference<Entry<?>> entryRef = new AtomicReference<>();
        // Start of a method/block
        convert(new ArrayList<>(), key, nbt, entry -> {
            // Calls a method
            assert entryRef.getPlain() == null : "Multiple entries found for nbt tag: " + key + " -> " + nbt;
            // Calls a method
            entryRef.setPlain(entry);
        // End of a block/expression
        });
        // Calls a method
        var entry = entryRef.getPlain();
        // Code statement
        assert entry != null;
        // Returns a value to the caller
        return entry;
    // End of a block/expression
    }

    // Start of a method/block
    private static void convert(List<String> path, String key, BinaryTag nbt, Consumer<Entry> consumer) {
        // Calls a method
        var tagFunction = SUPPORTED_TYPES.get(nbt.type());
        // Branch: checks a condition
        if (tagFunction != null) {
            // Calls a method
            Tag<?> tag = tagFunction.apply(key);
            // Calls a method
            consumer.accept(makeEntry(path, (Tag<Object>) tag, BinaryTagUtil.nbtValueFromTag(nbt)));
        // Branch: checks a condition
        } else if (nbt instanceof CompoundBinaryTag nbtCompound) {
            // Branch: checks a condition
            if (nbtCompound.isEmpty()) {
                // Branch: checks a condition
                if (ServerFlag.SERIALIZE_EMPTY_COMPOUND || path.isEmpty()) {
                    // Calls a method
                    consumer.accept(makeEntry(path, Tag.NBT(key), nbt));
                // End of a block/expression
                }
            // Alternative branch of the condition
            } else {
                // Loop: repeats a block
                for (var ent : nbtCompound) {
                    // Calls a method
                    var newPath = new ArrayList<>(path);
                    // Calls a method
                    newPath.add(key);
                    // Calls a method
                    convert(newPath, ent.getKey(), ent.getValue(), consumer);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // Branch: checks a condition
        } else if (nbt instanceof ListBinaryTag nbtList) {
            // Calls a method
            tagFunction = SUPPORTED_TYPES.get(nbtList.elementType());
            // Branch: checks a condition
            if (tagFunction == null) {
                // Invalid list subtype, fallback to nbt
                // Calls a method
                consumer.accept(makeEntry(path, Tag.NBT(key), nbt));
            // Alternative branch of the condition
            } else {
                // Exception handling
                try {
                    // Calls a method
                    var tag = tagFunction.apply(key).list();
                    // Calls a method
                    Object[] values = new Object[nbtList.size()];
                    // Loop: repeats a block
                    for (int i = 0; i < values.length; i++) {
                        // Calls a method
                        values[i] = BinaryTagUtil.nbtValueFromTag(nbtList.get(i));
                    // End of a block/expression
                    }
                    // Calls a method
                    consumer.accept(makeEntry(path, (Tag<? super List<Object>>) tag, List.of(values)));
                // Start of a method/block
                } catch (Exception e) {
                    // Calls a method
                    e.printStackTrace();
                    // Calls a method
                    consumer.accept(makeEntry(path, Tag.NBT(key), nbt));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // TODO array support
            // Calls a method
            consumer.accept(makeEntry(path, Tag.NBT(key), nbt));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> Entry<?> makeEntry(List<String> path, Tag<T> tag, T value) {
        // Returns a value to the caller
        return new Entry<>(tag.path(path.toArray(String[]::new)), value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Entry<T>(TagImpl<T> tag, T value) {
        // Start of a method/block
        public Entry(Tag<T> tag, T value) {
            // Calls a method
            this((TagImpl<T>) tag, value);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
