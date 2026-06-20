// Déclaration du paquet de ce fichier
package net.minestom.server.sound;

// Déclaration de type (classe/interface/enum/record)
public interface BlockSoundType {

    // Appelle une méthode
    float volume();

    // Appelle une méthode
    float pitch();

    // Appelle une méthode
    SoundEvent breakSound();

    // Appelle une méthode
    SoundEvent hitSound();

    // Appelle une méthode
    SoundEvent fallSound();

    // Appelle une méthode
    SoundEvent placeSound();

    // Appelle une méthode
    SoundEvent stepSound();
// Fin d'un bloc/d'une expression
}
