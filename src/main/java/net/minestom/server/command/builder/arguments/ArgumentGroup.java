// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.command.builder.parser.CommandParser;
// Import of a required class
import net.minestom.server.command.builder.parser.ValidSyntaxHolder;
// Import of a required class
import net.minestom.server.utils.StringUtils;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class ArgumentGroup extends Argument<CommandContext> {

    // Assigns a value
    public static final int INVALID_ARGUMENTS_ERROR = 1;

    // Code statement
    private final Argument<?>[] group;

    // Start of a method/block
    public ArgumentGroup(String id, Argument<?>... group) {
        // Access to the current/parent object
        super(id, true, false);
        // Access to the current/parent object
        this.group = group;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CommandContext parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Calls a method
        List<ValidSyntaxHolder> validSyntaxes = new ArrayList<>();
        // Calls a method
        CommandParser.parse(sender, null, group, input.split(StringUtils.SPACE), input, validSyntaxes, null);

        // Calls a method
        CommandContext context = new CommandContext(input);
        // Calls a method
        CommandParser.findMostCorrectSyntax(validSyntaxes, context);
        // Branch: checks a condition
        if (validSyntaxes.isEmpty()) {
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid arguments", input, INVALID_ARGUMENTS_ERROR);
        // End of a block/expression
        }

        // Returns a value to the caller
        return context;
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
    public List<Argument<?>> group() {
        // Returns a value to the caller
        return List.of(group);
    // End of a block/expression
    }
// End of a block/expression
}
