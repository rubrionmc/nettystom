// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record CustomModelData(
        // Instruction de code
        List<Float> floats, List<Boolean> flags,
        // Instruction de code
        List<String> strings, List<RGBLike> colors
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    private static final int MAX_ENTRIES = 256;

    // Affecte une valeur
    public static final NetworkBuffer.Type<CustomModelData> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.FLOAT.list(MAX_ENTRIES), CustomModelData::floats,
            // Instruction de code
            NetworkBuffer.BOOLEAN.list(MAX_ENTRIES), CustomModelData::flags,
            // Instruction de code
            NetworkBuffer.STRING.list(MAX_ENTRIES), CustomModelData::strings,
            // Instruction de code
            Color.NETWORK_TYPE.list(MAX_ENTRIES), CustomModelData::colors,
            // Instruction de code
            CustomModelData::new);
    // Affecte une valeur
    public static final Codec<CustomModelData> CODEC = StructCodec.struct(
            // Instruction de code
            "floats", Codec.FLOAT.list().optional(List.of()), CustomModelData::floats,
            // Instruction de code
            "flags", Codec.BOOLEAN.list().optional(List.of()), CustomModelData::flags,
            // Instruction de code
            "strings", Codec.STRING.list().optional(List.of()), CustomModelData::strings,
            // Instruction de code
            "colors", Color.CODEC.list().optional(List.of()), CustomModelData::colors,
            // Instruction de code
            CustomModelData::new);

    // Début d'une méthode/d'un bloc
    public CustomModelData {
        // Appelle une méthode
        floats = List.copyOf(floats);
        // Appelle une méthode
        flags = List.copyOf(flags);
        // Appelle une méthode
        strings = List.copyOf(strings);
        // Appelle une méthode
        colors = List.copyOf(colors);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
