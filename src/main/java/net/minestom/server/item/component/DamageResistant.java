// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryTag;

// Type declaration (class/interface/enum/record)
public record DamageResistant(RegistryTag<DamageType> types) {
    // Assigns a value
    public static final NetworkBuffer.Type<DamageResistant> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            RegistryTag.networkType(Registries::damageType), DamageResistant::types,
            // Code statement
            DamageResistant::new);
    // Assigns a value
    public static final Codec<DamageResistant> CODEC = StructCodec.struct(
            // Code statement
            "types", RegistryTag.codec(Registries::damageType), DamageResistant::types,
            // Code statement
            DamageResistant::new);
// End of a block/expression
}
