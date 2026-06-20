// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.TagKey;

// Déclaration de type (classe/interface/enum/record)
public record DamageResistant(TagKey<DamageType> types) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DamageResistant> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            TagKey.networkType(Registries::damageType), DamageResistant::types,
            // Instruction de code
            DamageResistant::new);
    // Affecte une valeur
    public static final Codec<DamageResistant> CODEC = StructCodec.struct(
            // Instruction de code
            "types", TagKey.hashCodec(Registries::damageType), DamageResistant::types,
            // Instruction de code
            DamageResistant::new);
// Fin d'un bloc/d'une expression
}
