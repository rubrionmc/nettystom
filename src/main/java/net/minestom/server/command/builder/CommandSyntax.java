// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.condition.CommandCondition;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Function;

/**
 * Represents a syntax in {@link Command}
 * which is initialized with {@link Command#addSyntax(CommandExecutor, Argument[])}.
 */
// Type declaration (class/interface/enum/record)
public class CommandSyntax {

    // Code statement
    private @Nullable CommandCondition commandCondition;
    // Code statement
    private CommandExecutor executor;

    // Code statement
    private final @Nullable Map<String, Function<CommandSender, Object>> defaultValuesMap;
    // Code statement
    private final Argument<?>[] args;

    // Code statement
    private final boolean suggestion;

    // Code statement
    protected CommandSyntax(@Nullable CommandCondition commandCondition,
                            // Code statement
                            CommandExecutor commandExecutor,
                            // Annotation for the following element
                            @Nullable Map<String, Function<CommandSender, Object>> defaultValuesMap,
                            // Start of a method/block
                            Argument<?>... args) {
        // Access to the current/parent object
        this.commandCondition = commandCondition;
        // Access to the current/parent object
        this.executor = commandExecutor;

        // Access to the current/parent object
        this.defaultValuesMap = defaultValuesMap;
        // Access to the current/parent object
        this.args = args;

        // Access to the current/parent object
        this.suggestion = Arrays.stream(args).anyMatch(Argument::hasSuggestion);
    // End of a block/expression
    }

    // Code statement
    protected CommandSyntax(@Nullable CommandCondition commandCondition,
                            // Code statement
                            CommandExecutor commandExecutor,
                            // Start of a method/block
                            Argument<?>... args) {
        // Calls a method
        this(commandCondition, commandExecutor, null, args);
    // End of a block/expression
    }

    /**
     * Gets the condition to use this syntax.
     *
     * @return this command condition, null if none
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public CommandCondition getCommandCondition() {
        // Returns a value to the caller
        return commandCondition;
    // End of a block/expression
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
    // Start of a method/block
    public void setCommandCondition(@Nullable CommandCondition commandCondition) {
        // Access to the current/parent object
        this.commandCondition = commandCondition;
    // End of a block/expression
    }

    /**
     * Gets the {@link CommandExecutor} of this syntax, executed once the syntax is properly written.
     *
     * @return the executor of this syntax
     */
    // Start of a method/block
    public CommandExecutor getExecutor() {
        // Returns a value to the caller
        return executor;
    // End of a block/expression
    }

    /**
     * Changes the {@link CommandExecutor} of this syntax.
     *
     * @param executor the new executor
     */
    // Start of a method/block
    public void setExecutor(CommandExecutor executor) {
        // Access to the current/parent object
        this.executor = executor;
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    protected Map<String, Function<CommandSender, Object>> getDefaultValuesMap() {
        // Returns a value to the caller
        return defaultValuesMap;
    // End of a block/expression
    }

    /**
     * Gets all the required {@link Argument} for this syntax.
     *
     * @return the required arguments
     */
    // Start of a method/block
    public Argument<?>[] getArguments() {
        // Returns a value to the caller
        return args;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean hasSuggestion() {
        // Returns a value to the caller
        return suggestion;
    // End of a block/expression
    }

    // Start of a method/block
    public String getSyntaxString() {
        // Calls a method
        StringBuilder builder = new StringBuilder();
        // Loop: repeats a block
        for (Argument<?> argument : args) {
            // Code statement
            builder.append(argument)
                    // Calls a method
                    .append(StringUtils.SPACE);
        // End of a block/expression
        }
        // Returns a value to the caller
        return builder.toString().trim();
    // End of a block/expression
    }
// End of a block/expression
}
