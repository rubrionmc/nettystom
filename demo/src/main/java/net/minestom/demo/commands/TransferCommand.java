// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class TransferCommand extends Command {
    // Start of a method/block
    public TransferCommand() {
        // Access to the current/parent object
        super("transfer");

        // Calls a method
        var hostArgument = ArgumentType.String("host");
        // Calls a method
        var portArgument = ArgumentType.Integer("port");

        // Access to the current/parent object
        this.addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (sender instanceof Player player) {
                // Code statement
                player.getPlayerConnection().transfer(
                        // Code statement
                        context.get(hostArgument),
                        // Calls a method
                        context.get(portArgument));
            // Alternative branch of the condition
            } else {
                // Code statement
                sender.sendMessage(Component.text(
                        // Code statement
                        "You must be a player to use this command!",
                        // Code statement
                        NamedTextColor.RED));
            // End of a block/expression
            }
        // Code statement
        }, hostArgument, portArgument);
    // End of a block/expression
    }
// End of a block/expression
}
