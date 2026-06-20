// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Import d'une classe nécessaire
import java.util.Collection;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Float;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;

// Déclaration de type (classe/interface/enum/record)
public class FindCommand extends Command {
    // Début d'une méthode/d'un bloc
    public FindCommand() {
        // Accès à l'objet courant/parent
        super("find");

        // Accès à l'objet courant/parent
        this.addSyntax(
                // Instruction de code
                this::executorEntity,
                // Instruction de code
                Literal("entity"),
                // Instruction de code
                Float("range")
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void executorEntity(CommandSender sender, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) sender;
        // Appelle une méthode
        float range = context.get("range");

        // Appelle une méthode
        Collection<Entity> entities = player.getInstance().getNearbyEntities(player.getPosition(), range);

        // Appelle une méthode
        player.sendMessage("Search result: ");

        // Boucle : répète un bloc
        for (Entity entity : entities) {
            // Appelle une méthode
            player.sendMessage("    " + entity.getEntityType() + ": ");
            // Appelle une méthode
            player.sendMessage("        Meta: " + entity.getEntityMeta());
            // Appelle une méthode
            player.sendMessage("        Position: " + entity.getPosition());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        player.sendMessage("End result.");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
