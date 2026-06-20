// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentNumber;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class HealthCommand extends Command {

    // Début d'une méthode/d'un bloc
    public HealthCommand() {
        // Accès à l'objet courant/parent
        super("health");

        // Appelle une méthode
        setCondition(Conditions::playerOnly);

        // Appelle une méthode
        setDefaultExecutor(this::defaultExecutor);

        // Appelle une méthode
        var modeArg = ArgumentType.Word("mode").from("set", "add");

        // Appelle une méthode
        var valueArg = ArgumentType.Integer("value").between(0, 100);

        // Appelle une méthode
        setArgumentCallback(this::onModeError, modeArg);
        // Appelle une méthode
        setArgumentCallback(this::onValueError, valueArg);

        // Appelle une méthode
        addSyntax(this::sendSuggestionMessage, modeArg);
        // Appelle une méthode
        addSyntax(this::onHealthCommand, modeArg, valueArg);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void defaultExecutor(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        sender.sendMessage(Component.text("Correct usage: health set|add <number>"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onModeError(CommandSender sender, ArgumentSyntaxException exception) {
        // Appelle une méthode
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by 'set' or 'add'"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onValueError(CommandSender sender, ArgumentSyntaxException exception) {
        // Appelle une méthode
        final int error = exception.getErrorCode();
        // Appelle une méthode
        final String input = exception.getInput();
        // Embranchement multiple (switch/case)
        switch (error) {
            // Embranchement multiple (switch/case)
            case ArgumentNumber.NOT_NUMBER_ERROR:
                // Appelle une méthode
                sender.sendMessage(Component.text("SYNTAX ERROR: '" + input + "' isn't a number!"));
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case ArgumentNumber.TOO_LOW_ERROR:
            // Embranchement multiple (switch/case)
            case ArgumentNumber.TOO_HIGH_ERROR:
                // Appelle une méthode
                sender.sendMessage(Component.text("SYNTAX ERROR: " + input + " is not between 0 and 100"));
                // Interrompt la boucle/le bloc
                break;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void sendSuggestionMessage(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        sender.sendMessage(Component.text("/health " + context.get("mode") + " [Integer]"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onHealthCommand(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        final Player player = (Player) sender;
        // Appelle une méthode
        final String mode = context.get("mode");
        // Appelle une méthode
        final int value = context.get("value");

        // Embranchement multiple (switch/case)
        switch (mode.toLowerCase()) {
            // Embranchement multiple (switch/case)
            case "set":
                // Appelle une méthode
                player.setHealth(value);
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "add":
                // Appelle une méthode
                player.setHealth(player.getHealth() + value);
                // Interrompt la boucle/le bloc
                break;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        player.sendMessage(Component.text("You have now " + player.getHealth() + " health"));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}