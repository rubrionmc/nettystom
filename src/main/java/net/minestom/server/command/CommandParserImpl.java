// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.Graph.Node;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.ArgumentCallback;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandData;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandExecutor;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentWord;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.CommandCondition;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.suggestion.Suggestion;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.suggestion.SuggestionCallback;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Déclaration de type (classe/interface/enum/record)
final class CommandParserImpl implements CommandParser {
    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandParserImpl.class);
    // Appelle une méthode
    static final CommandParserImpl PARSER = new CommandParserImpl();

    // Déclaration de type (classe/interface/enum/record)
    static final class Chain {
        // Annotation pour l'élément suivant
        @Nullable CommandExecutor defaultExecutor = null;
        // Annotation pour l'élément suivant
        @Nullable SuggestionCallback suggestionCallback = null;
        // Affecte une valeur
        final ArrayDeque<NodeResult> nodeResults = new ArrayDeque<>();
        // Affecte une valeur
        final List<CommandCondition> conditions = new ArrayList<>();
        // Affecte une valeur
        final List<CommandExecutor> globalListeners = new ArrayList<>();

        // Début d'une méthode/d'un bloc
        void append(NodeResult result) {
            // Accès à l'objet courant/parent
            this.nodeResults.add(result);
            // Appelle une méthode
            final Graph.Execution execution = result.node.execution();
            // Embranchement : vérifie une condition
            if (execution != null) {
                // Create condition chain
                // Appelle une méthode
                final CommandCondition condition = execution.condition();
                // Embranchement : vérifie une condition
                if (condition != null) conditions.add(condition);

                // Track default executor
                // Appelle une méthode
                final CommandExecutor defExec = execution.defaultExecutor();
                // Embranchement : vérifie une condition
                if (defExec != null) defaultExecutor = defExec;

                // Merge global listeners
                // Appelle une méthode
                final CommandExecutor globalListener = execution.globalListener();
                // Embranchement : vérifie une condition
                if (globalListener != null) globalListeners.add(globalListener);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        CommandCondition mergedConditions() {
            // Renvoie une valeur à l'appelant
            return (sender, commandString) -> {
                // Boucle : répète un bloc
                for (CommandCondition condition : conditions) {
                    // Embranchement : vérifie une condition
                    if (!condition.canUse(sender, commandString)) return false;
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        CommandExecutor mergedGlobalExecutors() {
            // Renvoie une valeur à l'appelant
            return (sender, context) -> globalListeners.forEach(x -> x.apply(sender, context));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        Map<String, ArgumentResult<Object>> collectArguments() {
            // Renvoie une valeur à l'appelant
            return nodeResults.stream()
                    // Instruction de code
                    .skip(2) // skip root node and command
                    // Appelle une méthode
                    .collect(Collectors.toUnmodifiableMap(NodeResult::name, NodeResult::argumentResult));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        List<Argument<?>> getArgs() {
            // Renvoie une valeur à l'appelant
            return nodeResults.stream().map(x -> x.node.argument()).collect(Collectors.toList());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        int size() {
            // Renvoie une valeur à l'appelant
            return nodeResults.size();
        // Fin d'un bloc/d'une expression
        }

        /**
         * Calculates the depth of the chain that is considered successful or valid, providing a more accurate measure
         * for deciding which chain is the most reliable to use. For example a chain that contains the following
         * values [, foo, bar, baz] given the command input "foo bar" will have a successful depth of 2.
         *
         * @return The successful result depth
         * @see #size() getting the size of all results
         */
        // Début d'une méthode/d'un bloc
        int depth() {
            // Affecte une valeur
            int depth = 0;

            // Boucle : répète un bloc
            for (NodeResult node : this.nodeResults) {
                // Embranchement : vérifie une condition
                if (depth++ == 0) {
                    // If we're on the first node, skip it and increment, we don't care about the empty first node
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }

                // If this node isn't a success, we're going to stop counting the depth and stop here
                // Embranchement : vérifie une condition
                if (!(node.argumentResult() instanceof ArgumentResult.Success<?>)) {
                    // Instruction de code
                    depth--;
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // The chain will always contain a empty node at the start, we don't care about it so we'll remove one
            // Renvoie une valeur à l'appelant
            return depth - 1;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the last successful argument result in the chain (breaking if hitting a non-successful result). This
         * method is very similar in how {@link #depth()}'s functions, and is used to get the last successful result
         *
         * @return The last successful result, or null if there isn't a good result to give back, such as if
         * the depth of the chain is zero (containing only an empty node result, or if no node results exist).
         * @see #depth() the depth size of the chain
         * @see #nodeResults all the node results in the chain
         */
        // Annotation pour l'élément suivant
        @Nullable NodeResult lastSuccessfulResult() {
            // Early exit if node results is empty or has only the empty node element
            // Embranchement : vérifie une condition
            if (this.nodeResults.size() <= 1) return null;

            // Affecte une valeur
            NodeResult previousNode = null;
            // Boucle : répète un bloc
            for (NodeResult node : this.nodeResults) {
                // We want to just skip the initial node, we never want to return it
                // Embranchement : vérifie une condition
                if (previousNode == null) {
                    // Affecte une valeur
                    previousNode = node;
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }

                // If this node isn't a success, we're going to stop counting the depth and stop here
                // Embranchement : vérifie une condition
                if (!(node.argumentResult() instanceof ArgumentResult.Success<?>)) {
                    // Renvoie une valeur à l'appelant
                    return previousNode;
                // Fin d'un bloc/d'une expression
                }

                // Affecte une valeur
                previousNode = node;
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return previousNode;
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        Chain() {}

        // Instruction de code
        Chain(@Nullable CommandExecutor defaultExecutor,
              // Annotation pour l'élément suivant
              @Nullable SuggestionCallback suggestionCallback,
              // Instruction de code
              ArrayDeque<NodeResult> nodeResults,
              // Instruction de code
              List<CommandCondition> conditions,
              // Début d'une méthode/d'un bloc
              List<CommandExecutor> globalListeners) {
            // Accès à l'objet courant/parent
            this.defaultExecutor = defaultExecutor;
            // Accès à l'objet courant/parent
            this.suggestionCallback = suggestionCallback;
            // Accès à l'objet courant/parent
            this.nodeResults.addAll(nodeResults);
            // Accès à l'objet courant/parent
            this.conditions.addAll(conditions);
            // Accès à l'objet courant/parent
            this.globalListeners.addAll(globalListeners);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        Chain fork() {
            // Renvoie une valeur à l'appelant
            return new Chain(defaultExecutor, suggestionCallback, nodeResults, conditions, globalListeners);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CommandParser.Result parse(CommandSender sender, Graph graph, String input) {
        // Appelle une méthode
        final CommandStringReader reader = new CommandStringReader(input);
        // Appelle une méthode
        Node parent = graph.root();

        // Appelle une méthode
        NodeResult result = parseNode(sender, parent, new Chain(), reader);
        // Appelle une méthode
        Chain chain = result.chain();

        // Appelle une méthode
        NodeResult lastNodeResult = chain.nodeResults.peekLast();
        // Embranchement : vérifie une condition
        if (lastNodeResult == null) return UnknownCommandResult.INSTANCE;
        // Affecte une valeur
        Node lastNode = lastNodeResult.node;

        // Embranchement : vérifie une condition
        if (result.argumentResult instanceof ArgumentResult.Success<?>) {
            // Appelle une méthode
            CommandExecutor executor = nullSafeGetter(lastNode.execution(), Graph.Execution::executor);
            // Embranchement : vérifie une condition
            if (executor != null) return ValidCommand.executor(input, chain, executor);
        // Fin d'un bloc/d'une expression
        }

        // If here, then the command failed or didn't have an executor, then this isn't a known command
        // Embranchement : vérifie une condition
        if (chain.depth() < 1) return UnknownCommandResult.INSTANCE;

        // Look for a default executor, or give up if we got nowhere
        // Embranchement : vérifie une condition
        if (lastNode.equals(parent)) return UnknownCommandResult.INSTANCE;

        // Appelle une méthode
        final @Nullable ValidCommand defaultExecutor = ValidCommand.defaultExecutor(input, chain);
        // Embranchement : vérifie une condition
        if (defaultExecutor != null) return defaultExecutor;

        // Renvoie une valeur à l'appelant
        return InvalidCommand.invalid(input, chain);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("null, _ -> null; !null, null -> fail; !null, !null -> _")
    // Début d'une méthode/d'un bloc
    private static <R, T> @Nullable R nullSafeGetter(@Nullable T obj, Function<T, R> getter) {
        // Renvoie une valeur à l'appelant
        return obj == null ? null : getter.apply(obj);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static NodeResult parseNode(CommandSender sender, Node node, Chain chain, CommandStringReader reader) {
        // Appelle une méthode
        chain = chain.fork();
        // Appelle une méthode
        Argument<?> argument = node.argument();
        // Appelle une méthode
        int start = reader.cursor();

        // Embranchement : vérifie une condition
        if (reader.hasRemaining()) {
            // Appelle une méthode
            SuggestionCallback suggestionCallback = argument.getSuggestionCallback();
            // Appelle une méthode
            ArgumentResult<?> result = parseArgument(sender, argument, reader);
            // Appelle une méthode
            NodeResult nodeResult = new NodeResult(node, chain, (ArgumentResult<Object>) result, suggestionCallback);
            // Appelle une méthode
            chain.append(nodeResult);
            // Embranchement : vérifie une condition
            if (suggestionCallback != null) chain.suggestionCallback = suggestionCallback;
            // Embranchement : vérifie une condition
            if (chain.size() == 1) { // If this is the root node (usually "Literal<>")
                // Appelle une méthode
                reader.cursor(start);
            // Branche alternative de la condition
            } else {
                // Embranchement : vérifie une condition
                if (!(result instanceof ArgumentResult.Success<?>)) {
                    // Appelle une méthode
                    reader.cursor(start);
                    // Renvoie une valeur à l'appelant
                    return nodeResult;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Nothing left, yet we're still being asked to parse? There must be defaults then
            // Appelle une méthode
            Function<CommandSender, ?> defaultSupplier = node.argument().getDefaultValue();
            // Embranchement : vérifie une condition
            if (defaultSupplier != null) {
                // Appelle une méthode
                Object value = defaultSupplier.apply(sender);
                // Affecte une valeur
                ArgumentResult<Object> argumentResult = new ArgumentResult.Success<>(value, "");
                // Appelle une méthode
                chain.append(new NodeResult(node, chain, argumentResult, argument.getSuggestionCallback()));
                // Add the default to the chain, and then carry on dealing with this node
            // Branche alternative de la condition
            } else {
                // Still being asked to parse yet there's nothing left, syntax error.
                // Renvoie une valeur à l'appelant
                return new NodeResult(
                        // Instruction de code
                        node,
                        // Instruction de code
                        chain,
                        // Crée un nouvel objet
                        new ArgumentResult.SyntaxError<>("Not enough arguments","",-1),
                        // Instruction de code
                        argument.getSuggestionCallback()
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Successfully matched this node's argument
        // Appelle une méthode
        start = reader.cursor();
        // Embranchement : vérifie une condition
        if (!reader.hasRemaining()) start--; // This is needed otherwise the reader throws an AssertionError

        // Affecte une valeur
        NodeResult error = null;
        // Boucle : répète un bloc
        for (Node child : node.next()) {
            // Appelle une méthode
            NodeResult childResult = parseNode(sender, child, chain, reader);
            // Embranchement : vérifie une condition
            if (childResult.argumentResult instanceof ArgumentResult.Success<Object>) {
                // Assume that there is only one successful node for a given chain of arguments
                // Renvoie une valeur à l'appelant
                return childResult;
            // Branche alternative de la condition
            } else {
                // Traverse through the node results to find the last
                // node with a valid argument
                // Appelle une méthode
                final int childDepth = childResult.chain().depth();
                // Appelle une méthode
                final boolean isDeeper = error != null && childDepth > error.chain().depth();

                // Embranchement : vérifie une condition
                if (childDepth > 0 && (error == null || isDeeper)) {
                    // If this is the base argument (e.g. "teleport" in /teleport) then
                    // do not report an argument to be incompatible, since the more
                    // correct thing would be to say that the command is unknown.
                    // Embranchement : vérifie une condition
                    if (!(childResult.chain.size() == 2 && childResult.argumentResult instanceof ArgumentResult.IncompatibleType<?>)) {
                        // If the last successful result is null, throw an exception instead of having unintended behaviour
                        // Appelle une méthode
                        error = Objects.requireNonNull(childResult.chain().lastSuccessfulResult());
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                reader.cursor(start);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // None were successful. Either incompatible types, or syntax error. It doesn't matter to us, though
        // Try to execute this node
        // Appelle une méthode
        CommandExecutor executor = nullSafeGetter(node.execution(), Graph.Execution::executor);
        // Embranchement : vérifie une condition
        if (executor == null) {
            // Stuck here with no executor
            // Embranchement : vérifie une condition
            if (error != null) {
                // Renvoie une valeur à l'appelant
                return error;
            // Branche alternative de la condition
            } else {
                // Renvoie une valeur à l'appelant
                return chain.nodeResults.peekLast();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (reader.hasRemaining()) {
            // Trailing data is a syntax error
            // Can get to here if there's a default executor even if the user is still typing the command
            // So let's supply the next argument's suggestion callback if it exists
            // Affecte une valeur
            Node returnNode = node;
            // Appelle une méthode
            SuggestionCallback suggestionCallback = argument.getSuggestionCallback();
            // Appelle une méthode
            List<Node> nextNodes = node.next();
            // Embranchement : vérifie une condition
            if (!nextNodes.isEmpty()) {
                // Renvoie une valeur à l'appelant
                returnNode = nextNodes.getFirst();
                // Appelle une méthode
                suggestionCallback = returnNode.argument().getSuggestionCallback();
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            NodeResult nodeResult = new NodeResult(
                    // Renvoie une valeur à l'appelant
                    returnNode,
                    // Instruction de code
                    error == null ? chain : error.chain,
                    // Crée un nouvel objet
                    new ArgumentResult.SyntaxError<>("Command has trailing data", "", -1),
                    // Instruction de code
                    suggestionCallback
            // Fin d'un bloc/d'une expression
            );
            // Affecte une valeur
            chain.suggestionCallback = suggestionCallback;
            // prevent duplicates from being added (Fixes CommandParseTest#singleCommandWithMultipleSyntax() failure)
            // Embranchement : vérifie une condition
            if (chain.getArgs().stream().noneMatch(arg -> arg.getId().equals(argument.getId()))) {
                // Appelle une méthode
                chain.append(nodeResult);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return nodeResult;
        // Fin d'un bloc/d'une expression
        }

        // Command was successful!
        // Renvoie une valeur à l'appelant
        return chain.nodeResults.peekLast();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record UnknownCommandResult() implements Result.UnknownCommand {
        // Appelle une méthode
        private static final Result INSTANCE = new UnknownCommandResult();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ExecutableCommand executable() {
            // Renvoie une valeur à l'appelant
            return UnknownExecutableCmd.INSTANCE;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable Suggestion suggestion(CommandSender sender) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<Argument<?>> args() {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface InternalKnownCommand extends Result.KnownCommand {
        // Appelle une méthode
        String input();

        // Annotation pour l'élément suivant
        @Nullable CommandCondition condition();

        // Appelle une méthode
        Map<String, ArgumentResult<Object>> arguments();

        // Appelle une méthode
        CommandExecutor globalListener();

        // Annotation pour l'élément suivant
        @Nullable SuggestionCallback suggestionCallback();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default @Nullable Suggestion suggestion(CommandSender sender) {
            // Appelle une méthode
            final SuggestionCallback callback = suggestionCallback();
            // Embranchement : vérifie une condition
            if (callback == null) return null;
            // Appelle une méthode
            final int lastSpace = input().lastIndexOf(" ");
            // Appelle une méthode
            final Suggestion suggestion = new Suggestion(input(), lastSpace + 2, input().length() - lastSpace - 1);
            // Appelle une méthode
            final CommandContext context = createCommandContext(input(), arguments());
            // Appelle une méthode
            callback.apply(sender, context, suggestion);
            // Renvoie une valeur à l'appelant
            return suggestion;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record InvalidCommand(String input, CommandCondition condition, ArgumentCallback callback,
                          // Instruction de code
                          ArgumentResult.SyntaxError<?> error,
                          // Instruction de code
                          Map<String, ArgumentResult<Object>> arguments, CommandExecutor globalListener,
                          // Annotation pour l'élément suivant
                          @Nullable SuggestionCallback suggestionCallback, List<Argument<?>> args)
            // Début d'une méthode/d'un bloc
            implements InternalKnownCommand, Result.KnownCommand.Invalid {

        // Début d'une méthode/d'un bloc
        static InvalidCommand invalid(String input, Chain chain) {
            // Renvoie une valeur à l'appelant
            return new InvalidCommand(input, chain.mergedConditions(),
                    // Instruction de code
                    null/*todo command syntax callback*/,
                    // Crée un nouvel objet
                    new ArgumentResult.SyntaxError<>("Command has trailing data.", null, -1),
                    // Appelle une méthode
                    chain.collectArguments(), chain.mergedGlobalExecutors(), chain.suggestionCallback, chain.getArgs());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ExecutableCommand executable() {
            // Renvoie une valeur à l'appelant
            return new InvalidExecutableCmd(condition, globalListener, callback, error, input, arguments);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ValidCommand(String input, CommandCondition condition, CommandExecutor executor,
                        // Instruction de code
                        Map<String, ArgumentResult<Object>> arguments,
                        // Instruction de code
                        CommandExecutor globalListener, @Nullable SuggestionCallback suggestionCallback, List<Argument<?>> args)
            // Début d'une méthode/d'un bloc
            implements InternalKnownCommand, Result.KnownCommand.Valid {

        // Début d'une méthode/d'un bloc
        static @Nullable ValidCommand defaultExecutor(String input, Chain chain) {
            // Affecte une valeur
            CommandExecutor defaultExecutor = null;

            // Boucle : répète un bloc
            for (Iterator<NodeResult> it = chain.nodeResults.descendingIterator(); it.hasNext();) {
                // Appelle une méthode
                final NodeResult node = it.next();
                // Appelle une méthode
                defaultExecutor = node.chain().defaultExecutor;
                // Embranchement : vérifie une condition
                if (defaultExecutor != null) break;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (defaultExecutor == null) return null;
            // Renvoie une valeur à l'appelant
            return new ValidCommand(input, chain.mergedConditions(), defaultExecutor, chain.collectArguments(),
                    // Appelle une méthode
                    chain.mergedGlobalExecutors(), chain.suggestionCallback, chain.getArgs());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static ValidCommand executor(String input, Chain chain, CommandExecutor executor) {
            // Renvoie une valeur à l'appelant
            return new ValidCommand(input, chain.mergedConditions(), executor, chain.collectArguments(), chain.mergedGlobalExecutors(),
                    // Appelle une méthode
                    chain.suggestionCallback, chain.getArgs());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ExecutableCommand executable() {
            // Renvoie une valeur à l'appelant
            return new ValidExecutableCmd(condition, globalListener, executor, input, arguments);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record UnknownExecutableCmd() implements ExecutableCommand {
        // Appelle une méthode
        static final ExecutableCommand INSTANCE = new UnknownExecutableCmd();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Result execute(CommandSender sender) {
            // Renvoie une valeur à l'appelant
            return ExecutionResultImpl.UNKNOWN;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ValidExecutableCmd(CommandCondition condition, CommandExecutor globalListener, CommandExecutor executor,
                              // Instruction de code
                              String input,
                              // Début d'une méthode/d'un bloc
                              Map<String, ArgumentResult<Object>> arguments) implements ExecutableCommand {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Result execute(CommandSender sender) {
            // Appelle une méthode
            final CommandContext context = createCommandContext(input, arguments);

            // Appelle une méthode
            globalListener().apply(sender, context);

            // Embranchement : vérifie une condition
            if (condition != null && !condition.canUse(sender, input())) {
                // Renvoie une valeur à l'appelant
                return ExecutionResultImpl.PRECONDITION_FAILED;
            // Fin d'un bloc/d'une expression
            }
            // Gestion des exceptions
            try {
                // Appelle une méthode
                executor().apply(sender, context);
                // Renvoie une valeur à l'appelant
                return new ExecutionResultImpl(ExecutableCommand.Result.Type.SUCCESS, context.getReturnData());
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Appelle une méthode
                LOGGER.error("An exception was encountered while executing command: " + input(), e);
                // Renvoie une valeur à l'appelant
                return ExecutionResultImpl.EXECUTOR_EXCEPTION;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record InvalidExecutableCmd(CommandCondition condition, CommandExecutor globalListener, ArgumentCallback callback,
                                // Instruction de code
                                ArgumentResult.SyntaxError<?> error, String input,
                                // Début d'une méthode/d'un bloc
                                Map<String, ArgumentResult<Object>> arguments) implements ExecutableCommand {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Result execute(CommandSender sender) {
            // Appelle une méthode
            globalListener().apply(sender, createCommandContext(input, arguments));

            // Embranchement : vérifie une condition
            if (condition != null && !condition.canUse(sender, input())) {
                // Renvoie une valeur à l'appelant
                return ExecutionResultImpl.PRECONDITION_FAILED;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (callback != null)
                // Appelle une méthode
                callback.apply(sender, new ArgumentSyntaxException(error.message(), error.input(), error.code()));
            // Renvoie une valeur à l'appelant
            return ExecutionResultImpl.INVALID_SYNTAX;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static CommandContext createCommandContext(String input, Map<String, ArgumentResult<Object>> arguments) {
        // Appelle une méthode
        final CommandContext context = new CommandContext(input);
        // Boucle : répète un bloc
        for (var entry : arguments.entrySet()) {
            // Appelle une méthode
            final String identifier = entry.getKey();
            // Appelle une méthode
            final ArgumentResult<Object> value = entry.getValue();

            // Appelle une méthode
            final Object argOutput = value instanceof ArgumentResult.Success<Object> success ? success.value() : null;
            // Appelle une méthode
            final String argInput = value instanceof ArgumentResult.Success<Object> success ? success.input() : "";

            // Appelle une méthode
            context.setArg(identifier, argOutput, argInput);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return context;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ExecutionResultImpl(Type type, CommandData commandData) implements ExecutableCommand.Result {
        // Appelle une méthode
        static final ExecutableCommand.Result CANCELLED = new ExecutionResultImpl(Type.CANCELLED, null);
        // Appelle une méthode
        static final ExecutableCommand.Result UNKNOWN = new ExecutionResultImpl(Type.UNKNOWN, null);
        // Appelle une méthode
        static final ExecutableCommand.Result EXECUTOR_EXCEPTION = new ExecutionResultImpl(Type.EXECUTOR_EXCEPTION, null);
        // Appelle une méthode
        static final ExecutableCommand.Result PRECONDITION_FAILED = new ExecutionResultImpl(Type.PRECONDITION_FAILED, null);
        // Appelle une méthode
        static final ExecutableCommand.Result INVALID_SYNTAX = new ExecutionResultImpl(Type.INVALID_SYNTAX, null);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record NodeResult(Node node, Chain chain, ArgumentResult<Object> argumentResult, SuggestionCallback callback) {
        // Début d'une méthode/d'un bloc
        public String name() {
            // Renvoie une valeur à l'appelant
            return node.argument().getId();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class CommandStringReader {
        // Instruction de code
        private final String input;
        // Affecte une valeur
        private int cursor = 0;

        // Début d'une méthode/d'un bloc
        CommandStringReader(String input) {
            // Accès à l'objet courant/parent
            this.input = input;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        boolean hasRemaining() {
            // Renvoie une valeur à l'appelant
            return cursor < input.length();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        String readWord() {
            // Affecte une valeur
            final String input = this.input;
            // Affecte une valeur
            final int cursor = this.cursor;

            // Appelle une méthode
            final int i = input.indexOf(' ', cursor);
            // Embranchement : vérifie une condition
            if (i == -1) {
                // Accès à l'objet courant/parent
                this.cursor = input.length() + 1;
                // Renvoie une valeur à l'appelant
                return input.substring(cursor);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final String read = input.substring(cursor, i);
            // Accès à l'objet courant/parent
            this.cursor += read.length() + 1;
            // Renvoie une valeur à l'appelant
            return read;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        String readRemaining() {
            // Affecte une valeur
            final String input = this.input;
            // Appelle une méthode
            final String result = input.substring(cursor);
            // Accès à l'objet courant/parent
            this.cursor = input.length();
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        int cursor() {
            // Renvoie une valeur à l'appelant
            return cursor;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void cursor(int cursor) {
            // Appelle une méthode
            assert cursor >= 0 && cursor <= input.length();
            // Accès à l'objet courant/parent
            this.cursor = cursor;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // ARGUMENT

    // Début d'une méthode/d'un bloc
    private static <T> ArgumentResult<T> parseArgument(CommandSender sender, Argument<T> argument, CommandStringReader reader) {
        // Handle specific type without loop
        // Gestion des exceptions
        try {
            // Single word argument
            // Embranchement : vérifie une condition
            if (!argument.allowSpace()) {
                // Appelle une méthode
                final String word = reader.readWord();
                // Renvoie une valeur à l'appelant
                return new ArgumentResult.Success<>(argument.parse(sender, word), word);
            // Fin d'un bloc/d'une expression
            }
            // Complete input argument
            // Embranchement : vérifie une condition
            if (argument.useRemaining()) {
                // Appelle une méthode
                final String remaining = reader.readRemaining();
                // Renvoie une valeur à l'appelant
                return new ArgumentResult.Success<>(argument.parse(sender, remaining), remaining);
            // Fin d'un bloc/d'une expression
            }
        // Début d'une méthode/d'un bloc
        } catch (ArgumentSyntaxException ignored) {
            // Renvoie une valeur à l'appelant
            return new ArgumentResult.IncompatibleType<>();
        // Fin d'un bloc/d'une expression
        }
        // Bruteforce
        // Appelle une méthode
        assert argument.allowSpace() && !argument.useRemaining();
        // Appelle une méthode
        StringBuilder current = new StringBuilder(reader.readWord());
        // Boucle : répète un bloc
        while (true) {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                final String input = current.toString();
                // Renvoie une valeur à l'appelant
                return new ArgumentResult.Success<>(argument.parse(sender, input), input);
            // Début d'une méthode/d'un bloc
            } catch (ArgumentSyntaxException ignored) {
                // Embranchement : vérifie une condition
                if (!reader.hasRemaining()) break;
                // Appelle une méthode
                current.append(" ");
                // Appelle une méthode
                current.append(reader.readWord());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new ArgumentResult.IncompatibleType<>();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private sealed interface ArgumentResult<R> {
        // Déclaration de type (classe/interface/enum/record)
        record Success<T>(T value, String input)
                // Début d'une méthode/d'un bloc
                implements ArgumentResult<T> {
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record IncompatibleType<T>()
                // Début d'une méthode/d'un bloc
                implements ArgumentResult<T> {
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record SyntaxError<T>(String message, String input, int code)
                // Début d'une méthode/d'un bloc
                implements ArgumentResult<T> {
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
