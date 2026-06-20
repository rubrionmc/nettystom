// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Annotation pour l'élément suivant
@SuppressWarnings("rawtypes")
// Déclaration de type (classe/interface/enum/record)
public class ArgumentEnum<E extends Enum> extends Argument<E> {

    // Affecte une valeur
    public final static int NOT_ENUM_VALUE_ERROR = 1;

    // Instruction de code
    private final Class<E> enumClass;
    // Instruction de code
    private final E[] values;
    // Affecte une valeur
    private Format format = Format.DEFAULT;

    // Début d'une méthode/d'un bloc
    public ArgumentEnum(String id, Class<E> enumClass) {
        // Accès à l'objet courant/parent
        super(id);
        // Accès à l'objet courant/parent
        this.enumClass = enumClass;
        // Accès à l'objet courant/parent
        this.values = enumClass.getEnumConstants();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ArgumentEnum<E> setFormat(Format format) {
        // Accès à l'objet courant/parent
        this.format = format;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public E parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Boucle : répète un bloc
        for (E value : this.values) {
            // Embranchement : vérifie une condition
            if (this.format.formatter.apply(value.name()).equals(input)) {
                // Renvoie une valeur à l'appelant
                return value;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Lève une exception
        throw new ArgumentSyntaxException("Not a " + this.enumClass.getSimpleName() + " value", input, NOT_ENUM_VALUE_ERROR);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<String> entries() {
        // Renvoie une valeur à l'appelant
        return Arrays.stream(values).map(x -> format.formatter.apply(x.name())).toList();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Format {
        // Instruction de code
        DEFAULT(name -> name),
        // Instruction de code
        LOWER_CASED(name -> name.toLowerCase(Locale.ROOT)),
        // Appelle une méthode
        UPPER_CASED(name -> name.toUpperCase(Locale.ROOT));

        // Instruction de code
        private final UnaryOperator<String> formatter;

        // Début d'une méthode/d'un bloc
        Format(UnaryOperator<String> formatter) {
            // Accès à l'objet courant/parent
            this.formatter = formatter;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Enum<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
