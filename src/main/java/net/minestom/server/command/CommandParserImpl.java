// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.Graph.Node;
// Import of a required class
import net.minestom.server.command.builder.ArgumentCallback;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.CommandData;
// Import of a required class
import net.minestom.server.command.builder.CommandExecutor;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.condition.CommandCondition;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.command.builder.suggestion.Suggestion;
// Import of a required class
import net.minestom.server.command.builder.suggestion.SuggestionCallback;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.stream.Collectors;

// Type declaration (class/interface/enum/record)
final class CommandParserImpl implements CommandParser {
    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandParserImpl.class);
    // Calls a method
    static final CommandParserImpl PARSER = new CommandParserImpl();

    // Type declaration (class/interface/enum/record)
    static final class Chain {
        // Annotation for the following element
        @Nullable CommandExecutor defaultExecutor = null;
        // Annotation for the following element
        @Nullable SuggestionCallback suggestionCallback = null;
        // Calls a method
        final ArrayDeque<NodeResult> nodeResults = new ArrayDeque<>();
        // Calls a method
        final List<CommandCondition> conditions = new ArrayList<>();
        // Calls a method
        final List<CommandExecutor> globalListeners = new ArrayList<>();

        // Start of a method/block
        void append(NodeResult result) {
            // Access to the current/parent object
            this.nodeResults.add(result);
            // Calls a method
            final Graph.Execution execution = result.node.execution();
            // Branch: checks a condition
            if (execution != null) {
                // Create condition chain
                // Calls a method
                final CommandCondition condition = execution.condition();
                // Branch: checks a condition
                if (condition != null) conditions.add(condition);

                // Track default executor
                // Calls a method
                final CommandExecutor defExec = execution.defaultExecutor();
                // Branch: checks a condition
                if (defExec != null) defaultExecutor = defExec;

                // Merge global listeners
                // Calls a method
                final CommandExecutor globalListener = execution.globalListener();
                // Branch: checks a condition
                if (globalListener != null) globalListeners.add(globalListener);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        CommandCondition mergedConditions() {
            // Returns a value to the caller
            return (sender, commandString) -> {
                // Loop: repeats a block
                for (CommandCondition condition : conditions) {
                    // Branch: checks a condition
                    if (!condition.canUse(sender, commandString)) return false;
                // End of a block/expression
                }
                // Returns a value to the caller
                return true;
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        CommandExecutor mergedGlobalExecutors() {
            // Returns a value to the caller
            return (sender, context) -> globalListeners.forEach(x -> x.apply(sender, context));
        // End of a block/expression
        }

        // Start of a method/block
        Map<String, ArgumentResult<Object>> collectArguments() {
            // Returns a value to the caller
            return nodeResults.stream()
                    // Code statement
                    .skip(2) // skip root node and command
                    // Calls a method
                    .collect(Collectors.toUnmodifiableMap(NodeResult::name, NodeResult::argumentResult));
        // End of a block/expression
        }

        // Start of a method/block
        List<Argument<?>> getArgs() {
            // Returns a value to the caller
            return nodeResults.stream().map(x -> x.node.argument()).collect(Collectors.toList());
        // End of a block/expression
        }

        // Start of a method/block
        int size() {
            // Returns a value to the caller
            return nodeResults.size();
        // End of a block/expression
        }

        /**
         * Calculates the depth of the chain that is considered successful or valid, providing a more accurate measure
         * for deciding which chain is the most reliable to use. For example a chain that contains the following
         * values [, foo, bar, baz] given the command input "foo bar" will have a successful depth of 2.
         *
         * @return The successful result depth
         * @see #size() getting the size of all results
         */
        // Start of a method/block
        int depth() {
            // Assigns a value
            int depth = 0;

            // Loop: repeats a block
            for (NodeResult node : this.nodeResults) {
                // Branch: checks a condition
                if (depth++ == 0) {
                    // If we're on the first node, skip it and increment, we don't care about the empty first node
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }

                // If this node isn't a success, we're going to stop counting the depth and stop here
                // Branch: checks a condition
                if (!(node.argumentResult() instanceof ArgumentResult.Success<?>)) {
                    // Code statement
                    depth--;
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }

            // The chain will always contain a empty node at the start, we don't care about it so we'll remove one
            // Returns a value to the caller
            return depth - 1;
        // End of a block/expression
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
        // Annotation for the following element
        @Nullable NodeResult lastSuccessfulResult() {
            // Early exit if node results is empty or has only the empty node element
            // Branch: checks a condition
            if (this.nodeResults.size() <= 1) return null;

            // Assigns a value
            NodeResult previousNode = null;
            // Loop: repeats a block
            for (NodeResult node : this.nodeResults) {
                // We want to just skip the initial node, we never want to return it
                // Branch: checks a condition
                if (previousNode == null) {
                    // Assigns a value
                    previousNode = node;
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }

                // If this node isn't a success, we're going to stop counting the depth and stop here
                // Branch: checks a condition
                if (!(node.argumentResult() instanceof ArgumentResult.Success<?>)) {
                    // Returns a value to the caller
                    return previousNode;
                // End of a block/expression
                }

                // Assigns a value
                previousNode = node;
            // End of a block/expression
            }

            // Returns a value to the caller
            return previousNode;
        // End of a block/expression
        }

        // Start of a method/block
        Chain() {
        // End of a block/expression
        }

        // Code statement
        Chain(@Nullable CommandExecutor defaultExecutor,
              // Annotation for the following element
              @Nullable SuggestionCallback suggestionCallback,
              // Code statement
              ArrayDeque<NodeResult> nodeResults,
              // Code statement
              List<CommandCondition> conditions,
              // Start of a method/block
              List<CommandExecutor> globalListeners) {
            // Access to the current/parent object
            this.defaultExecutor = defaultExecutor;
            // Access to the current/parent object
            this.suggestionCallback = suggestionCallback;
            // Access to the current/parent object
            this.nodeResults.addAll(nodeResults);
            // Access to the current/parent object
            this.conditions.addAll(conditions);
            // Access to the current/parent object
            this.globalListeners.addAll(globalListeners);
        // End of a block/expression
        }

        // Start of a method/block
        Chain fork() {
            // Returns a value to the caller
            return new Chain(defaultExecutor, suggestionCallback, nodeResults, conditions, globalListeners);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CommandParser.Result parse(CommandSender sender, Graph graph, String input) {
        // Calls a method
        final CommandStringReader reader = new CommandStringReader(input);
        // Calls a method
        Node parent = graph.root();

        // Calls a method
        NodeResult result = parseNode(sender, parent, new Chain(), reader);
        // Calls a method
        Chain chain = result.chain();

        // Calls a method
        NodeResult lastNodeResult = chain.nodeResults.peekLast();
        // Branch: checks a condition
        if (lastNodeResult == null) return UnknownCommandResult.INSTANCE;
        // Assigns a value
        Node lastNode = lastNodeResult.node;

        // Branch: checks a condition
        if (result.argumentResult instanceof ArgumentResult.Success<?>) {
            // Calls a method
            CommandExecutor executor = nullSafeGetter(lastNode.execution(), Graph.Execution::executor);
            // Branch: checks a condition
            if (executor != null) return ValidCommand.executor(input, chain, executor);
        // End of a block/expression
        }

        // If here, then the command failed or didn't have an executor, then this isn't a known command
        // Branch: checks a condition
        if (chain.depth() < 1) return UnknownCommandResult.INSTANCE;

        // Look for a default executor, or give up if we got nowhere
        // Branch: checks a condition
        if (lastNode.equals(parent)) return UnknownCommandResult.INSTANCE;

        // Calls a method
        final @Nullable ValidCommand defaultExecutor = ValidCommand.defaultExecutor(input, chain);
        // Branch: checks a condition
        if (defaultExecutor != null) return defaultExecutor;

        // Returns a value to the caller
        return InvalidCommand.invalid(input, chain);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("null, _ -> null; !null, null -> fail; !null, !null -> _")
    // Start of a method/block
    private static <R, T> @Nullable R nullSafeGetter(@Nullable T obj, Function<T, R> getter) {
        // Returns a value to the caller
        return obj == null ? null : getter.apply(obj);
    // End of a block/expression
    }

    // Start of a method/block
    private static NodeResult parseNode(CommandSender sender, Node node, Chain chain, CommandStringReader reader) {
        // Calls a method
        chain = chain.fork();
        // Calls a method
        Argument<?> argument = node.argument();
        // Calls a method
        int start = reader.cursor();

        // Branch: checks a condition
        if (reader.hasRemaining()) {
            // Calls a method
            SuggestionCallback suggestionCallback = argument.getSuggestionCallback();
            // Calls a method
            ArgumentResult<?> result = parseArgument(sender, argument, reader);
            // Calls a method
            NodeResult nodeResult = new NodeResult(node, chain, (ArgumentResult<Object>) result, suggestionCallback);
            // Calls a method
            chain.append(nodeResult);
            // Branch: checks a condition
            if (suggestionCallback != null) chain.suggestionCallback = suggestionCallback;
            // Branch: checks a condition
            if (chain.size() == 1) { // If this is the root node (usually "Literal<>")
                // Calls a method
                reader.cursor(start);
            // Alternative branch of the condition
            } else {
                // Branch: checks a condition
                if (!(result instanceof ArgumentResult.Success<?>)) {
                    // Calls a method
                    reader.cursor(start);
                    // Returns a value to the caller
                    return nodeResult;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Nothing left, yet we're still being asked to parse? There must be defaults then
            // Calls a method
            Function<CommandSender, ?> defaultSupplier = node.argument().getDefaultValue();
            // Branch: checks a condition
            if (defaultSupplier != null) {
                // Calls a method
                Object value = defaultSupplier.apply(sender);
                // Calls a method
                ArgumentResult<Object> argumentResult = new ArgumentResult.Success<>(value, "");
                // Calls a method
                chain.append(new NodeResult(node, chain, argumentResult, argument.getSuggestionCallback()));
                // Add the default to the chain, and then carry on dealing with this node
            // Alternative branch of the condition
            } else {
                // Still being asked to parse yet there's nothing left, syntax error.
                // Returns a value to the caller
                return new NodeResult(
                        // Code statement
                        node,
                        // Code statement
                        chain,
                        // Creates a new object
                        new ArgumentResult.SyntaxError<>("Not enough arguments", "", -1),
                        // Code statement
                        argument.getSuggestionCallback()
                // End of a block/expression
                );
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Successfully matched this node's argument
        // Calls a method
        start = reader.cursor();
        // Branch: checks a condition
        if (!reader.hasRemaining()) start--; // This is needed otherwise the reader throws an AssertionError

        // Assigns a value
        NodeResult error = null;
        // Loop: repeats a block
        for (Node child : node.next()) {
            // Calls a method
            NodeResult childResult = parseNode(sender, child, chain, reader);
            // Branch: checks a condition
            if (childResult.argumentResult instanceof ArgumentResult.Success<Object>) {
                // Assume that there is only one successful node for a given chain of arguments
                // Returns a value to the caller
                return childResult;
            // Alternative branch of the condition
            } else {
                // Traverse through the node results to find the last
                // node with a valid argument
                // Calls a method
                final int childDepth = childResult.chain().depth();
                // Calls a method
                final boolean isDeeper = error != null && childDepth > error.chain().depth();

                // Branch: checks a condition
                if (childDepth > 0 && (error == null || isDeeper)) {
                    // If this is the base argument (e.g. "teleport" in /teleport) then
                    // do not report an argument to be incompatible, since the more
                    // correct thing would be to say that the command is unknown.
                    // Branch: checks a condition
                    if (!(childResult.chain.size() == 2 && childResult.argumentResult instanceof ArgumentResult.IncompatibleType<?>)) {
                        // If the last successful result is null, throw an exception instead of having unintended behaviour
                        // Calls a method
                        error = Objects.requireNonNull(childResult.chain().lastSuccessfulResult());
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                reader.cursor(start);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // None were successful. Either incompatible types, or syntax error. It doesn't matter to us, though
        // Try to execute this node
        // Calls a method
        CommandExecutor executor = nullSafeGetter(node.execution(), Graph.Execution::executor);
        // Branch: checks a condition
        if (executor == null) {
            // Stuck here with no executor
            // Branch: checks a condition
            if (error != null) {
                // Returns a value to the caller
                return error;
            // Alternative branch of the condition
            } else {
                // Returns a value to the caller
                return chain.nodeResults.peekLast();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (reader.hasRemaining()) {
            // Trailing data is a syntax error
            // Can get to here if there's a default executor even if the user is still typing the command
            // So let's supply the next argument's suggestion callback if it exists
            // Assigns a value
            Node returnNode = node;
            // Calls a method
            SuggestionCallback suggestionCallback = argument.getSuggestionCallback();
            // Calls a method
            List<Node> nextNodes = node.next();
            // Branch: checks a condition
            if (!nextNodes.isEmpty()) {
                // Calls a method
                returnNode = nextNodes.getFirst();
                // Calls a method
                suggestionCallback = returnNode.argument().getSuggestionCallback();
            // End of a block/expression
            }
            // Assigns a value
            NodeResult nodeResult = new NodeResult(
                    // Code statement
                    returnNode,
                    // Code statement
                    error == null ? chain : error.chain,
                    // Creates a new object
                    new ArgumentResult.SyntaxError<>("Command has trailing data", "", -1),
                    // Code statement
                    suggestionCallback
            // End of a block/expression
            );
            // Assigns a value
            chain.suggestionCallback = suggestionCallback;
            // prevent duplicates from being added (Fixes CommandParseTest#singleCommandWithMultipleSyntax() failure)
            // Branch: checks a condition
            if (chain.getArgs().stream().noneMatch(arg -> arg.getId().equals(argument.getId()))) {
                // Calls a method
                chain.append(nodeResult);
            // End of a block/expression
            }
            // Returns a value to the caller
            return nodeResult;
        // End of a block/expression
        }

        // Command was successful!
        // Returns a value to the caller
        return chain.nodeResults.peekLast();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record UnknownCommandResult() implements Result.UnknownCommand {
        // Calls a method
        private static final Result INSTANCE = new UnknownCommandResult();

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ExecutableCommand executable() {
            // Returns a value to the caller
            return UnknownExecutableCmd.INSTANCE;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable Suggestion suggestion(CommandSender sender) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<Argument<?>> args() {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface InternalKnownCommand extends Result.KnownCommand {
        // Calls a method
        String input();

        // Annotation for the following element
        @Nullable CommandCondition condition();

        // Calls a method
        Map<String, ArgumentResult<Object>> arguments();

        // Calls a method
        CommandExecutor globalListener();

        // Annotation for the following element
        @Nullable SuggestionCallback suggestionCallback();

        // Annotation for the following element
        @Override
        // Start of a method/block
        default @Nullable Suggestion suggestion(CommandSender sender) {
            // Calls a method
            final SuggestionCallback callback = suggestionCallback();
            // Branch: checks a condition
            if (callback == null) return null;
            // Calls a method
            final int lastSpace = input().lastIndexOf(" ");
            // Calls a method
            final Suggestion suggestion = new Suggestion(input(), lastSpace + 2, input().length() - lastSpace - 1);
            // Calls a method
            final CommandContext context = createCommandContext(input(), arguments());
            // Calls a method
            callback.apply(sender, context, suggestion);
            // Returns a value to the caller
            return suggestion;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record InvalidCommand(String input, CommandCondition condition, ArgumentCallback callback,
                          // Code statement
                          ArgumentResult.SyntaxError<?> error,
                          // Code statement
                          Map<String, ArgumentResult<Object>> arguments, CommandExecutor globalListener,
                          // Annotation for the following element
                          @Nullable SuggestionCallback suggestionCallback, List<Argument<?>> args)
            // Start of a method/block
            implements InternalKnownCommand, Result.KnownCommand.Invalid {

        // Start of a method/block
        static InvalidCommand invalid(String input, Chain chain) {
            // Returns a value to the caller
            return new InvalidCommand(input, chain.mergedConditions(),
                    // Code statement
                    null/*todo command syntax callback*/,
                    // Creates a new object
                    new ArgumentResult.SyntaxError<>("Command has trailing data.", null, -1),
                    // Calls a method
                    chain.collectArguments(), chain.mergedGlobalExecutors(), chain.suggestionCallback, chain.getArgs());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ExecutableCommand executable() {
            // Returns a value to the caller
            return new InvalidExecutableCmd(condition, globalListener, callback, error, input, arguments);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ValidCommand(String input, CommandCondition condition, CommandExecutor executor,
                        // Code statement
                        Map<String, ArgumentResult<Object>> arguments,
                        // Code statement
                        CommandExecutor globalListener, @Nullable SuggestionCallback suggestionCallback,
                        // Code statement
                        List<Argument<?>> args)
            // Start of a method/block
            implements InternalKnownCommand, Result.KnownCommand.Valid {

        // Start of a method/block
        static @Nullable ValidCommand defaultExecutor(String input, Chain chain) {
            // Assigns a value
            CommandExecutor defaultExecutor = null;

            // Loop: repeats a block
            for (Iterator<NodeResult> it = chain.nodeResults.descendingIterator(); it.hasNext(); ) {
                // Calls a method
                final NodeResult node = it.next();
                // Calls a method
                defaultExecutor = node.chain().defaultExecutor;
                // Branch: checks a condition
                if (defaultExecutor != null) break;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (defaultExecutor == null) return null;
            // Returns a value to the caller
            return new ValidCommand(input, chain.mergedConditions(), defaultExecutor, chain.collectArguments(),
                    // Calls a method
                    chain.mergedGlobalExecutors(), chain.suggestionCallback, chain.getArgs());
        // End of a block/expression
        }

        // Start of a method/block
        static ValidCommand executor(String input, Chain chain, CommandExecutor executor) {
            // Returns a value to the caller
            return new ValidCommand(input, chain.mergedConditions(), executor, chain.collectArguments(), chain.mergedGlobalExecutors(),
                    // Calls a method
                    chain.suggestionCallback, chain.getArgs());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ExecutableCommand executable() {
            // Returns a value to the caller
            return new ValidExecutableCmd(condition, globalListener, executor, input, arguments);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record UnknownExecutableCmd() implements ExecutableCommand {
        // Calls a method
        static final ExecutableCommand INSTANCE = new UnknownExecutableCmd();

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Result execute(CommandSender sender) {
            // Returns a value to the caller
            return ExecutionResultImpl.UNKNOWN;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ValidExecutableCmd(CommandCondition condition, CommandExecutor globalListener, CommandExecutor executor,
                              // Code statement
                              String input,
                              // Start of a method/block
                              Map<String, ArgumentResult<Object>> arguments) implements ExecutableCommand {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public Result execute(CommandSender sender) {
            // Calls a method
            final CommandContext context = createCommandContext(input, arguments);

            // Calls a method
            globalListener().apply(sender, context);

            // Branch: checks a condition
            if (condition != null && !condition.canUse(sender, input())) {
                // Returns a value to the caller
                return ExecutionResultImpl.PRECONDITION_FAILED;
            // End of a block/expression
            }
            // Exception handling
            try {
                // Calls a method
                executor().apply(sender, context);
                // Returns a value to the caller
                return new ExecutionResultImpl(ExecutableCommand.Result.Type.SUCCESS, context.getReturnData());
            // Start of a method/block
            } catch (Exception e) {
                // Calls a method
                LOGGER.error("An exception was encountered while executing command: {}", input(), e);
                // Returns a value to the caller
                return ExecutionResultImpl.EXECUTOR_EXCEPTION;
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record InvalidExecutableCmd(CommandCondition condition, CommandExecutor globalListener, ArgumentCallback callback,
                                // Code statement
                                ArgumentResult.SyntaxError<?> error, String input,
                                // Start of a method/block
                                Map<String, ArgumentResult<Object>> arguments) implements ExecutableCommand {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public Result execute(CommandSender sender) {
            // Calls a method
            globalListener().apply(sender, createCommandContext(input, arguments));

            // Branch: checks a condition
            if (condition != null && !condition.canUse(sender, input())) {
                // Returns a value to the caller
                return ExecutionResultImpl.PRECONDITION_FAILED;
            // End of a block/expression
            }
            // Branch: checks a condition
            if (callback != null)
                // Calls a method
                callback.apply(sender, new ArgumentSyntaxException(error.message(), error.input(), error.code()));
            // Returns a value to the caller
            return ExecutionResultImpl.INVALID_SYNTAX;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static CommandContext createCommandContext(String input, Map<String, ArgumentResult<Object>> arguments) {
        // Calls a method
        final CommandContext context = new CommandContext(input);
        // Loop: repeats a block
        for (var entry : arguments.entrySet()) {
            // Calls a method
            final String identifier = entry.getKey();
            // Calls a method
            final ArgumentResult<Object> value = entry.getValue();

            // Calls a method
            final Object argOutput = value instanceof ArgumentResult.Success<Object> success ? success.value() : null;
            // Calls a method
            final String argInput = value instanceof ArgumentResult.Success<Object> success ? success.input() : "";

            // Calls a method
            context.setArg(identifier, argOutput, argInput);
        // End of a block/expression
        }
        // Returns a value to the caller
        return context;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ExecutionResultImpl(Type type, CommandData commandData) implements ExecutableCommand.Result {
        // Calls a method
        static final ExecutableCommand.Result CANCELLED = new ExecutionResultImpl(Type.CANCELLED, null);
        // Calls a method
        static final ExecutableCommand.Result UNKNOWN = new ExecutionResultImpl(Type.UNKNOWN, null);
        // Calls a method
        static final ExecutableCommand.Result EXECUTOR_EXCEPTION = new ExecutionResultImpl(Type.EXECUTOR_EXCEPTION, null);
        // Calls a method
        static final ExecutableCommand.Result PRECONDITION_FAILED = new ExecutionResultImpl(Type.PRECONDITION_FAILED, null);
        // Calls a method
        static final ExecutableCommand.Result INVALID_SYNTAX = new ExecutionResultImpl(Type.INVALID_SYNTAX, null);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record NodeResult(Node node, Chain chain, ArgumentResult<Object> argumentResult,
                              // Start of a method/block
                              SuggestionCallback callback) {
        // Start of a method/block
        public String name() {
            // Returns a value to the caller
            return node.argument().getId();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class CommandStringReader {
        // Code statement
        private final String input;
        // Assigns a value
        private int cursor = 0;

        // Start of a method/block
        CommandStringReader(String input) {
            // Access to the current/parent object
            this.input = input;
        // End of a block/expression
        }

        // Start of a method/block
        boolean hasRemaining() {
            // Returns a value to the caller
            return cursor < input.length();
        // End of a block/expression
        }

        // Start of a method/block
        String readWord() {
            // Assigns a value
            final String input = this.input;
            // Assigns a value
            final int cursor = this.cursor;

            // Calls a method
            final int i = input.indexOf(' ', cursor);
            // Branch: checks a condition
            if (i == -1) {
                // Access to the current/parent object
                this.cursor = input.length() + 1;
                // Returns a value to the caller
                return input.substring(cursor);
            // End of a block/expression
            }
            // Calls a method
            final String read = input.substring(cursor, i);
            // Access to the current/parent object
            this.cursor += read.length() + 1;
            // Returns a value to the caller
            return read;
        // End of a block/expression
        }

        // Start of a method/block
        String readRemaining() {
            // Assigns a value
            final String input = this.input;
            // Calls a method
            final String result = input.substring(cursor);
            // Access to the current/parent object
            this.cursor = input.length();
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Start of a method/block
        int cursor() {
            // Returns a value to the caller
            return cursor;
        // End of a block/expression
        }

        // Start of a method/block
        void cursor(int cursor) {
            // Calls a method
            assert cursor >= 0 && cursor <= input.length();
            // Access to the current/parent object
            this.cursor = cursor;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // ARGUMENT

    // Start of a method/block
    private static <T> ArgumentResult<T> parseArgument(CommandSender sender, Argument<T> argument, CommandStringReader reader) {
        // Handle specific type without loop
        // Exception handling
        try {
            // Single word argument
            // Branch: checks a condition
            if (!argument.allowSpace()) {
                // Calls a method
                final String word = reader.readWord();
                // Returns a value to the caller
                return new ArgumentResult.Success<>(argument.parse(sender, word), word);
            // End of a block/expression
            }
            // Complete input argument
            // Branch: checks a condition
            if (argument.useRemaining()) {
                // Calls a method
                final String remaining = reader.readRemaining();
                // Returns a value to the caller
                return new ArgumentResult.Success<>(argument.parse(sender, remaining), remaining);
            // End of a block/expression
            }
        // Start of a method/block
        } catch (ArgumentSyntaxException ignored) {
            // Returns a value to the caller
            return new ArgumentResult.IncompatibleType<>();
        // End of a block/expression
        }
        // Bruteforce
        // Calls a method
        assert argument.allowSpace() && !argument.useRemaining();
        // Calls a method
        StringBuilder current = new StringBuilder(reader.readWord());
        // Loop: repeats a block
        while (true) {
            // Exception handling
            try {
                // Calls a method
                final String input = current.toString();
                // Returns a value to the caller
                return new ArgumentResult.Success<>(argument.parse(sender, input), input);
            // Start of a method/block
            } catch (ArgumentSyntaxException ignored) {
                // Branch: checks a condition
                if (!reader.hasRemaining()) break;
                // Calls a method
                current.append(" ");
                // Calls a method
                current.append(reader.readWord());
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return new ArgumentResult.IncompatibleType<>();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private sealed interface ArgumentResult<R> {
        // Type declaration (class/interface/enum/record)
        record Success<T>(T value, String input)
                // Start of a method/block
                implements ArgumentResult<T> {
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record IncompatibleType<T>()
                // Start of a method/block
                implements ArgumentResult<T> {
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record SyntaxError<T>(String message, String input, int code)
                // Start of a method/block
                implements ArgumentResult<T> {
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
