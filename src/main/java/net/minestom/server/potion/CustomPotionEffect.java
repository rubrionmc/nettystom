// Déclaration du paquet de ce fichier
package net.minestom.server.potion;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

/**
 * Represents a custom effect in {@link net.minestom.server.component.DataComponents#POTION_CONTENTS}.
 */
// Déclaration de type (classe/interface/enum/record)
public record CustomPotionEffect(PotionEffect id, Settings settings) {

    // Affecte une valeur
    public static final NetworkBuffer.Type<CustomPotionEffect> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            PotionEffect.NETWORK_TYPE, CustomPotionEffect::id,
            // Instruction de code
            Settings.NETWORK_TYPE, CustomPotionEffect::settings,
            // Instruction de code
            CustomPotionEffect::new);
    // Affecte une valeur
    public static final Codec<CustomPotionEffect> CODEC = StructCodec.struct(
            // Instruction de code
            "id", PotionEffect.CODEC, CustomPotionEffect::id,
            // Instruction de code
            StructCodec.INLINE, Settings.CODEC, CustomPotionEffect::settings,
            // Instruction de code
            CustomPotionEffect::new);

    // Début d'une méthode/d'un bloc
    public CustomPotionEffect(PotionEffect id, int amplifier, int duration, boolean isAmbient, boolean showParticles, boolean showIcon) {
        // Appelle une méthode
        this(id, new Settings(amplifier, duration, isAmbient, showParticles, showIcon, null));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int amplifier() {
        // Renvoie une valeur à l'appelant
        return settings.amplifier;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int duration() {
        // Renvoie une valeur à l'appelant
        return settings.duration;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isAmbient() {
        // Renvoie une valeur à l'appelant
        return settings.isAmbient;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean showParticles() {
        // Renvoie une valeur à l'appelant
        return settings.showParticles;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean showIcon() {
        // Renvoie une valeur à l'appelant
        return settings.showIcon;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Settings(
            // Instruction de code
            int amplifier, int duration,
            // Instruction de code
            boolean isAmbient, boolean showParticles, boolean showIcon,
            // Annotation pour l'élément suivant
            @Nullable Settings hiddenEffect
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Settings> NETWORK_TYPE = NetworkBuffer.Recursive(self ->
                // Instruction de code
                NetworkBufferTemplate.template(
                        // Instruction de code
                        VAR_INT, Settings::amplifier,
                        // Instruction de code
                        VAR_INT, Settings::duration,
                        // Instruction de code
                        BOOLEAN, Settings::isAmbient,
                        // Instruction de code
                        BOOLEAN, Settings::showParticles,
                        // Instruction de code
                        BOOLEAN, Settings::showIcon,
                        // Instruction de code
                        self.optional(), Settings::hiddenEffect,
                        // Instruction de code
                        Settings::new
                // Instruction de code
                ));
        // Affecte une valeur
        public static final Codec<Settings> CODEC = Codec.Recursive(self -> StructCodec.struct(
                // Instruction de code
                "amplifier", Codec.BYTE.optional((byte) 0), s -> (byte) s.amplifier,
                // Instruction de code
                "duration", Codec.INT.optional(0), Settings::duration,
                // Instruction de code
                "ambient", Codec.BOOLEAN.optional(false), Settings::isAmbient,
                // Instruction de code
                "show_particles", Codec.BOOLEAN.optional(true), Settings::showParticles,
                // Instruction de code
                "show_icon", Codec.BOOLEAN.optional(), Settings::showIcon,
                // Instruction de code
                "hidden_effect", self.optional(), Settings::hiddenEffect,
                // Instruction de code
                Settings::withOptionalIcon
        // Instruction de code
        ));

        // Exists because showIcon needs to default to the value of showParticles which we can't do inline.
        // Instruction de code
        private static Settings withOptionalIcon(
                // Instruction de code
                byte amplifier, int duration,
                // Instruction de code
                boolean isAmbient, boolean showParticles,
                // Annotation pour l'élément suivant
                @Nullable Boolean showIcon,
                // Annotation pour l'élément suivant
                @Nullable Settings hiddenEffect
        // Début d'une méthode/d'un bloc
        ) {
            // Renvoie une valeur à l'appelant
            return new Settings(amplifier, duration, isAmbient, showParticles,
                    // Appelle une méthode
                    Objects.requireNonNullElse(showIcon, showParticles), hiddenEffect);
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
