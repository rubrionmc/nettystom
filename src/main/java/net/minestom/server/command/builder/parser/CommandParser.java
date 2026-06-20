// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.parser;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandSyntax;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Import statique d'un membre
import static net.minestom.server.command.builder.parser.ArgumentParser.validate;

/**
 * Class used to parse complete command inputs.
 */
// Déclaration de type (classe/interface/enum/record)
public final class CommandParser {

    // Instruction de code
    private static @Nullable CommandQueryResult recursiveCommandQuery(CommandDispatcher dispatcher,
                                                                      // Instruction de code
                                                                      List<Command> parents,
                                                                      // Annotation pour l'élément suivant
                                                                      @Nullable Command parentCommand, String commandName, String[] args) {
        // Appelle une méthode
        Command command = parentCommand == null ? dispatcher.findCommand(commandName) : parentCommand;
        // Embranchement : vérifie une condition
        if (command == null) return null;

        // Appelle une méthode
        CommandQueryResult commandQueryResult = new CommandQueryResult(parents, command, commandName, args);
        // Search for subcommand
        // Embranchement : vérifie une condition
        if (args.length > 0) {
            // Affecte une valeur
            final String subCommandName = args[0];
            // Boucle : répète un bloc
            for (Command subcommand : command.getSubcommands()) {
                // Embranchement : vérifie une condition
                if (Command.isValidName(subcommand, subCommandName)) {
                    // Appelle une méthode
                    final String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
                    // Appelle une méthode
                    parents.add(command);
                    // Renvoie une valeur à l'appelant
                    return recursiveCommandQuery(dispatcher, parents, subcommand, subCommandName, subArgs);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return commandQueryResult;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @Nullable CommandQueryResult findCommand(CommandDispatcher dispatcher, String input) {
        // Appelle une méthode
        final String[] parts = input.split(StringUtils.SPACE);
        // Affecte une valeur
        final String commandName = parts[0];

        // Affecte une valeur
        String[] args = new String[parts.length - 1];
        // Appelle une méthode
        System.arraycopy(parts, 1, args, 0, args.length);
        // Appelle une méthode
        List<Command> parents = new ArrayList<>();
        // Renvoie une valeur à l'appelant
        return recursiveCommandQuery(dispatcher, parents, null, commandName, args);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static void parse(CommandSender sender, @Nullable CommandSyntax syntax,
                             // Instruction de code
                             Argument<?>[] commandArguments, String[] inputArguments,
                             // Instruction de code
                             String commandString,
                             // Annotation pour l'élément suivant
                             @Nullable List<ValidSyntaxHolder> validSyntaxes,
                             // Annotation pour l'élément suivant
                             @Nullable Int2ObjectRBTreeMap<CommandSuggestionHolder> syntaxesSuggestions) {
        // Appelle une méthode
        final Map<Argument<?>, ArgumentParser.ArgumentResult> argumentValueMap = new HashMap<>();

        // Affecte une valeur
        boolean syntaxCorrect = true;
        // The current index in the raw command string arguments
        // Affecte une valeur
        int inputIndex = 0;

        // Affecte une valeur
        boolean useRemaining = false;
        // Check the validity of the arguments...
        // Boucle : répète un bloc
        for (int argIndex = 0; argIndex < commandArguments.length; argIndex++) {
            // Affecte une valeur
            final Argument<?> argument = commandArguments[argIndex];
            // Appelle une méthode
            ArgumentParser.ArgumentResult argumentResult = validate(sender, argument, commandArguments, argIndex, inputArguments, inputIndex);
            // Embranchement : vérifie une condition
            if (argumentResult == null) {
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }

            // Update local var
            // Affecte une valeur
            useRemaining = argumentResult.useRemaining;
            // Affecte une valeur
            inputIndex = argumentResult.inputIndex;

            // Embranchement : vérifie une condition
            if (argumentResult.correct) {
                // Appelle une méthode
                argumentValueMap.put(argumentResult.argument, argumentResult);
            // Branche alternative de la condition
            } else {
                // Argument is not correct, add it to the syntax suggestion with the number
                // of correct argument(s) and do not check the next syntax argument
                // Affecte une valeur
                syntaxCorrect = false;
                // Embranchement : vérifie une condition
                if (syntaxesSuggestions != null) {
                    // Appelle une méthode
                    syntaxesSuggestions.put(argIndex, new CommandSuggestionHolder(syntax, argumentResult.argumentSyntaxException, argIndex));
                // Fin d'un bloc/d'une expression
                }
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Add the syntax to the list of valid syntaxes if correct
        // Embranchement : vérifie une condition
        if (syntaxCorrect) {
            // Embranchement : vérifie une condition
            if (commandArguments.length == argumentValueMap.size() || useRemaining) {
                // Embranchement : vérifie une condition
                if (validSyntaxes != null) {
                    // Appelle une méthode
                    validSyntaxes.add(new ValidSyntaxHolder(commandString, syntax, argumentValueMap));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Retrieves from the valid syntax map the arguments condition result and get the one with the most
     * valid arguments.
     *
     * @param validSyntaxes the list containing all the valid syntaxes
     * @param context       the recipient of the argument parsed values
     * @return the command syntax with all of its arguments correct and with the most arguments count, null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Instruction de code
    public static ValidSyntaxHolder findMostCorrectSyntax(List<ValidSyntaxHolder> validSyntaxes,
                                                          // Début d'une méthode/d'un bloc
                                                          CommandContext context) {
        // Embranchement : vérifie une condition
        if (validSyntaxes.isEmpty()) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        ValidSyntaxHolder finalSyntax = null;
        // Affecte une valeur
        int maxArguments = 0;
        // Affecte une valeur
        CommandContext finalContext = null;

        // Boucle : répète un bloc
        for (ValidSyntaxHolder validSyntaxHolder : validSyntaxes) {
            // Appelle une méthode
            final Map<Argument<?>, ArgumentParser.ArgumentResult> argsValues = validSyntaxHolder.argumentResults();

            // Appelle une méthode
            final int argsSize = argsValues.size();

            // Check if the syntax has more valid arguments
            // Embranchement : vérifie une condition
            if (argsSize > maxArguments) {
                // Affecte une valeur
                finalSyntax = validSyntaxHolder;
                // Affecte une valeur
                maxArguments = argsSize;

                // Fill arguments map
                // Appelle une méthode
                finalContext = new CommandContext(validSyntaxHolder.commandString());
                // Boucle : répète un bloc
                for (var entry : argsValues.entrySet()) {
                    // Appelle une méthode
                    final Argument<?> argument = entry.getKey();
                    // Appelle une méthode
                    final ArgumentParser.ArgumentResult argumentResult = entry.getValue();
                    // Appelle une méthode
                    finalContext.setArg(argument.getId(), argumentResult.parsedValue, argumentResult.rawArg);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Get the arguments values
        // Embranchement : vérifie une condition
        if (finalSyntax != null) {
            // Appelle une méthode
            context.copy(finalContext);
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return finalSyntax;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Instruction de code
    public static ArgumentQueryResult findEligibleArgument(CommandSender sender,
                                                           // Instruction de code
                                                           Command command, String[] args, String commandString,
                                                           // Instruction de code
                                                           boolean trailingSpace, boolean forceCorrect,
                                                           // Instruction de code
                                                           Predicate<CommandSyntax> syntaxPredicate,
                                                           // Début d'une méthode/d'un bloc
                                                           Predicate<Argument<?>> argumentPredicate) {
        // Appelle une méthode
        final Collection<CommandSyntax> syntaxes = command.getSyntaxes();

        // Appelle une méthode
        Int2ObjectRBTreeMap<ArgumentQueryResult> suggestions = new Int2ObjectRBTreeMap<>(Collections.reverseOrder());

        // Boucle : répète un bloc
        for (CommandSyntax syntax : syntaxes) {
            // Embranchement : vérifie une condition
            if (!syntaxPredicate.test(syntax)) {
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final CommandContext context = new CommandContext(commandString);

            // Appelle une méthode
            final Argument<?>[] commandArguments = syntax.getArguments();
            // Affecte une valeur
            int inputIndex = 0;

            // Affecte une valeur
            ArgumentQueryResult maxArg = null;
            // Affecte une valeur
            int maxArgIndex = 0;
            // Boucle : répète un bloc
            for (int argIndex = 0; argIndex < commandArguments.length; argIndex++) {
                // Affecte une valeur
                Argument<?> argument = commandArguments[argIndex];
                // Appelle une méthode
                ArgumentParser.ArgumentResult argumentResult = validate(sender, argument, commandArguments, argIndex, args, inputIndex);
                // Embranchement : vérifie une condition
                if (argumentResult == null) {
                    // Nothing to analyze, create a dummy object
                    // Appelle une méthode
                    argumentResult = new ArgumentParser.ArgumentResult();
                    // Affecte une valeur
                    argumentResult.argument = argument;
                    // Affecte une valeur
                    argumentResult.correct = false;
                    // Affecte une valeur
                    argumentResult.inputIndex = inputIndex;
                    // Affecte une valeur
                    argumentResult.rawArg = "";
                // Fin d'un bloc/d'une expression
                }

                // Update local var
                // Affecte une valeur
                inputIndex = argumentResult.inputIndex;

                // Embranchement : vérifie une condition
                if (argumentResult.correct) {
                    // Fill context
                    // Appelle une méthode
                    context.setArg(argument.getId(), argumentResult.parsedValue, argumentResult.rawArg);
                // Fin d'un bloc/d'une expression
                }

                // Save result
                // Embranchement : vérifie une condition
                if ((!forceCorrect || argumentResult.correct) &&
                        // Début d'une méthode/d'un bloc
                        argumentPredicate.test(argument)) {
                    // Appelle une méthode
                    maxArg = new ArgumentQueryResult(syntax, argument, context, argumentResult.rawArg);
                    // Affecte une valeur
                    maxArgIndex = argIndex;
                // Fin d'un bloc/d'une expression
                }

                // Don't compute following arguments if the syntax is incorrect
                // Embranchement : vérifie une condition
                if (!argumentResult.correct) {
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }

                // Don't compute unrelated arguments
                // Affecte une valeur
                final boolean isLast = inputIndex == args.length;
                // Embranchement : vérifie une condition
                if (isLast && !trailingSpace) {
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }

            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (maxArg != null) {
                // Appelle une méthode
                suggestions.put(maxArgIndex, maxArg);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (suggestions.isEmpty()) {
            // No suggestion
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final int max = suggestions.firstIntKey();
        // Renvoie une valeur à l'appelant
        return suggestions.get(max);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
