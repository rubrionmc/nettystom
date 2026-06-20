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
import net.minestom.server.potion.PotionEffect;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record SuspiciousStewEffects(List<Effect> effects) {
    // Affecte une valeur
    public static final int DEFAULT_DURATION = 160;
    // Appelle une méthode
    public static final SuspiciousStewEffects EMPTY = new SuspiciousStewEffects(List.of());

    // Appelle une méthode
    public static final NetworkBuffer.Type<SuspiciousStewEffects> NETWORK_TYPE = Effect.NETWORK_TYPE.list(Short.MAX_VALUE).transform(SuspiciousStewEffects::new, SuspiciousStewEffects::effects);
    // Appelle une méthode
    public static final Codec<SuspiciousStewEffects> CODEC = Effect.CODEC.list().transform(SuspiciousStewEffects::new, SuspiciousStewEffects::effects);

    // Début d'une méthode/d'un bloc
    public SuspiciousStewEffects {
        // Appelle une méthode
        effects = List.copyOf(effects);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public SuspiciousStewEffects(Effect effect) {
        // Appelle une méthode
        this(List.of(effect));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public SuspiciousStewEffects with(Effect effect) {
        // Affecte une valeur
        List<Effect> newEffects = new ArrayList<>(effects);
        // Appelle une méthode
        newEffects.add(effect);
        // Renvoie une valeur à l'appelant
        return new SuspiciousStewEffects(newEffects);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Effect(PotionEffect id, int durationTicks) {

        // Affecte une valeur
        public static final NetworkBuffer.Type<Effect> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                PotionEffect.NETWORK_TYPE, Effect::id,
                // Instruction de code
                NetworkBuffer.VAR_INT, Effect::durationTicks,
                // Instruction de code
                Effect::new
        // Fin d'un bloc/d'une expression
        );
        // Affecte une valeur
        public static final Codec<Effect> CODEC = StructCodec.struct(
                // Instruction de code
                "id", PotionEffect.CODEC, Effect::id,
                // Instruction de code
                "duration", Codec.INT.optional(DEFAULT_DURATION), Effect::durationTicks,
                // Instruction de code
                Effect::new);

        // Début d'une méthode/d'un bloc
        public Effect(PotionEffect id) {
            // Appelle une méthode
            this(id, DEFAULT_DURATION);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
