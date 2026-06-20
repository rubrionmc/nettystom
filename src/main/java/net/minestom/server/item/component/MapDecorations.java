// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record MapDecorations(Map<String, Entry> decorations) {
    // Affecte une valeur
    public static final Codec<MapDecorations> CODEC = Codec.STRING.mapValue(Entry.CODEC)
            // Appelle une méthode
            .transform(MapDecorations::new, MapDecorations::decorations);

    // Début d'une méthode/d'un bloc
    public MapDecorations {
        // Appelle une méthode
        decorations = Map.copyOf(decorations);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public MapDecorations with(String id, String type, double x, double z, float rotation) {
        // Renvoie une valeur à l'appelant
        return with(id, new Entry(type, x, z, rotation));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public MapDecorations with(String id, Entry entry) {
        // Affecte une valeur
        Map<String, Entry> newDecorations = new HashMap<>(decorations);
        // Appelle une méthode
        newDecorations.put(id, entry);
        // Renvoie une valeur à l'appelant
        return new MapDecorations(newDecorations);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public MapDecorations remove(String id) {
        // Affecte une valeur
        Map<String, Entry> newDecorations = new HashMap<>(decorations);
        // Appelle une méthode
        newDecorations.remove(id);
        // Renvoie une valeur à l'appelant
        return new MapDecorations(newDecorations);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Entry(String type, double x, double z, float rotation) {
        // Affecte une valeur
        public static final Codec<Entry> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.STRING, Entry::type,
                // Instruction de code
                "x", Codec.DOUBLE, Entry::x,
                // Instruction de code
                "z", Codec.DOUBLE, Entry::z,
                // Instruction de code
                "rotation", Codec.FLOAT, Entry::rotation,
                // Instruction de code
                Entry::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
