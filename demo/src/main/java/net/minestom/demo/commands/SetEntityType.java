// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class SetEntityType extends Command {
    // Calls a method
    private final ArgumentEntityType entityTypeArg = ArgumentType.EntityType("type");

    // Start of a method/block
    public SetEntityType() {
        // Access to the current/parent object
        super("setentitytype");

        // Calls a method
        addSyntax(this::execute, entityTypeArg);
    // End of a block/expression
    }

    // Start of a method/block
    private void execute(CommandSender sender, CommandContext context) {
        // Branch: checks a condition
        if (!(sender instanceof Player player)) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        var entityType = context.get(entityTypeArg);
        // Calls a method
        player.switchEntityType(entityType);
        // Calls a method
        player.sendMessage("set entity type to " + entityType);
    // End of a block/expression
    }
// End of a block/expression
}
