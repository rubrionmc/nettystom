// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record DeathProtection(List<ConsumeEffect> deathEffects) {
    // Assigns a value
    public static final NetworkBuffer.Type<DeathProtection> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            ConsumeEffect.NETWORK_TYPE.list(256), DeathProtection::deathEffects,
            // Code statement
            DeathProtection::new);
    // Assigns a value
    public static final Codec<DeathProtection> CODEC = StructCodec.struct(
            // Code statement
            "death_effects", ConsumeEffect.CODEC.list().optional(List.of()), DeathProtection::deathEffects,
            // Code statement
            DeathProtection::new);

    // Start of a method/block
    public DeathProtection {
        // Calls a method
        deathEffects = List.copyOf(deathEffects);
    // End of a block/expression
    }
// End of a block/expression
}
