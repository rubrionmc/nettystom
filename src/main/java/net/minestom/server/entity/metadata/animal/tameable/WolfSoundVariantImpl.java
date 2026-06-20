// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record WolfSoundVariantImpl(
        // Instruction de code
        WolfSoundVariant.WolfSoundSet adultSounds,
        // Instruction de code
        WolfSoundVariant.WolfSoundSet babySounds
// Début d'une méthode/d'un bloc
) implements WolfSoundVariant {

    // Début d'une méthode/d'un bloc
    public WolfSoundVariantImpl {
        // Appelle une méthode
        Objects.requireNonNull(adultSounds, "adultSounds");
        // Appelle une méthode
        Objects.requireNonNull(babySounds, "babySounds");
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record WolfSoundSetImpl(
            // Instruction de code
            SoundEvent ambientSound,
            // Instruction de code
            SoundEvent deathSound,
            // Instruction de code
            SoundEvent growlSound,
            // Instruction de code
            SoundEvent hurtSound,
            // Instruction de code
            SoundEvent pantSound,
            // Instruction de code
            SoundEvent whineSound,
            // Instruction de code
            SoundEvent stepSound
    // Début d'une méthode/d'un bloc
    ) implements WolfSoundVariant.WolfSoundSet {
        // Début d'une méthode/d'un bloc
        public WolfSoundSetImpl {
            // Appelle une méthode
            Objects.requireNonNull(ambientSound, "ambientSound");
            // Appelle une méthode
            Objects.requireNonNull(deathSound, "deathSound");
            // Appelle une méthode
            Objects.requireNonNull(growlSound, "growlSound");
            // Appelle une méthode
            Objects.requireNonNull(hurtSound, "hurtSound");
            // Appelle une méthode
            Objects.requireNonNull(pantSound, "pantSound");
            // Appelle une méthode
            Objects.requireNonNull(whineSound, "whineSound");
            // Appelle une méthode
            Objects.requireNonNull(stepSound, "stepSound");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
