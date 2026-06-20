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
import net.minestom.server.potion.PotionEffect;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record SuspiciousStewEffects(List<Effect> effects) {
    // Assigns a value
    public static final int DEFAULT_DURATION = 160;
    // Calls a method
    public static final SuspiciousStewEffects EMPTY = new SuspiciousStewEffects(List.of());

    // Calls a method
    public static final NetworkBuffer.Type<SuspiciousStewEffects> NETWORK_TYPE = Effect.NETWORK_TYPE.list(Short.MAX_VALUE).transform(SuspiciousStewEffects::new, SuspiciousStewEffects::effects);
    // Calls a method
    public static final Codec<SuspiciousStewEffects> CODEC = Effect.CODEC.list().transform(SuspiciousStewEffects::new, SuspiciousStewEffects::effects);

    // Start of a method/block
    public SuspiciousStewEffects {
        // Calls a method
        effects = List.copyOf(effects);
    // End of a block/expression
    }

    // Start of a method/block
    public SuspiciousStewEffects(Effect effect) {
        // Calls a method
        this(List.of(effect));
    // End of a block/expression
    }

    // Start of a method/block
    public SuspiciousStewEffects with(Effect effect) {
        // Calls a method
        List<Effect> newEffects = new ArrayList<>(effects);
        // Calls a method
        newEffects.add(effect);
        // Returns a value to the caller
        return new SuspiciousStewEffects(newEffects);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Effect(PotionEffect id, int durationTicks) {

        // Assigns a value
        public static final NetworkBuffer.Type<Effect> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                PotionEffect.NETWORK_TYPE, Effect::id,
                // Code statement
                NetworkBuffer.VAR_INT, Effect::durationTicks,
                // Code statement
                Effect::new
        // End of a block/expression
        );
        // Assigns a value
        public static final Codec<Effect> CODEC = StructCodec.struct(
                // Code statement
                "id", PotionEffect.CODEC, Effect::id,
                // Code statement
                "duration", Codec.INT.optional(DEFAULT_DURATION), Effect::durationTicks,
                // Code statement
                Effect::new);

        // Start of a method/block
        public Effect(PotionEffect id) {
            // Calls a method
            this(id, DEFAULT_DURATION);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
