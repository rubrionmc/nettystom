// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record MapDecorations(Map<String, Entry> decorations) {
    // Assigns a value
    public static final Codec<MapDecorations> CODEC = Codec.STRING.mapValue(Entry.CODEC)
            // Calls a method
            .transform(MapDecorations::new, MapDecorations::decorations);

    // Start of a method/block
    public MapDecorations {
        // Calls a method
        decorations = Map.copyOf(decorations);
    // End of a block/expression
    }

    // Start of a method/block
    public MapDecorations with(String id, String type, double x, double z, float rotation) {
        // Returns a value to the caller
        return with(id, new Entry(type, x, z, rotation));
    // End of a block/expression
    }

    // Start of a method/block
    public MapDecorations with(String id, Entry entry) {
        // Calls a method
        Map<String, Entry> newDecorations = new HashMap<>(decorations);
        // Calls a method
        newDecorations.put(id, entry);
        // Returns a value to the caller
        return new MapDecorations(newDecorations);
    // End of a block/expression
    }

    // Start of a method/block
    public MapDecorations remove(String id) {
        // Calls a method
        Map<String, Entry> newDecorations = new HashMap<>(decorations);
        // Calls a method
        newDecorations.remove(id);
        // Returns a value to the caller
        return new MapDecorations(newDecorations);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entry(String type, double x, double z, float rotation) {
        // Assigns a value
        public static final Codec<Entry> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.STRING, Entry::type,
                // Code statement
                "x", Codec.DOUBLE, Entry::x,
                // Code statement
                "z", Codec.DOUBLE, Entry::z,
                // Code statement
                "rotation", Codec.FLOAT, Entry::rotation,
                // Code statement
                Entry::new);
    // End of a block/expression
    }
// End of a block/expression
}
