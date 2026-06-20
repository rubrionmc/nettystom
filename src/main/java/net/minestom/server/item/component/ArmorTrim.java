// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.item.armor.TrimMaterial;
// Import d'une classe nécessaire
import net.minestom.server.item.armor.TrimPattern;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;

// Déclaration de type (classe/interface/enum/record)
public record ArmorTrim(
        // Instruction de code
        Holder<TrimMaterial> material,
        // Instruction de code
        Holder<TrimPattern> pattern
// Début d'une méthode/d'un bloc
) {

    // Affecte une valeur
    public static final NetworkBuffer.Type<ArmorTrim> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            TrimMaterial.NETWORK_TYPE, ArmorTrim::material,
            // Instruction de code
            TrimPattern.NETWORK_TYPE, ArmorTrim::pattern,
            // Instruction de code
            ArmorTrim::new);
    // Affecte une valeur
    public static final Codec<ArmorTrim> CODEC = StructCodec.struct(
            // Instruction de code
            "material", TrimMaterial.CODEC, ArmorTrim::material,
            // Instruction de code
            "pattern", TrimPattern.CODEC, ArmorTrim::pattern,
            // Instruction de code
            ArmorTrim::new);

// Fin d'un bloc/d'une expression
}
