// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.CommandCondition;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * Represents a syntax in {@link Command}
 * which is initialized with {@link Command#addSyntax(CommandExecutor, Argument[])}.
 */
// Déclaration de type (classe/interface/enum/record)
public class CommandSyntax {

    // Instruction de code
    private @Nullable CommandCondition commandCondition;
    // Instruction de code
    private CommandExecutor executor;

    // Instruction de code
    private final @Nullable Map<String, Function<CommandSender, Object>> defaultValuesMap;
    // Instruction de code
    private final Argument<?>[] args;

    // Instruction de code
    private final boolean suggestion;

    // Instruction de code
    protected CommandSyntax(@Nullable CommandCondition commandCondition,
                            // Instruction de code
                            CommandExecutor commandExecutor,
                            // Annotation pour l'élément suivant
                            @Nullable Map<String, Function<CommandSender, Object>> defaultValuesMap,
                            // Début d'une méthode/d'un bloc
                            Argument<?>... args) {
        // Accès à l'objet courant/parent
        this.commandCondition = commandCondition;
        // Accès à l'objet courant/parent
        this.executor = commandExecutor;

        // Accès à l'objet courant/parent
        this.defaultValuesMap = defaultValuesMap;
        // Accès à l'objet courant/parent
        this.args = args;

        // Accès à l'objet courant/parent
        this.suggestion = Arrays.stream(args).anyMatch(Argument::hasSuggestion);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    protected CommandSyntax(@Nullable CommandCondition commandCondition,
                            // Instruction de code
                            CommandExecutor commandExecutor,
                            // Début d'une méthode/d'un bloc
                            Argument<?>... args) {
        // Appelle une méthode
        this(commandCondition, commandExecutor, null, args);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the condition to use this syntax.
     *
     * @return this command condition, null if none
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public CommandCondition getCommandCondition() {
        // Renvoie une valeur à l'appelant
        return commandCondition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the command condition of this syntax.
     * <p>
     * Be aware that changing the command condition will not automatically update players auto-completion.
     * You can create a new packet containing the changes with
     * {@link net.minestom.server.command.CommandManager#createDeclareCommandsPacket(Player)}.
     *
     * @param commandCondition the new command condition, null to remove it
     */
    // Début d'une méthode/d'un bloc
    public void setCommandCondition(@Nullable CommandCondition commandCondition) {
        // Accès à l'objet courant/parent
        this.commandCondition = commandCondition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link CommandExecutor} of this syntax, executed once the syntax is properly written.
     *
     * @return the executor of this syntax
     */
    // Début d'une méthode/d'un bloc
    public CommandExecutor getExecutor() {
        // Renvoie une valeur à l'appelant
        return executor;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link CommandExecutor} of this syntax.
     *
     * @param executor the new executor
     */
    // Début d'une méthode/d'un bloc
    public void setExecutor(CommandExecutor executor) {
        // Accès à l'objet courant/parent
        this.executor = executor;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    protected Map<String, Function<CommandSender, Object>> getDefaultValuesMap() {
        // Renvoie une valeur à l'appelant
        return defaultValuesMap;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the required {@link Argument} for this syntax.
     *
     * @return the required arguments
     */
    // Début d'une méthode/d'un bloc
    public Argument<?>[] getArguments() {
        // Renvoie une valeur à l'appelant
        return args;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean hasSuggestion() {
        // Renvoie une valeur à l'appelant
        return suggestion;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getSyntaxString() {
        // Appelle une méthode
        StringBuilder builder = new StringBuilder();
        // Boucle : répète un bloc
        for (Argument<?> argument : args) {
            // Instruction de code
            builder.append(argument.toString())
                    // Appelle une méthode
                    .append(StringUtils.SPACE);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return builder.toString().trim();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
