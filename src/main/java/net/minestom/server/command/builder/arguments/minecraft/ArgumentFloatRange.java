// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.utils.Range;

/**
 * Represents an argument which will give you an {@link Range.Float}.
 * <p>
 * Example: ..3, 3.., 5..10, 15
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentFloatRange extends ArgumentRange<Range.Float, Float> {

    // Début d'une méthode/d'un bloc
    public ArgumentFloatRange(String id) {
        // Accès à l'objet courant/parent
        super(id, -Float.MAX_VALUE, Float.MAX_VALUE, Float::parseFloat, Range.Float::new);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.FLOAT_RANGE;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("FloatRange<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
