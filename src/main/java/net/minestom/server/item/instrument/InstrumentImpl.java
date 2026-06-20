// Déclaration du paquet de ce fichier
package net.minestom.server.item.instrument;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
public record InstrumentImpl(
        // Instruction de code
        SoundEvent soundEvent,
        // Instruction de code
        float useDuration,
        // Instruction de code
        float range,
        // Instruction de code
        Component description
// Début d'une méthode/d'un bloc
) implements Instrument {

    // Annotation pour l'élément suivant
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Début d'une méthode/d'un bloc
    public InstrumentImpl {
        // Appelle une méthode
        Check.argCondition(soundEvent == null, "missing sound event");
        // Appelle une méthode
        Check.argCondition(description == null, "missing description");
        // Appelle une méthode
        Check.argCondition(useDuration <= 0, "use duration must be positive");
        // Appelle une méthode
        Check.argCondition(range <= 0, "range must be positive");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
