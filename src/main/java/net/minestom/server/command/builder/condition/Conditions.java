// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.condition;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.ConsoleSender;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;

/**
 * Common command conditions
 */
// Déclaration de type (classe/interface/enum/record)
public final class Conditions {
    /**
     * Will only execute if all command conditions succeed.
     */
    // Début d'une méthode/d'un bloc
    public static CommandCondition all(CommandCondition... conditions) {
        // Appelle une méthode
        Objects.requireNonNull(conditions, "conditions cannot be null");
        // Boucle : répète un bloc
        for (CommandCondition condition : conditions) {
            // Appelle une méthode
            Objects.requireNonNull(condition, "condition cannot be null");
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return (sender, commandString) -> {
            // Boucle : répète un bloc
            for (CommandCondition condition : conditions) {
                // Embranchement : vérifie une condition
                if (!condition.canUse(sender, commandString)) {
                    // Renvoie une valeur à l'appelant
                    return false;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Will execute if one or more command conditions succeed.
     */
    // Début d'une méthode/d'un bloc
    public static CommandCondition any(CommandCondition... conditions) {
        // Appelle une méthode
        Objects.requireNonNull(conditions, "conditions cannot be null");
        // Boucle : répète un bloc
        for (CommandCondition condition : conditions) {
            // Appelle une méthode
            Objects.requireNonNull(condition, "condition cannot be null");
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return (sender, commandString) -> {
            // Boucle : répète un bloc
            for (CommandCondition condition : conditions) {
                // Embranchement : vérifie une condition
                if (condition.canUse(sender, commandString)) {
                    // Renvoie une valeur à l'appelant
                    return true;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Will succeed if the command sender is a player.
     */
    // Début d'une méthode/d'un bloc
    public static boolean playerOnly(CommandSender sender, @Nullable String commandString) {
        // Renvoie une valeur à l'appelant
        return sender instanceof Player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Will succeed if the command sender is the server console.
     */
    // Début d'une méthode/d'un bloc
    public static boolean consoleOnly(CommandSender sender, @Nullable String commandString) {
        // Renvoie une valeur à l'appelant
        return sender instanceof ConsoleSender;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Inverts the result of the given condition.
     */
    // Début d'une méthode/d'un bloc
    public static CommandCondition not(CommandCondition condition) {
        // Appelle une méthode
        Objects.requireNonNull(condition, "condition cannot be null");
        // Renvoie une valeur à l'appelant
        return (sender, commandString) -> !condition.canUse(sender, commandString);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
