// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class TransferCommand extends Command {
    // Début d'une méthode/d'un bloc
    public TransferCommand() {
        // Accès à l'objet courant/parent
        super("transfer");

        // Appelle une méthode
        var hostArgument = ArgumentType.String("host");
        // Appelle une méthode
        var portArgument = ArgumentType.Integer("port");

        // Accès à l'objet courant/parent
        this.addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (sender instanceof Player player) {
                // Instruction de code
                player.getPlayerConnection().transfer(
                        // Instruction de code
                        context.get(hostArgument),
                        // Appelle une méthode
                        context.get(portArgument));
            // Branche alternative de la condition
            } else {
                // Instruction de code
                sender.sendMessage(Component.text(
                        // Instruction de code
                        "You must be a player to use this command!",
                        // Instruction de code
                        NamedTextColor.RED));
            // Fin d'un bloc/d'une expression
            }
        // Instruction de code
        }, hostArgument, portArgument);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
