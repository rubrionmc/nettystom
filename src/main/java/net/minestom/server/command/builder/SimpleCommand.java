// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public abstract class SimpleCommand extends Command {
    // Début d'une méthode/d'un bloc
    public SimpleCommand(String name, @Nullable String... aliases) {
        // Accès à l'objet courant/parent
        super(name, aliases);

        // Appelle une méthode
        setCondition(this::hasAccess);

        // Instruction de code
        setDefaultExecutor((sender, context) ->
                // Appelle une méthode
                process(sender, context.getCommandName(), new String[0]));

        // Appelle une méthode
        final var params = ArgumentType.StringArray("params");
        // Instruction de code
        addSyntax((sender, context) ->
                // Appelle une méthode
                process(sender, context.getCommandName(), context.get(params)), params);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when the command is executed by a {@link CommandSender}.
     *
     * @param sender  the sender which executed the command
     * @param command the command name used
     * @param args    an array containing all the args (split by space char)
     * @return true when the command is successful, false otherwise
     */
    // Appelle une méthode
    public abstract boolean process(CommandSender sender, String command, String[] args);

    /**
     * Called to know if a player has access to the command.
     *
     * @param sender        the command sender to check the access
     * @param commandString the raw command string,
     *                      null if this is an access request
     * @return true if the player has access to the command, false otherwise
     */
    // Appelle une méthode
    public abstract boolean hasAccess(CommandSender sender, @Nullable String commandString);

// Fin d'un bloc/d'une expression
}
