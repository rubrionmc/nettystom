// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemAnimation;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record Consumable(
        // Instruction de code
        float consumeSeconds,
        // Instruction de code
        ItemAnimation animation,
        // Instruction de code
        SoundEvent sound,
        // Instruction de code
        boolean hasConsumeParticles,
        // Instruction de code
        List<ConsumeEffect> effects
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final float DEFAULT_CONSUME_SECONDS = 1.6f;

    // Affecte une valeur
    public static final NetworkBuffer.Type<Consumable> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.FLOAT, Consumable::consumeSeconds,
            // Instruction de code
            ItemAnimation.NETWORK_TYPE, Consumable::animation,
            // Instruction de code
            SoundEvent.NETWORK_TYPE, Consumable::sound,
            // Instruction de code
            NetworkBuffer.BOOLEAN, Consumable::hasConsumeParticles,
            // Instruction de code
            ConsumeEffect.NETWORK_TYPE.list(Short.MAX_VALUE), Consumable::effects,
            // Instruction de code
            Consumable::new);
    // Affecte une valeur
    public static final Codec<Consumable> CODEC = StructCodec.struct(
            // Instruction de code
            "consume_seconds", Codec.FLOAT.optional(DEFAULT_CONSUME_SECONDS), Consumable::consumeSeconds,
            // Instruction de code
            "animation", ItemAnimation.CODEC.optional(ItemAnimation.EAT), Consumable::animation,
            // Instruction de code
            "sound", SoundEvent.CODEC.optional(SoundEvent.ENTITY_GENERIC_EAT), Consumable::sound,
            // Instruction de code
            "has_consume_particles", Codec.BOOLEAN.optional(true), Consumable::hasConsumeParticles,
            // Instruction de code
            "on_consume_effects", ConsumeEffect.CODEC.list().optional(List.of()), Consumable::effects,
            // Instruction de code
            Consumable::new);

    // Début d'une méthode/d'un bloc
    public Consumable {
        // Appelle une méthode
        effects = List.copyOf(effects);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int consumeTicks() {
        // Renvoie une valeur à l'appelant
        return (int) (consumeSeconds * ServerFlag.SERVER_TICKS_PER_SECOND);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
