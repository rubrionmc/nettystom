// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record AttackRange(
        // Instruction de code
        float minReach,
        // Instruction de code
        float maxReach,
        // Instruction de code
        float minCreativeReach,
        // Instruction de code
        float maxCreativeReach,
        // Instruction de code
        float hitboxMargin,
        // Instruction de code
        float mobFactor
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<AttackRange> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.FLOAT, AttackRange::minReach,
            // Instruction de code
            NetworkBuffer.FLOAT, AttackRange::maxReach,
            // Instruction de code
            NetworkBuffer.FLOAT, AttackRange::minCreativeReach,
            // Instruction de code
            NetworkBuffer.FLOAT, AttackRange::maxCreativeReach,
            // Instruction de code
            NetworkBuffer.FLOAT, AttackRange::hitboxMargin,
            // Instruction de code
            NetworkBuffer.FLOAT, AttackRange::mobFactor,
            // Instruction de code
            AttackRange::new);
    // Affecte une valeur
    public static final Codec<AttackRange> CODEC = StructCodec.struct(
            // Instruction de code
            "min_reach", Codec.FLOAT.optional(0f), AttackRange::minReach,
            // Instruction de code
            "max_reach", Codec.FLOAT.optional(3f), AttackRange::maxReach,
            // Instruction de code
            "min_creative_reach", Codec.FLOAT.optional(0f), AttackRange::minCreativeReach,
            // Instruction de code
            "max_creative_reach", Codec.FLOAT.optional(5f), AttackRange::maxCreativeReach,
            // Instruction de code
            "hitbox_margin", Codec.FLOAT.optional(0.3f), AttackRange::hitboxMargin,
            // Instruction de code
            "mob_factor", Codec.FLOAT.optional(1f), AttackRange::mobFactor,
            // Instruction de code
            AttackRange::new);

    // Début d'une méthode/d'un bloc
    public float effectiveMinReach(Entity entity) {
        // Embranchement : vérifie une condition
        if (!(entity instanceof Player player))
            // Renvoie une valeur à l'appelant
            return minReach * mobFactor;
        // Renvoie une valeur à l'appelant
        return switch (player.getGameMode()) {
            // Embranchement multiple (switch/case)
            case SPECTATOR -> 0f;
            // Embranchement multiple (switch/case)
            case CREATIVE -> minCreativeReach;
            // Instruction de code
            default -> minReach;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float effectiveMaxReach(Entity entity) {
        // Embranchement : vérifie une condition
        if (!(entity instanceof Player player))
            // Renvoie une valeur à l'appelant
            return maxReach * mobFactor;
        // Renvoie une valeur à l'appelant
        return player.getGameMode() == GameMode.CREATIVE
                // Instruction de code
                ? maxCreativeReach : maxReach;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
