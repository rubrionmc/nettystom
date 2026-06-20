// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record CowSoundVariantImpl(
        // Code statement
        CowSoundVariant.CowSoundSet adultSounds
//        CowSoundVariant.CowSoundSet babySounds
// Start of a method/block
) implements CowSoundVariant {

    // Start of a method/block
    public CowSoundVariantImpl {
        // Calls a method
        Objects.requireNonNull(adultSounds, "adultSounds");
//        Objects.requireNonNull(babySounds, "babySounds");
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record CowSoundSetImpl(
            // Code statement
            SoundEvent ambientSound,
            // Code statement
            SoundEvent hurtSound,
            // Code statement
            SoundEvent deathSound,
            // Code statement
            SoundEvent stepSound
    // Start of a method/block
    ) implements CowSoundVariant.CowSoundSet {
        // Start of a method/block
        public CowSoundSetImpl {
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