// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.chars.CharArrayList;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.chars.CharList;
// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;

/**
 * Represents an argument giving a time (day/second/tick).
 * <p>
 * Example: 50d, 25s, 75t
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentTime extends Argument<Duration> {

    // Affecte une valeur
    public static final int INVALID_TIME_FORMAT = -2;
    // Affecte une valeur
    public static final int NO_NUMBER = -3;

    // Appelle une méthode
    private static final CharList SUFFIXES = new CharArrayList(new char[]{'d', 's', 't'});

    // Affecte une valeur
    private int min = 0;

    // Début d'une méthode/d'un bloc
    public ArgumentTime(String id) {
        // Accès à l'objet courant/parent
        super(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ArgumentTime min(int min) {
        // Accès à l'objet courant/parent
        this.min = min;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Duration parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Appelle une méthode
        final char lastChar = input.charAt(input.length() - 1);

        // Instruction de code
        TemporalUnit timeUnit;
        // Embranchement : vérifie une condition
        if (Character.isDigit(lastChar))
            // Affecte une valeur
            timeUnit = TimeUnit.SERVER_TICK;
        // Embranchement : vérifie une condition
        else if (SUFFIXES.contains(lastChar)) {
            // Appelle une méthode
            input = input.substring(0, input.length() - 1);

            // Embranchement : vérifie une condition
            if (lastChar == 'd') {
                // Affecte une valeur
                timeUnit = TimeUnit.DAY;
            // Embranchement : vérifie une condition
            } else if (lastChar == 's') {
                // Affecte une valeur
                timeUnit = TimeUnit.SECOND;
            // Embranchement : vérifie une condition
            } else if (lastChar == 't') {
                // Affecte une valeur
                timeUnit = TimeUnit.SERVER_TICK;
            // Branche alternative de la condition
            } else {
                // Lève une exception
                throw new ArgumentSyntaxException("Time needs to have the unit d, s, t, or none", input, NO_NUMBER);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else
            // Lève une exception
            throw new ArgumentSyntaxException("Time needs to have a unit", input, NO_NUMBER);

        // Gestion des exceptions
        try {
            // Check if value is a number
            // Appelle une méthode
            final int time = Integer.parseInt(input);
            // Renvoie une valeur à l'appelant
            return Duration.of(time, timeUnit);
        // Début d'une méthode/d'un bloc
        } catch (NumberFormatException e) {
            // Lève une exception
            throw new ArgumentSyntaxException("Time needs to be a number", input, NO_NUMBER);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte @Nullable [] nodeProperties() {
        // Renvoie une valeur à l'appelant
        return NetworkBuffer.makeArray(NetworkBuffer.INT, min);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.TIME;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Time<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
