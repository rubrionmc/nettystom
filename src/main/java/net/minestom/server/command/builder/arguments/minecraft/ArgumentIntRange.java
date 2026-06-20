// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.utils.Range;

/**
 * Represents an argument which will give you an {@link Range.Int}.
 * <p>
 * Example: ..3, 3.., 5..10, 15
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentIntRange extends ArgumentRange<Range.Int, Integer> {

    // Début d'une méthode/d'un bloc
    public ArgumentIntRange(String id) {
        // Accès à l'objet courant/parent
        super(id, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer::parseInt, Range.Int::new);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.INT_RANGE;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("IntRange<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
