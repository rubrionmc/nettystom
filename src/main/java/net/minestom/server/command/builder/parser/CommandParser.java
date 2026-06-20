// Package declaration for this file
package net.minestom.server.command.builder.parser;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.CommandDispatcher;
// Import of a required class
import net.minestom.server.command.builder.CommandSyntax;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Predicate;

// Static import of a member
import static net.minestom.server.command.builder.parser.ArgumentParser.validate;

/**
 * Class used to parse complete command inputs.
 */
// Type declaration (class/interface/enum/record)
public final class CommandParser {

    // Code statement
    private static @Nullable CommandQueryResult recursiveCommandQuery(CommandDispatcher dispatcher,
                                                                      // Code statement
                                                                      List<Command> parents,
                                                                      // Annotation for the following element
                                                                      @Nullable Command parentCommand, String commandName, String[] args) {
        // Calls a method
        Command command = parentCommand == null ? dispatcher.findCommand(commandName) : parentCommand;
        // Branch: checks a condition
        if (command == null) return null;

        // Calls a method
        CommandQueryResult commandQueryResult = new CommandQueryResult(parents, command, commandName, args);
        // Search for subcommand
        // Branch: checks a condition
        if (args.length > 0) {
            // Assigns a value
            final String subCommandName = args[0];
            // Loop: repeats a block
            for (Command subcommand : command.getSubcommands()) {
                // Branch: checks a condition
                if (Command.isValidName(subcommand, subCommandName)) {
                    // Calls a method
                    final String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
                    // Calls a method
                    parents.add(command);
                    // Returns a value to the caller
                    return recursiveCommandQuery(dispatcher, parents, subcommand, subCommandName, subArgs);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return commandQueryResult;
    // End of a block/expression
    }

    // Start of a method/block
    public static @Nullable CommandQueryResult findCommand(CommandDispatcher dispatcher, String input) {
        // Calls a method
        final String[] parts = input.split(StringUtils.SPACE);
        // Assigns a value
        final String commandName = parts[0];

        // Assigns a value
        String[] args = new String[parts.length - 1];
        // Calls a method
        System.arraycopy(parts, 1, args, 0, args.length);
        // Calls a method
        List<Command> parents = new ArrayList<>();
        // Returns a value to the caller
        return recursiveCommandQuery(dispatcher, parents, null, commandName, args);
    // End of a block/expression
    }

    // Code statement
    public static void parse(CommandSender sender, @Nullable CommandSyntax syntax,
                             // Code statement
                             Argument<?>[] commandArguments, String[] inputArguments,
                             // Code statement
                             String commandString,
                             // Annotation for the following element
                             @Nullable List<ValidSyntaxHolder> validSyntaxes,
                             // Annotation for the following element
                             @Nullable Int2ObjectRBTreeMap<CommandSuggestionHolder> syntaxesSuggestions) {
        // Calls a method
        final Map<Argument<?>, ArgumentParser.ArgumentResult> argumentValueMap = new HashMap<>();

        // Assigns a value
        boolean syntaxCorrect = true;
        // The current index in the raw command string arguments
        // Assigns a value
        int inputIndex = 0;

        // Assigns a value
        boolean useRemaining = false;
        // Check the validity of the arguments...
        // Loop: repeats a block
        for (int argIndex = 0; argIndex < commandArguments.length; argIndex++) {
            // Assigns a value
            final Argument<?> argument = commandArguments[argIndex];
            // Calls a method
            ArgumentParser.ArgumentResult argumentResult = validate(sender, argument, commandArguments, argIndex, inputArguments, inputIndex);
            // Branch: checks a condition
            if (argumentResult == null) {
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }

            // Update local var
            // Assigns a value
            useRemaining = argumentResult.useRemaining;
            // Assigns a value
            inputIndex = argumentResult.inputIndex;

            // Branch: checks a condition
            if (argumentResult.correct) {
                // Calls a method
                argumentValueMap.put(argumentResult.argument, argumentResult);
            // Alternative branch of the condition
            } else {
                // Argument is not correct, add it to the syntax suggestion with the number
                // of correct argument(s) and do not check the next syntax argument
                // Assigns a value
                syntaxCorrect = false;
                // Branch: checks a condition
                if (syntaxesSuggestions != null) {
                    // Calls a method
                    syntaxesSuggestions.put(argIndex, new CommandSuggestionHolder(syntax, argumentResult.argumentSyntaxException, argIndex));
                // End of a block/expression
                }
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Add the syntax to the list of valid syntaxes if correct
        // Branch: checks a condition
        if (syntaxCorrect) {
            // Branch: checks a condition
            if (commandArguments.length == argumentValueMap.size() || useRemaining) {
                // Branch: checks a condition
                if (validSyntaxes != null) {
                    // Calls a method
                    validSyntaxes.add(new ValidSyntaxHolder(commandString, syntax, argumentValueMap));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Retrieves from the valid syntax map the arguments condition result and get the one with the most
     * valid arguments.
     *
     * @param validSyntaxes the list containing all the valid syntaxes
     * @param context       the recipient of the argument parsed values
     * @return the command syntax with all of its arguments correct and with the most arguments count, null if not any
     */
    // Annotation for the following element
    @Nullable
    // Code statement
    public static ValidSyntaxHolder findMostCorrectSyntax(List<ValidSyntaxHolder> validSyntaxes,
                                                          // Start of a method/block
                                                          CommandContext context) {
        // Branch: checks a condition
        if (validSyntaxes.isEmpty()) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Assigns a value
        ValidSyntaxHolder finalSyntax = null;
        // Assigns a value
        int maxArguments = 0;
        // Assigns a value
        CommandContext finalContext = null;

        // Loop: repeats a block
        for (ValidSyntaxHolder validSyntaxHolder : validSyntaxes) {
            // Calls a method
            final Map<Argument<?>, ArgumentParser.ArgumentResult> argsValues = validSyntaxHolder.argumentResults();

            // Calls a method
            final int argsSize = argsValues.size();

            // Check if the syntax has more valid arguments
            // Branch: checks a condition
            if (argsSize > maxArguments) {
                // Assigns a value
                finalSyntax = validSyntaxHolder;
                // Assigns a value
                maxArguments = argsSize;

                // Fill arguments map
                // Calls a method
                finalContext = new CommandContext(validSyntaxHolder.commandString());
                // Loop: repeats a block
                for (var entry : argsValues.entrySet()) {
                    // Calls a method
                    final Argument<?> argument = entry.getKey();
                    // Calls a method
                    final ArgumentParser.ArgumentResult argumentResult = entry.getValue();
                    // Calls a method
                    finalContext.setArg(argument.getId(), argumentResult.parsedValue, argumentResult.rawArg);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Get the arguments values
        // Branch: checks a condition
        if (finalSyntax != null) {
            // Calls a method
            context.copy(finalContext);
        // End of a block/expression
        }

        // Returns a value to the caller
        return finalSyntax;
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Code statement
    public static ArgumentQueryResult findEligibleArgument(CommandSender sender,
                                                           // Code statement
                                                           Command command, String[] args, String commandString,
                                                           // Code statement
                                                           boolean trailingSpace, boolean forceCorrect,
                                                           // Code statement
                                                           Predicate<CommandSyntax> syntaxPredicate,
                                                           // Start of a method/block
                                                           Predicate<Argument<?>> argumentPredicate) {
        // Calls a method
        final Collection<CommandSyntax> syntaxes = command.getSyntaxes();

        // Calls a method
        Int2ObjectRBTreeMap<ArgumentQueryResult> suggestions = new Int2ObjectRBTreeMap<>(Collections.reverseOrder());

        // Loop: repeats a block
        for (CommandSyntax syntax : syntaxes) {
            // Branch: checks a condition
            if (!syntaxPredicate.test(syntax)) {
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Calls a method
            final CommandContext context = new CommandContext(commandString);

            // Calls a method
            final Argument<?>[] commandArguments = syntax.getArguments();
            // Assigns a value
            int inputIndex = 0;

            // Assigns a value
            ArgumentQueryResult maxArg = null;
            // Assigns a value
            int maxArgIndex = 0;
            // Loop: repeats a block
            for (int argIndex = 0; argIndex < commandArguments.length; argIndex++) {
                // Assigns a value
                Argument<?> argument = commandArguments[argIndex];
                // Calls a method
                ArgumentParser.ArgumentResult argumentResult = validate(sender, argument, commandArguments, argIndex, args, inputIndex);
                // Branch: checks a condition
                if (argumentResult == null) {
                    // Nothing to analyze, create a dummy object
                    // Calls a method
                    argumentResult = new ArgumentParser.ArgumentResult();
                    // Assigns a value
                    argumentResult.argument = argument;
                    // Assigns a value
                    argumentResult.correct = false;
                    // Assigns a value
                    argumentResult.inputIndex = inputIndex;
                    // Assigns a value
                    argumentResult.rawArg = "";
                // End of a block/expression
                }

                // Update local var
                // Assigns a value
                inputIndex = argumentResult.inputIndex;

                // Branch: checks a condition
                if (argumentResult.correct) {
                    // Fill context
                    // Calls a method
                    context.setArg(argument.getId(), argumentResult.parsedValue, argumentResult.rawArg);
                // End of a block/expression
                }

                // Save result
                // Branch: checks a condition
                if ((!forceCorrect || argumentResult.correct) &&
                        // Start of a method/block
                        argumentPredicate.test(argument)) {
                    // Calls a method
                    maxArg = new ArgumentQueryResult(syntax, argument, context, argumentResult.rawArg);
                    // Assigns a value
                    maxArgIndex = argIndex;
                // End of a block/expression
                }

                // Don't compute following arguments if the syntax is incorrect
                // Branch: checks a condition
                if (!argumentResult.correct) {
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }

                // Don't compute unrelated arguments
                // Assigns a value
                final boolean isLast = inputIndex == args.length;
                // Branch: checks a condition
                if (isLast && !trailingSpace) {
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }

            // End of a block/expression
            }
            // Branch: checks a condition
            if (maxArg != null) {
                // Calls a method
                suggestions.put(maxArgIndex, maxArg);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (suggestions.isEmpty()) {
            // No suggestion
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Calls a method
        final int max = suggestions.firstIntKey();
        // Returns a value to the caller
        return suggestions.get(max);
    // End of a block/expression
    }

// End of a block/expression
}
