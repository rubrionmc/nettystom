// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record DeathProtection(List<ConsumeEffect> deathEffects) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DeathProtection> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            ConsumeEffect.NETWORK_TYPE.list(256), DeathProtection::deathEffects,
            // Instruction de code
            DeathProtection::new);
    // Affecte une valeur
    public static final Codec<DeathProtection> CODEC = StructCodec.struct(
            // Instruction de code
            "death_effects", ConsumeEffect.CODEC.list().optional(List.of()), DeathProtection::deathEffects,
            // Instruction de code
            DeathProtection::new);

    // Début d'une méthode/d'un bloc
    public DeathProtection {
        // Appelle une méthode
        deathEffects = List.copyOf(deathEffects);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
