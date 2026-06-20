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
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record UseCooldown(float seconds, @Nullable String cooldownGroup) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<UseCooldown> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.FLOAT, UseCooldown::seconds,
            // Instruction de code
            NetworkBuffer.STRING.optional(), UseCooldown::cooldownGroup,
            // Instruction de code
            UseCooldown::new);
    // Affecte une valeur
    public static final Codec<UseCooldown> CODEC = StructCodec.struct(
            // Instruction de code
            "seconds", Codec.FLOAT, UseCooldown::seconds,
            // Instruction de code
            "cooldown_group", Codec.STRING.optional(), UseCooldown::cooldownGroup,
            // Instruction de code
            UseCooldown::new);
// Fin d'un bloc/d'une expression
}
