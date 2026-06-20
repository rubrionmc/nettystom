// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record WolfSoundVariantImpl(
        // Code statement
        WolfSoundVariant.WolfSoundSet adultSounds,
        // Code statement
        WolfSoundVariant.WolfSoundSet babySounds
// Start of a method/block
) implements WolfSoundVariant {

    // Start of a method/block
    public WolfSoundVariantImpl {
        // Calls a method
        Objects.requireNonNull(adultSounds, "adultSounds");
        // Calls a method
        Objects.requireNonNull(babySounds, "babySounds");
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record WolfSoundSetImpl(
            // Code statement
            SoundEvent ambientSound,
            // Code statement
            SoundEvent deathSound,
            // Code statement
            SoundEvent growlSound,
            // Code statement
            SoundEvent hurtSound,
            // Code statement
            SoundEvent pantSound,
            // Code statement
            SoundEvent whineSound,
            // Code statement
            SoundEvent stepSound
    // Start of a method/block
    ) implements WolfSoundVariant.WolfSoundSet {
        // Start of a method/block
        public WolfSoundSetImpl {
            // Calls a method
            Objects.requireNonNull(ambientSound, "ambientSound");
            // Calls a method
            Objects.requireNonNull(deathSound, "deathSound");
            // Calls a method
            Objects.requireNonNull(growlSound, "growlSound");
            // Calls a method
            Objects.requireNonNull(hurtSound, "hurtSound");
            // Calls a method
            Objects.requireNonNull(pantSound, "pantSound");
            // Calls a method
            Objects.requireNonNull(whineSound, "whineSound");
            // Calls a method
            Objects.requireNonNull(stepSound, "stepSound");
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
