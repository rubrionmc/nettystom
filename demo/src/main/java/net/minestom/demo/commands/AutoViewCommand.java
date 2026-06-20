// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.utils.entity.EntityFinder;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Boolean;
// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.*;

// Type declaration (class/interface/enum/record)
public class AutoViewCommand extends Command {
    // Start of a method/block
    public AutoViewCommand() {
        // Access to the current/parent object
        super("autoview");

        // Modify viewable
        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            final boolean autoView = context.get("value");
            // Calls a method
            player.setAutoViewable(autoView);
            // Calls a method
            player.sendMessage("Auto-viewable set to " + autoView);
        // Calls a method
        }, Literal("viewable"), Boolean("value"));

        // Modify viewer
        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            final boolean autoView = context.get("value");
            // Calls a method
            player.setAutoViewEntities(autoView);
            // Calls a method
            player.sendMessage("Auto-viewer set to " + autoView);
        // Calls a method
        }, Literal("viewer"), Boolean("value"));

        // Modify viewable rule
        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            EntityFinder finder = context.get("targets");
            // Calls a method
            final List<Entity> entities = finder.find(sender);
            // Calls a method
            player.updateViewableRule(entities::contains);
            // Calls a method
            player.sendMessage("Viewable rule updated to see " + entities.size() + " players");
        // Calls a method
        }, Literal("rule-viewable"), Entity("targets").onlyPlayers(true));

        // Modify viewer rule
        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            EntityFinder finder = context.get("targets");
            // Calls a method
            final List<Entity> entities = finder.find(sender);
            // Calls a method
            player.updateViewerRule(entities::contains);
            // Calls a method
            player.sendMessage("Viewer rule updated to see " + entities.size() + " entities");
        // Calls a method
        }, Literal("rule-viewer"), Entity("targets"));

        // Remove viewable rule
        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            player.updateViewableRule(p -> true);
            // Calls a method
            player.sendMessage("Viewable rule removed");
        // Calls a method
        }, Literal("remove-rule-viewable"));

        // Remove viewer rule
        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            player.updateViewerRule(p -> true);
            // Calls a method
            player.sendMessage("Viewer rule removed");
        // Calls a method
        }, Literal("remove-rule-viewer"));

        // Update viewable rule
        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            player.updateViewableRule();
            // Calls a method
            player.sendMessage("Viewable rule updated");
        // Calls a method
        }, Literal("update-rule-viewable"));

        // Update viewer rule
        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            player.updateViewerRule();
            // Calls a method
            player.sendMessage("Viewer rule updated");
        // Calls a method
        }, Literal("update-rule-viewer"));
    // End of a block/expression
    }
// End of a block/expression
}
