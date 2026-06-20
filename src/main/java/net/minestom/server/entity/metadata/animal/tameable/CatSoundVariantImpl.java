// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record CatSoundVariantImpl(
        // Code statement
        CatSoundVariant.CatSoundSet adultSounds,
        // Code statement
        CatSoundVariant.CatSoundSet babySounds
// Start of a method/block
) implements CatSoundVariant {

    // Start of a method/block
    public CatSoundVariantImpl {
        // Calls a method
        Objects.requireNonNull(adultSounds, "adultSounds");
        // Calls a method
        Objects.requireNonNull(babySounds, "babySounds");
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record CatSoundSetImpl(
            // Code statement
            SoundEvent ambientSound,
            // Code statement
            SoundEvent strayAmbientSound,
            // Code statement
            SoundEvent hissSound,
            // Code statement
            SoundEvent hurtSound,
            // Code statement
            SoundEvent deathSound,
            // Code statement
            SoundEvent eatSound,
            // Code statement
            SoundEvent begForFoodSound,
            // Code statement
            SoundEvent purrSound,
            // Code statement
            SoundEvent purreowSound
    // Start of a method/block
    ) implements CatSoundVariant.CatSoundSet {

        // Start of a method/block
        public CatSoundSetImpl {
            // Calls a method
            Objects.requireNonNull(ambientSound, "ambientSound");
            // Calls a method
            Objects.requireNonNull(strayAmbientSound, "strayAmbientSound");
            // Calls a method
            Objects.requireNonNull(hissSound, "hissSound");
            // Calls a method
            Objects.requireNonNull(hurtSound, "hurtSound");
            // Calls a method
            Objects.requireNonNull(deathSound, "deathSound");
            // Calls a method
            Objects.requireNonNull(eatSound, "eatSound");
            // Calls a method
            Objects.requireNonNull(begForFoodSound, "begForFoodSound");
            // Calls a method
            Objects.requireNonNull(purrSound, "purrSound");
            // Calls a method
            Objects.requireNonNull(purreowSound, "purreowSound");
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
