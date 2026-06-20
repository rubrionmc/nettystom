// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.item.armor.TrimMaterial;
// Import of a required class
import net.minestom.server.item.armor.TrimPattern;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.Holder;

// Type declaration (class/interface/enum/record)
public record ArmorTrim(
        // Code statement
        Holder<TrimMaterial> material,
        // Code statement
        Holder<TrimPattern> pattern
// Start of a method/block
) {

    // Assigns a value
    public static final NetworkBuffer.Type<ArmorTrim> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            TrimMaterial.NETWORK_TYPE, ArmorTrim::material,
            // Code statement
            TrimPattern.NETWORK_TYPE, ArmorTrim::pattern,
            // Code statement
            ArmorTrim::new);
    // Assigns a value
    public static final Codec<ArmorTrim> CODEC = StructCodec.struct(
            // Code statement
            "material", TrimMaterial.CODEC, ArmorTrim::material,
            // Code statement
            "pattern", TrimPattern.CODEC, ArmorTrim::pattern,
            // Code statement
            ArmorTrim::new);

// End of a block/expression
}
