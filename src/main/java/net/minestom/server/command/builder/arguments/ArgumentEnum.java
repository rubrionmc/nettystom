// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.function.UnaryOperator;

// Annotation for the following element
@SuppressWarnings("rawtypes")
// Type declaration (class/interface/enum/record)
public class ArgumentEnum<E extends Enum> extends Argument<E> {

    // Assigns a value
    public final static int NOT_ENUM_VALUE_ERROR = 1;

    // Code statement
    private final Class<E> enumClass;
    // Code statement
    private final E[] values;
    // Assigns a value
    private Format format = Format.DEFAULT;

    // Start of a method/block
    public ArgumentEnum(String id, Class<E> enumClass) {
        // Access to the current/parent object
        super(id);
        // Access to the current/parent object
        this.enumClass = enumClass;
        // Access to the current/parent object
        this.values = enumClass.getEnumConstants();
    // End of a block/expression
    }

    // Start of a method/block
    public ArgumentEnum<E> setFormat(Format format) {
        // Access to the current/parent object
        this.format = format;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public E parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Loop: repeats a block
        for (E value : this.values) {
            // Branch: checks a condition
            if (this.format.formatter.apply(value.name()).equals(input)) {
                // Returns a value to the caller
                return value;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Throws an exception
        throw new ArgumentSyntaxException("Not a " + this.enumClass.getSimpleName() + " value", input, NOT_ENUM_VALUE_ERROR);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    public List<String> entries() {
        // Returns a value to the caller
        return Arrays.stream(values).map(x -> format.formatter.apply(x.name())).toList();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Format {
        // Code statement
        DEFAULT(name -> name),
        // Code statement
        LOWER_CASED(name -> name.toLowerCase(Locale.ROOT)),
        // Calls a method
        UPPER_CASED(name -> name.toUpperCase(Locale.ROOT));

        // Code statement
        private final UnaryOperator<String> formatter;

        // Start of a method/block
        Format(UnaryOperator<String> formatter) {
            // Access to the current/parent object
            this.formatter = formatter;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Enum<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
