// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandExecutor;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.condition.CommandCondition;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Predicate;

// Type declaration (class/interface/enum/record)
sealed interface Graph permits GraphImpl {
    // Start of a method/block
    static Builder builder(Argument<?> argument, @Nullable Execution execution) {
        // Returns a value to the caller
        return new GraphImpl.BuilderImpl(argument, execution);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder(Argument<?> argument) {
        // Returns a value to the caller
        return new GraphImpl.BuilderImpl(argument, null);
    // End of a block/expression
    }

    // Start of a method/block
    static Graph fromCommand(Command command) {
        // Returns a value to the caller
        return GraphImpl.fromCommand(command);
    // End of a block/expression
    }

    // Start of a method/block
    static Graph merge(Collection<Command> commands) {
        // Returns a value to the caller
        return GraphImpl.merge(commands);
    // End of a block/expression
    }

    // Start of a method/block
    static Graph merge(List<Graph> graphs) {
        // Returns a value to the caller
        return GraphImpl.merge(graphs);
    // End of a block/expression
    }

    // Start of a method/block
    static Graph merge(Graph ... graphs) {
        // Returns a value to the caller
        return merge(List.of(graphs));
    // End of a block/expression
    }

    // Calls a method
    Node root();

    // Calls a method
    boolean compare(Graph graph, Comparator comparator);

    // Type declaration (class/interface/enum/record)
    sealed interface Node permits GraphImpl.NodeImpl {
        // Calls a method
        Argument<?> argument();

        // Annotation for the following element
        @UnknownNullability Execution execution();

        // Calls a method
        List<Node> next();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface Execution extends Predicate<CommandSender> permits GraphImpl.ExecutionImpl {
        // Annotation for the following element
        @UnknownNullability CommandExecutor defaultExecutor();

        // Annotation for the following element
        @UnknownNullability CommandExecutor globalListener();

        /**
         * Non-null if the command at this point considered executable, must be present
         * on the last node of the syntax.
         */
        // Annotation for the following element
        @Nullable CommandExecutor executor();

        /**
         * Non-null if the command or syntax has a condition, must be present
         * only on nodes that specify it
         */
        // Annotation for the following element
        @Nullable CommandCondition condition();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface Builder permits GraphImpl.BuilderImpl {
        // Calls a method
        Builder append(Argument<?> argument, @Nullable Execution execution, Consumer<Builder> consumer);

        // Calls a method
        Builder append(Argument<?> argument, @Nullable Execution execution);

        // Start of a method/block
        default Builder append(Argument<?> argument, Consumer<Builder> consumer) {
            // Returns a value to the caller
            return append(argument, null, consumer);
        // End of a block/expression
        }

        // Start of a method/block
        default Builder append(Argument<?> argument) {
            // Returns a value to the caller
            return append(argument, (Execution) null);
        // End of a block/expression
        }

        // Calls a method
        Graph build();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum Comparator {
        // Code statement
        TREE
    // End of a block/expression
    }
// End of a block/expression
}
