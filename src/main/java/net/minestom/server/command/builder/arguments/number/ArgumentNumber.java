// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.number;

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
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.math.BigDecimal;
// Import d'une classe nécessaire
import java.util.Comparator;
// Import d'une classe nécessaire
import java.util.function.BiFunction;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.regex.Pattern;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentNumber<T extends Number> extends Argument<T> {

    // Affecte une valeur
    public static final int NOT_NUMBER_ERROR = 1;
    // Affecte une valeur
    public static final int TOO_LOW_ERROR = 2;
    // Affecte une valeur
    public static final int TOO_HIGH_ERROR = 3;

    // Instruction de code
    protected boolean hasMin, hasMax;
    // Instruction de code
    protected T min, max;

    // Instruction de code
    protected final ArgumentParserType parserName;
    // Instruction de code
    protected final BiFunction<String, Integer, T> radixParser;
    // Instruction de code
    protected final Function<String, T> parser;
    // Instruction de code
    protected final NetworkBuffer.Type<T> networkType;
    // Instruction de code
    protected final Comparator<T> comparator;

    // Instruction de code
    ArgumentNumber(String id, ArgumentParserType parserName, Function<String, T> parser,
                   // Instruction de code
                   BiFunction<String, Integer, T> radixParser, NetworkBuffer.Type<T> networkType,
                   // Début d'une méthode/d'un bloc
                   Comparator<T> comparator) {
        // Accès à l'objet courant/parent
        super(id);
        // Accès à l'objet courant/parent
        this.parserName = parserName;
        // Accès à l'objet courant/parent
        this.radixParser = radixParser;
        // Accès à l'objet courant/parent
        this.parser = parser;
        // Accès à l'objet courant/parent
        this.networkType = networkType;
        // Accès à l'objet courant/parent
        this.comparator = comparator;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Gestion des exceptions
        try {
            // Instruction de code
            final T value;
            // Appelle une méthode
            final int radix = getRadix(input);
            // Embranchement : vérifie une condition
            if (radix == 10) {
                // Appelle une méthode
                value = parser.apply(parseValue(input));
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                value = radixParser.apply(parseValue(input), radix);
            // Fin d'un bloc/d'une expression
            }

            // Check range
            // Embranchement : vérifie une condition
            if (hasMin && comparator.compare(value, min) < 0) {
                // Lève une exception
                throw new ArgumentSyntaxException("Input is lower than the minimum allowed value", input, TOO_LOW_ERROR);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (hasMax && comparator.compare(value, max) > 0) {
                // Lève une exception
                throw new ArgumentSyntaxException("Input is higher than the maximum allowed value", input, TOO_HIGH_ERROR);
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return value;
        // Début d'une méthode/d'un bloc
        } catch (NumberFormatException | NullPointerException e) {
            // Lève une exception
            throw new ArgumentSyntaxException("Input is not a number, or it's invalid for the given type", input, NOT_NUMBER_ERROR);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return parserName;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte @Nullable [] nodeProperties() {
        // Renvoie une valeur à l'appelant
        return NetworkBuffer.makeArray(buffer -> {
            // Appelle une méthode
            buffer.write(NetworkBuffer.BYTE, getNumberProperties());
            // Embranchement : vérifie une condition
            if (this.hasMin())
                // Appelle une méthode
                networkType.write(buffer, getMin());
            // Embranchement : vérifie une condition
            if (this.hasMax())
                // Appelle une méthode
                networkType.write(buffer, getMax());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ArgumentNumber<T> min(T value) {
        // Accès à l'objet courant/parent
        this.min = value;
        // Accès à l'objet courant/parent
        this.hasMin = true;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ArgumentNumber<T> max(T value) {
        // Accès à l'objet courant/parent
        this.max = value;
        // Accès à l'objet courant/parent
        this.hasMax = true;

        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ArgumentNumber<T> between(T min, T max) {
        // Accès à l'objet courant/parent
        this.min = min;
        // Accès à l'objet courant/parent
        this.max = max;
        // Accès à l'objet courant/parent
        this.hasMin = true;
        // Accès à l'objet courant/parent
        this.hasMax = true;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates the byteflag based on the number's min/max existence.
     *
     * @return A byteflag for argument specification.
     */
    // Début d'une méthode/d'un bloc
    public byte getNumberProperties() {
        // Affecte une valeur
        byte result = 0;
        // Embranchement : vérifie une condition
        if (this.hasMin())
            // Instruction de code
            result |= 0x1;
        // Embranchement : vérifie une condition
        if (this.hasMax())
            // Instruction de code
            result |= 0x2;
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the argument has a minimum.
     *
     * @return true if the argument has a minimum
     */
    // Début d'une méthode/d'un bloc
    public boolean hasMin() {
        // Renvoie une valeur à l'appelant
        return hasMin;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the minimum value for this argument.
     *
     * @return the minimum of this argument
     */
    // Début d'une méthode/d'un bloc
    public T getMin() {
        // Renvoie une valeur à l'appelant
        return min;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the argument has a maximum.
     *
     * @return true if the argument has a maximum
     */
    // Début d'une méthode/d'un bloc
    public boolean hasMax() {
        // Renvoie une valeur à l'appelant
        return hasMax;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the maximum value for this argument.
     *
     * @return the maximum of this argument
     */
    // Début d'une méthode/d'un bloc
    public T getMax() {
        // Renvoie une valeur à l'appelant
        return max;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected String parseValue(String value) {
        // Embranchement : vérifie une condition
        if (value.startsWith("0b")) {
            // Appelle une méthode
            value = value.replaceFirst(Pattern.quote("0b"), "");
        // Embranchement : vérifie une condition
        } else if (value.startsWith("0x")) {
            // Appelle une méthode
            value = value.replaceFirst(Pattern.quote("0x"), "");
        // Embranchement : vérifie une condition
        } else if (value.toLowerCase().contains("e")) {
            // Appelle une méthode
            value = removeScientificNotation(value);
        // Fin d'un bloc/d'une expression
        }
        // TODO number suffix support (k,m,b,t)
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected int getRadix(String value) {
        // Embranchement : vérifie une condition
        if (value.startsWith("0b")) {
            // Renvoie une valeur à l'appelant
            return 2;
        // Embranchement : vérifie une condition
        } else if (value.startsWith("0x")) {
            // Renvoie une valeur à l'appelant
            return 16;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return 10;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    protected String removeScientificNotation(String value) {
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return new BigDecimal(value).toPlainString();
        // Début d'une méthode/d'un bloc
        } catch (NumberFormatException e) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
