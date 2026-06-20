// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.relative;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.location.RelativeVec;

// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static net.minestom.server.utils.location.RelativeVec.CoordinateType.*;

/**
 * Common interface for all the relative location arguments.
 */
// Déclaration de type (classe/interface/enum/record)
abstract class ArgumentRelativeVec extends Argument<RelativeVec> {

    // Affecte une valeur
    private static final char RELATIVE_CHAR = '~';
    // Affecte une valeur
    private static final char LOCAL_CHAR = '^';

    // Affecte une valeur
    public static final int INVALID_NUMBER_COUNT_ERROR = 1;
    // Affecte une valeur
    public static final int INVALID_NUMBER_ERROR = 2;
    // Affecte une valeur
    public static final int MIXED_TYPE_ERROR = 3;

    // Instruction de code
    private final int numberCount;

    // Début d'une méthode/d'un bloc
    public ArgumentRelativeVec(String id, int numberCount) {
        // Accès à l'objet courant/parent
        super(id, true);
        // Accès à l'objet courant/parent
        this.numberCount = numberCount;
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    abstract Function<String, ? extends Number> getRelativeNumberParser();

    // Appelle une méthode
    abstract Function<String, ? extends Number> getAbsoluteNumberParser();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public RelativeVec parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Appelle une méthode
        final String[] split = input.split(StringUtils.SPACE);
        // Embranchement : vérifie une condition
        if (split.length != getNumberCount()) {
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid number of values", input, INVALID_NUMBER_COUNT_ERROR);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        double[] coordinates = new double[split.length];
        // Affecte une valeur
        boolean[] isRelative = new boolean[split.length];
        // Affecte une valeur
        boolean isLocalType = false;

        // Boucle : répète un bloc
        for (int i = 0; i < split.length; i++) {
            // Affecte une valeur
            final String element = split[i];
            // Gestion des exceptions
            try {
                // Appelle une méthode
                final char modifierChar = element.charAt(0);

                // Embranchement : vérifie une condition
                if (isLocalType && modifierChar != LOCAL_CHAR) {
                    // Lève une exception
                    throw new ArgumentSyntaxException("Cannot mix world & local coordinates (everything must either use ^ or not)", input, MIXED_TYPE_ERROR);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement multiple (switch/case)
                switch (modifierChar) {
                    // Embranchement multiple (switch/case)
                    case LOCAL_CHAR: {
                        // Affecte une valeur
                        isLocalType = true;
                        // Everything in local has to be relative. Fall through.
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement multiple (switch/case)
                    case RELATIVE_CHAR: {
                        // Affecte une valeur
                        isRelative[i] = true;
                        // Embranchement : vérifie une condition
                        if (element.length() == 1) break;
                        // Appelle une méthode
                        final String potentialNumber = element.substring(1);
                        // Appelle une méthode
                        coordinates[i] = getRelativeNumberParser().apply(potentialNumber).doubleValue();
                        // Interrompt la boucle/le bloc
                        break;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement multiple (switch/case)
                    default: {
                        // Appelle une méthode
                        coordinates[i] = getAbsoluteNumberParser().apply(element).doubleValue();
                        // Interrompt la boucle/le bloc
                        break;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Début d'une méthode/d'un bloc
            } catch (NumberFormatException e) {
                // Lève une exception
                throw new ArgumentSyntaxException("Invalid number", input, INVALID_NUMBER_ERROR);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        final boolean xRelative = isRelative[0];
        // Affecte une valeur
        final boolean yRelative = split.length == 3 && isRelative[1];
        // Affecte une valeur
        final boolean zRelative = isRelative[split.length == 3 ? 2 : 1];

        // Instruction de code
        final RelativeVec.CoordinateType type;
        // Embranchement : vérifie une condition
        if (isLocalType) {
            // Affecte une valeur
            type = LOCAL;
        // Embranchement : vérifie une condition
        } else if (xRelative || yRelative || zRelative) {
            // Affecte une valeur
            type = RELATIVE;
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            type = ABSOLUTE;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return new RelativeVec(split.length == 3 ?
                // Crée un nouvel objet
                new Vec(coordinates[0], coordinates[1], coordinates[2]) : new Vec(coordinates[0], coordinates[1]),
                // Instruction de code
                type,
                // Instruction de code
                xRelative, yRelative, zRelative);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the amount of numbers that this relative location needs.
     *
     * @return the amount of coordinate required
     */
    // Début d'une méthode/d'un bloc
    public int getNumberCount() {
        // Renvoie une valeur à l'appelant
        return numberCount;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
