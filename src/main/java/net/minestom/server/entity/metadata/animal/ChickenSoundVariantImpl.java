// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record ChickenSoundVariantImpl(
        // Code statement
        ChickenSoundVariant.ChickenSoundSet adultSounds,
        // Code statement
        ChickenSoundVariant.ChickenSoundSet babySounds
// Start of a method/block
) implements ChickenSoundVariant {

    // Start of a method/block
    public ChickenSoundVariantImpl {
        // Calls a method
        Objects.requireNonNull(adultSounds, "adultSounds");
        // Calls a method
        Objects.requireNonNull(babySounds, "babySounds");
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ChickenSoundSetImpl(
            // Code statement
            SoundEvent ambientSound,
            // Code statement
            SoundEvent hurtSound,
            // Code statement
            SoundEvent deathSound,
            // Code statement
            SoundEvent stepSound
    // Start of a method/block
    ) implements ChickenSoundVariant.ChickenSoundSet {
        // Start of a method/block
        public ChickenSoundSetImpl {
            // Calls a method
            Objects.requireNonNull(ambientSound, "ambientSound");
            // Calls a method
            Objects.requireNonNull(hurtSound, "hurtSound");
            // Calls a method
            Objects.requireNonNull(deathSound, "deathSound");
            // Calls a method
            Objects.requireNonNull(stepSound, "stepSound");
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
