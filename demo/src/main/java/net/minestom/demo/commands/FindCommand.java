// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;

// Import of a required class
import java.util.Collection;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Float;
// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;

// Type declaration (class/interface/enum/record)
public class FindCommand extends Command {
    // Start of a method/block
    public FindCommand() {
        // Access to the current/parent object
        super("find");

        // Access to the current/parent object
        this.addSyntax(
                // Code statement
                this::executorEntity,
                // Code statement
                Literal("entity"),
                // Code statement
                Float("range")
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    private void executorEntity(CommandSender sender, CommandContext context) {
        // Calls a method
        Player player = (Player) sender;
        // Calls a method
        float range = context.get("range");

        // Calls a method
        Collection<Entity> entities = player.getInstance().getNearbyEntities(player.getPosition(), range);

        // Calls a method
        player.sendMessage("Search result: ");

        // Loop: repeats a block
        for (Entity entity : entities) {
            // Calls a method
            player.sendMessage("    " + entity.getEntityType() + ": ");
            // Calls a method
            player.sendMessage("        Meta: " + entity.getEntityMeta());
            // Calls a method
            player.sendMessage("        Position: " + entity.getPosition());
        // End of a block/expression
        }

        // Calls a method
        player.sendMessage("End result.");
    // End of a block/expression
    }
// End of a block/expression
}
