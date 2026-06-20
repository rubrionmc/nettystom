// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.jukebox;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
record JukeboxSongImpl(
        // Instruction de code
        SoundEvent soundEvent,
        // Instruction de code
        Component description,
        // Instruction de code
        float lengthInSeconds,
        // Instruction de code
        int comparatorOutput
// Début d'une méthode/d'un bloc
) implements JukeboxSong {

    // Annotation pour l'élément suivant
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Début d'une méthode/d'un bloc
    JukeboxSongImpl {
        // Appelle une méthode
        Check.argCondition(soundEvent == null, "missing sound event");
        // Appelle une méthode
        Check.argCondition(description == null, "missing description");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
