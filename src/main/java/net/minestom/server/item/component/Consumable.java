// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.item.ItemAnimation;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record Consumable(
        // Code statement
        float consumeSeconds,
        // Code statement
        ItemAnimation animation,
        // Code statement
        SoundEvent sound,
        // Code statement
        boolean hasConsumeParticles,
        // Code statement
        List<ConsumeEffect> effects
// Start of a method/block
) {
    // Assigns a value
    public static final float DEFAULT_CONSUME_SECONDS = 1.6f;

    // Assigns a value
    public static final NetworkBuffer.Type<Consumable> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.FLOAT, Consumable::consumeSeconds,
            // Code statement
            ItemAnimation.NETWORK_TYPE, Consumable::animation,
            // Code statement
            SoundEvent.NETWORK_TYPE, Consumable::sound,
            // Code statement
            NetworkBuffer.BOOLEAN, Consumable::hasConsumeParticles,
            // Code statement
            ConsumeEffect.NETWORK_TYPE.list(Short.MAX_VALUE), Consumable::effects,
            // Code statement
            Consumable::new);
    // Assigns a value
    public static final Codec<Consumable> CODEC = StructCodec.struct(
            // Code statement
            "consume_seconds", Codec.FLOAT.optional(DEFAULT_CONSUME_SECONDS), Consumable::consumeSeconds,
            // Code statement
            "animation", ItemAnimation.CODEC.optional(ItemAnimation.EAT), Consumable::animation,
            // Code statement
            "sound", SoundEvent.CODEC.optional(SoundEvent.ENTITY_GENERIC_EAT), Consumable::sound,
            // Code statement
            "has_consume_particles", Codec.BOOLEAN.optional(true), Consumable::hasConsumeParticles,
            // Code statement
            "on_consume_effects", ConsumeEffect.CODEC.list().optional(List.of()), Consumable::effects,
            // Code statement
            Consumable::new);

    // Start of a method/block
    public Consumable {
        // Calls a method
        effects = List.copyOf(effects);
    // End of a block/expression
    }

    // Start of a method/block
    public int consumeTicks() {
        // Returns a value to the caller
        return (int) (consumeSeconds * ServerFlag.SERVER_TICKS_PER_SECOND);
    // End of a block/expression
    }
// End of a block/expression
}
