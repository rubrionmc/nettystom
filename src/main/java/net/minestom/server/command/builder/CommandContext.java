// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.NotNull;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Supplier;

/**
 * Class used to retrieve argument data in a {@link CommandExecutor}.
 * <p>
 * All id are the one specified in the {@link Argument} constructor.
 * <p>
 * All methods are @{@link NotNull} in the sense that you should not have to verify their validity since if the syntax
 * is called, it means that all of its arguments are correct. Be aware that trying to retrieve an argument not present
 * in the syntax will result in a {@link NullPointerException}.
 */
// Type declaration (class/interface/enum/record)
public class CommandContext {

    // Code statement
    private final String input;
    // Code statement
    private final String commandName;
    // Calls a method
    protected Map<String, Object> args = new HashMap<>();
    // Calls a method
    protected Map<String, String> rawArgs = new HashMap<>();
    // Code statement
    private CommandData returnData;

    // Start of a method/block
    public CommandContext(String input) {
        // Access to the current/parent object
        this.input = input;
        // Access to the current/parent object
        this.commandName = input.split(StringUtils.SPACE)[0];
    // End of a block/expression
    }

    // Start of a method/block
    public String getInput() {
        // Returns a value to the caller
        return input;
    // End of a block/expression
    }

    // Start of a method/block
    public String getCommandName() {
        // Returns a value to the caller
        return commandName;
    // End of a block/expression
    }

    // Start of a method/block
    public <T> T get(Argument<T> argument) {
        // Returns a value to the caller
        return get(argument.getId());
    // End of a block/expression
    }

    // Start of a method/block
    public <T> T get(String identifier) {
        // Returns a value to the caller
        return (T) args.get(identifier);
    // End of a block/expression
    }

    // Start of a method/block
    public <T> T getOrDefault(Argument<T> argument, T defaultValue) {
        // Returns a value to the caller
        return getOrDefault(argument.getId(), defaultValue);
    // End of a block/expression
    }

    // Start of a method/block
    public <T> T getOrDefault(String identifier, T defaultValue) {
        // Code statement
        T value;
        // Returns a value to the caller
        return (value = get(identifier)) != null ? value : defaultValue;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean has(Argument<?> argument) {
        // Returns a value to the caller
        return args.containsKey(argument.getId());
    // End of a block/expression
    }

    // Start of a method/block
    public boolean has(String identifier) {
        // Returns a value to the caller
        return args.containsKey(identifier);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable CommandData getReturnData() {
        // Returns a value to the caller
        return returnData;
    // End of a block/expression
    }

    // Start of a method/block
    public void setReturnData(@Nullable CommandData returnData) {
        // Access to the current/parent object
        this.returnData = returnData;
    // End of a block/expression
    }

    // Start of a method/block
    public Map<String, Object> getMap() {
        // Returns a value to the caller
        return args;
    // End of a block/expression
    }

    // Start of a method/block
    public void copy(CommandContext context) {
        // Access to the current/parent object
        this.args = context.args;
        // Access to the current/parent object
        this.rawArgs = context.rawArgs;
    // End of a block/expression
    }

    // Start of a method/block
    public String getRaw(Argument<?> argument) {
        // Returns a value to the caller
        return rawArgs.get(argument.getId());
    // End of a block/expression
    }

    // Start of a method/block
    public String getRaw(String identifier) {
        // Returns a value to the caller
        return rawArgs.get(identifier);
    // End of a block/expression
    }

    // Start of a method/block
    public void setArg(String id, Object value, String rawInput) {
        // Access to the current/parent object
        this.args.put(id, value);
        // Access to the current/parent object
        this.rawArgs.put(id, rawInput);
    // End of a block/expression
    }

    // Start of a method/block
    protected void clear() {
        // Access to the current/parent object
        this.args.clear();
    // End of a block/expression
    }

    // Start of a method/block
    protected void retrieveDefaultValues(@Nullable Map<String, Supplier<Object>> defaultValuesMap) {
        // Branch: checks a condition
        if (defaultValuesMap == null) return;
        // Loop: repeats a block
        for (var entry : defaultValuesMap.entrySet()) {
            // Calls a method
            final String key = entry.getKey();
            // Branch: checks a condition
            if (!args.containsKey(key)) {
                // Calls a method
                final var supplier = entry.getValue();
                // Access to the current/parent object
                this.args.put(key, supplier.get());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (this == o) return true;
        // Branch: checks a condition
        if (!(o instanceof CommandContext that)) return false;
        // Returns a value to the caller
        return Objects.equals(input, that.input) &&
                // Code statement
                Objects.equals(commandName, that.commandName) &&
                // Code statement
                Objects.equals(args, that.args) &&
                // Code statement
                Objects.equals(rawArgs, that.rawArgs) &&
                // Calls a method
                Objects.equals(returnData, that.returnData);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return Objects.hash(input, commandName, args, rawArgs, returnData);
    // End of a block/expression
    }
// End of a block/expression
}
