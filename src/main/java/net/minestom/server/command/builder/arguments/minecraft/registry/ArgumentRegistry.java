// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft.registry;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Type declaration (class/interface/enum/record)
public abstract class ArgumentRegistry<T> extends Argument<T> {

    // Assigns a value
    public static final int INVALID_NAME = -2;

    // Start of a method/block
    public ArgumentRegistry(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    // Calls a method
    public abstract T getRegistry(String value);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Calls a method
        final T registryValue = getRegistry(input);
        // Branch: checks a condition
        if (registryValue == null)
            // Throws an exception
            throw new ArgumentSyntaxException("Registry value is invalid", input, INVALID_NAME);

        // Returns a value to the caller
        return registryValue;
    // End of a block/expression
    }
// End of a block/expression
}
