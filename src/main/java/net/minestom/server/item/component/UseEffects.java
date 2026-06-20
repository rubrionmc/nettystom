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

// Type declaration (class/interface/enum/record)
public record UseEffects(
        // Code statement
        boolean canSprint,
        // Code statement
        boolean interactVibrations,
        // Code statement
        float speedMultiplier
// Start of a method/block
) {
    // Calls a method
    public static final UseEffects DEFAULT = new UseEffects(false, true, 0.2f);

    // Assigns a value
    public static final NetworkBuffer.Type<UseEffects> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BOOLEAN, UseEffects::canSprint,
            // Code statement
            NetworkBuffer.BOOLEAN, UseEffects::interactVibrations,
            // Code statement
            NetworkBuffer.FLOAT, UseEffects::speedMultiplier,
            // Code statement
            UseEffects::new);
    // Assigns a value
    public static final Codec<UseEffects> CODEC = StructCodec.struct(
            // Code statement
            "can_sprint", Codec.BOOLEAN.optional(false), UseEffects::canSprint,
            // Code statement
            "interact_vibrations", Codec.BOOLEAN.optional(true), UseEffects::interactVibrations,
            // Code statement
            "speed_multiplier", Codec.FLOAT.optional(0.2f), UseEffects::speedMultiplier,
            // Code statement
            UseEffects::new);
// End of a block/expression
}
