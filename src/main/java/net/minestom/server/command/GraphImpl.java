// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandExecutor;
// Import of a required class
import net.minestom.server.command.builder.CommandSyntax;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentCommand;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import of a required class
import net.minestom.server.command.builder.condition.CommandCondition;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Predicate;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Word;

// Type declaration (class/interface/enum/record)
record GraphImpl(NodeImpl root) implements Graph {
    // Start of a method/block
    static GraphImpl fromCommand(Command command) {
        // Returns a value to the caller
        return new GraphImpl(NodeImpl.command(command));
    // End of a block/expression
    }

    // Start of a method/block
    static Graph merge(Collection<Command> commands) {
        // Returns a value to the caller
        return new GraphImpl(NodeImpl.rootCommands(commands));
    // End of a block/expression
    }

    // Start of a method/block
    static GraphImpl merge(List<Graph> graphs) {
        // Calls a method
        final List<Node> children = graphs.stream().map(Graph::root).toList();
        // Calls a method
        final NodeImpl root = new NodeImpl(Literal(""), null, children);
        // Returns a value to the caller
        return new GraphImpl(root);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean compare(Graph graph, Comparator comparator) {
        // Returns a value to the caller
        return compare(root, graph.root(), comparator);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record BuilderImpl(Argument<?> argument, List<BuilderImpl> children, Execution execution) implements Graph.Builder {
        // Start of a method/block
        public BuilderImpl(Argument<?> argument, Execution execution) {
            // Calls a method
            this(argument, new ArrayList<>(), execution);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Code statement
        public Graph.Builder append(
                // Code statement
                Argument<?> argument, @Nullable Execution execution,
                // Code statement
                Consumer<Graph.Builder> consumer
        // Start of a method/block
        ) {
            // Calls a method
            BuilderImpl builder = new BuilderImpl(argument, execution);
            // Calls a method
            consumer.accept(builder);
            // Access to the current/parent object
            this.children.add(builder);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Graph.Builder append(Argument<?> argument, @Nullable Execution execution) {
            // Access to the current/parent object
            this.children.add(new BuilderImpl(argument, List.of(), execution));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public GraphImpl build() {
            // Returns a value to the caller
            return new GraphImpl(NodeImpl.fromBuilder(this));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record NodeImpl(Argument<?> argument, ExecutionImpl execution, List<Graph.Node> next) implements Graph.Node {
        // Start of a method/block
        NodeImpl(Argument<?> argument, ExecutionImpl execution, List<Graph.Node> next) {
            // Access to the current/parent object
            this.argument = argument;
            // Access to the current/parent object
            this.execution = execution;
            // Access to the current/parent object
            this.next = next.stream().sorted(nodePriority).toList();
        // End of a block/expression
        }

        // Start of a method/block
        static NodeImpl fromBuilder(BuilderImpl builder) {
            // Assigns a value
            final List<BuilderImpl> children = builder.children;
            // Calls a method
            Node[] nodes = new NodeImpl[children.size()];
            // Loop: repeats a block
            for (int i = 0; i < children.size(); i++) nodes[i] = fromBuilder(children.get(i));
            // Returns a value to the caller
            return new NodeImpl(builder.argument, (ExecutionImpl) builder.execution, List.of(nodes));
        // End of a block/expression
        }

        // Start of a method/block
        static NodeImpl command(Command command) {
            // Returns a value to the caller
            return ConversionNode.fromCommand(command).toNode();
        // End of a block/expression
        }

        // Start of a method/block
        static NodeImpl rootCommands(Collection<Command> commands) {
            // Returns a value to the caller
            return ConversionNode.rootConv(commands).toNode();
        // End of a block/expression
        }

        // Assigns a value
        private static final java.util.Comparator<Node> nodePriority = (node1, node2) -> {
            // Calls a method
            int node1Value = argumentValue(node1.argument());
            // Calls a method
            int node2Value = argumentValue(node2.argument());
            // Returns a value to the caller
            return Integer.compare(node1Value, node2Value);
        // End of a block/expression
        };

        // Start of a method/block
        private static int argumentValue(Argument<?> argument) {
            // Branch: checks a condition
            if (argument.getClass() == ArgumentCommand.class) return -3000;
            // Branch: checks a condition
            if (argument.getClass() == ArgumentLiteral.class) return -2000;
            // Returns a value to the caller
            return -1000;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ExecutionImpl(
            // Annotation for the following element
            @UnknownNullability Predicate<CommandSender> predicate,
            // Annotation for the following element
            @UnknownNullability CommandExecutor defaultExecutor,
            // Annotation for the following element
            @Nullable CommandExecutor globalListener,
            // Annotation for the following element
            @Nullable CommandExecutor executor,
            // Annotation for the following element
            @Nullable CommandCondition condition
    // Start of a method/block
    ) implements Execution {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean test(CommandSender commandSender) {
            // Returns a value to the caller
            return predicate.test(commandSender);
        // End of a block/expression
        }

        // Start of a method/block
        static ExecutionImpl fromCommand(Command command) {
            // Calls a method
            final CommandExecutor defaultExecutor = command.getDefaultExecutor();
            // Calls a method
            final CommandCondition defaultCondition = command.getCondition();

            // Assigns a value
            CommandExecutor executor = defaultExecutor;
            // Assigns a value
            CommandCondition condition = defaultCondition;
            // Loop: repeats a block
            for (var syntax : command.getSyntaxes()) {
                // Branch: checks a condition
                if (syntax.getArguments().length == 0) {
                    // Calls a method
                    executor = syntax.getExecutor();
                    // Calls a method
                    CommandCondition syntaxCondition = syntax.getCommandCondition();
                    // Branch: checks a condition
                    if (syntaxCondition != null && defaultCondition != null) {
                        // Assigns a value
                        condition = (sender, commandString) ->
                            // Calls a method
                            defaultCondition.canUse(sender, commandString) && syntaxCondition.canUse(sender, commandString);
                    // Branch: checks a condition
                    } else if (syntaxCondition != null) {
                        // Assigns a value
                        condition = syntaxCondition;
                    // End of a block/expression
                    }
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Assigns a value
            final CommandExecutor globalListener = (sender, context) -> command.globalListener(sender, context,
                                                                                               // Calls a method
                                                                                               context.getInput());

            // Returns a value to the caller
            return new ExecutionImpl(
                    // Code statement
                    commandSender -> defaultCondition == null || defaultCondition.canUse(commandSender, null),
                    // Code statement
                    defaultExecutor, globalListener, executor, condition);
        // End of a block/expression
        }

        // Start of a method/block
        static ExecutionImpl fromSyntax(CommandSyntax syntax) {
            // Calls a method
            final CommandExecutor executor = syntax.getExecutor();
            // Calls a method
            final CommandCondition condition = syntax.getCommandCondition();
            // Returns a value to the caller
            return new ExecutionImpl(commandSender -> condition == null || condition.canUse(commandSender, null),
                                     // Code statement
                                     null, null, executor, condition);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static final class ConversionNode {
        // Code statement
        final Argument<?> argument;
        // Code statement
        ExecutionImpl execution;
        // Code statement
        final Map<Argument<?>, ConversionNode> nextMap;

        // Start of a method/block
        public ConversionNode(Argument<?> argument, ExecutionImpl execution, Map<Argument<?>, ConversionNode> nextMap) {
            // Access to the current/parent object
            this.argument = argument;
            // Access to the current/parent object
            this.execution = execution;
            // Access to the current/parent object
            this.nextMap = nextMap;
        // End of a block/expression
        }

        // Start of a method/block
        ConversionNode(Argument<?> argument, ExecutionImpl execution) {
            // Calls a method
            this(argument, execution, new LinkedHashMap<>());
        // End of a block/expression
        }

        // Start of a method/block
        private NodeImpl toNode() {
            // Calls a method
            Node[] nodes = new NodeImpl[nextMap.size()];
            // Assigns a value
            int i = 0;
            // Loop: repeats a block
            for (var entry : nextMap.values()) nodes[i++] = entry.toNode();
            // Returns a value to the caller
            return new NodeImpl(argument, execution, List.of(nodes));
        // End of a block/expression
        }

        // Start of a method/block
        static ConversionNode fromCommand(Command command) {
            // Calls a method
            ConversionNode root = new ConversionNode(commandToArgument(command), ExecutionImpl.fromCommand(command));
            // Subcommands
            // Loop: repeats a block
            for (Command subcommand : command.getSubcommands()) {
                // Calls a method
                root.nextMap.put(commandToArgument(subcommand), fromCommand(subcommand));
            // End of a block/expression
            }
            // Syntaxes
            // Loop: repeats a block
            for (CommandSyntax syntax : command.getSyntaxes()) {
                // Assigns a value
                ConversionNode syntaxNode = root;
                // Loop: repeats a block
                for (Argument<?> arg : syntax.getArguments()) {
                    // Calls a method
                    boolean last = arg == syntax.getArguments()[syntax.getArguments().length - 1];
                    // Calls a method
                    var ex = last ? ExecutionImpl.fromSyntax(syntax) : null;
                    // Calls a method
                    syntaxNode = syntaxNode.nextMap.computeIfAbsent(arg, argument -> new ConversionNode(argument, ex));
                    // Branch: checks a condition
                    if (syntaxNode.execution == null) syntaxNode.execution = ex;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return root;
        // End of a block/expression
        }

        // Start of a method/block
        static ConversionNode rootConv(Collection<Command> commands) {
            // Calls a method
            Map<Argument<?>, ConversionNode> next = new LinkedHashMap<>(commands.size());
            // Loop: repeats a block
            for (Command command : commands) {
                // Calls a method
                final ConversionNode conv = fromCommand(command);
                // Calls a method
                next.put(conv.argument, conv);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new ConversionNode(Literal(""), null, next);
        // End of a block/expression
        }

    // End of a block/expression
    }

    // Start of a method/block
    static Argument<String> commandToArgument(Command command) {
        // Calls a method
        final String[] aliases = command.getNames();
        // Branch: checks a condition
        if (aliases.length == 1) return Literal(aliases[0]);
        // Returns a value to the caller
        return Word(command.getName()).from(command.getNames());
    // End of a block/expression
    }

    // Start of a method/block
    static boolean compare(Node first, Node second, Comparator comparator) {
        // Returns a value to the caller
        return switch (comparator) {
            // Multiple branching (switch/case)
            case TREE -> {
                // Branch: checks a condition
                if (!compareExecution(first, second)) yield false;
                // Branch: checks a condition
                if (!first.argument().equals(second.argument())) yield false;
                // Branch: checks a condition
                if (first.next().size() != second.next().size()) yield false;
                // Loop: repeats a block
                for (int i = 0; i < first.next().size(); i++) {
                    // Branch: checks a condition
                    if (!compare(first.next().get(i), second.next().get(i), comparator)) {
                        // Code statement
                        yield false;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Code statement
                yield true;
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean compareExecution(Node firstNode, Node secondNode) {
        // Calls a method
        Execution first = firstNode.execution(), second = secondNode.execution();
        // Assigns a value
        boolean firstExecutor = first != null && first.executor() != null,
                // Calls a method
                firstCondition = first != null && first.condition() != null;
        // Assigns a value
        boolean secondExecutor = second != null && second.executor() != null,
                // Calls a method
                secondCondition = second != null && second.condition() != null;
        // Returns a value to the caller
        return firstExecutor == secondExecutor &&
                // Code statement
                firstCondition == secondCondition;
    // End of a block/expression
    }
// End of a block/expression
}
