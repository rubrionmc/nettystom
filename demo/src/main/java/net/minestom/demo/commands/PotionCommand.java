// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;

// Déclaration de type (classe/interface/enum/record)
public class PotionCommand extends Command {
    // Appelle une méthode
    private final Argument<String> potionArg = ArgumentType.Resource("potion", "minecraft:potion");

    // Début d'une méthode/d'un bloc
    public PotionCommand() {
        // Accès à l'objet courant/parent
        super("potion");

        // Appelle une méthode
        addSyntax(this::potionCommand, potionArg);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void potionCommand(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        final String potion = context.get(potionArg);
        // Appelle une méthode
        sender.sendMessage("Potion: " + potion);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
