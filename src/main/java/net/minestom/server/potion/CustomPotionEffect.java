// Package declaration for this file
package net.minestom.server.potion;

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

// Import of a required class
import java.util.Objects;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

/**
 * Represents a custom effect in {@link net.minestom.server.component.DataComponents#POTION_CONTENTS}.
 */
// Type declaration (class/interface/enum/record)
public record CustomPotionEffect(PotionEffect id, Settings settings) {

    // Assigns a value
    public static final NetworkBuffer.Type<CustomPotionEffect> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            PotionEffect.NETWORK_TYPE, CustomPotionEffect::id,
            // Code statement
            Settings.NETWORK_TYPE, CustomPotionEffect::settings,
            // Code statement
            CustomPotionEffect::new);
    // Assigns a value
    public static final Codec<CustomPotionEffect> CODEC = StructCodec.struct(
            // Code statement
            "id", PotionEffect.CODEC, CustomPotionEffect::id,
            // Code statement
            StructCodec.INLINE, Settings.CODEC, CustomPotionEffect::settings,
            // Code statement
            CustomPotionEffect::new);

    // Start of a method/block
    public CustomPotionEffect(PotionEffect id, int amplifier, int duration, boolean isAmbient, boolean showParticles, boolean showIcon) {
        // Calls a method
        this(id, new Settings(amplifier, duration, isAmbient, showParticles, showIcon, null));
    // End of a block/expression
    }

    // Start of a method/block
    public int amplifier() {
        // Returns a value to the caller
        return settings.amplifier;
    // End of a block/expression
    }

    // Start of a method/block
    public int duration() {
        // Returns a value to the caller
        return settings.duration;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isAmbient() {
        // Returns a value to the caller
        return settings.isAmbient;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean showParticles() {
        // Returns a value to the caller
        return settings.showParticles;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean showIcon() {
        // Returns a value to the caller
        return settings.showIcon;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Settings(
            // Code statement
            int amplifier, int duration,
            // Code statement
            boolean isAmbient, boolean showParticles, boolean showIcon,
            // Annotation for the following element
            @Nullable Settings hiddenEffect
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<Settings> NETWORK_TYPE = NetworkBuffer.Recursive(self ->
                // Code statement
                NetworkBufferTemplate.template(
                        // Code statement
                        VAR_INT, Settings::amplifier,
                        // Code statement
                        VAR_INT, Settings::duration,
                        // Code statement
                        BOOLEAN, Settings::isAmbient,
                        // Code statement
                        BOOLEAN, Settings::showParticles,
                        // Code statement
                        BOOLEAN, Settings::showIcon,
                        // Code statement
                        self.optional(), Settings::hiddenEffect,
                        // Code statement
                        Settings::new
                // Code statement
                ));
        // Assigns a value
        public static final Codec<Settings> CODEC = Codec.Recursive(self -> StructCodec.struct(
                // Code statement
                "amplifier", Codec.BYTE.optional((byte) 0), s -> (byte) s.amplifier,
                // Code statement
                "duration", Codec.INT.optional(0), Settings::duration,
                // Code statement
                "ambient", Codec.BOOLEAN.optional(false), Settings::isAmbient,
                // Code statement
                "show_particles", Codec.BOOLEAN.optional(true), Settings::showParticles,
                // Code statement
                "show_icon", Codec.BOOLEAN.optional(), Settings::showIcon,
                // Code statement
                "hidden_effect", self.optional(), Settings::hiddenEffect,
                // Code statement
                Settings::withOptionalIcon
        // Code statement
        ));

        // Exists because showIcon needs to default to the value of showParticles which we can't do inline.
        // Code statement
        private static Settings withOptionalIcon(
                // Code statement
                byte amplifier, int duration,
                // Code statement
                boolean isAmbient, boolean showParticles,
                // Annotation for the following element
                @Nullable Boolean showIcon,
                // Annotation for the following element
                @Nullable Settings hiddenEffect
        // Start of a method/block
        ) {
            // Returns a value to the caller
            return new Settings(amplifier, duration, isAmbient, showParticles,
                    // Calls a method
                    Objects.requireNonNullElse(showIcon, showParticles), hiddenEffect);
        // End of a block/expression
        }

    // End of a block/expression
    }

// End of a block/expression
}
