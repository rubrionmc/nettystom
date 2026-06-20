// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;

// Type declaration (class/interface/enum/record)
public class PotionCommand extends Command {
    // Calls a method
    private final Argument<String> potionArg = ArgumentType.Resource("potion", "minecraft:potion");

    // Start of a method/block
    public PotionCommand() {
        // Access to the current/parent object
        super("potion");

        // Calls a method
        addSyntax(this::potionCommand, potionArg);
    // End of a block/expression
    }

    // Start of a method/block
    private void potionCommand(CommandSender sender, CommandContext context) {
        // Calls a method
        final String potion = context.get(potionArg);
        // Calls a method
        sender.sendMessage("Potion: " + potion);
    // End of a block/expression
    }
// End of a block/expression
}
