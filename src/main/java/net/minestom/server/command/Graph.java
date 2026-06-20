// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandExecutor;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.CommandCondition;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Déclaration de type (classe/interface/enum/record)
sealed interface Graph permits GraphImpl {
    // Début d'une méthode/d'un bloc
    static Builder builder(Argument<?> argument, @Nullable Execution execution) {
        // Renvoie une valeur à l'appelant
        return new GraphImpl.BuilderImpl(argument, execution);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder(Argument<?> argument) {
        // Renvoie une valeur à l'appelant
        return new GraphImpl.BuilderImpl(argument, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Graph fromCommand(Command command) {
        // Renvoie une valeur à l'appelant
        return GraphImpl.fromCommand(command);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Graph merge(Collection<Command> commands) {
        // Renvoie une valeur à l'appelant
        return GraphImpl.merge(commands);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Graph merge(List<Graph> graphs) {
        // Renvoie une valeur à l'appelant
        return GraphImpl.merge(graphs);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Graph merge(Graph ... graphs) {
        // Renvoie une valeur à l'appelant
        return merge(List.of(graphs));
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Node root();

    // Appelle une méthode
    boolean compare(Graph graph, Comparator comparator);

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Node permits GraphImpl.NodeImpl {
        // Appelle une méthode
        Argument<?> argument();

        // Annotation pour l'élément suivant
        @UnknownNullability Execution execution();

        // Appelle une méthode
        List<Node> next();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Execution extends Predicate<CommandSender> permits GraphImpl.ExecutionImpl {
        // Annotation pour l'élément suivant
        @UnknownNullability CommandExecutor defaultExecutor();

        // Annotation pour l'élément suivant
        @UnknownNullability CommandExecutor globalListener();

        /**
         * Non-null if the command at this point considered executable, must be present
         * on the last node of the syntax.
         */
        // Annotation pour l'élément suivant
        @Nullable CommandExecutor executor();

        /**
         * Non-null if the command or syntax has a condition, must be present
         * only on nodes that specify it
         */
        // Annotation pour l'élément suivant
        @Nullable CommandCondition condition();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Builder permits GraphImpl.BuilderImpl {
        // Appelle une méthode
        Builder append(Argument<?> argument, @Nullable Execution execution, Consumer<Builder> consumer);

        // Appelle une méthode
        Builder append(Argument<?> argument, @Nullable Execution execution);

        // Début d'une méthode/d'un bloc
        default Builder append(Argument<?> argument, Consumer<Builder> consumer) {
            // Renvoie une valeur à l'appelant
            return append(argument, null, consumer);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Builder append(Argument<?> argument) {
            // Renvoie une valeur à l'appelant
            return append(argument, (Execution) null);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Graph build();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum Comparator {
        // Instruction de code
        TREE
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
