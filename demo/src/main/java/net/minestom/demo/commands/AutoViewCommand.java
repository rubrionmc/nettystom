// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.utils.entity.EntityFinder;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Boolean;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.*;

// Déclaration de type (classe/interface/enum/record)
public class AutoViewCommand extends Command {
    // Début d'une méthode/d'un bloc
    public AutoViewCommand() {
        // Accès à l'objet courant/parent
        super("autoview");

        // Modify viewable
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            final boolean autoView = context.get("value");
            // Appelle une méthode
            player.setAutoViewable(autoView);
            // Appelle une méthode
            player.sendMessage("Auto-viewable set to " + autoView);
        // Appelle une méthode
        }, Literal("viewable"), Boolean("value"));

        // Modify viewer
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            final boolean autoView = context.get("value");
            // Appelle une méthode
            player.setAutoViewEntities(autoView);
            // Appelle une méthode
            player.sendMessage("Auto-viewer set to " + autoView);
        // Appelle une méthode
        }, Literal("viewer"), Boolean("value"));

        // Modify viewable rule
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            EntityFinder finder = context.get("targets");
            // Appelle une méthode
            final List<Entity> entities = finder.find(sender);
            // Appelle une méthode
            player.updateViewableRule(entities::contains);
            // Appelle une méthode
            player.sendMessage("Viewable rule updated to see " + entities.size() + " players");
        // Appelle une méthode
        }, Literal("rule-viewable"), Entity("targets").onlyPlayers(true));

        // Modify viewer rule
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            EntityFinder finder = context.get("targets");
            // Appelle une méthode
            final List<Entity> entities = finder.find(sender);
            // Appelle une méthode
            player.updateViewerRule(entities::contains);
            // Appelle une méthode
            player.sendMessage("Viewer rule updated to see " + entities.size() + " entities");
        // Appelle une méthode
        }, Literal("rule-viewer"), Entity("targets"));

        // Remove viewable rule
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            player.updateViewableRule(p -> true);
            // Appelle une méthode
            player.sendMessage("Viewable rule removed");
        // Appelle une méthode
        }, Literal("remove-rule-viewable"));

        // Remove viewer rule
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            player.updateViewerRule(p -> true);
            // Appelle une méthode
            player.sendMessage("Viewer rule removed");
        // Appelle une méthode
        }, Literal("remove-rule-viewer"));

        // Update viewable rule
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            player.updateViewableRule();
            // Appelle une méthode
            player.sendMessage("Viewable rule updated");
        // Appelle une méthode
        }, Literal("update-rule-viewable"));

        // Update viewer rule
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            player.updateViewerRule();
            // Appelle une méthode
            player.sendMessage("Viewer rule updated");
        // Appelle une méthode
        }, Literal("update-rule-viewer"));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
