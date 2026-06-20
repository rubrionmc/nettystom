// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record PigSoundVariantImpl(
        // Instruction de code
        PigSoundVariant.PigSoundSet adultSounds,
        // Instruction de code
        PigSoundVariant.PigSoundSet babySounds
// Début d'une méthode/d'un bloc
) implements PigSoundVariant {

    // Début d'une méthode/d'un bloc
    public PigSoundVariantImpl {
        // Appelle une méthode
        Objects.requireNonNull(adultSounds, "adultSounds");
        // Appelle une méthode
        Objects.requireNonNull(babySounds, "babySounds");
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record PigSoundSetImpl(
            // Instruction de code
            SoundEvent ambientSound,
            // Instruction de code
            SoundEvent hurtSound,
            // Instruction de code
            SoundEvent deathSound,
            // Instruction de code
            SoundEvent stepSound,
            // Instruction de code
            SoundEvent eatSound
    // Début d'une méthode/d'un bloc
    ) implements PigSoundVariant.PigSoundSet {
        // Début d'une méthode/d'un bloc
        public PigSoundSetImpl {
            // Appelle une méthode
            Objects.requireNonNull(ambientSound, "ambientSound");
            // Appelle une méthode
            Objects.requireNonNull(hurtSound, "hurtSound");
            // Appelle une méthode
            Objects.requireNonNull(deathSound, "deathSound");
            // Appelle une méthode
            Objects.requireNonNull(stepSound, "stepSound");
            // Appelle une méthode
            Objects.requireNonNull(eatSound, "eatSound");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
