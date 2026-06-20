// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
record WolfSoundVariantImpl(
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
        SoundEvent whineSound
// Début d'une méthode/d'un bloc
) implements WolfSoundVariant {

    // Début d'une méthode/d'un bloc
    public WolfSoundVariantImpl {
        // Appelle une méthode
        Check.notNull(ambientSound, "Ambient sound");
        // Appelle une méthode
        Check.notNull(deathSound, "Death sound");
        // Appelle une méthode
        Check.notNull(growlSound, "Growl sound");
        // Appelle une méthode
        Check.notNull(hurtSound, "Hurt sound");
        // Appelle une méthode
        Check.notNull(pantSound, "Pant sound");
        // Appelle une méthode
        Check.notNull(whineSound, "Whine sound");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
