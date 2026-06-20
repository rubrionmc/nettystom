// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class SetEntityType extends Command {
    // Appelle une méthode
    private final ArgumentEntityType entityTypeArg = ArgumentType.EntityType("type");

    // Début d'une méthode/d'un bloc
    public SetEntityType() {
        // Accès à l'objet courant/parent
        super("setentitytype");

        // Appelle une méthode
        addSyntax(this::execute, entityTypeArg);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void execute(CommandSender sender, CommandContext context) {
        // Embranchement : vérifie une condition
        if (!(sender instanceof Player player)) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var entityType = context.get(entityTypeArg);
        // Appelle une méthode
        player.switchEntityType(entityType);
        // Appelle une méthode
        player.sendMessage("set entity type to " + entityType);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
