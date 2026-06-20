// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandResult;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentCommand extends Argument<CommandResult> {

    // Affecte une valeur
    public static final int INVALID_COMMAND_ERROR = 1;

    // Instruction de code
    private boolean onlyCorrect;
    // Affecte une valeur
    private String shortcut = "";

    // Début d'une méthode/d'un bloc
    public ArgumentCommand(String id) {
        // Accès à l'objet courant/parent
        super(id, true, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CommandResult parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Affecte une valeur
        final String commandString = !shortcut.isEmpty() ?
                // Instruction de code
                shortcut + StringUtils.SPACE + input
                // Instruction de code
                : input;
        // Appelle une méthode
        CommandDispatcher dispatcher = MinecraftServer.getCommandManager().getDispatcher();
        // Appelle une méthode
        CommandResult result = dispatcher.parse(sender, commandString);

        // Embranchement : vérifie une condition
        if (onlyCorrect && result.getType() != CommandResult.Type.SUCCESS)
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid command", input, INVALID_COMMAND_ERROR);

        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isOnlyCorrect() {
        // Renvoie une valeur à l'appelant
        return onlyCorrect;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ArgumentCommand setOnlyCorrect(boolean onlyCorrect) {
        // Accès à l'objet courant/parent
        this.onlyCorrect = onlyCorrect;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getShortcut() {
        // Renvoie une valeur à l'appelant
        return shortcut;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public ArgumentCommand setShortcut(String shortcut) {
        // Accès à l'objet courant/parent
        this.shortcut = shortcut;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Command<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
