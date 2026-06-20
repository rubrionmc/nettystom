// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandExecutor;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandSyntax;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentCommand;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.CommandCondition;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Word;

// Déclaration de type (classe/interface/enum/record)
record GraphImpl(NodeImpl root) implements Graph {
    // Début d'une méthode/d'un bloc
    static GraphImpl fromCommand(Command command) {
        // Renvoie une valeur à l'appelant
        return new GraphImpl(NodeImpl.command(command));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Graph merge(Collection<Command> commands) {
        // Renvoie une valeur à l'appelant
        return new GraphImpl(NodeImpl.rootCommands(commands));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static GraphImpl merge(List<Graph> graphs) {
        // Appelle une méthode
        final List<Node> children = graphs.stream().map(Graph::root).toList();
        // Appelle une méthode
        final NodeImpl root = new NodeImpl(Literal(""), null, children);
        // Renvoie une valeur à l'appelant
        return new GraphImpl(root);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean compare(Graph graph, Comparator comparator) {
        // Renvoie une valeur à l'appelant
        return compare(root, graph.root(), comparator);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record BuilderImpl(Argument<?> argument, List<BuilderImpl> children, Execution execution) implements Graph.Builder {
        // Début d'une méthode/d'un bloc
        public BuilderImpl(Argument<?> argument, Execution execution) {
            // Appelle une méthode
            this(argument, new ArrayList<>(), execution);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Instruction de code
        public Graph.Builder append(
                // Instruction de code
                Argument<?> argument, @Nullable Execution execution,
                // Instruction de code
                Consumer<Graph.Builder> consumer
        // Début d'une méthode/d'un bloc
        ) {
            // Appelle une méthode
            BuilderImpl builder = new BuilderImpl(argument, execution);
            // Appelle une méthode
            consumer.accept(builder);
            // Accès à l'objet courant/parent
            this.children.add(builder);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Graph.Builder append(Argument<?> argument, @Nullable Execution execution) {
            // Accès à l'objet courant/parent
            this.children.add(new BuilderImpl(argument, List.of(), execution));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public GraphImpl build() {
            // Renvoie une valeur à l'appelant
            return new GraphImpl(NodeImpl.fromBuilder(this));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record NodeImpl(Argument<?> argument, ExecutionImpl execution, List<Graph.Node> next) implements Graph.Node {
        // Début d'une méthode/d'un bloc
        NodeImpl(Argument<?> argument, ExecutionImpl execution, List<Graph.Node> next) {
            // Accès à l'objet courant/parent
            this.argument = argument;
            // Accès à l'objet courant/parent
            this.execution = execution;
            // Accès à l'objet courant/parent
            this.next = next.stream().sorted(nodePriority).toList();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static NodeImpl fromBuilder(BuilderImpl builder) {
            // Affecte une valeur
            final List<BuilderImpl> children = builder.children;
            // Appelle une méthode
            Node[] nodes = new NodeImpl[children.size()];
            // Boucle : répète un bloc
            for (int i = 0; i < children.size(); i++) nodes[i] = fromBuilder(children.get(i));
            // Renvoie une valeur à l'appelant
            return new NodeImpl(builder.argument, (ExecutionImpl) builder.execution, List.of(nodes));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static NodeImpl command(Command command) {
            // Renvoie une valeur à l'appelant
            return ConversionNode.fromCommand(command).toNode();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static NodeImpl rootCommands(Collection<Command> commands) {
            // Renvoie une valeur à l'appelant
            return ConversionNode.rootConv(commands).toNode();
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        private static final java.util.Comparator<Node> nodePriority = (node1, node2) -> {
            // Appelle une méthode
            int node1Value = argumentValue(node1.argument());
            // Appelle une méthode
            int node2Value = argumentValue(node2.argument());
            // Renvoie une valeur à l'appelant
            return Integer.compare(node1Value, node2Value);
        // Fin d'un bloc/d'une expression
        };

        // Début d'une méthode/d'un bloc
        private static int argumentValue(Argument<?> argument) {
            // Embranchement : vérifie une condition
            if (argument.getClass() == ArgumentCommand.class) return -3000;
            // Embranchement : vérifie une condition
            if (argument.getClass() == ArgumentLiteral.class) return -2000;
            // Renvoie une valeur à l'appelant
            return -1000;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ExecutionImpl(
            // Annotation pour l'élément suivant
            @UnknownNullability Predicate<CommandSender> predicate,
            // Annotation pour l'élément suivant
            @UnknownNullability CommandExecutor defaultExecutor,
            // Annotation pour l'élément suivant
            @Nullable CommandExecutor globalListener,
            // Annotation pour l'élément suivant
            @Nullable CommandExecutor executor,
            // Annotation pour l'élément suivant
            @Nullable CommandCondition condition
    // Début d'une méthode/d'un bloc
    ) implements Execution {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean test(CommandSender commandSender) {
            // Renvoie une valeur à l'appelant
            return predicate.test(commandSender);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static ExecutionImpl fromCommand(Command command) {
            // Appelle une méthode
            final CommandExecutor defaultExecutor = command.getDefaultExecutor();
            // Appelle une méthode
            final CommandCondition defaultCondition = command.getCondition();

            // Affecte une valeur
            CommandExecutor executor = defaultExecutor;
            // Affecte une valeur
            CommandCondition condition = defaultCondition;
            // Boucle : répète un bloc
            for (var syntax : command.getSyntaxes()) {
                // Embranchement : vérifie une condition
                if (syntax.getArguments().length == 0) {
                    // Appelle une méthode
                    executor = syntax.getExecutor();
                    // Appelle une méthode
                    CommandCondition syntaxCondition = syntax.getCommandCondition();
                    // Embranchement : vérifie une condition
                    if (syntaxCondition != null && defaultCondition != null) {
                        // Affecte une valeur
                        condition = (sender, commandString) ->
                            // Appelle une méthode
                            defaultCondition.canUse(sender, commandString) && syntaxCondition.canUse(sender, commandString);
                    // Embranchement : vérifie une condition
                    } else if (syntaxCondition != null) {
                        // Affecte une valeur
                        condition = syntaxCondition;
                    // Fin d'un bloc/d'une expression
                    }
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            final CommandExecutor globalListener = (sender, context) -> command.globalListener(sender, context,
                                                                                               // Appelle une méthode
                                                                                               context.getInput());

            // Renvoie une valeur à l'appelant
            return new ExecutionImpl(
                    // Instruction de code
                    commandSender -> defaultCondition == null || defaultCondition.canUse(commandSender, null),
                    // Instruction de code
                    defaultExecutor, globalListener, executor, condition);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static ExecutionImpl fromSyntax(CommandSyntax syntax) {
            // Appelle une méthode
            final CommandExecutor executor = syntax.getExecutor();
            // Appelle une méthode
            final CommandCondition condition = syntax.getCommandCondition();
            // Renvoie une valeur à l'appelant
            return new ExecutionImpl(commandSender -> condition == null || condition.canUse(commandSender, null),
                                     // Instruction de code
                                     null, null, executor, condition);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class ConversionNode {
        // Instruction de code
        final Argument<?> argument;
        // Instruction de code
        ExecutionImpl execution;
        // Instruction de code
        final Map<Argument<?>, ConversionNode> nextMap;

        // Début d'une méthode/d'un bloc
        public ConversionNode(Argument<?> argument, ExecutionImpl execution, Map<Argument<?>, ConversionNode> nextMap) {
            // Accès à l'objet courant/parent
            this.argument = argument;
            // Accès à l'objet courant/parent
            this.execution = execution;
            // Accès à l'objet courant/parent
            this.nextMap = nextMap;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        ConversionNode(Argument<?> argument, ExecutionImpl execution) {
            // Appelle une méthode
            this(argument, execution, new LinkedHashMap<>());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private NodeImpl toNode() {
            // Appelle une méthode
            Node[] nodes = new NodeImpl[nextMap.size()];
            // Affecte une valeur
            int i = 0;
            // Boucle : répète un bloc
            for (var entry : nextMap.values()) nodes[i++] = entry.toNode();
            // Renvoie une valeur à l'appelant
            return new NodeImpl(argument, execution, List.of(nodes));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static ConversionNode fromCommand(Command command) {
            // Appelle une méthode
            ConversionNode root = new ConversionNode(commandToArgument(command), ExecutionImpl.fromCommand(command));
            // Subcommands
            // Boucle : répète un bloc
            for (Command subcommand : command.getSubcommands()) {
                // Appelle une méthode
                root.nextMap.put(commandToArgument(subcommand), fromCommand(subcommand));
            // Fin d'un bloc/d'une expression
            }
            // Syntaxes
            // Boucle : répète un bloc
            for (CommandSyntax syntax : command.getSyntaxes()) {
                // Affecte une valeur
                ConversionNode syntaxNode = root;
                // Boucle : répète un bloc
                for (Argument<?> arg : syntax.getArguments()) {
                    // Appelle une méthode
                    boolean last = arg == syntax.getArguments()[syntax.getArguments().length - 1];
                    // Appelle une méthode
                    var ex = last ? ExecutionImpl.fromSyntax(syntax) : null;
                    // Appelle une méthode
                    syntaxNode = syntaxNode.nextMap.computeIfAbsent(arg, argument -> new ConversionNode(argument, ex));
                    // Embranchement : vérifie une condition
                    if (syntaxNode.execution == null) syntaxNode.execution = ex;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return root;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static ConversionNode rootConv(Collection<Command> commands) {
            // Appelle une méthode
            Map<Argument<?>, ConversionNode> next = new LinkedHashMap<>(commands.size());
            // Boucle : répète un bloc
            for (Command command : commands) {
                // Appelle une méthode
                final ConversionNode conv = fromCommand(command);
                // Appelle une méthode
                next.put(conv.argument, conv);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new ConversionNode(Literal(""), null, next);
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Argument<String> commandToArgument(Command command) {
        // Appelle une méthode
        final String[] aliases = command.getNames();
        // Embranchement : vérifie une condition
        if (aliases.length == 1) return Literal(aliases[0]);
        // Renvoie une valeur à l'appelant
        return Word(command.getName()).from(command.getNames());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static boolean compare(Node first, Node second, Comparator comparator) {
        // Renvoie une valeur à l'appelant
        return switch (comparator) {
            // Embranchement multiple (switch/case)
            case TREE -> {
                // Embranchement : vérifie une condition
                if (!compareExecution(first, second)) yield false;
                // Embranchement : vérifie une condition
                if (!first.argument().equals(second.argument())) yield false;
                // Embranchement : vérifie une condition
                if (first.next().size() != second.next().size()) yield false;
                // Boucle : répète un bloc
                for (int i = 0; i < first.next().size(); i++) {
                    // Embranchement : vérifie une condition
                    if (!compare(first.next().get(i), second.next().get(i), comparator)) {
                        // Instruction de code
                        yield false;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Instruction de code
                yield true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean compareExecution(Node firstNode, Node secondNode) {
        // Appelle une méthode
        Execution first = firstNode.execution(), second = secondNode.execution();
        // Affecte une valeur
        boolean firstExecutor = first != null && first.executor() != null,
                // Appelle une méthode
                firstCondition = first != null && first.condition() != null;
        // Affecte une valeur
        boolean secondExecutor = second != null && second.executor() != null,
                // Appelle une méthode
                secondCondition = second != null && second.condition() != null;
        // Renvoie une valeur à l'appelant
        return firstExecutor == secondExecutor &&
                // Instruction de code
                firstCondition == secondCondition;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
