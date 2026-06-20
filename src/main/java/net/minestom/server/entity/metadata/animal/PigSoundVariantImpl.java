// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record PigSoundVariantImpl(
        // Code statement
        PigSoundVariant.PigSoundSet adultSounds,
        // Code statement
        PigSoundVariant.PigSoundSet babySounds
// Start of a method/block
) implements PigSoundVariant {

    // Start of a method/block
    public PigSoundVariantImpl {
        // Calls a method
        Objects.requireNonNull(adultSounds, "adultSounds");
        // Calls a method
        Objects.requireNonNull(babySounds, "babySounds");
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record PigSoundSetImpl(
            // Code statement
            SoundEvent ambientSound,
            // Code statement
            SoundEvent hurtSound,
            // Code statement
            SoundEvent deathSound,
            // Code statement
            SoundEvent stepSound,
            // Code statement
            SoundEvent eatSound
    // Start of a method/block
    ) implements PigSoundVariant.PigSoundSet {
        // Start of a method/block
        public PigSoundSetImpl {
            // Calls a method
            Objects.requireNonNull(ambientSound, "ambientSound");
            // Calls a method
            Objects.requireNonNull(hurtSound, "hurtSound");
            // Calls a method
            Objects.requireNonNull(deathSound, "deathSound");
            // Calls a method
            Objects.requireNonNull(stepSound, "stepSound");
            // Calls a method
            Objects.requireNonNull(eatSound, "eatSound");
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
