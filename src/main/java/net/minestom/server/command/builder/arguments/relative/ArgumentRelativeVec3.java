// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.relative;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;

// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * Represents a {@link Vec} with 3 floating numbers (x;y;z) which can take relative coordinates.
 * <p>
 * Example: -1.2 ~ 5
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentRelativeVec3 extends ArgumentRelativeVec {

    // Début d'une méthode/d'un bloc
    public ArgumentRelativeVec3(String id) {
        // Accès à l'objet courant/parent
        super(id, 3);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.VEC3;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("RelativeVec3<%s>", getId());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    Function<String, ? extends Number> getRelativeNumberParser() {
        // Renvoie une valeur à l'appelant
        return Double::parseDouble;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    Function<String, ? extends Number> getAbsoluteNumberParser() {
        // Renvoie une valeur à l'appelant
        return Double::parseDouble;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
