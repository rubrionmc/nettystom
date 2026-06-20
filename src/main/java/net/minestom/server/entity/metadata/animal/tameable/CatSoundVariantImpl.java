// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record CatSoundVariantImpl(
        // Instruction de code
        CatSoundVariant.CatSoundSet adultSounds,
        // Instruction de code
        CatSoundVariant.CatSoundSet babySounds
// Début d'une méthode/d'un bloc
) implements CatSoundVariant {

    // Début d'une méthode/d'un bloc
    public CatSoundVariantImpl {
        // Appelle une méthode
        Objects.requireNonNull(adultSounds, "adultSounds");
        // Appelle une méthode
        Objects.requireNonNull(babySounds, "babySounds");
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record CatSoundSetImpl(
            // Instruction de code
            SoundEvent ambientSound,
            // Instruction de code
            SoundEvent strayAmbientSound,
            // Instruction de code
            SoundEvent hissSound,
            // Instruction de code
            SoundEvent hurtSound,
            // Instruction de code
            SoundEvent deathSound,
            // Instruction de code
            SoundEvent eatSound,
            // Instruction de code
            SoundEvent begForFoodSound,
            // Instruction de code
            SoundEvent purrSound,
            // Instruction de code
            SoundEvent purreowSound
    // Début d'une méthode/d'un bloc
    ) implements CatSoundVariant.CatSoundSet {

        // Début d'une méthode/d'un bloc
        public CatSoundSetImpl {
            // Appelle une méthode
            Objects.requireNonNull(ambientSound, "ambientSound");
            // Appelle une méthode
            Objects.requireNonNull(strayAmbientSound, "strayAmbientSound");
            // Appelle une méthode
            Objects.requireNonNull(hissSound, "hissSound");
            // Appelle une méthode
            Objects.requireNonNull(hurtSound, "hurtSound");
            // Appelle une méthode
            Objects.requireNonNull(deathSound, "deathSound");
            // Appelle une méthode
            Objects.requireNonNull(eatSound, "eatSound");
            // Appelle une méthode
            Objects.requireNonNull(begForFoodSound, "begForFoodSound");
            // Appelle une méthode
            Objects.requireNonNull(purrSound, "purrSound");
            // Appelle une méthode
            Objects.requireNonNull(purreowSound, "purreowSound");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
