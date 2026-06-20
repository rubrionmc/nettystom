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
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record UseCooldown(float seconds, @Nullable String cooldownGroup) {
    // Assigns a value
    public static final NetworkBuffer.Type<UseCooldown> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.FLOAT, UseCooldown::seconds,
            // Code statement
            NetworkBuffer.STRING.optional(), UseCooldown::cooldownGroup,
            // Code statement
            UseCooldown::new);
    // Assigns a value
    public static final Codec<UseCooldown> CODEC = StructCodec.struct(
            // Code statement
            "seconds", Codec.FLOAT, UseCooldown::seconds,
            // Code statement
            "cooldown_group", Codec.STRING.optional(), UseCooldown::cooldownGroup,
            // Code statement
            UseCooldown::new);
// End of a block/expression
}
