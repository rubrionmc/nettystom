// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record ChickenSoundVariantImpl(
        // Instruction de code
        ChickenSoundVariant.ChickenSoundSet adultSounds,
        // Instruction de code
        ChickenSoundVariant.ChickenSoundSet babySounds
// Début d'une méthode/d'un bloc
) implements ChickenSoundVariant {

    // Début d'une méthode/d'un bloc
    public ChickenSoundVariantImpl {
        // Appelle une méthode
        Objects.requireNonNull(adultSounds, "adultSounds");
        // Appelle une méthode
        Objects.requireNonNull(babySounds, "babySounds");
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ChickenSoundSetImpl(
            // Instruction de code
            SoundEvent ambientSound,
            // Instruction de code
            SoundEvent hurtSound,
            // Instruction de code
            SoundEvent deathSound,
            // Instruction de code
            SoundEvent stepSound
    // Début d'une méthode/d'un bloc
    ) implements ChickenSoundVariant.ChickenSoundSet {
        // Début d'une méthode/d'un bloc
        public ChickenSoundSetImpl {
            // Appelle une méthode
            Objects.requireNonNull(ambientSound, "ambientSound");
            // Appelle une méthode
            Objects.requireNonNull(hurtSound, "hurtSound");
            // Appelle une méthode
            Objects.requireNonNull(deathSound, "deathSound");
            // Appelle une méthode
            Objects.requireNonNull(stepSound, "stepSound");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
