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

// Déclaration de type (classe/interface/enum/record)
public record UseEffects(
        // Instruction de code
        boolean canSprint,
        // Instruction de code
        boolean interactVibrations,
        // Instruction de code
        float speedMultiplier
// Début d'une méthode/d'un bloc
) {
    // Appelle une méthode
    public static final UseEffects DEFAULT = new UseEffects(false, true, 0.2f);

    // Affecte une valeur
    public static final NetworkBuffer.Type<UseEffects> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BOOLEAN, UseEffects::canSprint,
            // Instruction de code
            NetworkBuffer.BOOLEAN, UseEffects::interactVibrations,
            // Instruction de code
            NetworkBuffer.FLOAT, UseEffects::speedMultiplier,
            // Instruction de code
            UseEffects::new);
    // Affecte une valeur
    public static final Codec<UseEffects> CODEC = StructCodec.struct(
            // Instruction de code
            "can_sprint", Codec.BOOLEAN.optional(false), UseEffects::canSprint,
            // Instruction de code
            "interact_vibrations", Codec.BOOLEAN.optional(true), UseEffects::interactVibrations,
            // Instruction de code
            "speed_multiplier", Codec.FLOAT.optional(0.2f), UseEffects::speedMultiplier,
            // Instruction de code
            UseEffects::new);
// Fin d'un bloc/d'une expression
}
