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
import net.minestom.server.potion.CustomPotionEffect;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public sealed interface ConsumeEffect {
    // Assigns a value
    NetworkBuffer.Type<ConsumeEffect> NETWORK_TYPE = ConsumeEffectType.NETWORK_TYPE
            // Calls a method
            .unionType(ConsumeEffect::networkType, ConsumeEffect::consumeEffectToType);
    // Assigns a value
    StructCodec<ConsumeEffect> CODEC = ConsumeEffectType.CODEC
            // Calls a method
            .unionType(ConsumeEffect::codec, ConsumeEffect::consumeEffectToType);

    // Type declaration (class/interface/enum/record)
    record ApplyEffects(List<CustomPotionEffect> effects, float probability) implements ConsumeEffect {
        // Assigns a value
        private static final int MAX_EFFECTS = 256;

        // Assigns a value
        public static final NetworkBuffer.Type<ApplyEffects> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                CustomPotionEffect.NETWORK_TYPE.list(MAX_EFFECTS), ApplyEffects::effects,
                // Code statement
                NetworkBuffer.FLOAT, ApplyEffects::probability,
                // Code statement
                ApplyEffects::new);
        // Assigns a value
        public static final StructCodec<ApplyEffects> CODEC = StructCodec.struct(
                // Code statement
                "effects", CustomPotionEffect.CODEC.list(), ApplyEffects::effects,
                // Code statement
                "probability", Codec.FLOAT.optional(1f), ApplyEffects::probability,
                // Code statement
                ApplyEffects::new);

        // Start of a method/block
        public ApplyEffects {
            // Calls a method
            Check.argCondition(probability < 0 || probability > 1, "Probability must be between 0 and 1");
            // Calls a method
            effects = List.copyOf(effects);
        // End of a block/expression
        }

        // Start of a method/block
        public ApplyEffects(CustomPotionEffect effect, float probability) {
            // Calls a method
            this(List.of(effect), probability);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RemoveEffects(RegistryTag<PotionEffect> effects) implements ConsumeEffect {
        // Assigns a value
        public static final NetworkBuffer.Type<RemoveEffects> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                RegistryTag.networkType(Registries::potionEffect), RemoveEffects::effects,
                // Code statement
                RemoveEffects::new);
        // Assigns a value
        public static final StructCodec<RemoveEffects> CODEC = StructCodec.struct(
                // Code statement
                "effects", RegistryTag.codec(Registries::potionEffect), RemoveEffects::effects,
                // Code statement
                RemoveEffects::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ClearAllEffects implements ConsumeEffect {
        // Calls a method
        public static final ClearAllEffects INSTANCE = new ClearAllEffects();

        // Calls a method
        public static final NetworkBuffer.Type<ClearAllEffects> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);
        // Calls a method
        public static final StructCodec<ClearAllEffects> CODEC = StructCodec.struct(INSTANCE);

        // Start of a method/block
        private ClearAllEffects() {
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TeleportRandomly(float diameter) implements ConsumeEffect {
        // Assigns a value
        public static final float DEFAULT_DIAMETER = 16.0f;

        // Assigns a value
        public static final NetworkBuffer.Type<TeleportRandomly> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.FLOAT, TeleportRandomly::diameter,
                // Code statement
                TeleportRandomly::new);
        // Assigns a value
        public static final StructCodec<TeleportRandomly> CODEC = StructCodec.struct(
                // Code statement
                "diameter", Codec.FLOAT.optional(DEFAULT_DIAMETER), TeleportRandomly::diameter,
                // Code statement
                TeleportRandomly::new);

        // Start of a method/block
        public TeleportRandomly() {
            // Calls a method
            this(DEFAULT_DIAMETER);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record PlaySound(SoundEvent sound) implements ConsumeEffect {
        // Assigns a value
        public static final NetworkBuffer.Type<PlaySound> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SoundEvent.NETWORK_TYPE, PlaySound::sound,
                // Code statement
                PlaySound::new);
        // Assigns a value
        public static final StructCodec<PlaySound> CODEC = StructCodec.struct(
                // Code statement
                "sound", SoundEvent.CODEC, PlaySound::sound,
                // Code statement
                PlaySound::new);
    // End of a block/expression
    }

    // Start of a method/block
    private static NetworkBuffer.Type<? extends ConsumeEffect> networkType(ConsumeEffectType type) {
        // Returns a value to the caller
        return switch (type) {
            // Multiple branching (switch/case)
            case APPLY_EFFECTS -> ApplyEffects.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case REMOVE_EFFECTS -> RemoveEffects.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case CLEAR_ALL_EFFECTS -> ClearAllEffects.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case TELEPORT_RANDOMLY -> TeleportRandomly.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case PLAY_SOUND -> PlaySound.NETWORK_TYPE;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static StructCodec<? extends ConsumeEffect> codec(ConsumeEffectType type) {
        // Returns a value to the caller
        return switch (type) {
            // Multiple branching (switch/case)
            case APPLY_EFFECTS -> ApplyEffects.CODEC;
            // Multiple branching (switch/case)
            case REMOVE_EFFECTS -> RemoveEffects.CODEC;
            // Multiple branching (switch/case)
            case CLEAR_ALL_EFFECTS -> ClearAllEffects.CODEC;
            // Multiple branching (switch/case)
            case TELEPORT_RANDOMLY -> TeleportRandomly.CODEC;
            // Multiple branching (switch/case)
            case PLAY_SOUND -> PlaySound.CODEC;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static ConsumeEffectType consumeEffectToType(ConsumeEffect consumeEffect) {
        // Returns a value to the caller
        return switch (consumeEffect) {
            // Multiple branching (switch/case)
            case ApplyEffects ignored -> ConsumeEffectType.APPLY_EFFECTS;
            // Multiple branching (switch/case)
            case RemoveEffects ignored -> ConsumeEffectType.REMOVE_EFFECTS;
            // Multiple branching (switch/case)
            case ClearAllEffects ignored -> ConsumeEffectType.CLEAR_ALL_EFFECTS;
            // Multiple branching (switch/case)
            case TeleportRandomly ignored -> ConsumeEffectType.TELEPORT_RANDOMLY;
            // Multiple branching (switch/case)
            case PlaySound ignored -> ConsumeEffectType.PLAY_SOUND;
        // End of a block/expression
        };
    // End of a block/expression
    }

// End of a block/expression
}
