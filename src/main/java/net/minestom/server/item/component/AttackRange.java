// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Type declaration (class/interface/enum/record)
public record AttackRange(
        // Code statement
        float minReach,
        // Code statement
        float maxReach,
        // Code statement
        float minCreativeReach,
        // Code statement
        float maxCreativeReach,
        // Code statement
        float hitboxMargin,
        // Code statement
        float mobFactor
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<AttackRange> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.FLOAT, AttackRange::minReach,
            // Code statement
            NetworkBuffer.FLOAT, AttackRange::maxReach,
            // Code statement
            NetworkBuffer.FLOAT, AttackRange::minCreativeReach,
            // Code statement
            NetworkBuffer.FLOAT, AttackRange::maxCreativeReach,
            // Code statement
            NetworkBuffer.FLOAT, AttackRange::hitboxMargin,
            // Code statement
            NetworkBuffer.FLOAT, AttackRange::mobFactor,
            // Code statement
            AttackRange::new);
    // Assigns a value
    public static final Codec<AttackRange> CODEC = StructCodec.struct(
            // Code statement
            "min_reach", Codec.FLOAT.optional(0f), AttackRange::minReach,
            // Code statement
            "max_reach", Codec.FLOAT.optional(3f), AttackRange::maxReach,
            // Code statement
            "min_creative_reach", Codec.FLOAT.optional(0f), AttackRange::minCreativeReach,
            // Code statement
            "max_creative_reach", Codec.FLOAT.optional(5f), AttackRange::maxCreativeReach,
            // Code statement
            "hitbox_margin", Codec.FLOAT.optional(0.3f), AttackRange::hitboxMargin,
            // Code statement
            "mob_factor", Codec.FLOAT.optional(1f), AttackRange::mobFactor,
            // Code statement
            AttackRange::new);

    // Start of a method/block
    public float effectiveMinReach(Entity entity) {
        // Branch: checks a condition
        if (!(entity instanceof Player player))
            // Returns a value to the caller
            return minReach * mobFactor;
        // Returns a value to the caller
        return switch (player.getGameMode()) {
            // Multiple branching (switch/case)
            case SPECTATOR -> 0f;
            // Multiple branching (switch/case)
            case CREATIVE -> minCreativeReach;
            // Multiple branching (switch/case)
            default -> minReach;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public float effectiveMaxReach(Entity entity) {
        // Branch: checks a condition
        if (!(entity instanceof Player player))
            // Returns a value to the caller
            return maxReach * mobFactor;
        // Returns a value to the caller
        return player.getGameMode() == GameMode.CREATIVE
                // Code statement
                ? maxCreativeReach : maxReach;
    // End of a block/expression
    }
// End of a block/expression
}
