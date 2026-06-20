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
import net.minestom.server.potion.CustomPotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public sealed interface ConsumeEffect {
    // Affecte une valeur
    NetworkBuffer.Type<ConsumeEffect> NETWORK_TYPE = ConsumeEffectType.NETWORK_TYPE
            // Appelle une méthode
            .unionType(ConsumeEffect::networkType, ConsumeEffect::consumeEffectToType);
    // Affecte une valeur
    StructCodec<ConsumeEffect> CODEC = ConsumeEffectType.CODEC
            // Appelle une méthode
            .unionType(ConsumeEffect::codec, ConsumeEffect::consumeEffectToType);

    // Déclaration de type (classe/interface/enum/record)
    record ApplyEffects(List<CustomPotionEffect> effects, float probability) implements ConsumeEffect {
        // Affecte une valeur
        private static final int MAX_EFFECTS = 256;

        // Affecte une valeur
        public static final NetworkBuffer.Type<ApplyEffects> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                CustomPotionEffect.NETWORK_TYPE.list(MAX_EFFECTS), ApplyEffects::effects,
                // Instruction de code
                NetworkBuffer.FLOAT, ApplyEffects::probability,
                // Instruction de code
                ApplyEffects::new);
        // Affecte une valeur
        public static final StructCodec<ApplyEffects> CODEC = StructCodec.struct(
                // Instruction de code
                "effects", CustomPotionEffect.CODEC.list(), ApplyEffects::effects,
                // Instruction de code
                "probability", Codec.FLOAT.optional(1f), ApplyEffects::probability,
                // Instruction de code
                ApplyEffects::new);

        // Début d'une méthode/d'un bloc
        public ApplyEffects {
            // Appelle une méthode
            Check.argCondition(probability < 0 || probability > 1, "Probability must be between 0 and 1");
            // Appelle une méthode
            effects = List.copyOf(effects);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public ApplyEffects(CustomPotionEffect effect, float probability) {
            // Appelle une méthode
            this(List.of(effect), probability);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RemoveEffects(RegistryTag<PotionEffect> effects) implements ConsumeEffect {
        // Affecte une valeur
        public static final NetworkBuffer.Type<RemoveEffects> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                RegistryTag.networkType(Registries::potionEffect), RemoveEffects::effects,
                // Instruction de code
                RemoveEffects::new);
        // Affecte une valeur
        public static final StructCodec<RemoveEffects> CODEC = StructCodec.struct(
                // Instruction de code
                "effects", RegistryTag.codec(Registries::potionEffect), RemoveEffects::effects,
                // Instruction de code
                RemoveEffects::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ClearAllEffects implements ConsumeEffect {
        // Appelle une méthode
        public static final ClearAllEffects INSTANCE = new ClearAllEffects();

        // Appelle une méthode
        public static final NetworkBuffer.Type<ClearAllEffects> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);
        // Appelle une méthode
        public static final StructCodec<ClearAllEffects> CODEC = StructCodec.struct(INSTANCE);

        // Début d'une méthode/d'un bloc
        private ClearAllEffects() {
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TeleportRandomly(float diameter) implements ConsumeEffect {
        // Affecte une valeur
        public static final float DEFAULT_DIAMETER = 16.0f;

        // Affecte une valeur
        public static final NetworkBuffer.Type<TeleportRandomly> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.FLOAT, TeleportRandomly::diameter,
                // Instruction de code
                TeleportRandomly::new);
        // Affecte une valeur
        public static final StructCodec<TeleportRandomly> CODEC = StructCodec.struct(
                // Instruction de code
                "diameter", Codec.FLOAT.optional(DEFAULT_DIAMETER), TeleportRandomly::diameter,
                // Instruction de code
                TeleportRandomly::new);

        // Début d'une méthode/d'un bloc
        public TeleportRandomly() {
            // Appelle une méthode
            this(DEFAULT_DIAMETER);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record PlaySound(SoundEvent sound) implements ConsumeEffect {
        // Affecte une valeur
        public static final NetworkBuffer.Type<PlaySound> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SoundEvent.NETWORK_TYPE, PlaySound::sound,
                // Instruction de code
                PlaySound::new);
        // Affecte une valeur
        public static final StructCodec<PlaySound> CODEC = StructCodec.struct(
                // Instruction de code
                "sound", SoundEvent.CODEC, PlaySound::sound,
                // Instruction de code
                PlaySound::new);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static NetworkBuffer.Type<? extends ConsumeEffect> networkType(ConsumeEffectType type) {
        // Renvoie une valeur à l'appelant
        return switch (type) {
            // Embranchement multiple (switch/case)
            case APPLY_EFFECTS -> ApplyEffects.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case REMOVE_EFFECTS -> RemoveEffects.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case CLEAR_ALL_EFFECTS -> ClearAllEffects.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case TELEPORT_RANDOMLY -> TeleportRandomly.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case PLAY_SOUND -> PlaySound.NETWORK_TYPE;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static StructCodec<? extends ConsumeEffect> codec(ConsumeEffectType type) {
        // Renvoie une valeur à l'appelant
        return switch (type) {
            // Embranchement multiple (switch/case)
            case APPLY_EFFECTS -> ApplyEffects.CODEC;
            // Embranchement multiple (switch/case)
            case REMOVE_EFFECTS -> RemoveEffects.CODEC;
            // Embranchement multiple (switch/case)
            case CLEAR_ALL_EFFECTS -> ClearAllEffects.CODEC;
            // Embranchement multiple (switch/case)
            case TELEPORT_RANDOMLY -> TeleportRandomly.CODEC;
            // Embranchement multiple (switch/case)
            case PLAY_SOUND -> PlaySound.CODEC;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static ConsumeEffectType consumeEffectToType(ConsumeEffect consumeEffect) {
        // Renvoie une valeur à l'appelant
        return switch (consumeEffect) {
            // Embranchement multiple (switch/case)
            case ApplyEffects ignored -> ConsumeEffectType.APPLY_EFFECTS;
            // Embranchement multiple (switch/case)
            case RemoveEffects ignored -> ConsumeEffectType.REMOVE_EFFECTS;
            // Embranchement multiple (switch/case)
            case ClearAllEffects ignored -> ConsumeEffectType.CLEAR_ALL_EFFECTS;
            // Embranchement multiple (switch/case)
            case TeleportRandomly ignored -> ConsumeEffectType.TELEPORT_RANDOMLY;
            // Embranchement multiple (switch/case)
            case PlaySound ignored -> ConsumeEffectType.PLAY_SOUND;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
