// Package declaration for this file
package net.minestom.server.command.builder.condition;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.ConsoleSender;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;

/**
 * Common command conditions
 */
// Type declaration (class/interface/enum/record)
public final class Conditions {
    /**
     * Will only execute if all command conditions succeed.
     */
    // Start of a method/block
    public static CommandCondition all(CommandCondition... conditions) {
        // Calls a method
        Objects.requireNonNull(conditions, "conditions cannot be null");
        // Loop: repeats a block
        for (CommandCondition condition : conditions) {
            // Calls a method
            Objects.requireNonNull(condition, "condition cannot be null");
        // End of a block/expression
        }
        // Returns a value to the caller
        return (sender, commandString) -> {
            // Loop: repeats a block
            for (CommandCondition condition : conditions) {
                // Branch: checks a condition
                if (!condition.canUse(sender, commandString)) {
                    // Returns a value to the caller
                    return false;
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Returns a value to the caller
            return true;
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Will execute if one or more command conditions succeed.
     */
    // Start of a method/block
    public static CommandCondition any(CommandCondition... conditions) {
        // Calls a method
        Objects.requireNonNull(conditions, "conditions cannot be null");
        // Loop: repeats a block
        for (CommandCondition condition : conditions) {
            // Calls a method
            Objects.requireNonNull(condition, "condition cannot be null");
        // End of a block/expression
        }
        // Returns a value to the caller
        return (sender, commandString) -> {
            // Loop: repeats a block
            for (CommandCondition condition : conditions) {
                // Branch: checks a condition
                if (condition.canUse(sender, commandString)) {
                    // Returns a value to the caller
                    return true;
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Returns a value to the caller
            return false;
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Will succeed if the command sender is a player.
     */
    // Start of a method/block
    public static boolean playerOnly(CommandSender sender, @Nullable String commandString) {
        // Returns a value to the caller
        return sender instanceof Player;
    // End of a block/expression
    }

    /**
     * Will succeed if the command sender is the server console.
     */
    // Start of a method/block
    public static boolean consoleOnly(CommandSender sender, @Nullable String commandString) {
        // Returns a value to the caller
        return sender instanceof ConsoleSender;
    // End of a block/expression
    }

    /**
     * Inverts the result of the given condition.
     */
    // Start of a method/block
    public static CommandCondition not(CommandCondition condition) {
        // Calls a method
        Objects.requireNonNull(condition, "condition cannot be null");
        // Returns a value to the caller
        return (sender, commandString) -> !condition.canUse(sender, commandString);
    // End of a block/expression
    }
// End of a block/expression
}
